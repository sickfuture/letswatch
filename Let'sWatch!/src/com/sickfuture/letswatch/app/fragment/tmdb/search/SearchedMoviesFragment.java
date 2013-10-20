package com.sickfuture.letswatch.app.fragment.tmdb.search;

import java.io.InputStream;
import java.util.Locale;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.NetworkHelper;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.MoviePosterGridAdapter;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.SearchActivity;
import com.sickfuture.letswatch.app.activity.tmdb.MovieActivity;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public class SearchedMoviesFragment extends SickGridCursorFragment {

	private InputMethodManager mKeyboard;
	private SearchView mSearchView;
	
	private static final Uri sSearchedMoviesUri = ContractUtils
			.getProviderUriFromContract(Contract.SearchedMoviesColumns.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mKeyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		setHasOptionsMenu(true);
		search(getArguments());
	}

	private void search(Bundle arguments) {
		String query = arguments.getString(SearchManager.QUERY);
		loadInfo(query);
	}

	private void loadInfo(String query) {
		getActivity().getContentResolver().delete(sSearchedMoviesUri, null,
				null);
		if (NetworkHelper.checkConnection(getActivity())) {
			mSearchView.setQuery("", false);
			String url = TmdbApi.searchMovie(query, 0, Locale.getDefault()
					.getLanguage(), false, 0, 0, null);
			DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
					url);
			SourceService
					.execute(
							getActivity(),
							request,
							LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
							LetsWatchApplication.TMDB_SEARCHED_MOVIES_PROCESSOR_SERVICE,
							getResultReceiver());
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), sSearchedMoviesUri, null, null,
				null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		((CursorAdapter) getAdapter()).swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) getAdapter()).swapCursor(null);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Cursor c = (Cursor) adapterView.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(), MovieActivity.class);
		intent.putExtra(SearchActivity.SEARCHED_MOVIE_ID,
				c.getString(c.getColumnIndex(MovieColumns.TMDB_ID)));
		startActivity(intent);
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new MoviePosterGridAdapter(getActivity(), null);
	}

	@Override
	protected void start(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void error(Exception exception) {
		Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG)
				.show();

	}

	@Override
	protected void done(Bundle result) {
		// TODO Auto-generated method stub

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				query = query.trim().replace(" ", "+");
				if (TextUtils.isEmpty(query)) {
					return false;
				}
				mKeyboard.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
				loadInfo(query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (menu != null) {
			menu.removeItem(R.id.menu_refresh);
		}

	}

}
