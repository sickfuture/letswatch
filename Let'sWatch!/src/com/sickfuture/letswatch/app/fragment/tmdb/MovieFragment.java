package com.sickfuture.letswatch.app.fragment.tmdb;

import java.io.InputStream;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class MovieFragment extends Fragment implements LoaderCallbacks<Cursor> {

	private static final String LOG_TAG = MovieFragment.class.getSimpleName();

	private static final int BACKDROP = R.id.image_view_fragment_movie_backdrop;
	private static final int TAGLINE = R.id.text_view_fragment_movie_tagline;
	private static final int POSTER = R.id.image_view_fragment_movie_poster;
	private static final int YEAR = R.id.text_view_fragment_movie_year;
	private static final int INFO_CONTAINER = R.id.layout_fragment_movie_info_container;
	private static final int CERTIFICATION = R.id.image_view_certification;
	private static final int STORYLINE_CONTAINER = R.id.layout_fragment_movie_storyline;
	private static final int STORYLINE = R.id.text_view_fragment_movie_storyline;
	private static final int CASTS_CONTAINER = R.id.layout_fragment_movie_casts;
	private static final int SIMILAR_CONTAINER = R.id.layout_fragment_movie_similar;
	private static final int PRODUCTION_CONTAINER = R.id.layout_fragment_movie_production_creds;

	private View mScrollView;
	private RecyclingImageView mBackdropImageView, mPosterImageView;
	private ImageView mCertifyImageView;
	private TextView mTaglineTextView, mYearTextView;

	private int mLoaderId;
	private String mid;

	private SickImageLoader mImageLoader;

	private Uri uri;
	private String[] projection;
	private String selection;
	private String[] selectionArgs;
	private String sortOrder;

	private ViewGroup mStoryContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mid = args.getString(Contract.MovieColumns.TMDB_ID);
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		uri = Uri.parse(ContractUtils
				.getProviderUriFromContract(Contract.MovieColumns.class)
				+ "#query");
		selection = Contract.MovieColumns.TMDB_ID + " = " + mid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mScrollView = inflater.inflate(R.layout.fragment_movie, null);
		mBackdropImageView = (RecyclingImageView) mScrollView
				.findViewById(BACKDROP);
		mPosterImageView = (RecyclingImageView) mScrollView
				.findViewById(POSTER);
		mCertifyImageView = (ImageView) mScrollView.findViewById(CERTIFICATION);
		mTaglineTextView = (TextView) mScrollView.findViewById(TAGLINE);
		mYearTextView = (TextView) mScrollView.findViewById(YEAR);
		mStoryContainer = (ViewGroup) mScrollView
				.findViewById(STORYLINE_CONTAINER);
		mLoaderId = hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, null,
				this);
		return mScrollView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		loadData();
		return new CursorLoader(getActivity(), uri, projection, selection,
				selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// loadData();
		// if (cursor.getCount() == 0) {
		// loadData();
		// } else
		if (cursor.getCount() != 0)
			compliteView(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}

	private void loadData() {
		String url = MovieApis.TmdbApi.getMovie(mid, null);
		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_MOVIE_PROCESSOR_SERVICE);

	}

	private void compliteView(Cursor c) {
		if (c.moveToFirst() && getActivity() != null) {
			String title = getString(c, Contract.MovieColumns.TITLE);
			if(!TextUtils.isEmpty(title))
				((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(title);
			if (!TextUtils.isEmpty(getString(c,
					Contract.MovieColumns.BACKDROP_PATH))) {
				String backdrop = TmdbApi.getBackdrop(
						getString(c, Contract.MovieColumns.BACKDROP_PATH),
						TmdbApi.BACKDROP.W300);
				mImageLoader.loadBitmap(mBackdropImageView, backdrop);
			}
			if (!TextUtils.isEmpty(getString(c,
					Contract.MovieColumns.POSTER_PATH))) {
				String poster = TmdbApi.getPoster(
						getString(c, Contract.MovieColumns.POSTER_PATH),
						TmdbApi.POSTER.W154);
				mImageLoader.loadBitmap(mBackdropImageView, poster);
			}
			String tagline = getString(c, Contract.MovieColumns.TAGLINE);
			if (!TextUtils.isEmpty(tagline)) {
				mTaglineTextView.setText(tagline);
				mTaglineTextView.setVisibility(View.VISIBLE);
			} else
				mTaglineTextView.setVisibility(View.GONE);
			mYearTextView
					.setText(getString(c, Contract.MovieColumns.YEAR));
			String story = getString(c, Contract.MovieColumns.OVERVIEW);
			if (!TextUtils.isEmpty(story)) {
				TextView storyView = new TextView(getActivity());
				storyView.setText(story);
				mStoryContainer.removeAllViews();
				mStoryContainer.addView(storyView);
				mStoryContainer.setVisibility(View.VISIBLE);
			} else
				mStoryContainer.setVisibility(View.GONE);
		}
	}

	private String getString(Cursor cursor, String column) {
		String s = cursor.getString(cursor.getColumnIndex(column));

		Log.d(LOG_TAG, "getString:" + column + " " + s);
		return s;

	}

}
