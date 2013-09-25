package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;

public class CredsCursorAdapter  extends BaseCursorAdapter {

	private static final int TEXT_VIEW_MOVIE_TITLE = R.id.text_view_adapter_poster_title;
	private static final int IMAGE_VIEW_POSTER = R.id.image_view_adapter_poster;
	private SickImageLoader mImageLoader;
	
	public CredsCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void bindData(View view, Context context, Cursor c,
			ViewHolder holder) {
		RecyclingImageView imageView = (RecyclingImageView) holder
				.getViewById(IMAGE_VIEW_POSTER);
		String path = c.getString(c
				.getColumnIndex(CastColumns.MOVIE_POSTER_PATH));
		if (!TextUtils.isEmpty(path)) {
			mImageLoader.loadBitmap(imageView,
					TmdbApi.getPoster(path, POSTER.W185));
		}
		TextView titleView = (TextView) holder.getViewById(TEXT_VIEW_MOVIE_TITLE);
		String title = c.getString(c.getColumnIndex(CastColumns.MOVIE_TITLE));
		titleView.setText(title);
	}
	
	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_MOVIE_TITLE, IMAGE_VIEW_POSTER };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_posters, null);
	}


}
