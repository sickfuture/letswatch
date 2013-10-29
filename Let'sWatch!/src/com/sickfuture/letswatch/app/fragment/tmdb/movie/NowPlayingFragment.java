package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import java.io.InputStream;
import java.util.Locale;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.NowPlayingTmdbColumns;

public class NowPlayingFragment extends CommonGridFragment {

	private static final String LOG_TAG = NowPlayingFragment.class
			.getSimpleName();

	private static final String[] sProjection = new String[] {
			MovieColumns._ID, MovieColumns.TMDB_ID, MovieColumns.TITLE,
			MovieColumns.TITLE_ORIGINAL, MovieColumns.POSTER_PATH,
			MovieColumns.VOTE_AVERAGE, NowPlayingTmdbColumns.MOVIE_TMDB_ID,
			NowPlayingTmdbColumns._ID };

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.NowPlayingTmdbColumns.class);
	}

	@Override
	protected void loadData(int page) {
		String url = MovieApis.TmdbApi.getNowPlayingMovies(Locale.getDefault()
				.getLanguage(), page);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_NOW_PLAYING_PROCESSOR_SERVICE,
				getResultReceiver());

	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).setTitle(getResources().getString(
				R.string.now_playing));
		super.onStart();
	}

	@Override
	protected String[] getProjection() {
		return null;
	}

	@Override
	protected String getSelection() {
		return null;
	}

	@Override
	protected String getPagingPrefsCurrKey() {
		return getActivity().getResources().getString(
				R.string.prefs_paging_now_playing_curr_page_count_key);
	}

	@Override
	protected String getPagingMaxPrefsKey() {
		return getActivity().getResources().getString(
				R.string.prefs_paging_now_playing_max_page_count_key);
	}

	@Override
	protected boolean hasPaging() {
		return true;
	}

}
