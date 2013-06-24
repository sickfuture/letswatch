package com.sickfuture.letswatch.app.fragment.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.MainActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.content.contract.Contract;

public class CommonListFragment extends SherlockFragment implements
		LoaderCallbacks<Cursor>, OnRefreshListener<ListView>, OnScrollListener,
		OnItemClickListener {

	private static final String LOG_TAG = CommonListFragment.class
			.getSimpleName();

	private int mResourse, mLoaderId, mSectionMarker;
	private PullToRefreshListView mListView;
	private IListClickable mClickable;
	private Uri mUri = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);
	private BroadcastReceiver mBroadcastReceiver;

	// private common adapter

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoaderId = getClass().hashCode();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(mResourse, null);
		// mListView = (ListView)
		// view.findViewById(R.id.list_view_common_fragment);
		// mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof IListClickable))
			throw new IllegalArgumentException(
					"Activity must implements IListClickable");
		mClickable = (IListClickable) activity;
		super.onAttach(activity);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		Bundle arguments = new Bundle();
		arguments.putInt(Contract.SECTION, mSectionMarker);
		arguments
				.putInt(Contract.ID, cursor.getInt(cursor
						.getColumnIndex(Contract.MovieColumns._ID)));
		// arguments.putInt(MainActivity.FRAGMENT, MainActivity.UPCOM_FRAGMENT);
		mClickable.onItemListClick(arguments);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

}
