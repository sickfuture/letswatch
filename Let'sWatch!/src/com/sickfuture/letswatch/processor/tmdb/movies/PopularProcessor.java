package com.sickfuture.letswatch.processor.tmdb.movies;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class PopularProcessor extends TmdbMovieListProcessor {

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		Uri uri = ContractUtils.getProviderUriFromContract(Contract.PopularTmdbColumns.class);
		context.getContentResolver().bulkInsert(uri, result);
		return false;
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_POPULAR_PROCESSOR;
	}

}
