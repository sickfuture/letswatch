package com.sickfuture.letswatch.app.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.InetChecker;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.provider.RecentMovieSuggestionsProvider;
import com.sickfuture.letswatch.request.LoadingRequest;
import com.sickfuture.letswatch.request.LoadingRequest.RequestHelper;
import com.sickfuture.letswatch.service.LoadingService;

public class SearchActivity extends SherlockFragmentActivity implements
		LoaderCallbacks<Cursor> {

	private final Uri mUri = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);
	private CursorAdapter mAdapter;
	private String mSelection = Contract.MovieColumns.SECTION + " = ?";
	private String[] mSelectionArgs = new String[] { String
			.valueOf(Contract.SEARCH) };
	private int mLoaderId;
	
	private ListView mListView;

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		//
		// searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		mLoaderId = getClass().hashCode();
		mAdapter = new BoxOfficeCursorAdapter(this, null);
		mListView = (ListView) findViewById(R.id.listview_activity_search);
		mListView.setAdapter(mAdapter);
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
		query = query.trim().replace(" ", "+");
		if(TextUtils.isEmpty(query))
			return;
		String searchUrl = getString(R.string.API_SEARCH_REQUEST_URL, query);
		if (InetChecker.checkInetConnection(this)) {
			getContentResolver()
					.delete(mUri,mSelection,
							mSelectionArgs);
			Intent intent = new Intent(this,
					LoadingService.class);
			LoadingRequest request = new LoadingRequest(RequestType.GET,
					searchUrl,
					Contract.SEARCH,
					RequestHelper.PROCESS_MOVIE_LIST, mUri);
			intent.putExtra("request", request);
			startService(intent);
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, mUri, null, mSelection, mSelectionArgs, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);

	}

}
