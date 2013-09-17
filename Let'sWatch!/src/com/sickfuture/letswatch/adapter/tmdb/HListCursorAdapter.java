package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;

public abstract class HListCursorAdapter extends BaseCursorAdapter {

	protected static final int IMAGE = R.id.image_view_hlist_item_picture;
	protected static final int TITLE = R.id.text_view_hlist_item_title;

	protected SickImageLoader mImageLoader;

	public HListCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { IMAGE, TITLE };
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.adapter_horisontal_list, null);
	}

}
