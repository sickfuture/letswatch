package com.sickfuture.letswatch.adapter;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private static final String LOG_TAG = SectionsPagerAdapter.class
			.getSimpleName();

	private String[] mTabTitles;
	private String[] mFragments;
	private Context mContext;

	public SectionsPagerAdapter(Context context, FragmentManager fm,
			String[] fragments, String[] tabTitles) {
		super(fm);
		mContext = context;
		mFragments = fragments;
		mTabTitles = tabTitles;
	}

	@Override
	public Fragment getItem(int position) {

		Log.d(LOG_TAG, "getItem: " + mFragments[position]);
		return Fragment.instantiate(mContext, mFragments[position]);
	}

	@Override
	public int getCount() {
		return mTabTitles.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		return mTabTitles[position].toUpperCase(l);
	}
}