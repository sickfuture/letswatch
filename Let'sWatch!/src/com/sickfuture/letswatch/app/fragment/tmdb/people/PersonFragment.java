package com.sickfuture.letswatch.app.fragment.tmdb.people;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.io.InputStream;
import java.text.DateFormat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.CredsHListAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.tmdb.MovieActivity;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.helpers.AgeHelper;
import com.sickfuture.letswatch.helpers.UIHelper;

public class PersonFragment extends Fragment implements
		LoaderCallbacks<Cursor>, OnItemClickListener {

	private static final String LOG_TAG = PersonFragment.class.getSimpleName();

	private static final int HLIST_CREW = R.id.hlist_view_fragment_person_crew;
	private static final int HLIST_CAST = R.id.hlist_view_fragment_person_cast;
	private static final int CASTS_CONTAINER = R.id.layout_fragment_person_casts;
	private static final int CREW_CONTAINER = R.id.layout_fragment_person_crew;

	private ViewGroup mCrewContainer, mCastsContainer;
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
	private String castSortOrder;

	private static final Uri crewUri = ContractUtils
			.getProviderUriFromContract(CrewColumns.class);
	private String[] crewProjection;
	private String crewSelection;
	private String[] crewSelectionArgs;
	private String crewSortOrder;

	private int mLoaderId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoaderId = hashCode();
		Bundle args = getArguments();
		pid = args.getString(Contract.PersonColumns.TMDB_ID);
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		uri = ContractUtils
				.getProviderUriFromContract(Contract.PersonColumns.class);
		selection = Contract.PersonColumns.TMDB_ID + " = " + pid;
		castSelection = CastColumns.TMDB_PERSON_ID + " = " + pid;
		crewSelection = CrewColumns.TMDB_PERSON_ID + " = " + pid;
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
		mCrewHListView = (HListView) parent.findViewById(HLIST_CREW);
		mCrewAdapter = new CredsHListAdapter(getActivity(), null);
		mCrewHListView.setAdapter(mCrewAdapter);
		mCrewHListView.setOnItemClickListener(this);

		mCastsContainer = (ViewGroup) parent.findViewById(CASTS_CONTAINER);
		mCastHListView = (HListView) parent.findViewById(HLIST_CAST);
		mCastAdapter = new CredsHListAdapter(getActivity(), null);
		mCastHListView.setAdapter(mCastAdapter);
		mCastHListView.setOnItemClickListener(this);

		getLoaderManager().initLoader(mLoaderId, null, this);
		getLoaderManager().initLoader(mLoaderId + 1, null, this);
		getLoaderManager().initLoader(mLoaderId + 2, null, this);
		return parent;
	}

	private void loadData() {
		String url = MovieApis.TmdbApi.getPerson(pid, "credits");
		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_PERSON_PROCESSOR_SERVICE);
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
			// loadCast();
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
				L.d(LOG_TAG, "onLoadFinished: cast");
				mCastsContainer.setVisibility(View.VISIBLE);
				mCastAdapter.swapCursor(cursor);
			} else if (loader.getId() == mLoaderId + 2) {
				L.d(LOG_TAG, "onLoadFinished: crew");
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
				c.getString(c.getColumnIndex(CastColumns.TMDB_MOVIE_ID)));
		startActivity(intent);

	}

}
