package com.sickfuture.letswatch.processor.tmdb.casts;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

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
import com.sickfuture.letswatch.bo.tmdb.Credits;
import com.sickfuture.letswatch.bo.tmdb.Crew;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbMovieProcessor;

public class PersonCreditsProcessor implements
		IProcessor<InputStream, ContentValues[]>, ICasts {

	private static final String LOG_TAG = PersonCreditsProcessor.class
			.getSimpleName();

	private static final Uri CAST_URI = ContractUtils
			.getProviderUriFromContract(Contract.CastColumns.class);
	private static final Uri CREW_URI = ContractUtils
			.getProviderUriFromContract(Contract.CrewColumns.class);
	private static final Uri MOVIE_URI = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_PERSON_CREDITS_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		Credits credits = gson.fromJson(new InputStreamReader(data),
				Credits.class);
		processCredits(credits);
		return null;
	}

	public void processCredits(Credits credits) {
		long time = System.currentTimeMillis();
		long id = credits.getId();

		java.util.List<Cast> cast = credits.getCast();
		ArrayList<ContentValues> castsValues = null;
		if (cast != null && cast.size() > 0) {
			castsValues = new ArrayList<ContentValues>(cast.size());
			for (Cast c : cast) {
				ContentValues castsValue = ProcessorHelper.processCast(c);
				if (castsValue == null)
					continue;
				castsValue.put(Contract.CastColumns.LAST_UPDATE, time);
				castsValue.put(Contract.CastColumns.TMDB_PERSON_ID, id);
				castsValues.add(castsValue);
			}
		}

		java.util.List<Crew> crew = credits.getCrew();
		ArrayList<ContentValues> crewValues = null;
		if (crew != null && crew.size() > 0) {
			crewValues = new ArrayList<ContentValues>(crew.size());
			for (Crew cr : crew) {
				ContentValues crewValue = ProcessorHelper.processCrew(cr);
				if (crewValue == null)
					continue;
				crewValue.put(Contract.CrewColumns.LAST_UPDATE, time);
				crewValue.put(Contract.CrewColumns.TMDB_PERSON_ID, id);
				crewValues.add(crewValue);
			}
		}

		processNewMovies(castsValues, crewValues);
		if (castsValues != null)
			processNew(
					castsValues,
					id,
					ContractUtils.getProviderUriFromContract(CastColumns.class),
					CastColumns.TMDB_PERSON_ID, CastColumns.TMDB_MOVIE_ID);
		if (crewValues != null)
			processNew(
					crewValues,
					id,
					ContractUtils.getProviderUriFromContract(CrewColumns.class),
					CrewColumns.TMDB_PERSON_ID, CrewColumns.TMDB_MOVIE_ID);

	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		return true;
	}

	private void processNewMovies(ArrayList<ContentValues> castsValues,
			ArrayList<ContentValues> crewValues) {
		HashSet<Long> ids = new HashSet<Long>();
		if (castsValues != null) {
			if (castsValues.size() > 0) {
				sResultBundle.putInt(RESULT_CAST, castsValues.size());
			}
			for (ContentValues cv : castsValues) {
				ids.add(cv.getAsLong(CastColumns.TMDB_MOVIE_ID));
			}
		}
		if (crewValues != null) {
			if (crewValues.size() > 0) {
				sResultBundle.putInt(RESULT_CREW, crewValues.size());
			}
			for (ContentValues cv : crewValues) {
				ids.add(cv.getAsLong(CastColumns.TMDB_MOVIE_ID));
			}
		}
		loadNewPersons(ids);
	}

	@SuppressWarnings("unchecked")
	private void loadNewPersons(HashSet<Long> ids) {
		String idsStr = StringsUtils.join(ids, ",");
		Context context = ContextHolder.getInstance().getContext();
		Cursor cursor = context.getContentResolver().query(MOVIE_URI, null,
				MovieColumns.TMDB_ID + " IN (" + idsStr + ")", null, null);
		try {
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					long mid = cursor.getLong(cursor
							.getColumnIndex(MovieColumns.TMDB_ID));
					ids.remove(mid);
					cursor.moveToNext();
				}
			}
		} finally {
			if (cursor != null && !cursor.isClosed())
				cursor.close();
		}

		TmdbMovieProcessor processor = (TmdbMovieProcessor) AppUtils.get(
				context, LetsWatchApplication.TMDB_MOVIE_PROCESSOR_SERVICE);
		ArrayList<ContentValues> movieValues = new ArrayList<ContentValues>();
		for (Long id : ids) {
			String url = TmdbApi.getMovie(String.valueOf(id), Locale
					.getDefault().getLanguage());
			IDataSource<InputStream> dataSource = (IDataSource<InputStream>) AppUtils
					.get(context,
							LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY);
			try {
				movieValues.add(processor.process(dataSource.getSource(url)));
			} catch (BadRequestException e) {
				L.d(LOG_TAG, "loadNewPersons: " + e.toString());
			}
		}
		context.getContentResolver().bulkInsert(MOVIE_URI,
				movieValues.toArray(new ContentValues[movieValues.size()]));
	}

	protected void processNew(ArrayList<ContentValues> array, long id, Uri uri,
			String field_person, String field_movie) {

		Cursor cursor = ContextHolder.getInstance().getContext()
				.getContentResolver()
				.query(uri, null, field_person + " = " + id, null, null);
		ArrayList<Long> toUpdate = new ArrayList<Long>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Long mid = cursor.getLong(cursor.getColumnIndex(field_movie));
				toUpdate.add(mid);
				cursor.moveToNext();
			}
		}
		cursor.close();

		updateOldAndPutNew(array, toUpdate, uri, field_movie);
	}

	protected void updateOldAndPutNew(ArrayList<ContentValues> array,
			ArrayList<Long> toUpdate, Uri uri, String field_movie) {
		if (toUpdate.size() > 0) {
			ArrayList<ContentValues> forUpdate = new ArrayList<ContentValues>();
			for (Long mid : toUpdate) {
				ContentValues toRemove = null;
				for (ContentValues v : array) {
					int i = (Integer) v.get(field_movie);
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
			// updateInDb(forUpdate, uri, field_movie);
		} else {
			insertToDb(array, uri);
		}
	}

	protected void updateInDb(ArrayList<ContentValues> forUpdate, Uri uri,
			String field_movie) {
		forUpdate.trimToSize();
		Context context = ContextHolder.getInstance().getContext();
		for (int i = 0; i < forUpdate.size(); i++) {
			String where = field_movie + " = "
					+ forUpdate.get(i).getAsString(field_movie);
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

	@Override
	public Bundle extraProcessingData() {
		return sResultBundle;
	}

}
