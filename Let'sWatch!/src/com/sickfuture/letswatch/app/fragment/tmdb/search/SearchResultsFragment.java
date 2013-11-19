package com.sickfuture.letswatch.app.fragment.tmdb.search;

import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.sickfuture.letswatch.adapter.tmdb.SearchedItemsAdapter;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.SearchActivity;
import com.sickfuture.letswatch.app.activity.tmdb.MovieActivity;
import com.sickfuture.letswatch.app.activity.tmdb.PeopleActivity;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class SearchResultsFragment extends SickGridCursorFragment {

	private static final int RES_PROGRESS_BAR = R.id.progress_bar_fragment_grid_posters;
	private static final int RES_ADAPTER = R.id.grid_view_fragment_grid_posters;
	private static final int RES_FRAGMENT = R.layout.fragment_grid_posters;
	private static final Uri sSearchedMoviesUri = ContractUtils
			.getProviderUriFromContract(Contract.SearchedMoviesColumns.class);
	private static final Uri sSearchedPersonsUri = ContractUtils
			.getProviderUriFromContract(Contract.SearchedPersonsColumns.class);

	private InputMethodManager mKeyboard;
	private SearchView mSearchView;

	public int mCurrSearchType;
	private Uri mCurrentUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mKeyboard = (InputMethodManager) getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE);
		setHasOptionsMenu(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		search(getArguments());
	}

	// TODO check
	public void search(Bundle args) {
		mCurrSearchType = args.getInt(SearchActivity.SEARCH_TYPE);
		String query = args.getString(SearchManager.QUERY);
		if (mCurrSearchType == SearchActivity.CHOSEN) {
			mCurrSearchType = SearchActivity.MOVIE;
			mSearchCallback.showSearchMenu(true);
		}
		if (TextUtils.isEmpty(query)) {
			getLoaderManager().restartLoader(getLoaderId(), args, this);
		} else {
			loadInfo(query, args.getInt(SearchActivity.SEARCH_TYPE));
		}
	}

	private void loadInfo(final String query, final int type) {

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				getActivity().getContentResolver().delete(mCurrentUri, null,
						null);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				if (NetworkHelper.checkConnection(getActivity())) {
					showProgress(true);
					mSearchView.setQuery("", false);
					String url = null;
					String proc = null;
					switch (type) {
					case SearchActivity.MOVIE:
						url = TmdbApi.searchMovie(query, 0, Locale.getDefault()
								.getLanguage(), false, 0, 0, null);
						proc = LetsWatchApplication.TMDB_SEARCHED_MOVIES_PROCESSOR_SERVICE;
						break;
					case SearchActivity.PERSON:
						url = TmdbApi.searchPerson(query, 0, true, null);
						proc = LetsWatchApplication.TMDB_SEARCHED_PERSONS_PROCESSOR_SERVICE;
						break;
					case SearchActivity.COLLECTION:
						break;
					case SearchActivity.COMPANY:
						break;
					case SearchActivity.LIST:
						break;
					case SearchActivity.TV:
						break;
					default:
						break;
					}
					DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
							url);
					SourceService.execute(getActivity(), request,
							LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
							proc, getResultReceiver());
				}
			}

		}.execute();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (mCurrSearchType < 0) {
			return null;
		}
		switch (mCurrSearchType) {
		case SearchActivity.MOVIE:
			mCurrentUri = sSearchedMoviesUri;
			break;
		case SearchActivity.PERSON:
			mCurrentUri = sSearchedPersonsUri;
			break;
		case SearchActivity.COLLECTION:
		case SearchActivity.COMPANY:
		case SearchActivity.LIST:
		case SearchActivity.TV:
			return null;
		default:
			break;
		}
		return new CursorLoader(getActivity(), mCurrentUri, null, null, null,
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		showProgress(false);
		((CursorAdapter) getAdapter()).swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) getAdapter()).swapCursor(null);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO make paging

	}

	// FIXME refine to new logic
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Cursor c = (Cursor) adapterView.getItemAtPosition(position);
		Intent intent = null;
		switch (mCurrSearchType) {
		case SearchActivity.MOVIE:
			intent = new Intent(getActivity(), MovieActivity.class);
			intent.putExtra(SearchActivity.SEARCHED_MOVIE_ID,
					c.getString(c.getColumnIndex(MovieColumns.TMDB_ID)));
			startActivity(intent);
			break;
		case SearchActivity.PERSON:
			intent = new Intent(getActivity(), PeopleActivity.class);
			intent.putExtra(SearchActivity.SEARCHED_PERSON_ID,
					c.getString(c.getColumnIndex(PersonColumns.TMDB_ID)));
			startActivity(intent);
			break;
		default:
			break;
		}
		
		
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new SearchedItemsAdapter(getActivity(), null);
	}

	@Override
	protected void start(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void error(Exception exception) {
		showProgress(false);
		Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG)
				.show();

	}

	@Override
	protected void done(Bundle result) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int fragmentResource() {
		return RES_FRAGMENT;
	}

	@Override
	protected int adapterViewResource() {
		return RES_ADAPTER;
	}

	@Override
	protected int progressViewResource() {
		return RES_PROGRESS_BAR;
	}

	// TODO change search field similar to google now
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				query = query.trim().replace(" ", "+");
				if (TextUtils.isEmpty(query)) {
					return false;
				}
				mKeyboard.hideSoftInputFromWindow(mSearchView.getWindowToken(),
						0);
				loadInfo(query, mCurrSearchType);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO track text change and show from db
				return false;
			}
		});
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (menu != null) {
			menu.removeItem(R.id.menu_refresh);
			// menu.removeItem(R.id.menu_search);
		}

	}

	public interface ISearchCallbacks {
		public void changeSearchType(int type);

		public void showSearchMenu(boolean show);
	}

	private ISearchCallbacks mSearchCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mSearchCallback = (ISearchCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ISearchCallbacks");
		}
	}

}
