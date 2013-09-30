package com.sickfuture.letswatch.processor.tmdb.search;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.SearchedMoviesColumns;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbMovieListProcessor;

public class SearchedMoviesProcessor extends TmdbMovieListProcessor {

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		Uri uri = ContractUtils.getProviderUriFromContract(Contract.SearchedMoviesColumns.class);
		context.getContentResolver().bulkInsert(uri, result);
		return true;
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_SEARCHED_MOVIES_PROCESSOR_SERVICE;
	}

	@Override
	public String getIdColumnName() {
		return SearchedMoviesColumns.MOVIE_ID;
	}

}
