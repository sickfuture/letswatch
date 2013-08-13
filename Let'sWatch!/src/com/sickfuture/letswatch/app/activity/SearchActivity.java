package com.sickfuture.letswatch.app.activity;

import java.io.InputStream;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceResultReceiver;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.provider.RecentMovieSuggestionsProvider;

public class SearchActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor>, OnItemClickListener {

	private static final Uri mUri = ContractUtils
			.getProviderUriFromContract(Contract.SearchColumns.class);
	private CursorAdapter mAdapter;
	private int mLoaderId;
	private SourceResultReceiver mReceiver;
	private ListView mListView;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		mLoaderId = getClass().hashCode();
		mAdapter = new BoxOfficeCursorAdapter(this, null);
		mListView = (ListView) findViewById(R.id.listview_activity_search);
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar_activity_search);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mReceiver = new SourceResultReceiver(new Handler()) {

			@Override
			public void onStart(Bundle result) {
			}

			@Override
			public void onError(Exception exception) {
				mProgressBar.setVisibility(View.INVISIBLE);
				Toast.makeText(SearchActivity.this, exception.toString(),
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onDone(Bundle result) {
				mProgressBar.setVisibility(View.INVISIBLE);

			}
		};
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			handleIntent(intent);
		}
		getSupportLoaderManager().initLoader(mLoaderId, null, this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		String query = intent.getStringExtra(SearchManager.QUERY);
		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
				RecentMovieSuggestionsProvider.AUTHORITY,
				RecentMovieSuggestionsProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		search(query);

	}

	private void search(String query) {
		mProgressBar.setVisibility(View.VISIBLE);
		query = query.trim().replace(" ", "+");
		if (TextUtils.isEmpty(query))
			return;
		String searchUrl = getString(R.string.API_SEARCH_REQUEST_URL, query);
		if (InetChecker.checkInetConnection(this)) {
			getContentResolver().delete(mUri, null, null);
			DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
					searchUrl);
			request.setIsCacheable(true);
			SourceService.execute(this, request,
					LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
					LetsWatchApplication.SEARCH_PROCESSOR_SERVICE, mReceiver);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryRefinementEnabled(true);

		return true;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, mUri, null, null, null, null);
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
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Intent details = new Intent(this, MovieDetailsActivity.class);
		Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
		Bundle arguments = new Bundle();
		arguments.putInt(Contract.ID,
				cursor.getInt(cursor.getColumnIndex(MovieColumns.MOVIE_ID)));
		details.putExtra(MainActivity.ARGUMENTS, arguments);
		startActivity(details);
		
	}

}
