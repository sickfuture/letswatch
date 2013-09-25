package com.sickfuture.letswatch.content.provider.tmdb;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.SQLQueryBuilder;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PopularTmdbColumns;
import com.sickfuture.letswatch.content.contract.Contract.TopRatedTmdbColumns;

public class TmdbTopRatedProvider extends CommonProvider {

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String moviesTable = DatabaseUtils
				.getTableNameFromContract(Contract.MovieColumns.class);
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
						childTable + "." + TopRatedTmdbColumns.MOVIE_TMDB_ID,
						childTable + "." + TopRatedTmdbColumns._ID)
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
