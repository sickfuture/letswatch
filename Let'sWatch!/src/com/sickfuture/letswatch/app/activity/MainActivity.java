package com.sickfuture.letswatch.app.activity;

import java.util.Locale;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.BoxOfficeFragment;
import com.sickfuture.letswatch.app.fragment.FavoritesFragment;
import com.sickfuture.letswatch.app.fragment.OpeningFragment;
import com.sickfuture.letswatch.app.fragment.TheatersFragment;
import com.sickfuture.letswatch.app.fragment.TheatersPagerFragment;
import com.sickfuture.letswatch.app.fragment.UpcomingFragment;
import com.sickfuture.letswatch.app.fragment.common.PagerFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class MainActivity extends SherlockFragmentActivity implements
		IListClickable {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	private String[] mDrawerTitles;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	public static final String FRAGMENT = "FRAGMENT";
	public static final String ARGUMENTS = "ARGS";

	public static final int FRAGMENT_BOXOFFICE = 0;
	public static final int FRAGMENT_UPCOMING = 1;
	public static final int FRAGMENT_THEATERS = 2;
	public static final int FRAGMENT_OPENING = 3;

	private InputMethodManager mKeyboard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		mDrawerTitles = getResources().getStringArray(R.array.titles_drawer);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mDrawerTitles));
		// Set the list's click listener
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
            	ActionBar actionBar = getSupportActionBar();
            	actionBar.setTitle(mTitle);
            	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
            	ActionBar actionBar = getSupportActionBar();
            	actionBar.setTitle(mDrawerTitle);
            	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

//		FragmentManager fragmentManager = getSupportFragmentManager();
//		fragmentManager.beginTransaction()
//				.replace(R.id.content_frame, new TheatersPagerFragment())
//				.commit();

		mDrawerLayout.openDrawer(mDrawerList);

		mKeyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

	}
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {

		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new TheatersPagerFragment();
			break;
		case 1:
			fragment = new TheatersPagerFragment();
			break;
		case 2:
			fragment = new FavoritesFragment();
			break;
		default:
			break;
		}

		Bundle args = new Bundle();
		fragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).disallowAddToBackStack()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();

		mDrawerList.setItemChecked(position, true);
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		setTitle(mDrawerTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onItemListClick(Bundle arguments) {
		Intent details = new Intent(this, MovieDetailsActivity.class);
		details.putExtra(MainActivity.ARGUMENTS, arguments);
		startActivity(details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.menu_search).getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryRefinementEnabled(true);

		return true;
	}
	

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
