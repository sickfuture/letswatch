package com.sickfuture.letswatch.app.activity.tmdb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.movie.MovieFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.movie.MovieSectionsFragment;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class MovieActivity extends DrawerActivity implements IListClickable {

	private static final String ADD_TO_BACKSTACK = "addToBackstack";
	
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
		} else {
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction()
				.replace(CONTENT_FRAME, new MovieSectionsFragment()).commit();
		}
	}

	@Override
	public void onItemListClick(Bundle arguments) {
		Fragment fragment = new MovieFragment();
		fragment.setArguments(arguments);
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(CONTENT_FRAME, fragment);
		if (arguments.getBoolean(ADD_TO_BACKSTACK, true)) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

}
