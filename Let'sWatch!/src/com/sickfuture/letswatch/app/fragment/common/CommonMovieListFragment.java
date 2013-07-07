package com.sickfuture.letswatch.app.fragment.common;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.db.BaseColumns;
import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.MainActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.request.LoadingRequest;
import com.sickfuture.letswatch.request.LoadingRequest.RequestHelper;
import com.sickfuture.letswatch.service.LoadingService;

public class CommonMovieListFragment extends SickCursorListFragment {

	private static final String LOG_TAG = CommonMovieListFragment.class
			.getSimpleName();
	
	private final Uri mUri = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);
	private CursorAdapter mAdapter;
	private int mSection;
	private int mUrlResource;

	
	public CommonMovieListFragment(CursorAdapter adapter, int section, int urlResource) {
		super();
		mAdapter = adapter;
		mSection = section;
		mUrlResource = urlResource;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getSherlockActivity(), mUri, null,
				Contract.MovieColumns.SECTION + " = ?",
				new String[] { String.valueOf(mSection) },
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		
		Log.d(LOG_TAG, "onRefresh: ");
		if (InetChecker.checkInetConnection(getSherlockActivity())) {
			getSherlockActivity().getContentResolver()
					.delete(mUri,
							MovieColumns.SECTION + " = ?",
							new String[] { String
									.valueOf(mSection) });
			Intent intent = new Intent(getSherlockActivity(),
					LoadingService.class);
			LoadingRequest request = new LoadingRequest(RequestType.GET,
					getString(mUrlResource),
					mSection,
					RequestHelper.PROCESS_MOVIE_LIST, mUri);
			intent.putExtra("request", request);
			getSherlockActivity().startService(intent);
		} else {
			refreshView.onRefreshComplete();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public CursorAdapter cursorAdapter() {
		return mAdapter;
	}

	@Override
	public void onListItemClick(AdapterView<?> list, View view, int position,
			long id, IListClickable clickable) {
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		Bundle arguments = new Bundle();
		arguments.putInt(Contract.SECTION, mSection);
		arguments.putInt(Contract.ID,
				cursor.getInt(cursor.getColumnIndex(MovieColumns.MOVIE_ID)));
		arguments
				.putInt(MainActivity.FRAGMENT, mSection);
		clickable.onItemListClick(arguments);

	}

	@Override
	public IntentFilter filter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(LoadingService.ACTION_ON_ERROR);
		filter.addAction(LoadingService.ACTION_ON_SUCCESS);
		return filter;
	}

	@Override
	public void handleOnRecieve(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(LoadingService.ACTION_ON_ERROR)) {
			mListView.onRefreshComplete();
			Toast.makeText(getSherlockActivity(),
					intent.getStringExtra(LoadingService.EXTRA_KEY_MESSAGE),
					Toast.LENGTH_SHORT).show();
		} else if (action.equals(LoadingService.ACTION_ON_SUCCESS)) {
			mListView.onRefreshComplete();
		}
	}

}
