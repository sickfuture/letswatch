package com.sickfuture.letswatch.app.fragment.dvd;

import java.io.InputStream;

import android.content.ContentValues;
import android.support.v4.widget.CursorAdapter;
import android.widget.AbsListView;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class UpcomingDvdFragment extends CommonMovieListFragment {

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	@Override
	protected int getSection() {
		return Contract.UPCOMING_DVD_SECTION;
	}

	@Override
	protected void loadData() {
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				getActivity().getString(R.string.API_UPCOMING_DVDS_REQUEST_URL));
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.UPCOMING_DVD_PROCESSOR_SERVICE,
				mResultReceiver);
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new BoxOfficeCursorAdapter(getActivity(), null);
	}

}
