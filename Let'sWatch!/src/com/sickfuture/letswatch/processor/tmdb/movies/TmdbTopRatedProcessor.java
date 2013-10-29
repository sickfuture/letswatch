package com.sickfuture.letswatch.processor.tmdb.movies;

import com.android.sickfuture.sickcore.preference.PreferencesHelper;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.TopRatedTmdbColumns;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class TmdbTopRatedProcessor extends TmdbMovieListProcessor {

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		Uri uri = ContractUtils.getProviderUriFromContract(Contract.TopRatedTmdbColumns.class);
		context.getContentResolver().bulkInsert(uri, result);
		String prefName = context.getResources().getString(
				R.string.prefs_paging_name);
		PreferencesHelper.putInt(
				context,
				prefName,
				context.getResources().getString(
						R.string.prefs_paging_top_rat_curr_page_count_key),
				getCurrPage());
		PreferencesHelper.putInt(
				context,
				prefName,
				context.getResources().getString(
						R.string.prefs_paging_top_rat_max_page_count_key),
				getTotalPages());
		return true;
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_TOP_RATED_PROCESSOR_SERVICE;
	}

	@Override
	public String getIdColumnName() {
		return TopRatedTmdbColumns.MOVIE_TMDB_ID;
	}

}
