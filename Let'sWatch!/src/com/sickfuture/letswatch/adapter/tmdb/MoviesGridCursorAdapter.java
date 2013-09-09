package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

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

	public static final int TEXT_VIEW_MOVIE_TITLE = R.id.text_view_section_title;
	public static final int TEXT_VIEW_RATING = R.id.text_view_rating;
	public static final int IMAGE_VIEW_POSTER = R.id.image_view_section_backdrop;

	private SickImageLoader mImageLoader;
	private RecyclingImageView mPosterImageView;

	public MoviesGridCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder) {
		TextView title = (TextView) holder.getViewById(TEXT_VIEW_MOVIE_TITLE);
		title.setText(cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.TITLE)));
		TextView ratingView = (TextView) holder.getViewById(TEXT_VIEW_RATING);
		String rating = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.VOTE_AVERAGE));
		float r = Float.parseFloat(rating);
		if (!TextUtils.isEmpty(rating) && r > 0) {
			ratingView.setText(String.format("%.1f", r));
			ratingView.setVisibility(View.VISIBLE);
		} else {
			ratingView.setVisibility(View.INVISIBLE);
		}
		mPosterImageView = (RecyclingImageView) holder
				.getViewById(IMAGE_VIEW_POSTER);
		String path = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
		String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);// "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w300";
		mPosterImageView.setScaleType(ScaleType.CENTER_CROP);
		mImageLoader.loadBitmap(mPosterImageView, posterUrl);

	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_MOVIE_TITLE, IMAGE_VIEW_POSTER,
				TEXT_VIEW_RATING };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_movie_grid, null);
	}

}
