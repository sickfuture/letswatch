package com.sickfuture.letswatch.adapter.tmdb;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.provider.tmdb.CastProvider;

public class CastGridAdapter extends PeopleGridCursorAdapter {

	private static StyleSpan sBoldSpan = new StyleSpan(Typeface.BOLD);

	private static final String personTable = DatabaseUtils
			.getTableNameFromContract(Contract.PersonColumns.class);
	private static final String PROFILE_PATH_COLUMN = personTable + "." + PersonColumns.PROFILE_PATH;
	private static final String NAME_COLUMN = personTable + "." + PersonColumns.NAME;
	
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
			builder.insert(0, "as ");
			builder.setSpan(sBoldSpan, 3, builder.length(), 0);
			tvi.setText(builder);
		}

	}

	@Override
	protected String getProfPathColumn() {
		return CastProvider.PERSON_PROFILE_PATH;
	}

	@Override
	protected String getNameColumn() {
		return CastProvider.PERSON_NAME;
	}

}
