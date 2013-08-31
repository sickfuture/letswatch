package com.sickfuture.letswatch.content.provider;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.SQLQueryBuilder;
import com.sickfuture.letswatch.content.contract.Contract;

public abstract class CommonSectionProvider extends CommonProvider {

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String moviesTable = DatabaseUtils
				.getTableNameFromContract(Contract.MovieColumns.class);
		String childTable = DatabaseUtils
				.getTableNameFromContract(getContractClass());
		if (uri.getEncodedFragment() != null) {
			String temp = uri.toString();
			uri = Uri.parse(temp.substring(0, temp.indexOf("#")));
			return super.query(uri, projection, selection, selectionArgs,
					sortOrder);
		} else {
			String sql = new SQLQueryBuilder()
					.select(null, "*")
					.from(moviesTable, childTable)
					.where(// selection +
					String.format("%s.%s = %s.%s", moviesTable,
							Contract.MovieColumns.ROTTEN_ID, childTable,
							Contract.MovieColumns.ROTTEN_ID))
					// .orderBy(sortOrder)
					.getSql();

			Log.d(LOG_TAG, "query: " + sql);
			return rawQuery(getContractClass(), uri, sql, selectionArgs);
		}
	}

}
