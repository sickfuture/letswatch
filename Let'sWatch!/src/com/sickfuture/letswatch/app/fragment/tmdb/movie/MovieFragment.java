package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.CastHListAdapter;
import com.sickfuture.letswatch.adapter.tmdb.CrewHListAdapter;
import com.sickfuture.letswatch.adapter.tmdb.SimilarHListAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.tmdb.PeopleActivity;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.people.CastFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.people.CrewFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.helpers.UIHelper;

public class MovieFragment extends Fragment implements LoaderCallbacks<Cursor>,
		OnItemClickListener, OnClickListener {

	private static final String LOG_TAG = MovieFragment.class.getSimpleName();

	private static final int HLIST_MOVIE_CREW = R.id.hlist_view_fragment_movie_crew;
	private static final int HLIST_MOVIE_CAST = R.id.hlist_view_fragment_movie_cast;
	private static final int HLIST_SIMILAR = R.id.hlist_view_fragment_movie_similar;
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
	private RecyclingImageView mBackdropImageView, mPosterImageView;
	private ImageView mCertifyImageView;
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

	private String mSimilarIds;
	private String similarSelection;
	private String[] similarProjection = new String[] { MovieColumns._ID,
			MovieColumns.TMDB_ID, MovieColumns.TITLE,
			MovieColumns.TITLE_ORIGINAL, MovieColumns.POSTER_PATH };
	private String similarSortOrder;
	private String[] similarSelectionArgs;
	private CursorAdapter mSimilarAdapter;
	private boolean similarLoaderInited = false;

	private static final String movTable = DatabaseUtils
			.getTableNameFromContract(MovieColumns.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Bundle args = getArguments();
		mid = args.getString(Contract.MovieColumns.TMDB_ID);
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		moviesSelection = movTable + "." + Contract.MovieColumns.TMDB_ID
				+ " = " + mid;
		castSelection = CastColumns.TMDB_MOVIE_ID + " = " + mid;
		crewSelection = CrewColumns.TMDB_MOVIE_ID + " = " + mid;
		// similarSelection = movTable + "." + Contract.MovieColumns.TMDB_ID
		// + " IN (" + mSimilarIds + ")";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mParentView = inflater.inflate(R.layout.fragment_movie, null);
		mBackdropImageView = (RecyclingImageView) mParentView
				.findViewById(BACKDROP);
		mPosterImageView = (RecyclingImageView) mParentView
				.findViewById(POSTER);
		mCertifyImageView = (ImageView) mParentView.findViewById(CERTIFICATION);
		mTaglineTextView = (TextView) mParentView.findViewById(TAGLINE);
		mYearTextView = (TextView) mParentView.findViewById(YEAR);
		mInfoContainer = (ViewGroup) mParentView.findViewById(INFO_CONTAINER);
		mStoryContainer = (ViewGroup) mParentView
				.findViewById(STORYLINE_CONTAINER);

		mSimilarContainer = (ViewGroup) mParentView
				.findViewById(SIMILAR_CONTAINER);
		mSimilarContainer.setOnClickListener(this);
		mSimilarHListView = (HListView) mParentView.findViewById(HLIST_SIMILAR);
		mSimilarAdapter = new SimilarHListAdapter(getActivity(), null);
		mSimilarHListView.setAdapter(mSimilarAdapter);
		mSimilarHListView.setOnItemClickListener(this);

		mCrewContainer = (ViewGroup) mParentView.findViewById(CREW_CONTAINER);
		mCrewContainer.setOnClickListener(this);
		mCrewHListView = (HListView) mParentView.findViewById(HLIST_MOVIE_CREW);
		mCrewAdapter = new CrewHListAdapter(getActivity(), null);
		mCrewHListView.setAdapter(mCrewAdapter);
		mCrewHListView.setOnItemClickListener(this);

		mCastsContainer = (ViewGroup) mParentView.findViewById(CASTS_CONTAINER);
		mCastsContainer.setOnClickListener(this);
		mCastHListView = (HListView) mParentView.findViewById(HLIST_MOVIE_CAST);
		mCastAdapter = new CastHListAdapter(getActivity(), null);
		mCastHListView.setAdapter(mCastAdapter);
		mCastHListView.setOnItemClickListener(this);

		mProductionContainer = (ViewGroup) mParentView
				.findViewById(PRODUCTION_CONTAINER);
		mLoaderId = hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, null,
				this);
		getActivity().getSupportLoaderManager().initLoader(mLoaderId + 1, null,
				this);
		getActivity().getSupportLoaderManager().initLoader(mLoaderId + 2, null,
				this);
		getActivity().getSupportLoaderManager().initLoader(mLoaderId + 3, null,
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
		} else if (id == mLoaderId + 3) {
			similarSelection = movTable + "." + Contract.MovieColumns.TMDB_ID
					+ " IN (" + mSimilarIds + ")";
			return new CursorLoader(getActivity(), moviesUri,
					similarProjection, similarSelection, similarSelectionArgs,
					similarSortOrder);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		L.d(LOG_TAG, "onLoadFinished: ");
		if (cursor.getCount() > 0)
			if (loader.getId() == mLoaderId) {
				compliteView(cursor);
			} else if (loader.getId() == mLoaderId + 1) {
				mCastsContainer.setVisibility(View.VISIBLE);
				mCastAdapter.swapCursor(cursor);
			} else if (loader.getId() == mLoaderId + 2) {
				mCrewContainer.setVisibility(View.VISIBLE);
				mCrewAdapter.swapCursor(cursor);
			} else if (loader.getId() == mLoaderId + 3) {
				mSimilarContainer.setVisibility(View.VISIBLE);
				mSimilarAdapter.swapCursor(cursor);
			}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (loader.getId() == mLoaderId + 3) {
			mSimilarAdapter.swapCursor(null);
		} else if (loader.getId() == mLoaderId + 1 && mCastAdapter != null) {
			mCastAdapter.swapCursor(null);
		} else if (loader.getId() == mLoaderId + 2 && mCrewAdapter != null) {
			mCrewAdapter.swapCursor(null);
		}

	}

	private void loadMovieData() {
		String url = MovieApis.TmdbApi.getMovie(mid, Locale.getDefault()
				.getLanguage(), "similar_movies");
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
			mSimilarIds = c.getString(c
					.getColumnIndex(MovieColumns.SIMILAR_IDS));
			if (!TextUtils.isEmpty(mSimilarIds) && !similarLoaderInited) {
				L.d(LOG_TAG, "compliteView: simIds" + mSimilarIds);
				getActivity().getSupportLoaderManager().restartLoader(
						mLoaderId + 3, null, this);
				similarLoaderInited = true;
			}
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Cursor c = (Cursor) parent.getItemAtPosition(position);
		if (parent.getId() == HLIST_MOVIE_CAST
				|| parent.getId() == HLIST_MOVIE_CREW) {
			Intent intent = new Intent(getActivity(), PeopleActivity.class);
			intent.putExtra(CastColumns.TMDB_PERSON_ID,
					c.getString(c.getColumnIndex(CastColumns.TMDB_PERSON_ID)));
			startActivity(intent);
		} else if (parent.getId() == HLIST_SIMILAR) {
			Bundle args = new Bundle();
			args.putString(MovieColumns.TMDB_ID,
					c.getString(c.getColumnIndex(MovieColumns.TMDB_ID)));
			((IListClickable) getActivity()).onItemListClick(args);
		}

	}

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof IListClickable))
			throw new IllegalArgumentException(
					"Activity must implements IListClickable");
		super.onAttach(activity);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == CASTS_CONTAINER) {
			Intent intent = new Intent(getActivity(), PeopleActivity.class);
			intent.putExtra("cast_mid", mid);
			startActivity(intent);
		} else if (view.getId() == CREW_CONTAINER) {
			Intent intent = new Intent(getActivity(), PeopleActivity.class);
			intent.putExtra("crew_mid", mid);
			startActivity(intent);
		} else if (view.getId() == SIMILAR_CONTAINER) {
			Bundle args = new Bundle();
			args.putString("ids", mSimilarIds);
			Fragment fragment = new SimilarMoviesFragment();
			fragment.setArguments(args);
			FragmentManager fragmentManager = getActivity()
					.getSupportFragmentManager();
			fragmentManager.beginTransaction().addToBackStack(null)
			.replace(R.id.content_frame, fragment).commit();
		}

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if(menu!=null){
			menu.removeItem(R.id.menu_refresh);
		}
			
	}
}
