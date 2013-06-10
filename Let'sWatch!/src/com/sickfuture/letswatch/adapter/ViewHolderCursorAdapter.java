package com.sickfuture.letswatch.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sickfuture.letswatch.R;

abstract class ViewHolderCursorAdapter extends CursorAdapter {

	public ViewHolderCursorAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	private ViewHolder newHolder() {
		ViewHolder holder = new ViewHolder();
		holder.mViewIdsMap = getViewPack();
		holder.mTableColumns = getTableColumns();
		return holder;
	}

	public abstract ArrayList<String> getTableColumns();

	public abstract HashMap<String, Integer> getViewPack();

	public abstract View getNewView(Context context, Cursor cursor, ViewGroup viewGroup);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!mDataValid) {
			throw new IllegalStateException(
					"this should only be called when the cursor is valid");
		}
		if (!mCursor.moveToPosition(position)) {
			throw new IllegalStateException("couldn't move cursor to position "
					+ position);
		}
		View view;
		if (convertView == null) {
			view = newView(mContext, mCursor, parent);
			ViewHolder holder = newHolder();
			for (String key : holder.mViewIdsMap.keySet()) {
				holder.mViewList.add(view.findViewById(holder.mViewIdsMap
						.get(key)));
			}
			view.setTag(R.string.view_holder, holder);
		} else {
			view = convertView;
		}
		bindView(view, mContext, mCursor);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		for (int i = 0; i < holder.mTableColumns.size(); i++) {
			String text = cursor.getString(cursor
					.getColumnIndex(holder.mTableColumns.get(i)));
			TextView textView = (TextView) holder.mViewList.get(i);
			if (!TextUtils.isEmpty(text) && textView != null)
				textView.setText(text);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return getNewView(context, cursor, viewGroup);
	}

	static class ViewHolder {

		ArrayList<View> mViewList;

		ArrayList<String> mTableColumns;

		HashMap<String, Integer> mViewIdsMap;

	}
}
