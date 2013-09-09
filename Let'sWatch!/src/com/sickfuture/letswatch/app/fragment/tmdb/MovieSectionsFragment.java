package com.sickfuture.letswatch.app.fragment.tmdb;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.tmdb.DrawerActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MovieSectionsFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, new String[] {
						"Now playing", "Popular", "Top rated", "Upcoming" }));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new NowPlayingFragment();
			break;
		case 1:
			fragment = new PopularMoviesFragment();
			break;
		case 2:
			fragment = new TopRatedFragment();
			break;
		case 3:
			fragment = new UpcomingMoviesFragment();
			break;
		}
		FragmentManager manager = getActivity().getSupportFragmentManager();
		manager.beginTransaction().addToBackStack(null)
				.replace(DrawerActivity.CONTENT_FRAME, fragment).commit();
	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				getResources().getString(R.string.drawer_movies));
		super.onStart();
	}

}
