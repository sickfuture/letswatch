package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;

public class CastGridAdapter extends PeopleGridCursorAdapter {

	private static StyleSpan sBoldSpan = new StyleSpan(Typeface.BOLD);

	public CastGridAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor c, ViewHolder holder) {
		super.bindData(view, context, c, holder);
		String character = c.getString(c.getColumnIndex(CastColumns.CHARACTER));
		TextView tvi = (TextView) holder.getViewById(TEXT_VIEW_INFO);
		tvi.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(character)) {
			SpannableStringBuilder builder = new SpannableStringBuilder(
					character);
			builder.setSpan(sBoldSpan, 0, character.length(), 0);
			builder.insert(0, "as ");
			tvi.setText(builder);
		}

	}

	@Override
	protected String getProfPathColumn() {
		return CastColumns.PERSON_PROFILE_PATH;
	}

	@Override
	protected String getNameColumn() {
		return CastColumns.PERSON_NAME;
	}

}
