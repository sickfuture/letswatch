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
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.provider.tmdb.CrewProvider;

public class CrewHListAdapter extends HListCursorAdapter {

	private static final String personTable = DatabaseUtils
			.getTableNameFromContract(Contract.PersonColumns.class);
	private static final String PROFILE_PATH_COLUMN = personTable + "."
			+ PersonColumns.PROFILE_PATH;
	private static final String NAME_COLUMN = personTable + "."
			+ PersonColumns.NAME;

	public CrewHListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor c, ViewHolder holder) {
		RecyclingImageView imageView = (RecyclingImageView) holder
				.getViewById(IMAGE);
		String path = c.getString(c.getColumnIndex(CrewProvider.PERSON_PROFILE_PATH));
		if (!TextUtils.isEmpty(path)) {
			mImageLoader.loadBitmap(imageView,
					TmdbApi.getProfile(path, PROFILE.W185));
		}
		TextView titleView = (TextView) holder.getViewById(TITLE);
		String title = c.getString(c.getColumnIndex(CrewProvider.PERSON_NAME));
		titleView.setText(title);

	}

}
