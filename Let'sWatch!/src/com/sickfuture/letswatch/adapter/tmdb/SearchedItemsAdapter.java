package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.contract.Contract.SearchedMoviesColumns;
import com.sickfuture.letswatch.content.contract.Contract.SearchedPersonsColumns;

public class SearchedItemsAdapter extends BaseCursorAdapter {

	private static final String LOG_TAG = SearchedItemsAdapter.class
			.getSimpleName();

	public SearchedItemsAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	public static final int TEXT_VIEW_NAME = R.id.text_view_adapter_poster_title;
	public static final int TEXT_VIEW_INFO = R.id.text_view_adapter_poster_addit_info;
	public static final int IMAGE_VIEW_PHOTO = R.id.image_view_adapter_poster;

	private static final String personTable = DatabaseUtils
			.getTableNameFromContract(Contract.PersonColumns.class);
	private static final String PERSON_PROFILE_PATH_COLUMN = personTable + "."
			+ PersonColumns.PROFILE_PATH;
	private static final String PERSON_NAME_COLUMN = personTable + "."
			+ PersonColumns.NAME;

	private static final String movieTable = DatabaseUtils
			.getTableNameFromContract(Contract.MovieColumns.class);
	private static final String MOVIE_POSTER_PATH_COLUMN = movieTable + "."
			+ MovieColumns.POSTER_PATH;
	private static final String MOVIE_TITLE_COLUMN = movieTable + "."
			+ MovieColumns.TITLE;
	private static final String MOVIE_TITLE_ORIG_COLUMN = movieTable + "."
			+ MovieColumns.TITLE_ORIGINAL;
	private static final String MOVIE_VOTE_AVER_COLUMN = movieTable + "."
			+ MovieColumns.VOTE_AVERAGE;

	private static final String searchedMoviesTable = DatabaseUtils
			.getTableNameFromContract(Contract.SearchedMoviesColumns.class);
	private static final String SEARCHED_M_ID = 
			searchedMoviesTable + "."
			+ 
			SearchedMoviesColumns.MOVIE_ID;
	private static final String searchedPeopleTable = DatabaseUtils
			.getTableNameFromContract(Contract.SearchedPersonsColumns.class);
	private static final String SEARCHED_P_ID =
			searchedPeopleTable + "."
			+ 
			SearchedPersonsColumns.PERSON_ID;

	private SickImageLoader mImageLoader;

	@Override
	public void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder) {

		if (cursor.getColumnIndex(SEARCHED_M_ID) > -1) {
			bindAsMovie(view, context, cursor, holder);
		} else if (cursor.getColumnIndex(SEARCHED_P_ID) > -1) {
			bindAsPerson(view, context, cursor, holder);
		} else {
			L.d(LOG_TAG, "bindData: cant bind");
		}

	}

	private void bindAsMovie(View view, Context context, Cursor cursor,
			ViewHolder holder) {
		TextView titleView = (TextView) holder.getViewById(TEXT_VIEW_NAME);
		String title = cursor.getString(cursor
				.getColumnIndex(MOVIE_TITLE_COLUMN));
		String titleOrig = cursor.getString(cursor
				.getColumnIndex(MOVIE_TITLE_ORIG_COLUMN));
		titleView.setText(TextUtils.isEmpty(title) ? titleOrig : title);
		TextView ratingView = (TextView) holder.getViewById(TEXT_VIEW_INFO);
		String rating = cursor.getString(cursor
				.getColumnIndex(MOVIE_VOTE_AVER_COLUMN));
		float r = Float.parseFloat(rating);
		if (!TextUtils.isEmpty(rating) && r > 0) {
			ratingView.setText(String.format("%.1f", r));
			ratingView.setVisibility(View.VISIBLE);
		} else {
			ratingView.setVisibility(View.INVISIBLE);
		}
		RecyclingImageView posterImageView = (RecyclingImageView) holder
				.getViewById(IMAGE_VIEW_PHOTO);
		String posterPath = cursor.getString(cursor
				.getColumnIndex(MOVIE_POSTER_PATH_COLUMN));
		mImageLoader.loadBitmap(posterImageView,
				TmdbApi.getPoster(posterPath, POSTER.W185));

	}

	private void bindAsPerson(View view, Context context, Cursor c,
			ViewHolder holder) {
		String name = c.getString(c.getColumnIndex(PERSON_NAME_COLUMN));
		String poster = c.getString(c
				.getColumnIndex(PERSON_PROFILE_PATH_COLUMN));
		((TextView) holder.getViewById(TEXT_VIEW_NAME)).setText(name);
		ImageView posterView = (ImageView) holder.getViewById(IMAGE_VIEW_PHOTO);
		posterView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		mImageLoader.loadBitmap(posterView,
				MovieApis.TmdbApi.getPoster(poster, POSTER.W185));

	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_NAME, IMAGE_VIEW_PHOTO, TEXT_VIEW_INFO };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_posters, null);
	}

}
