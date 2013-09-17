package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import it.sephiroth.android.library.widget.HListView;

import java.io.InputStream;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
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
import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.CastHListAdapter;
import com.sickfuture.letswatch.adapter.tmdb.CrewHListAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
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
	private static final int CREW_CONTAINER = R.id.layout_fragment_movie_crew;
	private static final int SIMILAR_CONTAINER = R.id.layout_fragment_movie_similar;
	private static final int PRODUCTION_CONTAINER = R.id.layout_fragment_movie_production_creds;

	private View mParentView;
	private ViewGroup mInfoContainer, mStoryContainer, mCastsContainer,
			mCrewContainer, mSimilarContainer, mProductionContainer;
	private ImageView mCertifyImageView, mBackdropImageView, mPosterImageView;
	private TextView mTaglineTextView, mYearTextView;
	private HListView mCastHListView, mCrewHListView, mSimilarHListView;

	private int mLoaderId;
	private String mid;

	private SickImageLoader mImageLoader;

	private static final Uri moviesUri = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);
	private String[] moviesProjection;
	private String moviesSelection;
	private String[] moviesSelectionArgs;
	private String moviesSortOrder;

	private static final Uri castUri = ContractUtils
			.getProviderUriFromContract(CastColumns.class);
	private String[] castProjection;
	private String castSelection;
	private String[] castSelectionArgs;
	private String castSortOrder;
	private CursorAdapter mCastAdapter;

	private static final Uri crewUri = ContractUtils
			.getProviderUriFromContract(CrewColumns.class);
	private String[] crewProjection;
	private String crewSelection;
	private String[] crewSelectionArgs;
	private String crewSortOrder;
	private CursorAdapter mCrewAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mid = args.getString(Contract.MovieColumns.TMDB_ID);
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		moviesSelection = Contract.MovieColumns.TMDB_ID + " = " + mid;
		castSelection = CastColumns.TMDB_MOVIE_ID + " = " + mid;
		crewSelection = CrewColumns.TMDB_MOVIE_ID + " = " + mid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mParentView = inflater.inflate(R.layout.fragment_movie, null);
		mBackdropImageView = (ImageView) mParentView.findViewById(BACKDROP);
		mPosterImageView = (ImageView) mParentView.findViewById(POSTER);
		mCertifyImageView = (ImageView) mParentView.findViewById(CERTIFICATION);
		mTaglineTextView = (TextView) mParentView.findViewById(TAGLINE);
		mYearTextView = (TextView) mParentView.findViewById(YEAR);
		mInfoContainer = (ViewGroup) mParentView.findViewById(INFO_CONTAINER);
		mStoryContainer = (ViewGroup) mParentView
				.findViewById(STORYLINE_CONTAINER);
		mCastsContainer = (ViewGroup) mParentView.findViewById(CASTS_CONTAINER);
		mCrewContainer = (ViewGroup) mParentView.findViewById(CREW_CONTAINER);
		mSimilarContainer = (ViewGroup) mParentView
				.findViewById(SIMILAR_CONTAINER);
		mProductionContainer = (ViewGroup) mParentView
				.findViewById(PRODUCTION_CONTAINER);
		mLoaderId = hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, null,
				this);
		getActivity().getSupportLoaderManager().initLoader(mLoaderId + 1, null,
				this);
		// TODO create single provider for cast and crew
		getActivity().getSupportLoaderManager().initLoader(mLoaderId + 2, null,
				this);
		return mParentView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO load when data is older than 1 hour
		if (id == mLoaderId) {
			loadMovieData();
			return new CursorLoader(getActivity(), moviesUri, moviesProjection,
					moviesSelection, moviesSelectionArgs, moviesSortOrder);
		} else if (id == mLoaderId + 1) {
			loadCast();
			return new CursorLoader(getActivity(), castUri, castProjection,
					castSelection, castSelectionArgs, castSortOrder);
		} else if (id == mLoaderId + 2) {
			return new CursorLoader(getActivity(), crewUri, crewProjection,
					crewSelection, crewSelectionArgs, crewSortOrder);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// loadData();
		// if (cursor.getCount() == 0) {
		// loadData();
		// } else
		if (cursor.getCount() > 0)
			if (loader.getId() == mLoaderId) {

				Log.d(LOG_TAG,
						"onLoadFinished: movie " + cursor.getColumnCount());
				compliteView(cursor);
			} else if (loader.getId() == mLoaderId + 1) {
				mCastsContainer.setVisibility(View.VISIBLE);
				Log.d(LOG_TAG, "onLoadFinished: cast" + cursor.getCount());
				if (mCastHListView == null) {
					mCastHListView = (HListView) mParentView
							.findViewById(R.id.hlist_view_fragment_movie_cast);
					mCastAdapter = new CastHListAdapter(getActivity(), cursor);
					mCastHListView.setAdapter(mCastAdapter);
				} else
					mCastAdapter.swapCursor(cursor);
			} else if (loader.getId() == mLoaderId + 2) {
				mCrewContainer.setVisibility(View.VISIBLE);
				Log.d(LOG_TAG, "onLoadFinished: crew" + cursor.getCount());
				if (mCrewHListView == null) {
					mCrewHListView = (HListView) mParentView
							.findViewById(R.id.hlist_view_fragment_movie_crew);
					mCrewAdapter = new CrewHListAdapter(getActivity(), cursor);
					mCrewHListView.setAdapter(mCrewAdapter);
				} else
					mCastAdapter.swapCursor(cursor);
			}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (loader.getId() == mLoaderId + 1 && mCastAdapter != null) {
			mCastAdapter.swapCursor(null);
		} else if (loader.getId() == mLoaderId + 2 && mCrewAdapter != null) {
			mCrewAdapter.swapCursor(null);
		}

	}

	private void loadMovieData() {
		String url = MovieApis.TmdbApi.getMovie(mid, null);
		L.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_MOVIE_PROCESSOR_SERVICE);

	}

	private void loadCast() {
		getActivity().getContentResolver().delete(castUri, castSelection,
				castSelectionArgs);
		getActivity().getContentResolver().delete(crewUri, crewSelection,
				crewSelectionArgs);
		String url = TmdbApi.getMovieCasts(mid);
		L.d(LOG_TAG, "loadCast: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_CASTS_PROCESSOR_SERVICE, null);
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
			builder.append("Revenue ").append(String.format("%,d", revenue))
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
		UIHelper.setTextOrGone(mTaglineTextView, c,
				Contract.MovieColumns.TAGLINE);
	}

	private void setPoster(Cursor c) {
		String posterPath = getString(c, Contract.MovieColumns.POSTER_PATH);
		if (!TextUtils.isEmpty(posterPath)) {
			String poster = TmdbApi.getPoster(posterPath, TmdbApi.POSTER.W185);

			Log.d(LOG_TAG, "setPoster: " + poster);
			mImageLoader.loadBitmap(mPosterImageView, poster);
		}
	}

	private void setBackdrop(Cursor c) {
		String bdPath = getString(c, Contract.MovieColumns.BACKDROP_PATH);
		if (!TextUtils.isEmpty(bdPath)) {
			String backdrop = TmdbApi
					.getBackdrop(bdPath, TmdbApi.BACKDROP.W780);
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

		// Log.d(LOG_TAG, "getString:" + column + " " + s);
		return s;
	}

	private int getInt(Cursor cursor, String column) {
		return cursor.getInt(cursor.getColumnIndex(column));
	}

}
