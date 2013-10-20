package com.sickfuture.letswatch.processor.tmdb.movies;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.exceptions.BadRequestException;
import com.android.sickfuture.sickcore.source.IDataSource;
import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.android.sickfuture.sickcore.utils.StringsUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Cast;
import com.sickfuture.letswatch.bo.tmdb.Casts;
import com.sickfuture.letswatch.bo.tmdb.Crew;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;
import com.sickfuture.letswatch.processor.tmdb.persons.PersonProcessor;

public class CastsProcessor implements IProcessor<InputStream, ContentValues[]> {
	private static final String LOG_TAG = CastsProcessor.class.getSimpleName();

	private static final Uri CAST_URI = ContractUtils
			.getProviderUriFromContract(Contract.CastColumns.class);
	private static final Uri CREW_URI = ContractUtils
			.getProviderUriFromContract(Contract.CrewColumns.class);
	private static final Uri PERSON_URI = ContractUtils
			.getProviderUriFromContract(Contract.PersonColumns.class);

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_CASTS_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		Casts casts = gson.fromJson(new InputStreamReader(data), Casts.class);
		return processCasts(casts);
	}

	public ContentValues[] processCasts(Casts casts) {

		long time = System.currentTimeMillis();
		int id = casts.getId();
		java.util.List<Cast> cast = casts.getCast();
		ArrayList<ContentValues> castsValues = null;
		if (cast != null && cast.size() > 0) {
			castsValues = new ArrayList<ContentValues>(cast.size());
			for (Cast c : cast) {
				ContentValues castsValue = ProcessorHelper.processCast(c);
				if (castsValue == null)
					continue;
				castsValue.put(Contract.CastColumns.LAST_UPDATE, time);
				castsValue.put(Contract.CastColumns.TMDB_MOVIE_ID, id);
				castsValues.add(castsValue);
			}
		}
		java.util.List<Crew> crew = casts.getCrew();
		ArrayList<ContentValues> crewValues = null;
		if (crew != null && crew.size() > 0) {
			crewValues = new ArrayList<ContentValues>(crew.size());
			for (Crew cr : crew) {
				ContentValues crewValue = ProcessorHelper.processCrew(cr);
				if (crewValue == null)
					continue;
				crewValue.put(Contract.CrewColumns.LAST_UPDATE, time);
				crewValue.put(Contract.CrewColumns.TMDB_MOVIE_ID, id);
				crewValues.add(crewValue);
			}
		}
		processNewPersons(castsValues, crewValues);
		if (castsValues != null)
			// insertToDb(castsValues, CAST_URI);
			processNew(castsValues, id, CAST_URI, CastColumns.TMDB_MOVIE_ID,
					CastColumns.TMDB_PERSON_ID);
		if (crewValues != null)
			// insertToDb(crewValues, CREW_URI);
			processNew(crewValues, id, CREW_URI, CrewColumns.TMDB_MOVIE_ID,
					CrewColumns.TMDB_PERSON_ID);

		return null;
	}

	private void processNewPersons(ArrayList<ContentValues> castsValues,
			ArrayList<ContentValues> crewValues) {
		HashSet<Long> ids = new HashSet<Long>();
		if (castsValues != null) {
			for (ContentValues cv : castsValues) {
				ids.add(cv.getAsLong(CastColumns.TMDB_PERSON_ID));
			}
		}
		if (crewValues != null) {
			for (ContentValues cv : crewValues) {
				ids.add(cv.getAsLong(CastColumns.TMDB_PERSON_ID));
			}
		}
		loadNewPersons(ids);
	}

	@SuppressWarnings("unchecked")
	private void loadNewPersons(HashSet<Long> ids) {
		String idsStr = StringsUtils.join(ids, ",");
		Context context = ContextHolder.getInstance().getContext();
		Cursor cursor = context.getContentResolver().query(PERSON_URI, null,
				PersonColumns.TMDB_ID + " IN (" + idsStr + ")", null, null);
		try {
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					long pid = cursor.getLong(cursor
							.getColumnIndex(PersonColumns.TMDB_ID));
					ids.remove(pid);
					cursor.moveToNext();
				}
			}
		} finally {
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}

		if (ids.size() > 0) {
			PersonProcessor processor = (PersonProcessor) AppUtils.get(context,
					LetsWatchApplication.TMDB_PERSON_PROCESSOR_SERVICE);
			ArrayList<ContentValues> personValues = new ArrayList<ContentValues>();
			for (Long id : ids) {
				String url = TmdbApi.getPerson(String.valueOf(id));
				IDataSource<InputStream> dataSource = (IDataSource<InputStream>) AppUtils
						.get(context,
								LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY);
				try {
					personValues.add(processor.process(dataSource
							.getSource(url)));
				} catch (BadRequestException e) {
					L.d(LOG_TAG, "loadNewPersons: " + e.toString());
				}
			}
			context.getContentResolver()
					.bulkInsert(
							PERSON_URI,
							personValues.toArray(new ContentValues[personValues
									.size()]));
		}
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		return true;
	}

	protected void processNew(ArrayList<ContentValues> array, long id, Uri uri,
			String fieldToQuery, String fieldToCompare) {

		Cursor cursor = ContextHolder.getInstance().getContext()
				.getContentResolver()
				.query(uri, null, fieldToQuery + " = " + id, null, null);
		ArrayList<Long> toUpdate = new ArrayList<Long>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Long mid = cursor
						.getLong(cursor.getColumnIndex(fieldToCompare));
				toUpdate.add(mid);
				cursor.moveToNext();
			}
		}
		cursor.close();

		updateOldAndPutNew(array, toUpdate, uri, fieldToCompare);
	}

	protected void updateOldAndPutNew(ArrayList<ContentValues> array,
			ArrayList<Long> toUpdate, Uri uri, String fieldToCompare) {
		if (toUpdate.size() > 0) {
			ArrayList<ContentValues> forUpdate = new ArrayList<ContentValues>();
			for (Long mid : toUpdate) {
				ContentValues toRemove = null;
				for (ContentValues v : array) {
					int i = (Integer) v.get(fieldToCompare);
					if (i == mid) {
						forUpdate.add(v);
						toRemove = v;
						break;
					}
				}
				if (toRemove != null) {
					array.remove(toRemove);
				}
			}
			if (array.size() > 0)
				insertToDb(array, uri);
			// updateInDb(forUpdate, uri, fieldToCompare);
		} else {
			insertToDb(array, uri);
		}
	}

	protected void updateInDb(ArrayList<ContentValues> forUpdate, Uri uri,
			String fieldToCompare) {
		forUpdate.trimToSize();
		Context context = ContextHolder.getInstance().getContext();
		for (int i = 0; i < forUpdate.size(); i++) {
			String where = fieldToCompare + " = "
					+ forUpdate.get(i).getAsString(fieldToCompare);
			int x = context.getContentResolver().update(uri, forUpdate.get(i),
					where, null);
			L.d("LOG_TAG update", "updateInDb: " + x);
		}

	}

	protected void insertToDb(ArrayList<ContentValues> array, Uri uri) {
		ContentValues[] values = array.toArray(new ContentValues[array.size()]);
		Context context = ContextHolder.getInstance().getContext();
		int x = context.getContentResolver().bulkInsert(uri, values);
		L.d("LOG_TAG", "insertToDb: " + x);
	}

}
