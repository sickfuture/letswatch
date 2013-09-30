package com.sickfuture.letswatch.content.provider.tmdb;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.SQLQueryBuilder;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.SearchedMoviesColumns;

public class SearchedMoviesProvider extends CommonProvider {

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String moviesTable = DatabaseUtils
				.getTableNameFromContract(MovieColumns.class);
		String childTable = DatabaseUtils
				.getTableNameFromContract(getContractClass());
		String sql = new SQLQueryBuilder()
				.select(null, moviesTable + "." + MovieColumns._ID,
						moviesTable + "." + MovieColumns.TMDB_ID,
						moviesTable + "." + MovieColumns.TITLE,
						moviesTable + "." + MovieColumns.TITLE_ORIGINAL,
						moviesTable + "." + MovieColumns.POSTER_PATH,
						moviesTable + "." + MovieColumns.BACKDROP_PATH,
						moviesTable + "." + MovieColumns.VOTE_AVERAGE,
						childTable + "." + SearchedMoviesColumns.MOVIE_ID,
						childTable + "." + SearchedMoviesColumns._ID)
				.from(moviesTable, childTable)
				.where(String.format("%s.%s = %s.%s", moviesTable,
						MovieColumns.TMDB_ID, childTable,
						SearchedMoviesColumns.MOVIE_ID)).getSql();

		Log.d(LOG_TAG, "query: " + sql);
		return rawQuery(getContractClass(), uri, sql, selectionArgs);
	}

	@Override
	protected Class<?> getContractClass() {
		return SearchedMoviesColumns.class;
	}

}
