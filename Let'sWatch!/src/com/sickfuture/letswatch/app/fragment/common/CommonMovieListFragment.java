package com.sickfuture.letswatch.app.fragment.common;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public abstract class CommonMovieListFragment extends SickCursorListFragment {

	private static final String LOG_TAG = CommonMovieListFragment.class
			.getSimpleName();

	private Uri mUri;

	public CommonMovieListFragment() {
		super();
	}

	protected abstract Uri getUri();

	protected abstract void loadData();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUri = getUri();

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), mUri, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		((CursorAdapter) getAdapter()).swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) getAdapter()).swapCursor(null);
	}

	// @Override
	// public void onRefresh(PullToRefreshBase<ListView> refreshView) {
	//
	// Log.d(LOG_TAG, "onRefresh: ");
	// if (InetChecker.checkInetConnection(getActivity())) {
	// getActivity().getContentResolver().delete(mUri, null, null);
	// loadData();
	// } else {
	// refreshView.onRefreshComplete();
	// }
	// }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	protected void start(Bundle bundle) {

	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		Bundle args = new Bundle();
		args.putInt(Contract.ID,
				cursor.getInt(cursor.getColumnIndex(MovieColumns.ROTTEN_ID)));
		((IListClickable) getActivity()).onItemListClick(args);
	}

	@Override
	protected void error(Exception exception) {
		// mListView.onRefreshComplete();
		Toast.makeText(getActivity(), exception.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void done(Bundle result) {
		// mListView.onRefreshComplete();
	}

	@Override
	protected int fragmentResource() {
		return R.layout.fragment_common;
	}

	@Override
	protected int adapterViewResource() {
		return R.id.list_view_common_fragment;
	}

	@Override
	protected int progressViewResource() {
		return R.id.progress_bar_common_fragment;
	}

}
