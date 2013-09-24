package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.content.contract.Contract;

public class MoviePosterGridAdapter extends MoviesGridCursorAdapter {

	private static final int TEXT_VIEW_MOVIE_TITLE = R.id.text_view_adapter_poster_title;
	private static final int TEXT_VIEW_RATING = R.id.text_view_adapter_poster_addit_info;
	private static final int IMAGE_VIEW_POSTER = R.id.image_view_adapter_poster;

	public MoviePosterGridAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	protected String getImageUrl(Cursor cursor) {
		String path = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.POSTER_PATH));
		return TmdbApi.getPoster(path, POSTER.W185);
	}

	@Override
	protected int getImageRes() {
		return IMAGE_VIEW_POSTER;
	}

	@Override
	protected int getRatingViewRes() {
		return TEXT_VIEW_RATING;
	}

	@Override
	protected int getTitleViewRes() {
		return TEXT_VIEW_MOVIE_TITLE;
	}

	@Override
	protected int getAdapterRes() {
		return R.layout.adapter_posters;
	}

}
