package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class PeopleGridCursorAdapter extends BaseCursorAdapter {

	public static final int TEXT_VIEW_NAME = R.id.text_view_person_name;
	public static final int IMAGE_VIEW_PHOTO = R.id.image_view_person_photo;

	private SickImageLoader mImageLoader;

	public PeopleGridCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void bindData(View view, Context context, Cursor c, ViewHolder holder) {
		String name = c
				.getString(c.getColumnIndex(Contract.PersonColumns.NAME));
		String poster = c.getString(c
				.getColumnIndex(Contract.PersonColumns.PROFILE_PATH));
		((TextView) holder.getViewById(TEXT_VIEW_NAME)).setText(name);
		ImageView posterView = (ImageView) holder.getViewById(IMAGE_VIEW_PHOTO);
		posterView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		mImageLoader.loadBitmap(posterView, MovieApis.TmdbApi.getPoster(poster, POSTER.W185));
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_NAME, IMAGE_VIEW_PHOTO };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_person_grid, null);
	}

}