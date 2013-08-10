package com.sickfuture.letswatch.app.fragment.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.sickfuture.letswatch.R;

public abstract class PagerFragment extends Fragment {

	private PagerSlidingTabStrip mTabs;
	private FragmentPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mSectionsPagerAdapter = getPagerAdapter();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pager, null);
		mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		mViewPager.setPageMargin(pageMargin);

		mTabs.setViewPager(mViewPager);

		return view;
	}

	protected abstract FragmentPagerAdapter getPagerAdapter();

}
