package com.sickfuture.letswatch.content.provider;

import android.database.Cursor;
import android.net.Uri;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.android.sickfuture.sickcore.utils.SQLQueryBuilder;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public class MoviesProvider extends CommonProvider {

//	private static final String movTable = DatabaseUtils
//			.getTableNameFromContract(MovieColumns.class);
	// private static final String persTable =
	// DatabaseUtils.getTableNameFromContract(PersonColumns.class);
	// private static final String castTable =
	// DatabaseUtils.getTableNameFromContract(CastColumns.class);
	// private static final String m2caTable =
	// DatabaseUtils.getTableNameFromContract(MovieToCastColumns.class);

//	private static final String sql = new SQLQueryBuilder()
//			.select(null, "*")
//			.from(movTable,
//					new SQLQueryBuilder().select(
//							"SM",
//							movTable + "." + MovieColumns.TMDB_ID
//									+ " as similar_movie_id",
//							movTable + "." + MovieColumns.TITLE
//									+ " as similar_movie_title",
//							movTable + "." + MovieColumns.POSTER_PATH
//									+ " as similar_movie_poster_path").from(
//							movTable),
//					new SQLQueryBuilder().select(
//							"SIDS",
//							movTable + "." + MovieColumns.SIMILAR_IDS
//									+ " as movie_similar_ids").from(movTable))
//			.where("SM.similar_movie_id IN (SIDS.movie_similar_ids)" + " AND %s").getSql();

//	@Override
//	public Cursor query(Uri uri, String[] projection, String selection,
//			String[] selectionArgs, String sortOrder) {
//		if (uri.getEncodedFragment() != null) {
//			String temp = uri.toString();
//			uri = Uri.parse(temp.substring(0, temp.indexOf("#")));
//			return super.query(uri, projection, selection, selectionArgs,
//					sortOrder);
//		} else {
//			String q = String.format(sql, selection);
//			L.d(LOG_TAG, "query: "+q);
//			return rawQuery(getContractClass(), uri,
//					q, selectionArgs);
//		}
//	}

	@Override
	protected Class<?> getContractClass() {
		return Contract.MovieColumns.class;
	}

}
