package com.sickfuture.letswatch.app.fragment.tmdb.people;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.CursorUtils;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.CredsHListAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.tmdb.MovieActivity;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.provider.tmdb.CastProvider;
import com.sickfuture.letswatch.content.provider.tmdb.CrewProvider;
import com.sickfuture.letswatch.helpers.AgeHelper;
import com.sickfuture.letswatch.helpers.UIHelper;

public class PersonFragment extends CommonFragment implements
		LoaderCallbacks<Cursor>, OnItemClickListener, OnClickListener {

	private static final String LOG_TAG = PersonFragment.class.getSimpleName();

	public static final String PERSON_ID = "person_id";

	private static final int HLIST_CREW = R.id.hlist_view_fragment_person_crew;
	private static final int HLIST_CAST = R.id.hlist_view_fragment_person_cast;
	private static final int CASTS_CONTAINER = R.id.layout_fragment_person_casts;
	private static final int CREW_CONTAINER = R.id.layout_fragment_person_crew;
	private static final int PR_BAR_CAST = R.id.progress_bar_fragment_person_cast;
	private static final int PR_BAR_CREW = R.id.progress_bar_fragment_person_crew;

	private static final String moviesTable = DatabaseUtils
			.getTableNameFromContract(MovieColumns.class);
	private static final String castTable = DatabaseUtils
			.getTableNameFromContract(CastColumns.class);
	private static final String crewTable = DatabaseUtils
			.getTableNameFromContract(CrewColumns.class);

	// private ViewGroup mCrewContainer, mCastsContainer;
	private HListView mCastHListView, mCrewHListView;
	private RecyclingImageView mProfileImageView;
	private TextView mBioTextView, mInfoTextView;
	private String sortOrder;
	private String[] selectionArgs;
	private String selection;
	private String[] projection;
	private Uri uri;
	private SickImageLoader mImageLoader;
	private String pid;

	private CursorAdapter mCrewAdapter, mCastAdapter;

	private static final Uri castUri = ContractUtils
			.getProviderUriFromContract(CastColumns.class);
	private String[] castProjection;
	private String castSelection;
	private String[] castSelectionArgs;
	private String castSortOrder = CastProvider.MOVIE_ID;

	private static final Uri crewUri = ContractUtils
			.getProviderUriFromContract(CrewColumns.class);
	private String[] crewProjection;
	private String crewSelection;
	private String[] crewSelectionArgs;
	private String crewSortOrder = CrewProvider.MOVIE_ID;

	private int mLoaderId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mLoaderId = hashCode();
		Bundle args = getArguments();
		pid = args.getString(PERSON_ID);
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		uri = ContractUtils
				.getProviderUriFromContract(Contract.PersonColumns.class);
		selection = Contract.PersonColumns.TMDB_ID + " = " + pid;
		castSelection = CastProvider.PERSON_ID + " = " + pid;
		crewSelection = CrewProvider.PERSON_ID + " = " + pid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parent = inflater.inflate(R.layout.fragment_person, null);
		mProfileImageView = (RecyclingImageView) parent
				.findViewById(R.id.image_view_fragment_person_photo);
		mInfoTextView = (TextView) parent
				.findViewById(R.id.text_view_fragment_person_age_info);
		mBioTextView = (TextView) parent
				.findViewById(R.id.text_view_fragment_person_bio);
		mCrewContainer = (ViewGroup) parent.findViewById(CREW_CONTAINER);
		mCrewContainer.setOnClickListener(this);
		mCrewHListView = (HListView) parent.findViewById(HLIST_CREW);
		mCrewAdapter = new CredsHListAdapter(getActivity(), null);
		mCrewHListView.setAdapter(mCrewAdapter);
		mCrewHListView.setOnItemClickListener(this);

		mCastsContainer = (ViewGroup) parent.findViewById(CASTS_CONTAINER);
		mCastsContainer.setOnClickListener(this);
		mCastHListView = (HListView) parent.findViewById(HLIST_CAST);
		mCastAdapter = new CredsHListAdapter(getActivity(), null);
		mCastHListView.setAdapter(mCastAdapter);
		mCastHListView.setOnItemClickListener(this);

		mProgressBarCast = (ProgressBar) parent.findViewById(PR_BAR_CAST);
		mProgressBarCrew = (ProgressBar) parent.findViewById(PR_BAR_CREW);

		return parent;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		getLoaderManager().initLoader(mLoaderId, null, this);
		getLoaderManager().initLoader(mLoaderId + 1, null, this);
		getLoaderManager().initLoader(mLoaderId + 2, null, this);

	}

	private void loadData() {
		// getActivity().getContentResolver().delete(castUri,
		// CastColumns.TMDB_PERSON_ID + " = " + pid, castSelectionArgs);
		// getActivity().getContentResolver().delete(crewUri,
		// CrewColumns.TMDB_PERSON_ID + " = " + pid, crewSelectionArgs);
		String url = MovieApis.TmdbApi.getPerson(pid);
		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_PERSON_PROCESSOR_SERVICE);
	}

	private void loadCreds() {
		String url = TmdbApi.getPersonCredits(pid, Locale.getDefault()
				.getLanguage());
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_PERSON_CREDITS_PROCESSOR_SERVICE,
				mReceiverCasts);
	}

	private void compliteView(Cursor c) {
		if (c.moveToFirst() && getActivity() != null) {
			String posterPath = c.getString(c
					.getColumnIndex(PersonColumns.PROFILE_PATH));
			if (!TextUtils.isEmpty(posterPath)) {
				UIHelper.setImage(mProfileImageView, mImageLoader,
						TmdbApi.getProfile(posterPath, PROFILE.W185));
			}
			String bDay = c.getString(c.getColumnIndex(PersonColumns.BIRTHDAY));
			int age = AgeHelper.getAge(bDay);
			bDay = AgeHelper.formatDate(bDay, DateFormat.MEDIUM);
			if (age > 0) {
				bDay += "\nAge: " + age;
			}
			UIHelper.setTextOrInvisible(mInfoTextView, bDay);
			UIHelper.setTextOrGone(mBioTextView, c, PersonColumns.BIIOGRAPHY);
		}

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id == mLoaderId) {
			loadData();
			return new CursorLoader(getActivity(), uri, projection, selection,
					selectionArgs, sortOrder);
		} else if (id == mLoaderId + 1) {
			loadCreds();
			L.d(LOG_TAG, "onCreateLoader: cast");
			return new CursorLoader(getActivity(), castUri, castProjection,
					castSelection, castSelectionArgs, castSortOrder);
		} else if (id == mLoaderId + 2) {
			L.d(LOG_TAG, "onCreateLoader: crew");
			return new CursorLoader(getActivity(), crewUri, crewProjection,
					crewSelection, crewSelectionArgs, crewSortOrder);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.getCount() > 0) {
			if (loader.getId() == mLoaderId) {
				compliteView(cursor);
			} else if (loader.getId() == mLoaderId + 1) {
				
				if (cursor.getCount() > 0) {
					mProgressBarCast.setVisibility(View.GONE);
				}
				mCastAdapter.swapCursor(cursor);
			} else if (loader.getId() == mLoaderId + 2) {
				if (cursor.getCount() > 0) {
					mProgressBarCrew.setVisibility(View.GONE);
				}
				mCrewContainer.setVisibility(View.VISIBLE);
				mCrewAdapter.swapCursor(cursor);
			}
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (loader.getId() == mLoaderId + 2) {
			mCrewAdapter.swapCursor(null);
		} else if (loader.getId() == mLoaderId + 1 && mCastAdapter != null) {
			mCastAdapter.swapCursor(null);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Cursor c = (Cursor) parent.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(), MovieActivity.class);
		intent.putExtra(CastColumns.TMDB_MOVIE_ID,
				c.getString(c.getColumnIndex(CastProvider.MOVIE_ID)));
		startActivity(intent);

	}

	@Override
	public void onClick(View view) {

		if (view.getId() == CASTS_CONTAINER) {
			Intent intent = new Intent(getActivity(), MovieActivity.class);
			intent.putExtra("cast_pid", pid);
			startActivity(intent);
		} else if (view.getId() == CREW_CONTAINER) {
			Intent intent = new Intent(getActivity(), MovieActivity.class);
			intent.putExtra("crew_pid", pid);
			startActivity(intent);
		}
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (menu != null) {
			menu.removeItem(R.id.menu_refresh);
		}

	}
}
