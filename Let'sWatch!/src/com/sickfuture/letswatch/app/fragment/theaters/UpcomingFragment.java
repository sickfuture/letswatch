package com.sickfuture.letswatch.app.fragment.theaters;

import java.io.InputStream;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.widget.AbsListView;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class UpcomingFragment extends CommonMovieListFragment {

	private static final String LOG_TAG = UpcomingFragment.class
			.getSimpleName();

	@Override
	protected Uri getUri() {
		return ContractUtils.getProviderUriFromContract(Contract.UpcomingColumns.class);
	}

	@Override
	protected void loadData() {
		String url = MovieApis.RottenApi.upcomingMovies(0, 0, null);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.UPCOMING_PROCESSOR_SERVICE,
				mResultReceiver);
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new BoxOfficeCursorAdapter(getActivity(), null);
	}

	@Override
	public void onScroll(AbsListView absListView, int i, int i2, int i3) {

	}
}
