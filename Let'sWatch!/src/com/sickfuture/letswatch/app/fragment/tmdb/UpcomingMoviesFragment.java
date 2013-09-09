package com.sickfuture.letswatch.app.fragment.tmdb;

import java.io.InputStream;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class UpcomingMoviesFragment extends CommonGridFragment {

	private static final String LOG_TAG = UpcomingMoviesFragment.class
			.getSimpleName();

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.UpcomingTmdbColumns.class);
	}

	@Override
	protected void loadData() {
		String url = MovieApis.TmdbApi.getUpcomingMovies(null, 0);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_UPCOMING_PROCESSOR_SERVICE,
				mResultReceiver);

	}

}
