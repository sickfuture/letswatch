package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Cast;
import com.sickfuture.letswatch.bo.tmdb.Casts;
import com.sickfuture.letswatch.bo.tmdb.Crew;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;

public class CastsProcessor implements IProcessor<InputStream, ContentValues[]> {
	private static final String LOG_TAG = CastsProcessor.class.getSimpleName();

	private static final Uri CAST_URI = ContractUtils
			.getProviderUriFromContract(Contract.CastColumns.class);
	private static final Uri CREW_URI = ContractUtils
			.getProviderUriFromContract(Contract.CrewColumns.class);

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
		ContentValues[] valuesCrew = null;
		if (casts.getCrew() != null && casts.getCrew().size() != 0) {
			valuesCrew = new ContentValues[casts.getCrew().size()];
			int i = 0;
			for (Crew crew : casts.getCrew()) {
				valuesCrew[i] = ProcessorHelper.processCrew(crew);
				valuesCrew[i].put(Contract.CrewColumns.TMDB_MOVIE_ID, id);
				valuesCrew[i].put(Contract.CrewColumns.LAST_UPDATE, time);
				i++;
			}
		}
		if (casts.getCast() == null || casts.getCast().size() == 0) {
			return null;
		}
		ContentValues[] values = new ContentValues[casts.getCast().size()];
		int i = 0;
		for (Cast cast : casts.getCast()) {
			values[i] = ProcessorHelper.processCast(cast);
			values[i].put(Contract.CastColumns.TMDB_MOVIE_ID, id);
			values[i].put(Contract.CastColumns.LAST_UPDATE, time);
			i++;
		}
		if (valuesCrew != null) {
			ContextHolder.getInstance().getContext().getContentResolver()
					.bulkInsert(CREW_URI, valuesCrew);
		}
		return values;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver().bulkInsert(CAST_URI, result);
		return true;
	}

}
