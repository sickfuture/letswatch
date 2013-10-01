package com.sickfuture.letswatch.processor.tmdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.sickfuture.letswatch.R;

public abstract class InsertOrUpdateProcessor<DataSource> implements
		IProcessor<DataSource, ContentValues> {

	@Override
	public boolean cache(ContentValues result, Context context) {
		String field = getIdField();
		String selection = field + " = " + result.getAsString(field);
		Cursor cursor = context.getContentResolver().query(getUri(), null,
				selection, null, null);
		if (cursor.getCount() == 0) {
			context.getContentResolver().insert(getUri(), result);
			cursor.close();
		} else {
			context.getContentResolver().update(getUri(), result, selection,
					null);
			cursor.close();
		}
		return true;
	}

	public abstract Uri getUri();

	public abstract String getIdField();

}
