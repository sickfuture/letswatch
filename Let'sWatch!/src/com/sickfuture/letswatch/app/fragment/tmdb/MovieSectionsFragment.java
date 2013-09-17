package com.sickfuture.letswatch.app.fragment.tmdb;

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

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.animations.FilpImagesAnimationGridLayout;
import com.sickfuture.letswatch.content.contract.Contract;

public class MovieSectionsFragment extends Fragment implements
		LoaderCallbacks<Cursor> {

	// private GridView mGridViewNowPlaying, mGridViewPopular,
	// mGridViewTopRated,
	// mGridViewUpcoming;

	// private MoviesSectionsGridAdapter mGridViewNowPlayingAdapter,
	// mGridViewPopularAdapter, mGridViewTopRatedAdapter,
	// mGridViewUpcomingAdapter;

	private int mNowPlayingLoaderId, mPopularLoaderId, mTopRatedLoaderId,
			mUpcomingLoaderId;

	private FilpImagesAnimationGridLayout layout;

	private Uri mLoaderUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_movie_sections_grids1,
				null);
		initViews(view);
		LoaderManager loaderManager = getActivity().getSupportLoaderManager();
		loaderManager.initLoader(mNowPlayingLoaderId, null, this);
		// loaderManager.initLoader(mPopularLoaderId, null, this);
		// loaderManager.initLoader(mTopRatedLoaderId, null, this);
		// loaderManager.initLoader(mUpcomingLoaderId, null, this);
		return view;
	}

	private void initViews(View view) {
		// mGridViewNowPlaying = (GridView) view
		// .findViewById(R.id.grid_view_sections_now_playing);
		// mGridViewNowPlayingAdapter = new MoviesSectionsGridAdapter(
		// getActivity(), null);
		// mGridViewNowPlaying.setAdapter(mGridViewNowPlayingAdapter);
		// mNowPlayingLoaderId = mGridViewNowPlaying.hashCode();
		// mGridViewPopular = (GridView) view
		// .findViewById(R.id.grid_view_sections_popular);
		// mGridViewPopularAdapter = new
		// MoviesSectionsGridAdapter(getActivity(),
		// null);
		// mGridViewPopular.setAdapter(mGridViewPopularAdapter);
		// mPopularLoaderId = mGridViewPopular.hashCode();
		// mGridViewTopRated = (GridView) view
		// .findViewById(R.id.grid_view_sections_top_rated);
		// mGridViewTopRatedAdapter = new
		// MoviesSectionsGridAdapter(getActivity(),
		// null);
		// mGridViewTopRated.setAdapter(mGridViewTopRatedAdapter);
		// mTopRatedLoaderId = mGridViewTopRated.hashCode();
		// mGridViewUpcoming = (GridView) view
		// .findViewById(R.id.grid_view_sections_upcoming);
		// mGridViewUpcomingAdapter = new
		// MoviesSectionsGridAdapter(getActivity(),
		// null);
		// mGridViewUpcoming.setAdapter(mGridViewUpcomingAdapter);
		// mUpcomingLoaderId = mGridViewUpcoming.hashCode();
		layout = (FilpImagesAnimationGridLayout) view
				.findViewById(R.id.now_playing_grid_layout_section);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		if (id == mNowPlayingLoaderId) {
			mLoaderUri = Uri.parse(ContractUtils
					.getProviderUriFromContract(Contract.MovieColumns.class)
					+ "#query");
		} else if (id == mPopularLoaderId) {
			// TODO set appropriate uri
		} else if (id == mTopRatedLoaderId) {
			// TODO set appropriate uri
		} else if (id == mUpcomingLoaderId) {
			// TODO set appropriate uri
		}
		return new CursorLoader(getActivity(), mLoaderUri, null, null, null,
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		layout.swapCursor(cursor);
		// int loaderId = loader.getId();
		// if (loaderId == mNowPlayingLoaderId) {
		// mGridViewNowPlayingAdapter.swapCursor(cursor);
		// } else if (loaderId == mPopularLoaderId) {
		// mGridViewPopularAdapter.swapCursor(cursor);
		// } else if (loaderId == mTopRatedLoaderId) {
		// mGridViewTopRatedAdapter.swapCursor(cursor);
		// } else if (loaderId == mUpcomingLoaderId) {
		// mGridViewUpcomingAdapter.swapCursor(cursor);
		// }
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// int loaderId = loader.getId();
		// if (loaderId == mNowPlayingLoaderId) {
		// mGridViewNowPlayingAdapter.swapCursor(null);
		// } else if (loaderId == mPopularLoaderId) {
		// mGridViewPopularAdapter.swapCursor(null);
		// } else if (loaderId == mTopRatedLoaderId) {
		// mGridViewTopRatedAdapter.swapCursor(null);
		// } else if (loaderId == mUpcomingLoaderId) {
		// mGridViewUpcomingAdapter.swapCursor(null);
		// }
	}

}
