package com.sickfuture.letswatch.app.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.sickfuture.sickcore.db.BaseColumns;
import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.android.sickfuture.sickcore.service.CommonService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.activity.MainActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.request.LoadingRequest;
import com.sickfuture.letswatch.request.LoadingRequest.RequestHelper;
import com.sickfuture.letswatch.service.LoadingService;

public class BoxOfficeFragment extends SherlockFragment implements
		OnRefreshListener<ListView>, LoaderCallbacks<Cursor>,
		OnItemClickListener {

	private PullToRefreshListView mListView;

	private BoxOfficeCursorAdapter mBoxOfficeCursorAdapter;

	private BroadcastReceiver mBroadcastReceiver;

	private IListClickable mCallback;

	private final Uri mUri = ContractUtils.getProviderUriFromContract(Contract.MovieColumns.class);

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof IListClickable)) {
			throw new IllegalArgumentException(
					"Activity must implements IListClickable");
		}
		mCallback = (IListClickable) activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_box_office, null);
		mListView = (PullToRefreshListView) rootView
				.findViewById(R.id.box_office_pull_refresh_list);
		mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(CommonService.ACTION_ON_ERROR)) {
					mListView.onRefreshComplete();
					Toast.makeText(
							getSherlockActivity(),
							intent.getStringExtra(CommonService.EXTRA_KEY_MESSAGE),
							Toast.LENGTH_SHORT).show();
				} else if (action.equals(CommonService.ACTION_ON_SUCCESS)) {
					mListView.onRefreshComplete();
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(CommonService.ACTION_ON_ERROR);
		filter.addAction(CommonService.ACTION_ON_SUCCESS);
		getActivity().registerReceiver(mBroadcastReceiver, filter);
		mBoxOfficeCursorAdapter = new BoxOfficeCursorAdapter(
				getSherlockActivity(), null);
		mListView.setAdapter(mBoxOfficeCursorAdapter);
		mListView.setOnRefreshListener(this);
		mListView.setOnItemClickListener(this);
		getSherlockActivity().getSupportLoaderManager().initLoader(0, null,
				this);
		return rootView;
	}

	@Override
	public void onDestroy() {
		getSherlockActivity().unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		if (InetChecker.checkInetConnection(getSherlockActivity())) {
			getSherlockActivity().getContentResolver()
					.delete(mUri,
							MovieColumns.SECTION + " = ?",
							new String[] { String
									.valueOf(Contract.BOX_OFFICE_SECTION) });
			Intent intent = new Intent(getSherlockActivity(),
					LoadingService.class);
			LoadingRequest request = new LoadingRequest(RequestType.GET,
					getString(R.string.API_BOX_OFFICE_REQUEST_URL),
					Contract.BOX_OFFICE_SECTION,
					RequestHelper.PROCESS_MOVIE_LIST,
					mUri);
			intent.putExtra("request", request);
			getSherlockActivity().startService(intent);
		} else {
			refreshView.onRefreshComplete();
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(getSherlockActivity(), mUri, null,
				Contract.MovieColumns.SECTION + " = ?",
				new String[] { String.valueOf(Contract.BOX_OFFICE_SECTION) }, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		/*
		 * if (cursor.getCount() == 0) { onRefresh(mListView); }
		 */
		mBoxOfficeCursorAdapter.swapCursor(cursor);
		mListView.onRefreshComplete();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mBoxOfficeCursorAdapter.swapCursor(null);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		Bundle arguments = new Bundle();
		arguments.putInt(Contract.SECTION, Contract.BOX_OFFICE_SECTION);
		arguments.putInt(Contract.ID,
				cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
		arguments
				.putInt(MainActivity.FRAGMENT, MainActivity.BOXOFFICE_FRAGMENT);
		mCallback.onItemListClick(arguments);

	}

}
