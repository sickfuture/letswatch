package com.sickfuture.letswatch.app.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.tmdb.DrawerActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.movie.MovieFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class MainActivity extends DrawerActivity implements IListClickable {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

	@Override
	public void onItemListClick(Bundle arguments) {
		if (arguments.containsKey(Contract.MovieColumns.TMDB_ID)) {
			Fragment fragment = new MovieFragment();
			fragment.setArguments(arguments);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().addToBackStack(null)
					.replace(R.id.content_frame, fragment).commit();
		} else {
//			Intent details = new Intent(this, MovieDetailsActivity.class);
//			details.putExtra(MainActivity.ARGUMENTS, arguments);
//			startActivity(details);
		}
	}

	@Override
	protected int getActivityNumberInDrawer() {
		return -1;
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
