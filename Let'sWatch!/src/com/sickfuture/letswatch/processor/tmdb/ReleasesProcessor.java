package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Country;
import com.sickfuture.letswatch.bo.tmdb.Releases;
import com.sickfuture.letswatch.content.contract.Contract;

public class ReleasesProcessor implements
		IProcessor<InputStream, ContentValues[]> {

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_RELEASES_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		Releases releases = gson.fromJson(new InputStreamReader(data),
				Releases.class);
		return processReleases(releases);
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver()
				.bulkInsert(
						ContractUtils
								.getProviderUriFromContract(Contract.ReleasesColumns.class),
						result);
		return true;
	}

	public ContentValues[] processReleases(Releases releases) {
		if (releases.getCountries().size() == 0)
			return null;
		long id = releases.getId();
		ContentValues[] values = new ContentValues[releases.getCountries()
				.size()];
		int i = 0;
		for (Country country : releases.getCountries()) {
			values[i] = ProcessorHelper.processRelease(country);
			values[i].put(Contract.ReleasesColumns.MOVIE_TMDB_ID, id);
			i++;
		}
		return values;
	}
}
