package com.sickfuture.letswatch.processor.tmdb.persons;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Cast;
import com.sickfuture.letswatch.bo.tmdb.Credits;
import com.sickfuture.letswatch.bo.tmdb.Crew;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

public class PersonCreditsProcessor implements
		IProcessor<InputStream, ContentValues[]> {

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
		java.util.List<Cast> cast = credits.getCast();
		long time = System.currentTimeMillis();
		int id = credits.getId();
		if (cast != null && cast.size() > 0) {
			ContentValues[] castsValues = new ContentValues[cast.size()];
			int i = 0;
			for (Cast c : cast) {
				castsValues[i] = ProcessorHelper.processCast(c);
				castsValues[i].put(Contract.CastColumns.LAST_UPDATE, time);
				castsValues[i].put(Contract.CastColumns.TMDB_PERSON_ID, id);
				i++;
			}
			ContextHolder
					.getInstance()
					.getContext()
					.getContentResolver()
					.bulkInsert(
							ContractUtils
									.getProviderUriFromContract(Contract.CastColumns.class),
							castsValues);
		}
		java.util.List<Crew> crew = credits.getCrew();
		if (crew != null && crew.size() > 0) {
			ContentValues[] crewValues = new ContentValues[crew.size()];
			int i = 0;
			for (Crew cr : crew) {
				crewValues[i] = ProcessorHelper.processCrew(cr);
				crewValues[i].put(Contract.CrewColumns.LAST_UPDATE, time);
				crewValues[i].put(Contract.CrewColumns.TMDB_PERSON_ID, id);
				i++;
			}
			ContextHolder
					.getInstance()
					.getContext()
					.getContentResolver()
					.bulkInsert(
							ContractUtils
									.getProviderUriFromContract(Contract.CrewColumns.class),
							crewValues);
		}

	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		return true;
	}

}
