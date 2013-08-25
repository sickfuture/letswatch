package com.sickfuture.letswatch.app.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class InfoFragment extends ListFragment implements
		LoaderCallbacks<Cursor>, OnClickListener {

	private SickImageLoader mImageLoader;

	private View mInfoHeaderView;
	private RecyclingImageView mPosterImageView;
	private TextView mYearTextView, mMpaaTextView, mCriticsTextView,
			mAudienceTextView, mDescripTextView, mRuntimeTextView;
	private ListView mListView;
	private Button mFavButton;

	private int mId, mIsFavorite;

//	private static final String[] PROJECTION = new String[] {
//			Contract.MovieColumns.MOVIE_ID, Contract.MovieColumns.MOVIE_TITLE,
//			Contract.MovieColumns.YEAR, Contract.MovieColumns.RUNTIME,
//			Contract.MovieColumns.RATING_CRITICS,
//			Contract.MovieColumns.RATING_AUDIENCE,
//			Contract.MovieColumns.SYNOPSIS,
//			Contract.MovieColumns.POSTERS_DETAILED };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		View view = inflater
				.inflate(R.layout.fragment_movie_details_info, null);
		mInfoHeaderView = inflater.inflate(R.layout.view_movie_details_header,
				null);
		mListView = (ListView) view.findViewById(android.R.id.list);
		mListView.addHeaderView(mInfoHeaderView);
		mYearTextView = (TextView) mInfoHeaderView
				.findViewById(R.id.textview_year_movie_details_header);
		mMpaaTextView = (TextView) mInfoHeaderView
				.findViewById(R.id.textview_mpaa_movie_details_header);
		mCriticsTextView = (TextView) mInfoHeaderView
				.findViewById(R.id.textview_critics_movie_details_header);
		mAudienceTextView = (TextView) mInfoHeaderView
				.findViewById(R.id.textview_audience_movie_details_header);
		mDescripTextView = (TextView) mInfoHeaderView
				.findViewById(R.id.textview_descript_movie_details_header);
		mRuntimeTextView = (TextView) mInfoHeaderView
				.findViewById(R.id.textview_runtime_movie_details_header);
		mPosterImageView = (RecyclingImageView) mInfoHeaderView
				.findViewById(R.id.imageview_movie_details_header);
		mFavButton = (Button) mInfoHeaderView
				.findViewById(R.id.button_movie_details_favorite);
		mFavButton.setOnClickListener(this);
		loadInfoValues();
		mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.adapter_movie_details_critics_replies));
		return view;
	}

	private void loadInfoValues() {
		Uri uri = ContractUtils
				.getProviderUriFromContract(Contract.MovieColumns.class);
		Cursor cursor = getActivity().getContentResolver().query(uri,
				null, Contract.MovieColumns.ROTTEN_ID + " = ?",
				new String[] { String.valueOf(mId) }, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				mYearTextView.setText(cursor.getString(cursor
						.getColumnIndex(Contract.MovieColumns.YEAR)));
				mRuntimeTextView.setText(cursor.getString(cursor
						.getColumnIndex(Contract.MovieColumns.RUNTIME)));
				mMpaaTextView.setText(cursor.getString(cursor
						.getColumnIndex(Contract.MovieColumns.MPAA)));
				mAudienceTextView
						.setText(cursor.getString(cursor
								.getColumnIndex(Contract.MovieColumns.RATING_AUDIENCE)));
				mCriticsTextView.setText(cursor.getString(cursor
						.getColumnIndex(Contract.MovieColumns.RATING_CRITICS)));
				mDescripTextView.setText(cursor.getString(cursor
						.getColumnIndex(Contract.MovieColumns.SYNOPSIS)));
				mIsFavorite = cursor.getInt(cursor
						.getColumnIndex(Contract.MovieColumns.IS_FAVORITE));
				mFavButton
						.setBackgroundResource(mIsFavorite == 1 ? android.R.drawable.star_big_on
								: android.R.drawable.star_big_off);
				mImageLoader
						.loadBitmap(
								mPosterImageView,
								cursor.getString(cursor
										.getColumnIndex(Contract.MovieColumns.POSTERS_DETAILED)));
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mId = getArguments().getInt(Contract.ID);
		super.onCreate(savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		Uri uri = ContractUtils
				.getProviderUriFromContract(Contract.MovieColumns.class);
		int newVal = mIsFavorite == 1 ? 0 : 1;
		ContentValues values = new ContentValues();
		values.put(Contract.MovieColumns.IS_FAVORITE, newVal);
		String where = Contract.MovieColumns.ROTTEN_ID + " = " + mId;
		getActivity().getContentResolver().update(uri, values, where, null);
		mIsFavorite = newVal;
		mFavButton
				.setBackgroundResource(mIsFavorite == 1 ? android.R.drawable.star_big_on
						: android.R.drawable.star_big_off);

	}

}
