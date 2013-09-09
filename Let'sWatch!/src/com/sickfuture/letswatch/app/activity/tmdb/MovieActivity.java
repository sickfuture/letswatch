package com.sickfuture.letswatch.app.activity.tmdb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.MovieFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.MovieSectionsFragment;

public class MovieActivity extends DrawerActivity implements IListClickable {

	@Override
	protected int getActivityNumberInDrawer() {
		return ACTIVITY_MOVIES;
	}

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().addToBackStack(null)
				.replace(CONTENT_FRAME, new MovieSectionsFragment()).commit();
	}

	@Override
	public void onItemListClick(Bundle arguments) {
		Fragment fragment = new MovieFragment();
		fragment.setArguments(arguments);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(CONTENT_FRAME, fragment)
				.addToBackStack(null).commit();

	}

}
