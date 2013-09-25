package com.sickfuture.letswatch.app.fragment.tmdb.common;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.utils.NetworkHelper;
import com.android.sickfuture.sickcore.utils.NetworkHelper.NetworkCallback;
import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.MoviePosterGridAdapter;
import com.sickfuture.letswatch.adapter.tmdb.MoviesGridCursorAdapter;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public abstract class CommonGridFragment extends SickGridCursorFragment
		implements RefreshActionListener {

	private static final String LOG_TAG = CommonGridFragment.class
			.getSimpleName();
	
	private RefreshActionItem mRefreshActionItem;

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
	}

	protected abstract Uri getUri();

	protected abstract void loadData();

	@Override
	public CursorAdapter cursorAdapter() {
		return new MoviePosterGridAdapter(getActivity(), null);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), getUri(), getProjection(), getSelection(), null, null);
	}

	protected abstract String[] getProjection();

	protected abstract String getSelection();

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data.getCount() == 0) {
			if (NetworkHelper.checkConnection(getActivity())) {
				loadData();
			}
		} else {
			((CursorAdapter) getAdapter()).swapCursor(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) getAdapter()).swapCursor(null);
	}

	@Override
	protected void start(Bundle bundle) {
		mRefreshActionItem.showProgress(true);
	}

	@Override
	protected void error(Exception exception) {
		Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT)
				.show();
		Log.d(LOG_TAG, "error: " + exception.toString());
		mRefreshActionItem.showProgress(false);
	}

	@Override
	protected void done(Bundle result) {
		mRefreshActionItem.showProgress(false);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.menu_refresh);
		mRefreshActionItem = (RefreshActionItem) MenuItemCompat
				.getActionView(item);
		mRefreshActionItem.setMenuItem(item);
		mRefreshActionItem
				.setProgressIndicatorType(ProgressIndicatorType.INDETERMINATE);
		mRefreshActionItem.setRefreshActionListener(this);
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected int fragmentResource() {
		return R.layout.fragment_grid_posters;
	}

	@Override
	protected int adapterViewResource() {
		return R.id.grid_view_fragment_grid_posters;
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		String key = Contract.MovieColumns.TMDB_ID;
		Cursor cursor = (Cursor) list.getItemAtPosition(position);
		long mid = cursor.getLong(cursor.getColumnIndex(key));
		Bundle args = new Bundle();
		args.putString(key, String.valueOf(mid));
		((IListClickable) getActivity()).onItemListClick(args);

	}

	@Override
	public void onRefreshButtonClick(RefreshActionItem sender) {
		NetworkHelper.checkAndConnect(getActivity(), new NetworkCallback() {

			@Override
			public void processTask(Context context) {
				loadData();
			}

			@Override
			public void onError(Context context, Exception e) {

			}

			@Override
			public void onCancel(Context context) {

			}
		});
	}

}
