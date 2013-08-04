package com.sickfuture.letswatch.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.SectionsPagerAdapter;
import com.sickfuture.letswatch.app.fragment.common.PagerFragment;

public class TheatersPagerFragment extends PagerFragment {

	private static final String LOG_TAG = TheatersPagerFragment.class
			.getSimpleName();
	
	private String[] mFragments;
	private String[] mTabTitles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(LOG_TAG, "onCreate: ");
		mTabTitles = getResources().getStringArray(R.array.titles_theater);
		mFragments = new String[] { BoxOfficeFragment.class.getName(),
				UpcomingFragment.class.getName(),
				TheatersFragment.class.getName(),
				OpeningFragment.class.getName() };
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onAttach: ");
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onDetach: ");
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onDestroy: ");
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onPause: ");
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onResume: ");
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onStart: ");
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
		Log.d(LOG_TAG, "onStop: ");
		super.onStop();
	}

	@Override
	protected FragmentPagerAdapter getPagerAdapter() {
		return new SectionsPagerAdapter(getActivity(), getFragmentManager(),
				mFragments, mTabTitles);
	}

}
