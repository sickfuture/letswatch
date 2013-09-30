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
import com.sickfuture.letswatch.app.fragment.tmdb.movie.MovieFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.movie.MovieSectionsFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.people.CastFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.people.CrewFragment;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class MovieActivity extends DrawerActivity implements IListClickable {

	@Override
	protected int getActivityNumberInDrawer() {
		return ACTIVITY_MOVIES;
	}

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().hasExtra(CastColumns.TMDB_MOVIE_ID)) {
			Bundle args = new Bundle();
			args.putString(PersonColumns.TMDB_ID,
					getIntent().getStringExtra(CastColumns.TMDB_MOVIE_ID));
			args.putBoolean(ADD_TO_BACKSTACK, false);
			onItemListClick(args);
		} else if (getIntent().hasExtra("cast_pid")) {
			Bundle args = new Bundle();
			args.putString("pid", getIntent().getStringExtra("cast_pid"));
			replaceFragment(new CastFragment(), args);
		} else if (getIntent().hasExtra("crew_pid")) {
			Bundle args = new Bundle();
			args.putString("pid", getIntent().getStringExtra("crew_pid"));
			replaceFragment(new CrewFragment(), args);
		} else if (getIntent().hasExtra(SearchActivity.SEARCHED_MOVIE_ID)) {
			Bundle arguments = new Bundle();
			arguments.putString(SearchActivity.SEARCHED_MOVIE_ID, getIntent()
					.getStringExtra(SearchActivity.SEARCHED_MOVIE_ID));
			onItemListClick(arguments);
		} else {
			replaceFragment(new MovieSectionsFragment(), null);
		}
	}

	@Override
	public void onItemListClick(Bundle arguments) {
		replaceFragment(new MovieFragment(), arguments);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean sup = super.onCreateOptionsMenu(menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setQueryHint("Search movie");
		return sup;
	}

	@Override
	public void performSearch(String query) {
		Intent intent = new Intent(this, SearchActivity.class);
		intent.setAction(Intent.ACTION_SEARCH);
		intent.putExtra(SearchManager.QUERY, query);
		Bundle appData = new Bundle();
		appData.putInt(SearchActivity.SEARCH_TYPE, SearchActivity.MOVIE);
		intent.putExtra(SearchManager.APP_DATA, appData);
		startActivity(intent);

	}

}
