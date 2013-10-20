package com.sickfuture.letswatch.app.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.tmdb.DrawerActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.search.SearchedMoviesFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.search.SearchedPersonsFragment;

public class SearchActivity extends DrawerActivity implements IListClickable {

	private static final String LOG_TAG = SearchActivity.class.getSimpleName();

	public static final String SEARCHED_MOVIE_ID = "searched_movie_id";
	public static final String SEARCHED_PERSON_ID = "searched_person_id";

	public static final String SEARCH_TYPE = "search_type";
	public static final int MOVIE = 1;
	public static final int PERSON = 2;
	public static final int LIST = 3;
	public static final int COLLECTION = 4;
	public static final int COMPANY = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			handleIntent(intent);
		} else finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		String query = intent.getStringExtra(SearchManager.QUERY);
		query = query.trim().replace(" ", "+");
		if (TextUtils.isEmpty(query))
			return;
		Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
		if (appData != null) {
			appData.putString(SearchManager.QUERY, query);
			Fragment fr = null;
			switch (appData.getInt(SEARCH_TYPE)) {
			case MOVIE:
				fr = new SearchedMoviesFragment();
				fr.setArguments(appData);
				break;
			case PERSON:
				fr = new SearchedPersonsFragment();
				fr.setArguments(appData);
				break;
			default:
				break;
			}
			replaceFragment(fr);
		}
		// TODO deal with suggestions
		// SearchRecentSuggestions suggestions = new
		// SearchRecentSuggestions(this,
		// RecentMovieSuggestionsProvider.AUTHORITY,
		// RecentMovieSuggestionsProvider.MODE);
		// suggestions.saveRecentQuery(query, null);
		// search(query);

	}

	private void replaceFragment(Fragment fr) {
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(DrawerActivity.CONTENT_FRAME, fr)
				.commit();

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
	protected int getActivityNumberInDrawer() {
		return -1;
	}

	@Override
	public void performSearch(String query) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemListClick(Bundle arguments) {
		// TODO Auto-generated method stub

	}

}
