package com.sickfuture.letswatch.app.fragment.theaters;

import java.io.InputStream;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.widget.AbsListView;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class OpeningFragment extends CommonMovieListFragment {

	public OpeningFragment() {
		super();
	}

	@Override
	protected Uri getUri() {
		return ContractUtils.getProviderUriFromContract(Contract.OpeningColumns.class);
	}

	@Override
	protected void loadData() {
		String url = MovieApis.RottenApi.openingMovies(0, null);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(url);
		request.setIsCacheable(true);
		SourceService
				.execute(getActivity(), request,
						LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
						LetsWatchApplication.OPENING_PROCESSOR_SERVICE,
						mResultReceiver);
	}

	@Override
	public void onScroll(AbsListView absListView, int i, int i2, int i3) {

	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new BoxOfficeCursorAdapter(getActivity(), null);
	}
}
