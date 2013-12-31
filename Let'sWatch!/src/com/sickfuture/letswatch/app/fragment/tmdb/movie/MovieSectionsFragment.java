package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.animations.AnimatedCellLayout;
import com.sickfuture.letswatch.app.activity.tmdb.DrawerActivity;
import com.sickfuture.letswatch.content.contract.Contract;

public class MovieSectionsFragment extends Fragment implements
		LoaderCallbacks<Cursor>, OnClickListener {

	private static final String LOG_TAG = MovieSectionsFragment.class
			.getSimpleName();

	public static final Uri NOW_PLAYING_URI = ContractUtils
			.getProviderUriFromContract(Contract.NowPlayingTmdbColumns.class);
	public static final Uri POPULAR_URI = ContractUtils
			.getProviderUriFromContract(Contract.PopularTmdbColumns.class);
	public static final Uri TOP_RATED_URI = ContractUtils
			.getProviderUriFromContract(Contract.TopRatedTmdbColumns.class);
	public static final Uri UPCOMING_URI = ContractUtils
			.getProviderUriFromContract(Contract.UpcomingTmdbColumns.class);

	private static final int ID_NOW_PLAYING = R.id.view_now_playing;
	private static final int ID_POPULAR = R.id.view_popular;
	private static final int ID_TOP_RATED = R.id.view_top_rated;
	private static final int ID_UPCOMING = R.id.view_upcoming;

	private int mNowPlayingLoaderId, mPopularLoaderId, mTopRatedLoaderId,
			mUpcomingLoaderId;

	private AnimatedCellLayout mNowPlayingLayout;
	private AnimatedCellLayout mPopularLayout;
	private AnimatedCellLayout mTopRatedLayout;
	private AnimatedCellLayout mUpcomingLayout;

	private View mViewPop, mViewNow, mViewTop,
			mViewUp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_movie_sections_grids,
				null);
		init(view);
		return view;
	}

	private void init(View view) {
		mNowPlayingLayout = (AnimatedCellLayout) view
				.findViewById(R.id.sections_now_playing_grid);
		mNowPlayingLoaderId = mNowPlayingLayout.hashCode();

		mPopularLayout = (AnimatedCellLayout) view
				.findViewById(R.id.sections_popular_grid);
		mPopularLoaderId = mPopularLayout.hashCode();

		mTopRatedLayout = (AnimatedCellLayout) view
				.findViewById(R.id.sections_top_rated_grid);
		mTopRatedLoaderId = mTopRatedLayout.hashCode();

		mUpcomingLayout = (AnimatedCellLayout) view
				.findViewById(R.id.sections_upcoming_grid);
		mUpcomingLoaderId = mUpcomingLayout.hashCode();

		mViewNow = view.findViewById(ID_NOW_PLAYING);
		mViewPop = view.findViewById(ID_POPULAR);
		mViewTop = view.findViewById(ID_TOP_RATED);
		mViewUp = view.findViewById(ID_UPCOMING);
		
		mViewNow.setOnClickListener(this);
		mViewPop.setOnClickListener(this);
		mViewTop.setOnClickListener(this);
		mViewUp.setOnClickListener(this);
		
		LoaderManager loaderManager = getActivity().getSupportLoaderManager();
		loaderManager.initLoader(mNowPlayingLoaderId, null, this);
		loaderManager.initLoader(mPopularLoaderId, null, this);
		loaderManager.initLoader(mTopRatedLoaderId, null, this);
		loaderManager.initLoader(mUpcomingLoaderId, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		// TODO use projection!!!
		if (id == mNowPlayingLoaderId) {
			return new CursorLoader(getActivity(), NOW_PLAYING_URI, null, null,
					null, null);
		} else if (id == mPopularLoaderId) {
			// TODO set appropriate uri
			return new CursorLoader(getActivity(), POPULAR_URI, null, null,
					null, null);
		} else if (id == mTopRatedLoaderId) {
			// TODO set appropriate uri
			return new CursorLoader(getActivity(), TOP_RATED_URI, null, null,
					null, null);
		} else if (id == mUpcomingLoaderId) {
			// TODO set appropriate uri
			return new CursorLoader(getActivity(), UPCOMING_URI, null, null,
					null, null);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor == null) {
			return;
		}
		if (cursor.getCount() <= 0) {
			// TODO load requested data
			return;
		}
		int loaderId = loader.getId();
		if (loaderId == mNowPlayingLoaderId) {
			mNowPlayingLayout.swapCursor(cursor);
		} else if (loaderId == mPopularLoaderId) {
			mPopularLayout.swapCursor(cursor);
		} else if (loaderId == mTopRatedLoaderId) {
			mTopRatedLayout.swapCursor(cursor);
		} else if (loaderId == mUpcomingLoaderId) {
			mUpcomingLayout.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		int loaderId = loader.getId();
		if (loaderId == mNowPlayingLoaderId) {
			mNowPlayingLayout.swapCursor(null);
		} else if (loaderId == mPopularLoaderId) {
			mPopularLayout.swapCursor(null);
		} else if (loaderId == mTopRatedLoaderId) {
			mTopRatedLayout.swapCursor(null);
		} else if (loaderId == mUpcomingLoaderId) {
			mUpcomingLayout.swapCursor(null);
		}
	}

	@Override
	public void onClick(View view) {
		Fragment fragment = null;
		switch (view.getId()) {
		case ID_NOW_PLAYING:
			fragment = new NowPlayingFragment();
			break;
		case ID_POPULAR:
			fragment = new PopularMoviesFragment();
			break;
		case ID_TOP_RATED:
			fragment = new TopRatedFragment();
			break;
		case ID_UPCOMING:
			fragment = new UpcomingMoviesFragment();
			break;
		}
		FragmentManager manager = getActivity().getSupportFragmentManager();
		manager.beginTransaction().addToBackStack(null)
				.replace(DrawerActivity.CONTENT_FRAME, fragment).commit();

	}

}
