package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
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
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class MoviesGridCursorAdapter extends BaseCursorAdapter {

	public static final int TEXT_VIEW_MOVIE_TITLE = R.id.text_view_section_title;
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

		mPosterImageView = (RecyclingImageView) holder
				.getViewById(IMAGE_VIEW_POSTER);
		String posterUrl = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w300"+cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
		mPosterImageView.setScaleType(ScaleType.CENTER_CROP);
		mImageLoader.loadBitmap(mPosterImageView, posterUrl);

	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_MOVIE_TITLE, IMAGE_VIEW_POSTER };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.view_movie_main_fragment, null);
	}

}
