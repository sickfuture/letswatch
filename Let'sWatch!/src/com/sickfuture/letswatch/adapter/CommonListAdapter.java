package com.sickfuture.letswatch.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;

public class CommonListAdapter extends BaseCursorAdapter {

	public CommonListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor cursor,
			ViewHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int[] getViewsIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
