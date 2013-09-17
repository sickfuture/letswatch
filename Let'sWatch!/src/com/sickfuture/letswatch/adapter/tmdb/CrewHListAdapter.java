package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;

public class CrewHListAdapter extends HListCursorAdapter {

	public CrewHListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor c, ViewHolder holder) {
		RecyclingImageView imageView = (RecyclingImageView) holder
				.getViewById(IMAGE);
		String path = c.getString(c
				.getColumnIndex(CrewColumns.PERSON_PROFILE_PATH));
		if (!TextUtils.isEmpty(path)) {
			mImageLoader.loadBitmap(imageView,
					TmdbApi.getProfile(path, PROFILE.W185));
		}
		TextView titleView = (TextView) holder.getViewById(TITLE);
		String title = c.getString(c.getColumnIndex(CrewColumns.PERSON_NAME));
		titleView.setText(title);

	}

}
