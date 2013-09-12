package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import java.io.InputStream;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.MoviesGridCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonMovieGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.PopularTmdbColumns;

public class PopularMoviesFragment extends CommonMovieGridFragment {

	private static final String LOG_TAG = PopularMoviesFragment.class
			.getSimpleName();
	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(PopularTmdbColumns.class);
	}
	
	@Override
	protected void loadData() {
		String url = MovieApis.TmdbApi.getPopularMovies(null, 0);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_POPULAR_PROCESSOR_SERVICE, mResultReceiver);
	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).setTitle(
				getResources().getString(R.string.popular));
		super.onStart();
	}

//	private RefreshActionItem mRefreshActionItem;
//
//	@Override
//	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//	}
	
//	@Override
//	public CursorAdapter cursorAdapter() {
//		return new MoviesGridCursorAdapter(getActivity(), null);
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setHasOptionsMenu(true);
//	}
//
//	@Override
//	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//		return new CursorLoader(getActivity(), getUri(), null, null, null, null);
//	}
//
//	@Override
//	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//		if (data == null) {
//			loadData();
//		} else {
//			((CursorAdapter) mGridViewAdapter).swapCursor(data);
//		}
//
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> loader) {
//		((CursorAdapter) mGridViewAdapter).swapCursor(null);
//	}
//
//	@Override
//	protected void start(Bundle bundle) {
//		mRefreshActionItem.showProgress(true);
//	}
//
//	@Override
//	protected void error(Exception exception) {
//		Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT)
//				.show();
//		Log.d(LOG_TAG, "error: " + exception.toString());
//		mRefreshActionItem.showProgress(false);
//	}
//
//	@Override
//	protected void done(Bundle result) {
//		mRefreshActionItem.showProgress(false);
//	}
//
//	@Override
//	public void onPrepareOptionsMenu(Menu menu) {
//		MenuItem item = menu.findItem(R.id.menu_refresh);
//        mRefreshActionItem = (RefreshActionItem) MenuItemCompat.getActionView(item);
//        mRefreshActionItem.setMenuItem(item);
//        mRefreshActionItem.setProgressIndicatorType(ProgressIndicatorType.INDETERMINATE);
//        mRefreshActionItem.setRefreshActionListener(this);
//		super.onPrepareOptionsMenu(menu);
//	}
//
//	@Override
//	protected int fragmentResource() {
//		return R.layout.fragment_grid;
//	}
//
//	@Override
//	protected int gridViewResource() {
//		return R.id.grid_view_fragment_grid;
//	}
//
//	@Override
//	public void onListItemClick(AdapterView<?> list, View view, int position,
//			long id, IListClickable clickable) {
//		String key = Contract.MovieColumns.TMDB_ID;
//		Cursor cursor = (Cursor) list.getItemAtPosition(position);
//		long mid = cursor.getLong(cursor.getColumnIndex(key));
//		Bundle arguments = new Bundle();
//		arguments.putString(key, String.valueOf(mid));
//		clickable.onItemListClick(arguments);
//
//	}
//
//	@Override
//	public void onRefreshButtonClick(RefreshActionItem sender) {
//		loadData();
//		
//	}

}
