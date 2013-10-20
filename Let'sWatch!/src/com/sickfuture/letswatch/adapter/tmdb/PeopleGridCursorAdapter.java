package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.POSTER;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class PeopleGridCursorAdapter extends BaseCursorAdapter {

	public static final int TEXT_VIEW_NAME = R.id.text_view_adapter_poster_title;
	public static final int TEXT_VIEW_INFO = R.id.text_view_adapter_poster_addit_info;
	public static final int IMAGE_VIEW_PHOTO = R.id.image_view_adapter_poster;

	private static final String personTable = DatabaseUtils
			.getTableNameFromContract(Contract.PersonColumns.class);
	private static final String PROFILE_PATH_COLUMN = personTable + "."
			+ PersonColumns.PROFILE_PATH;
	private static final String NAME_COLUMN = personTable + "."
			+ PersonColumns.NAME;

	private SickImageLoader mImageLoader;

	public PeopleGridCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void bindData(View view, Context context, Cursor c, ViewHolder holder) {
		String name = c
				.getString(c.getColumnIndex(getNameColumn()));
		String poster = c.getString(c
				.getColumnIndex(getProfPathColumn()));
		((TextView) holder.getViewById(TEXT_VIEW_NAME)).setText(name);
		ImageView posterView = (ImageView) holder.getViewById(IMAGE_VIEW_PHOTO);
		posterView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		mImageLoader.loadBitmap(posterView, MovieApis.TmdbApi.getPoster(poster, POSTER.W185));
	}

	protected String getProfPathColumn() {
		return PROFILE_PATH_COLUMN;
	}

	protected String getNameColumn() {
		return NAME_COLUMN;
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_NAME, IMAGE_VIEW_PHOTO, TEXT_VIEW_INFO };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_posters, null);
	}

}
