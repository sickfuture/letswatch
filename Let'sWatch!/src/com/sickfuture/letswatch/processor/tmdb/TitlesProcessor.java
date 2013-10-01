package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.AltTitles;
import com.sickfuture.letswatch.bo.tmdb.Title;
import com.sickfuture.letswatch.content.contract.Contract;

public class TitlesProcessor implements
		IProcessor<InputStream, ContentValues[]> {

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_TITLES_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		AltTitles altTitles = gson.fromJson(new InputStreamReader(data),
				AltTitles.class);
		return processTitles(altTitles);
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver()
				.bulkInsert(
						ContractUtils
								.getProviderUriFromContract(Contract.AltTitlesColumns.class),
						result);
		return true;
	}

	public ContentValues[] processTitles(AltTitles altTitles) {
		if (altTitles.getTitles().size() == 0) {
			return null;
		}
		ContentValues[] values = new ContentValues[altTitles.getTitles().size()];
		int i = 0;
		for (Title title : altTitles.getTitles()) {
			values[i] = ProcessorHelper.processTitle(title);
			values[i].put(Contract.AltTitlesColumns.TMDB_MOVIE_ID, altTitles.getId());
			i++;
		}
		return values;
	}
}
