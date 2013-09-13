package com.sickfuture.letswatch.app.fragment.tmdb.people;

import java.io.InputStream;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.helpers.UIHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonFragment extends Fragment implements LoaderCallbacks<Cursor> {

	private static final String LOG_TAG = PersonFragment.class.getSimpleName();

	private RecyclingImageView mProfileImageView;
	private TextView mBioTextView, mInfoTextView;
	private String sortOrder;
	private String[] selectionArgs;
	private String selection;
	private String[] projection;
	private Uri uri;
	private SickImageLoader mImageLoader;
	private String pid;

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
		getLoaderManager().initLoader(mLoaderId, null, this);
		return parent;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		loadData();
		return new CursorLoader(getActivity(), uri, projection, selection,
				selectionArgs, sortOrder);
	}

	private void loadData() {
		String url = MovieApis.TmdbApi.getPerson(pid);
		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_PERSON_PROCESSOR_SERVICE);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.getCount() > 0) {
			completeView(cursor);
		}

	}

	private void completeView(Cursor c) {
		if (c.moveToFirst() && getActivity() != null) {
			String posterPath = c.getString(c
					.getColumnIndex(PersonColumns.PROFILE_PATH));
			if (!TextUtils.isEmpty(posterPath)) {
				UIHelper.setImage(mProfileImageView, mImageLoader,
						TmdbApi.getProfile(posterPath, PROFILE.W185));
			}
			UIHelper.setTextOrInvisible(mInfoTextView, c,
					PersonColumns.BIRTHDAY);
			UIHelper.setTextOrGone(mBioTextView, c, PersonColumns.BIIOGRAPHY);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}

}
