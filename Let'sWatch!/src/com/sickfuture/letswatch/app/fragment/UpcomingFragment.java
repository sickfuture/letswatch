package com.sickfuture.letswatch.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.android.sickfuture.sickcore.utils.ui.ViewHider;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.UpcomingCursorAdapter;
import com.sickfuture.letswatch.app.activity.MainActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.SickCursorListFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.request.LoadingRequest;
import com.sickfuture.letswatch.request.LoadingRequest.RequestHelper;
import com.sickfuture.letswatch.service.LoadingService;

public class UpcomingFragment extends SickCursorListFragment  {

	private static final String LOG_TAG = UpcomingFragment.class.getSimpleName();

	private UpcomingCursorAdapter mUpcomingCursorAdapter;

	private final Uri mUri = ContractUtils.getProviderUriFromContract(Contract.MovieColumns.class);

	private View mViewLoading;
	private boolean mViewLoadingHidden, mLoading = true;
	private SharedPreferences mPreferences;
	private static int PAGINATION = R.string.pagination;
	private static final String URL = "url";

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d(LOG_TAG, "onRefresh");
		if (InetChecker.checkInetConnection(getSherlockActivity())) {
			getSherlockActivity().getContentResolver()
			.delete(mUri,
					MovieColumns.SECTION + " = ?",
					new String[] { String
							.valueOf(Contract.UPCOMING_SECTION) });
			Intent intent = new Intent(getSherlockActivity(),
					LoadingService.class);
			LoadingRequest request = new LoadingRequest(RequestType.GET,
					getString(R.string.API_UPCOMING_REQUEST_URL),
					Contract.UPCOMING_SECTION,
					RequestHelper.PROCESS_MOVIE_LIST,
					mUri);
			intent.putExtra("request", request);
			load(intent);
		} else {
			Log.i(LOG_TAG, "onRefreshComplete");
			mListView.onRefreshComplete();
		}
	}

	private void load(Intent intent) {
		getSherlockActivity().startService(intent);
		mLoading = true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getSherlockActivity(),
				mUri, null,
				Contract.MovieColumns.SECTION + " = ?",
				new String[] { String.valueOf(Contract.UPCOMING_SECTION) },
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		/*
		 * if (cursor.getCount() == 0) { onRefresh(null); }
		 */
		mUpcomingCursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mUpcomingCursorAdapter.swapCursor(null);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		// TODO refactor
		
//		if (visibleItemCount > 0
//				&& firstVisibleItem + visibleItemCount + 3 >= totalItemCount
//				&& InetChecker
//						.checkInetConnection(getSherlockActivity(), false)) {
//			if (!mLoading) {
//				Log.d(LOG_TAG, "onScroll load");
//				mPreferences = getSherlockActivity().getSharedPreferences(
//						getString(PAGINATION), Context.MODE_PRIVATE);
//				String nextPage = mPreferences.getString(
//						UpcomingService.NEXT_UPCOMING, null);
//				if (!TextUtils.isEmpty(nextPage)) {
//					if (mViewLoadingHidden)
//						hideViewLoading(false);
//					Log.d(LOG_TAG, "next = " + nextPage);
//					Intent intent = new Intent(getSherlockActivity(),
//							UpcomingService.class);
//					intent.putExtra(URL, nextPage);
//					load(intent);
//				} else {
//					if (!mViewLoadingHidden)
//						hideViewLoading(true);
//				}
//			}
//		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	private void hideViewLoading(boolean hide) {
		mViewLoadingHidden = hide;
		ViewHider.hideListItem(mViewLoading, hide);
	}

	@Override
	public CursorAdapter cursorAdapter() {
		mUpcomingCursorAdapter = new UpcomingCursorAdapter(
				getSherlockActivity(), null);
		return mUpcomingCursorAdapter;
	}

	@Override
	public void onListItemClick(AdapterView<?> list, View view, int position,
			long id, IListClickable clickable) {
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		Bundle arguments = new Bundle();
		arguments.putInt(Contract.SECTION, Contract.UPCOMING_SECTION);
		arguments
				.putInt(Contract.ID, cursor.getInt(cursor
						.getColumnIndex(Contract.MovieColumns._ID)));
		arguments.putInt(MainActivity.FRAGMENT, MainActivity.FRAGMENT_UPCOMING);
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
			Toast.makeText(
					getSherlockActivity(),
					intent.getStringExtra(LoadingService.EXTRA_KEY_MESSAGE),
					Toast.LENGTH_SHORT).show();
			mLoading = false;
		} else if (action.equals(LoadingService.ACTION_ON_SUCCESS)) {
			mListView.onRefreshComplete();
			mLoading = false;
		}
	}

}
