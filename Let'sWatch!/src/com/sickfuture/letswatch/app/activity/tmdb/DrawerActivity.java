package com.sickfuture.letswatch.app.activity.tmdb;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sickfuture.letswatch.R;

public abstract class DrawerActivity extends ActionBarActivity {

	public static final String FRAGMENT = "FRAGMENT";
	public static final String ARGUMENTS = "ARGS";
	
	public static final int ACTIVITY_MOVIES = 0;
	public static final int ACTIVITY_PEOPLE = 1;
	public static final int ACTIVITY_USER = 2;
	public static final int ACTIVITY_DISCOVER = 3;
	
	public static final int CONTENT_FRAME = R.id.content_frame;
	private static final int TITLES_DRAWER = R.array.titles_drawer;
	
	private String[] mDrawerTitles;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private InputMethodManager mKeyboard;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		mDrawerTitles = getResources().getStringArray(TITLES_DRAWER);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mDrawerTitles));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				ActionBar actionBar = getSupportActionBar();
				actionBar.setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				ActionBar actionBar = getSupportActionBar();
				actionBar.setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				if (position != getActivityNumberInDrawer())
					selectItem(position);
				else
					mDrawerLayout.closeDrawer(mDrawerList);
			}
		});

		mKeyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	protected abstract int getActivityNumberInDrawer();

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_refresh).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {

		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent(this, MovieActivity.class);
			break;
		case 1:
			intent = new Intent(this, PeopleActivity.class);
			break;
		case 2:
			intent = new Intent(this, UserActivity.class);
			break;
		case 3:
			intent = new Intent(this, DiscoverActivity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	// @Override
	// public void onItemListClick(Bundle arguments) {
	// if (arguments.containsKey(Contract.MovieColumns.TMDB_ID)) {
	// Fragment fragment = new MovieFragment();
	// fragment.setArguments(arguments);
	// FragmentManager fragmentManager = getSupportFragmentManager();
	// fragmentManager.beginTransaction().addToBackStack(null)
	// .replace(R.id.content_frame, fragment).commit();
	// } else {
	// Intent details = new Intent(this, MovieDetailsActivity.class);
	// details.putExtra(MainActivity.ARGUMENTS, arguments);
	// startActivity(details);
	// }
	// }

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
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected((MenuItem) item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}