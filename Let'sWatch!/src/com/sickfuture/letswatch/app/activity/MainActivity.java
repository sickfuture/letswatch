package com.sickfuture.letswatch.app.activity;

import java.util.Locale;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.BoxOfficeFragment;
import com.sickfuture.letswatch.app.fragment.UpcomingFragment;

public class MainActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener, IListClickable {

	private InputMethodManager mKeyboard;
	
	public static final String FRAGMENT = "FRAGMENT";

	public static final String ARGUMENTS = "ARGS";

	public static final int BOXOFFICE_FRAGMENT = 0;

	public static final int UPCOM_FRAGMENT = 1;

	private static final String LOG_TAG = "MainActivity";

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mKeyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		setContentView(R.layout.activity_main);
		final ActionBar actBar = getSupportActionBar();
		actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actBar.addTab(actBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new BoxOfficeFragment();
			case 1:
				return new UpcomingFragment();
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case BOXOFFICE_FRAGMENT:
				return getString(R.string.title_box_office).toUpperCase(l);
			case UPCOM_FRAGMENT:
				return getString(R.string.title_upcoming).toUpperCase(l);
			}
			return null;
		}
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
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryRefinementEnabled(true);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				Log.d(LOG_TAG, "submit");
				mKeyboard.hideSoftInputFromWindow(searchView.getWindowToken(),
						0);
				//startSearch(query, mIgnoreNativeCreate, null, mIgnoreNativeCreate);
				return onSearchRequested();
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

}
