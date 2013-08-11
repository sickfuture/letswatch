package com.sickfuture.letswatch.app.fragment.theaters;

import java.io.InputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class BoxOfficeFragment extends CommonMovieListFragment {

	private static final String LOG_TAG = BoxOfficeFragment.class
			.getSimpleName();

	public BoxOfficeFragment() {
		super();
	}

	@Override
	protected Uri getUri() {
		return ContractUtils.getProviderUriFromContract(Contract.BoxOfficeColumns.class);
	}

	@Override
	protected void loadData() {
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				getActivity().getString(R.string.API_BOX_OFFICE_REQUEST_URL));
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.BOX_OFFICE_PROCESSOR_SERVICE,
				mResultReceiver);
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new BoxOfficeCursorAdapter(getActivity(), null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d(LOG_TAG, "onCreateView: ");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.d(LOG_TAG, "onCreate: ");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {

		Log.d(LOG_TAG, "onAttach: ");
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {

		Log.d(LOG_TAG, "onDetach: ");
		super.onDetach();
	}

	@Override
	public void onDestroy() {

		Log.d(LOG_TAG, "onDestroy: ");
		super.onDestroy();
	}

	@Override
	public void onPause() {

		Log.d(LOG_TAG, "onPause: ");
		super.onPause();
	}

	@Override
	public void onResume() {

		Log.d(LOG_TAG, "onResume: ");
		super.onResume();
	}

	@Override
	public void onStart() {

		Log.d(LOG_TAG, "onStart: ");
		super.onStart();
	}

	@Override
	public void onStop() {

		Log.d(LOG_TAG, "onStop: ");
		super.onStop();
	}

	@Override
	public void onScroll(AbsListView absListView, int i, int i2, int i3) {

	}
}
