package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.provider.tmdb.CastProvider;

public class CredsHListAdapter extends HListCursorAdapter {

	private static final String moviesTable = DatabaseUtils
			.getTableNameFromContract(Contract.MovieColumns.class);
	private static final String TITLE_COLUMN = moviesTable + "." + MovieColumns.TITLE;
	private static final String POSTER_PATH_COLUMN = moviesTable + "." + MovieColumns.POSTER_PATH;
	
	public CredsHListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor c,
			ViewHolder holder) {
		RecyclingImageView imageView = (RecyclingImageView) holder
				.getViewById(IMAGE);
		String path = c.getString(c
				.getColumnIndex(CastProvider.MOVIE_POSTER_PATH));
		if (!TextUtils.isEmpty(path)) {
			mImageLoader.loadBitmap(imageView,
					TmdbApi.getPoster(path, POSTER.W92));
		}
		TextView titleView = (TextView) holder.getViewById(TITLE);
		String title = c.getString(c.getColumnIndex(CastProvider.MOVIE_TITLE));
		titleView.setText(title);
	}

}
