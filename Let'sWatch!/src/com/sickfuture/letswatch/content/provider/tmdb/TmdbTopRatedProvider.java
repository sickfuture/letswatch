package com.sickfuture.letswatch.content.provider.tmdb;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.SQLQueryBuilder;
import com.sickfuture.letswatch.content.contract.Contract;

public class TmdbTopRatedProvider extends CommonProvider {

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String moviesTable = DatabaseUtils
				.getTableNameFromContract(Contract.MovieColumns.class);
		String childTable = DatabaseUtils
				.getTableNameFromContract(getContractClass());
		String sql = new SQLQueryBuilder()
				.select(null, "*")
				.from(moviesTable, childTable)
				.where(// selection +
				String.format("%s.%s = %s.%s", moviesTable,
								Contract.MovieColumns.TMDB_ID, childTable,
								Contract.TopRatedTmdbColumns.MOVIE_TMDB_ID))
				// .orderBy(sortOrder)
				.getSql();

		Log.d(LOG_TAG, "query: " + sql);
		return rawQuery(getContractClass(), uri, sql, selectionArgs);
	}

	@Override
	protected Class<?> getContractClass() {
		return Contract.TopRatedTmdbColumns.class;
	}

}
