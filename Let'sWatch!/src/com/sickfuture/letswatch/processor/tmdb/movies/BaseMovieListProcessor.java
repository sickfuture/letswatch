package com.sickfuture.letswatch.processor.tmdb.movies;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.StringsUtils;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.processor.tmdb.BaseListProcessor;

public abstract class BaseMovieListProcessor<DataSource, Result> extends BaseListProcessor<DataSource, Result> {

	
//	private static final Uri mMoviesUri = ContractUtils
//			.getProviderUriFromContract(Contract.MovieColumns.class);
//	
//	protected void processNewMovies(ArrayList<ContentValues> array) {
//		String ids = StringsUtils.join(array, getMovieIdField(),
//				",");
//		String field = getMovieIdField();
//		Cursor cursor = ContextHolder.getInstance().getContext()
//				.getContentResolver()
//				.query(mMoviesUri, null, field + " IN (" + ids + ")", null, null);
//		ArrayList<Long> toUpdate = new ArrayList<Long>();
//		if (cursor.getCount() > 0) {
//			cursor.moveToFirst();
//			while (!cursor.isAfterLast()) {
//				Long id = Long.valueOf(cursor.getString(cursor
//						.getColumnIndex(getMovieIdField())));
//				toUpdate.add(id);
//				cursor.moveToNext();
//			}
//		}
//		cursor.close();
//
//		updateOldAndPutNew(array, toUpdate);
//	}
//
//	protected abstract String getMovieIdField();
//
//	protected void updateOldAndPutNew(ArrayList<ContentValues> array, ArrayList<Long> toUpdate) {
//		if (toUpdate.size() > 0) {
//			ArrayList<ContentValues> forUpdate = new ArrayList<ContentValues>();
//			for (Long id : toUpdate) {
//				ContentValues toRemove = null;
//				for (ContentValues v : array) {
//					int i = (Integer) v.get(getMovieIdField());
//					if (i == id) {
//						forUpdate.add(v);
//						toRemove = v;
//						break;
//					}
//				}
//				if (toRemove != null) {
//					array.remove(toRemove);
//				}
//			}
//			insertToDb(array);
//			updateInDb(forUpdate);
//		} else {
//			insertToDb(array);
//		}
//	}
//
//	private void updateInDb(ArrayList<ContentValues> forUpdate) {
//		forUpdate.trimToSize();
//		Context context = ContextHolder.getInstance().getContext();
//		for (int i = 0; i < forUpdate.size(); i++) {
//			String where = getMovieIdField()
//					+ " = "
//					+ forUpdate.get(i).getAsString(
//							getMovieIdField());
//			context.getContentResolver().update(mMoviesUri, forUpdate.get(i),
//					where, null);
//		}
//
//	}
//
//	private void insertToDb(ArrayList<ContentValues> array) {
//		ContentValues[] values = array.toArray(new ContentValues[array
//				.size()]);
//		Context context = ContextHolder.getInstance().getContext();
//		context.getContentResolver().bulkInsert(mMoviesUri, values);
//	}


}
