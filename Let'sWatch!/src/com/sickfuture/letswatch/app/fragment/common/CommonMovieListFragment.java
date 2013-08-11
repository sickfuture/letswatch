package com.sickfuture.letswatch.app.fragment.common;

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

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public abstract class CommonMovieListFragment extends SickCursorListFragment {

	private static final String LOG_TAG = CommonMovieListFragment.class
			.getSimpleName();

	private Uri mUri;
	// private int mSection;
	private SickImageLoader mImageLoader;

	public CommonMovieListFragment() {
		super();
	}

	protected abstract Uri getUri();

	protected abstract void loadData();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// mSection = getSection();
		mUri = getUri();
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), mUri, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		((CursorAdapter) mListViewAdapter).swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) mListViewAdapter).swapCursor(null);

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {

		Log.d(LOG_TAG, "onRefresh: ");
		if (InetChecker.checkInetConnection(getActivity())) {
			getActivity().getContentResolver().delete(mUri, null, null);
			loadData();
		} else {
			refreshView.onRefreshComplete();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
			mImageLoader.setPauseWork(true);
		} else {
			mImageLoader.setPauseWork(false);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageLoader.setPauseWork(false);
	}

	@Override
	public void onListItemClick(AdapterView<?> list, View view, int position,
			long id, IListClickable clickable) {
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		Bundle arguments = new Bundle();
		arguments.putInt(Contract.ID,
				cursor.getInt(cursor.getColumnIndex(MovieColumns.MOVIE_ID)));
//		arguments.putInt(MainActivity.FRAGMENT, mSection);
//		clickable.onItemListClick(arguments);

	}

	@Override
	protected void start(Bundle bundle) {

	}

	@Override
	protected void error(Exception exception) {
		mListView.onRefreshComplete();
		Toast.makeText(getActivity(), exception.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void done(Bundle result) {
		mListView.onRefreshComplete();
	}

}
