package com.sickfuture.letswatch.processor.tmdb.movies;

import com.android.sickfuture.sickcore.preference.PreferencesHelper;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.NowPlayingTmdbColumns;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class TmdbNowPlayingProcessor extends TmdbMovieListProcessor {

	private static final String LOG_TAG = TmdbNowPlayingProcessor.class
			.getSimpleName();
	
	@Override
	public boolean cache(ContentValues[] result, Context context) {
		Uri uri = ContractUtils
				.getProviderUriFromContract(Contract.NowPlayingTmdbColumns.class);
		context.getContentResolver().bulkInsert(uri, result);
		String prefName = context.getResources().getString(
				R.string.prefs_paging_name);
		PreferencesHelper.putInt(
				context,
				prefName,
				context.getResources().getString(
						R.string.prefs_paging_now_playing_curr_page_count_key),
				getCurrPage());
		PreferencesHelper.putInt(
				context,
				prefName,
				context.getResources().getString(
						R.string.prefs_paging_now_playing_max_page_count_key),
				getTotalPages());
		return true;
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_NOW_PLAYING_PROCESSOR_SERVICE;
	}

	@Override
	public String getIdColumnName() {
		return NowPlayingTmdbColumns.MOVIE_TMDB_ID;
	}

}
