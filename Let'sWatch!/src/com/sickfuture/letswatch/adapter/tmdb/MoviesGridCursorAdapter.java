package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.BACKDROP;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class MoviesGridCursorAdapter extends BaseCursorAdapter {

	public static final int TEXT_VIEW_MOVIE_TITLE = R.id.text_view_adapter_backdrop_title;
	public static final int TEXT_VIEW_RATING = R.id.text_view_adapter_backdrop_addit_info;
	public static final int IMAGE_VIEW_POSTER = R.id.image_view_adapter_backdrop;

	private SickImageLoader mImageLoader;

	public MoviesGridCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder) {
		TextView title = (TextView) holder.getViewById(getTitleViewRes());
		title.setText(cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.TITLE)));
		TextView ratingView = (TextView) holder.getViewById(getRatingViewRes());
		String rating = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.VOTE_AVERAGE));
		float r = Float.parseFloat(rating);
		if (!TextUtils.isEmpty(rating) && r > 0) {
			ratingView.setText(String.format("%.1f", r));
			ratingView.setVisibility(View.VISIBLE);
		} else {
			ratingView.setVisibility(View.GONE);
		}
		RecyclingImageView posterImageView = (RecyclingImageView) holder
				.getViewById(getImageRes());
		String posterUrl = getImageUrl(cursor);
		mImageLoader.loadBitmap(posterImageView, posterUrl);

	}

	protected String getImageUrl(Cursor cursor) {
		String path = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
		String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);
		return posterUrl;
	}

	protected int getImageRes() {
		return IMAGE_VIEW_POSTER;
	}

	protected int getRatingViewRes() {
		return TEXT_VIEW_RATING;
	}

	protected int getTitleViewRes() {
		return TEXT_VIEW_MOVIE_TITLE;
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { getTitleViewRes(), getImageRes(), getRatingViewRes() };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, getAdapterRes(), null);
	}

	protected int getAdapterRes() {
		return R.layout.adapter_backdrops;
	}

}
