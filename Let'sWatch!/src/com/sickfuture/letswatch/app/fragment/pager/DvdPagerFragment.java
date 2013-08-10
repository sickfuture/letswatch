package com.sickfuture.letswatch.app.fragment.pager;

import android.support.v4.app.FragmentPagerAdapter;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.SectionsPagerAdapter;
import com.sickfuture.letswatch.app.fragment.common.PagerFragment;
import com.sickfuture.letswatch.app.fragment.dvd.CurrentReleaseFragment;
import com.sickfuture.letswatch.app.fragment.dvd.NewReleaseFragment;
import com.sickfuture.letswatch.app.fragment.dvd.TopRentalsFragment;
import com.sickfuture.letswatch.app.fragment.dvd.UpcomingDvdFragment;

public class DvdPagerFragment extends PagerFragment {

	private static final String LOG_TAG = DvdPagerFragment.class
			.getSimpleName();

	@Override
	protected FragmentPagerAdapter getPagerAdapter() {
		String[] sFragments = new String[] {
				TopRentalsFragment.class.getName(),
				NewReleaseFragment.class.getName(),
				CurrentReleaseFragment.class.getName(),
				UpcomingDvdFragment.class.getName() };
		String[] sTabTitles = getResources().getStringArray(R.array.titles_dvd);

		return new SectionsPagerAdapter(getActivity(),
				getChildFragmentManager(), sFragments, sTabTitles);
	}

}
