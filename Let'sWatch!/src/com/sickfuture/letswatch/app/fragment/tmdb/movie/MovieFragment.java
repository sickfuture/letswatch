package com.sickfuture.letswatch.app.fragment.tmdb.movie;

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
import android.widget.Button;
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
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.helpers.UIHelper;

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
	private ViewGroup mInfoContainer, mStoryContainer, mCastsContainer,
			mSimilarContainer, mProductionContainer;
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
		mInfoContainer = (ViewGroup) mScrollView.findViewById(INFO_CONTAINER);
		mStoryContainer = (ViewGroup) mScrollView
				.findViewById(STORYLINE_CONTAINER);
		mCastsContainer = (ViewGroup) mScrollView.findViewById(CASTS_CONTAINER);
		mSimilarContainer = (ViewGroup) mScrollView
				.findViewById(SIMILAR_CONTAINER);
		mProductionContainer = (ViewGroup) mScrollView
				.findViewById(PRODUCTION_CONTAINER);
		mLoaderId = hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, null,
				this);
		return mScrollView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO load if data is older than 1 hour
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
		if (cursor.getCount() > 0)
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
			setTitle(c);
			setBackdrop(c);
			setPoster(c);
			setTagline(c);
			setInfo(c);
			setStoryline(c);
			setProductionCreds(c);
		}
	}

	private void setProductionCreds(Cursor c) {
		int runtime = getInt(c, Contract.MovieColumns.RUNTIME);
		int budget = getInt(c, Contract.MovieColumns.BUDGET);
		int revenue = getInt(c, Contract.MovieColumns.REVENUE);
		int voteCount = getInt(c, Contract.MovieColumns.VOTE_COUNT);
		String voteAverage = getString(c, Contract.MovieColumns.VOTE_AVERAGE);
		StringBuilder builder = new StringBuilder();
		if (runtime > 0) {
			builder.append("Runtime ").append(runtime).append("\n");
		}
		if (budget > 0) {
			builder.append("Budget ").append(String.format("%,d", budget))
					.append("\n");
		}
		if (revenue > 0) {
			builder.append("Rvenue ").append(String.format("%,d", revenue))
					.append("\n");
		}
		if (voteCount > 0) {
			builder.append("Rating ")
					.append(String.format("%.1f", Float.valueOf(voteAverage)))
					.append("/").append(voteCount).append("\n");
		}
		if (builder.length() > 4) {
			String creds = builder.substring(0, builder.length() - 2);
			if (!TextUtils.isEmpty(creds)) {
				TextView pc = new TextView(getActivity());
				pc.setText(creds);
				mProductionContainer.removeAllViews();
				mProductionContainer.setVisibility(View.VISIBLE);
				mProductionContainer.addView(pc);
			}
		} else {
			mProductionContainer.setVisibility(View.GONE);
		}

	}

	private void setStoryline(Cursor c) {
		String story = getString(c, Contract.MovieColumns.OVERVIEW);
		if (!TextUtils.isEmpty(story)) {
			TextView storyView = (TextView) mStoryContainer
					.findViewById(STORYLINE);
			storyView.setText(story);
			mStoryContainer.setVisibility(View.VISIBLE);
		} else
			mStoryContainer.setVisibility(View.GONE);
	}

	private void setInfo(Cursor c) {
		String year = getString(c, Contract.MovieColumns.YEAR);
		if (!TextUtils.isEmpty(year)) {
			mYearTextView.setText(year);
		} else {
			String releaseDate = getString(c,
					Contract.MovieColumns.RELEASE_DATE);
			UIHelper.setTextOrGone(mYearTextView, releaseDate);
		}
		String genres = getString(c, MovieColumns.GENRES);
		if (!TextUtils.isEmpty(genres)) {
			TextView genreView = new TextView(getActivity());
			genreView.setText(genres);
			mInfoContainer.removeAllViews();
			mInfoContainer.addView(genreView);
			mInfoContainer.setVisibility(View.VISIBLE);
		}
		// String ids = getString(c, Contract.MovieColumns.GENRES_IDS);
		// if (!TextUtils.isEmpty(ids)) {
		// Cursor gc = getActivity().getContentResolver().query(uri, null,
		// Contract.GenresColumns.TMDB_ID + " IN (" + ids + ")", null,
		// null);
		// if (gc.getCount() > 0 && gc.moveToFirst()) {
		// mInfoContainer.removeAllViews();
		// while (!gc.isAfterLast()) {
		// Button genButton = new Button(getActivity());
		// genButton
		// .setText(getString(gc, Contract.GenresColumns.NAME));
		// mInfoContainer.addView(genButton);
		// }
		// mInfoContainer.setVisibility(View.VISIBLE);
		// } else {
		// mInfoContainer.setVisibility(View.GONE);
		// }
		// }
	}

	private void setTagline(Cursor c) {
		UIHelper.setTextOrGone(mTaglineTextView, c, Contract.MovieColumns.TAGLINE);
	}

	private void setPoster(Cursor c) {
		if (!TextUtils.isEmpty(getString(c, Contract.MovieColumns.POSTER_PATH))) {
			String poster = TmdbApi.getPoster(
					getString(c, Contract.MovieColumns.POSTER_PATH),
					TmdbApi.POSTER.W185);
			mImageLoader.loadBitmap(mPosterImageView, poster);
		}
	}

	private void setBackdrop(Cursor c) {
		if (!TextUtils
				.isEmpty(getString(c, Contract.MovieColumns.BACKDROP_PATH))) {
			String backdrop = TmdbApi.getBackdrop(
					getString(c, Contract.MovieColumns.BACKDROP_PATH),
					TmdbApi.BACKDROP.W780);
			mImageLoader.loadBitmap(mBackdropImageView, backdrop);
		}
	}

	private void setTitle(Cursor c) {
		String title = getString(c, Contract.MovieColumns.TITLE);
		if (!TextUtils.isEmpty(title))
			((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
					title);
	}

	private String getString(Cursor cursor, String column) {
		String s = cursor.getString(cursor.getColumnIndex(column));

		Log.d(LOG_TAG, "getString:" + column + " " + s);
		return s;
	}

	private int getInt(Cursor cursor, String column) {
		return cursor.getInt(cursor.getColumnIndex(column));
	}

}
