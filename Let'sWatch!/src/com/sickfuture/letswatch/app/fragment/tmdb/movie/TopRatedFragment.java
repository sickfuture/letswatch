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
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonMovieGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class TopRatedFragment extends CommonMovieGridFragment {

	private static final String LOG_TAG = TopRatedFragment.class
			.getSimpleName();

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.TopRatedTmdbColumns.class);
	}

	@Override
	protected void loadData() {
		String url = MovieApis.TmdbApi.getTopRatedMovies(Locale.getDefault()
				.getLanguage(), 0);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_TOP_RATED_PROCESSOR_SERVICE,
				mResultReceiver);

	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).setTitle(
				getResources().getString(R.string.top_rated));
		super.onStart();
	}


}
