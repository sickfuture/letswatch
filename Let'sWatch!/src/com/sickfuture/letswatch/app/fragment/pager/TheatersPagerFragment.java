package com.sickfuture.letswatch.app.fragment.pager;

import android.support.v4.app.FragmentPagerAdapter;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.SectionsPagerAdapter;
import com.sickfuture.letswatch.app.fragment.common.PagerFragment;
import com.sickfuture.letswatch.app.fragment.theaters.BoxOfficeFragment;
import com.sickfuture.letswatch.app.fragment.theaters.OpeningFragment;
import com.sickfuture.letswatch.app.fragment.theaters.TheatersFragment;
import com.sickfuture.letswatch.app.fragment.theaters.UpcomingFragment;

public class TheatersPagerFragment extends PagerFragment {

	private static final String LOG_TAG = TheatersPagerFragment.class
			.getSimpleName();

	@Override
	protected FragmentPagerAdapter getPagerAdapter() {
		String[] sFragments = new String[] { BoxOfficeFragment.class.getName(),
				UpcomingFragment.class.getName(),
				TheatersFragment.class.getName(),
				OpeningFragment.class.getName() };
		String[] sTabTitles = getResources().getStringArray(
				R.array.titles_theater);

		return new SectionsPagerAdapter(getActivity(),
				getChildFragmentManager(), sFragments, sTabTitles);
	}

}
