package com.sickfuture.letswatch.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class FavoritesGridAdapter extends BaseCursorAdapter {
	
	private static final String LOG_TAG = FavoritesGridAdapter.class
			.getSimpleName();

	public static final int TEXT_VIEW_MOVIE_TITLE = R.id.text_view_movie_title_grid;
	public static final int IMAGE_VIEW_POSTER = R.id.image_view_poster_favorites_grid;

	private SickImageLoader mImageLoader;
	private RecyclingImageView mPosterImageView;

	public FavoritesGridAdapter(Context context, Cursor c) {
		
		super(context, c);
		Log.d(LOG_TAG, "FavoritesGridAdapter: ");
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder) {
		
		Log.d(LOG_TAG, "bindData: ");
		TextView title = (TextView) holder.getViewById(TEXT_VIEW_MOVIE_TITLE);
		title.setText(cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.TITLE)));

		mPosterImageView = (RecyclingImageView) holder
				.getViewById(IMAGE_VIEW_POSTER);
		String posterUrl = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.POSTERS_DETAILED));
		mImageLoader.loadBitmap(mPosterImageView, posterUrl);
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_MOVIE_TITLE, IMAGE_VIEW_POSTER };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup root) {
		return View.inflate(context, R.layout.adapter_favorites_grid, null);
	}

}
