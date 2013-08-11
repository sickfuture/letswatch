package com.sickfuture.letswatch.app.fragment.dvd;

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
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class TopRentalsFragment extends CommonMovieListFragment {

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	protected Uri getUri() {
		return ContractUtils.getProviderUriFromContract(Contract.TopRentalsColumns.class);
	}

	@Override
	protected void loadData() {
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				getActivity().getString(R.string.API_TOP_RENTALS_REQUEST_URL));
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TOP_RENTALS_PROCESSOR_SERVICE,
				mResultReceiver);
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new BoxOfficeCursorAdapter(getActivity(), null);
	}

}
