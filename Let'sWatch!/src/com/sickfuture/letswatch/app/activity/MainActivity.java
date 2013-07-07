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
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.widget.SearchView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.BoxOfficeCursorAdapter;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.BoxOfficeFragment;
import com.sickfuture.letswatch.app.fragment.OpeningFragment;
import com.sickfuture.letswatch.app.fragment.TheatersFragment;
import com.sickfuture.letswatch.app.fragment.UpcomingFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class MainActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener, IListClickable {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	public static final String FRAGMENT = "FRAGMENT";
	public static final String ARGUMENTS = "ARGS";

	public static final int FRAGMENT_BOXOFFICE = 0;
	public static final int FRAGMENT_UPCOMING = 1;
	public static final int FRAGMENT_THEATERS = 2;
	public static final int FRAGMENT_OPENING = 3;

	private InputMethodManager mKeyboard;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

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

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
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
			case FRAGMENT_BOXOFFICE:
				return new BoxOfficeFragment(new BoxOfficeCursorAdapter(
						getApplicationContext(), null),
						Contract.BOX_OFFICE_SECTION,
						R.string.API_BOX_OFFICE_REQUEST_URL);
			case FRAGMENT_UPCOMING:
				return new UpcomingFragment();
			case FRAGMENT_THEATERS:
				return new TheatersFragment(new BoxOfficeCursorAdapter(
						getApplicationContext(), null),
						Contract.IN_THEATRES_SECTION,
						R.string.API_IN_THEATERS_REQUEST_URL);
			case FRAGMENT_OPENING:
				return new OpeningFragment(new BoxOfficeCursorAdapter(
						getApplicationContext(), null),
						Contract.OPENING_SECTION,
						R.string.API_OPENING_REQUEST_URL);
			}
			return null;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case FRAGMENT_BOXOFFICE:
				return getString(R.string.title_box_office).toUpperCase(l);
			case FRAGMENT_UPCOMING:
				return getString(R.string.title_upcoming).toUpperCase(l);
			case FRAGMENT_THEATERS:
				return getString(R.string.title_theaters).toUpperCase(l);
			case FRAGMENT_OPENING:
				return getString(R.string.title_opening).toUpperCase(l);
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
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryRefinementEnabled(true);

		return true;
	}

}
