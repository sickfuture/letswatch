package com.sickfuture.letswatch.adapter.tmdb;

import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class SimilarHListAdapter extends HListCursorAdapter {

//	public static final String SIMILAR_MOVIE_ID = "similar_movie_id";
//	public static final String SIMILAR_MOVIE_TITLE = "similar_movie_title";
//	public static final String SIMILAR_MOVIE_POSTER_PATH = "similar_movie_poster_path";
	
	public SimilarHListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor c,
			ViewHolder holder) {
		RecyclingImageView imageView = (RecyclingImageView) holder
				.getViewById(IMAGE);
		String path = c.getString(c
				.getColumnIndex(MovieColumns.POSTER_PATH));
		if (!TextUtils.isEmpty(path)) {
			mImageLoader.loadBitmap(imageView,
					TmdbApi.getPoster(path, POSTER.W92));
		}
		TextView titleView = (TextView) holder.getViewById(TITLE);
		String title = c.getString(c.getColumnIndex(MovieColumns.TITLE));
		titleView.setText(title);

	}

}