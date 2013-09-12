package com.sickfuture.letswatch.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
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

public class MoviesSectionsGridAdapter extends BaseCursorAdapter {

	private static final int ITEM_IMAGE_VIEW = R.id.image_view_movies_sections_grid_item;

	private RecyclingImageView mImageView;

	private SickImageLoader mImageLoader;

	public MoviesSectionsGridAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder) {
		mImageView = (RecyclingImageView) holder.getViewById(ITEM_IMAGE_VIEW);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		mImageView.setScaleType(ScaleType.CENTER_CROP);
		String path = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
		String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);
		mImageLoader.loadBitmap(mImageView, posterUrl);
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { ITEM_IMAGE_VIEW };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View
				.inflate(context, R.layout.adapter_movie_sections_grid, null);
	}

}
