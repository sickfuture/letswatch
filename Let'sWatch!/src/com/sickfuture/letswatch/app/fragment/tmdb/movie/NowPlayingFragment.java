package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import java.io.InputStream;

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
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonMovieGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class NowPlayingFragment extends CommonMovieGridFragment {

	private static final String LOG_TAG = NowPlayingFragment.class
			.getSimpleName();

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.NowPlayingTmdbColumns.class);
	}

	@Override
	protected void loadData() {
		String url = MovieApis.TmdbApi.getNowPlayingMovies(null, 0);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_NOW_PLAYING_PROCESSOR_SERVICE,
				mResultReceiver);

	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).setTitle(getResources().getString(
				R.string.now_playing));
		super.onStart();
	}

}
