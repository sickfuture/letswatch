package com.sickfuture.letswatch.app.activity.tmdb;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.SearchActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.people.CastFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.people.CrewFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.people.PersonFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.people.PopularPersonsFragment;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class PeopleActivity extends DrawerActivity implements IListClickable {

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = null;
		if (getIntent().hasExtra(CastColumns.TMDB_PERSON_ID)) {
			args = new Bundle();
			args.putString(PersonFragment.PERSON_ID,
					getIntent().getStringExtra(CastColumns.TMDB_PERSON_ID));
			args.putBoolean(ADD_TO_BACKSTACK, false);
			onItemListClick(args);
		} else if (getIntent().hasExtra("cast_mid")) {
			args = new Bundle();
			args.putString("mid", getIntent().getStringExtra("cast_mid"));
			args.putBoolean(ADD_TO_BACKSTACK, false);
			replaceFragment(new CastFragment(), args);
		} else if (getIntent().hasExtra("crew_mid")) {
			args = new Bundle();
			args.putString("mid", getIntent().getStringExtra("crew_mid"));
			args.putBoolean(ADD_TO_BACKSTACK, false);
			replaceFragment(new CrewFragment(), args);
		} else if (getIntent().hasExtra(SearchActivity.SEARCHED_PERSON_ID)) {
			Bundle arguments = new Bundle();
			arguments.putString(PersonFragment.PERSON_ID, getIntent()
					.getStringExtra(SearchActivity.SEARCHED_PERSON_ID));
			arguments.putBoolean(ADD_TO_BACKSTACK, false);
			onItemListClick(arguments);
		} else {
			replaceFragment(new PopularPersonsFragment(), args);
		}
	}

	@Override
	protected int getActivityNumberInDrawer() {
		return ACTIVITY_PEOPLE;
	}

	@Override
	public void onItemListClick(Bundle arguments) {
		replaceFragment(new PersonFragment(), arguments);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean sup = super.onCreateOptionsMenu(menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setQueryHint(getString(R.string.search_hint_people));
		return sup;
	}

	@Override
	public void performSearch(String query) {
		Intent intent = new Intent(this, SearchActivity.class);
		intent.setAction(Intent.ACTION_SEARCH);
		intent.putExtra(SearchManager.QUERY, query);
		Bundle appData = new Bundle();
		appData.putInt(SearchActivity.SEARCH_TYPE, SearchActivity.PERSON);
		intent.putExtra(SearchManager.APP_DATA, appData);
		startActivity(intent);

	}

}
