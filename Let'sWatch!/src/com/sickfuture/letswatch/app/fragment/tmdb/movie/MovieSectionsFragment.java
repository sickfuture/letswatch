package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.animations.AnimatedCellLayout;

public class MovieSectionsFragment extends Fragment implements
		LoaderCallbacks<Cursor> {

	private static final String LOG_TAG = MovieSectionsFragment.class
			.getSimpleName();

	private int mNowPlayingLoaderId, mPopularLoaderId, mTopRatedLoaderId,
			mUpcomingLoaderId;

	private AnimatedCellLayout mNowPlayingLayout;
	private AnimatedCellLayout mPopularLayout;
	private AnimatedCellLayout mTopRatedLayout;
	private AnimatedCellLayout mUpcomingLayout;

	private Uri mLoaderUri;

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

		LoaderManager loaderManager = getActivity().getSupportLoaderManager();
		loaderManager.initLoader(mNowPlayingLoaderId, null, this);
		loaderManager.initLoader(mPopularLoaderId, null, this);
		loaderManager.initLoader(mTopRatedLoaderId, null, this);
		loaderManager.initLoader(mUpcomingLoaderId, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		if (id == mNowPlayingLoaderId) {
			// TODO set appropriate uri
		} else if (id == mPopularLoaderId) {
			// TODO set appropriate uri
		} else if (id == mTopRatedLoaderId) {
			// TODO set appropriate uri
		} else if (id == mUpcomingLoaderId) {
			// TODO set appropriate uri
		}
		if (mLoaderUri == null) {
			L.w(LOG_TAG, "Can't load cursor by 'null' uri!");
			return null;
		}
		return new CursorLoader(getActivity(), mLoaderUri, null, null, null,
				null);
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

}
