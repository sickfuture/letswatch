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
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;

public class CrewGridAdapter extends PeopleGridCursorAdapter {

	private static StyleSpan sBoldSpan = new StyleSpan(Typeface.BOLD);

	public CrewGridAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindData(View view, Context context, Cursor c, ViewHolder holder) {
		super.bindData(view, context, c, holder);
		String character = c.getString(c.getColumnIndex(CrewColumns.JOB));
		TextView tvi = (TextView) holder.getViewById(TEXT_VIEW_INFO);
		tvi.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(character)) {
			SpannableStringBuilder builder = new SpannableStringBuilder(
					character);
			builder.setSpan(sBoldSpan, 0, character.length(), 0);
			tvi.setText(builder);
		}

	}

	@Override
	protected String getProfPathColumn() {
		return CrewColumns.PERSON_PROFILE_PATH;
	}

	@Override
	protected String getNameColumn() {
		return CrewColumns.PERSON_NAME;
	}

}
