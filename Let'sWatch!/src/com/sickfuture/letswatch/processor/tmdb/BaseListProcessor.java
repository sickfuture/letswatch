package com.sickfuture.letswatch.processor.tmdb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.StringsUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.content.contract.Contract;

public abstract class BaseListProcessor <DataSource, Result> implements IProcessor<DataSource, Result> {

	protected void processNew(ArrayList<ContentValues> array) {
		processNew(array, getIdField(), getUri());
	}

	protected void processNew(ArrayList<ContentValues> array, String field, Uri uri) {
		String ids = StringsUtils.join(array, field,
				",");
		Cursor cursor = ContextHolder.getInstance().getContext()
				.getContentResolver()
				.query(uri, null, field + " IN (" + ids + ")", null, null);
		ArrayList<Long> toUpdate = new ArrayList<Long>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Long id = cursor.getLong(cursor
						.getColumnIndex(field));
				toUpdate.add(id);
				cursor.moveToNext();
			}
		}
		cursor.close();

		updateOldAndPutNew(array, toUpdate);
	}
	
	protected abstract String getIdField();

	protected abstract Uri getUri();

	protected void updateOldAndPutNew(ArrayList<ContentValues> array, ArrayList<Long> toUpdate) {
		if (toUpdate.size() > 0) {
			ArrayList<ContentValues> forUpdate = new ArrayList<ContentValues>();
			for (Long id : toUpdate) {
				ContentValues toRemove = null;
				for (ContentValues v : array) {
					int i = (Integer) v.get(getIdField());
					if (i == id) {
						forUpdate.add(v);
						toRemove = v;
						break;
					}
				}
				if (toRemove != null) {
					array.remove(toRemove);
				}
			}
			insertToDb(array);
			updateInDb(forUpdate);
		} else {
			insertToDb(array);
		}
	}

	protected void updateInDb(ArrayList<ContentValues> forUpdate) {
		forUpdate.trimToSize();
		Context context = ContextHolder.getInstance().getContext();
		for (int i = 0; i < forUpdate.size(); i++) {
			String where = getIdField()
					+ " = "
					+ forUpdate.get(i).getAsString(
							getIdField());
			context.getContentResolver().update(getUri(), forUpdate.get(i),
					where, null);
		}

	}

	protected void insertToDb(ArrayList<ContentValues> array) {
		ContentValues[] values = array.toArray(new ContentValues[array
				.size()]);
		Context context = ContextHolder.getInstance().getContext();
		context.getContentResolver().bulkInsert(getUri(), values);
	}

	@Override
	public Bundle extraProcessingData() {
		return null;
	}
	
}
