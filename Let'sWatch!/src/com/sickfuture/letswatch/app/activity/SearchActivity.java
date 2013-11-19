package com.sickfuture.letswatch.app.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.tmdb.DrawerActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.search.SearchResultsFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.search.SearchResultsFragment.ISearchCallbacks;

public class SearchActivity extends DrawerActivity implements IListClickable,
		OnItemSelectedListener, ISearchCallbacks {

	private static final String SEARCH_FRAGMENT_TAG = "search_fragment";

	private static final String LOG_TAG = SearchActivity.class.getSimpleName();

	public static final String SEARCHED_MOVIE_ID = "searched_movie_id";
	public static final String SEARCHED_PERSON_ID = "searched_person_id";

	public static final String SEARCH_TYPE = "search_type";
	public static final int CHOSEN = -1;
	public static final int MOVIE = 0;
	public static final int PERSON = 1;
	public static final int LIST = 2;
	public static final int COLLECTION = 3;
	public static final int COMPANY = 4;
	public static final int TV = 5;

	private SearchResultsFragment mSearchFragment;

	private Spinner mSpinnerViewType, mSpinnerViewYears;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fillRightMenu();
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			handleIntent(intent);
		} else
			finish();
	}

	private void fillRightMenu() {
		unlockRightMenu(true);
		mSpinnerViewType = (Spinner) findViewById(R.id.spiner_search_for);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.titles_search_drawer,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerViewType.setAdapter(adapter);
		mSpinnerViewType.setOnItemSelectedListener(this);
		mSpinnerViewYears = (Spinner) findViewById(R.id.spiner_search_release_year);
		List<CharSequence> years = new ArrayList<CharSequence>();
		years.add(getResources().getString(R.string.any));
		for (int i = 2015; i > 1900; i--) {
			years.add(String.valueOf(i));
		}
		ArrayAdapter<CharSequence> adapterYears = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item, years);
		adapterYears
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerViewYears.setAdapter(adapterYears);
		mSpinnerViewYears.setOnItemSelectedListener(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		String query = intent.getStringExtra(SearchManager.QUERY);
		query = query.trim().replace(" ", "+");
		if (TextUtils.isEmpty(query)) {
			// TODO show recent search results
			// return;
		}
		Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
		if (appData != null) {
			appData.putString(SearchManager.QUERY, query);
			mSearchFragment = (SearchResultsFragment) getSupportFragmentManager()
					.findFragmentByTag(SEARCH_FRAGMENT_TAG);
			if (mSearchFragment == null) {
				mSearchFragment = new SearchResultsFragment();
			}
			mSearchFragment.setArguments(appData);
			replaceFragment(mSearchFragment);
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
		manager.beginTransaction()
				.replace(DrawerActivity.CONTENT_FRAME, fr, SEARCH_FRAGMENT_TAG)
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

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		if (position != mSearchFragment.mCurrSearchType) {
			Bundle args = new Bundle();
			args.putInt(SEARCH_TYPE, position);
			args.putString(SearchManager.QUERY, "");
			mSearchFragment.search(args);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		// do nothing

	}

	@Override
	public void changeSearchType(int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showSearchMenu(boolean show) {
		showRightMenu(show);

	}

}
