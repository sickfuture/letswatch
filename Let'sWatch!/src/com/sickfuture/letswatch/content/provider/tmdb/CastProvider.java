package com.sickfuture.letswatch.content.provider.tmdb;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.android.sickfuture.sickcore.utils.DatabaseUtils;
import com.android.sickfuture.sickcore.utils.SQLQueryBuilder;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.contract.Contract.PopularTmdbColumns;

public class CastProvider extends CommonProvider {

	public static final String PERSON_PROFILE_PATH = "person_profile_path";
	public static final String PERSON_NAME = "person_name";
	public static final String PERSON_ADULT = "person_adult";
	public static final String PERSON_ID = "person_id";
	public static final String MOVIE_VOTE_AVER = "movie_vote_aver";
	public static final String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
	public static final String MOVIE_POSTER_PATH = "movie_poster_path";
	public static final String MOVIE_TITLE_ORIGINAL = "movie_title_original";
	public static final String MOVIE_TITLE = "movie_title";
	public static final String MOVIE_ADULT = "movie_adult";
	public static final String MOVIE_ID = "movie_id";

	private static final String moviesTable = DatabaseUtils
			.getTableNameFromContract(Contract.MovieColumns.class);
	private static final String personTable = DatabaseUtils
			.getTableNameFromContract(Contract.PersonColumns.class);
	private static final String castTable = DatabaseUtils
			.getTableNameFromContract(CastColumns.class);


	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (!TextUtils.isEmpty(selection)) {
			selection += " AND ";
		}
		SQLQueryBuilder sqlBuilder = new SQLQueryBuilder()
				.select(null,
						moviesTable + "." + MovieColumns._ID + " as movie_id_",
						moviesTable + "." + MovieColumns.TMDB_ID
								+ " as " + MOVIE_ID,
						moviesTable + "." + MovieColumns.ADULT
								+ " as " + MOVIE_ADULT,
						moviesTable + "." + MovieColumns.TITLE
								+ " as " + MOVIE_TITLE,
						moviesTable + "." + MovieColumns.TITLE_ORIGINAL
								+ " as " + MOVIE_TITLE_ORIGINAL,
						moviesTable + "." + MovieColumns.POSTER_PATH
								+ " as " + MOVIE_POSTER_PATH,
						moviesTable + "." + MovieColumns.BACKDROP_PATH
								+ " as " + MOVIE_BACKDROP_PATH,
						moviesTable + "." + MovieColumns.VOTE_AVERAGE
								+ " as " + MOVIE_VOTE_AVER,
						personTable + "." + PersonColumns._ID
								+ " as person_id_",
						personTable + "." + PersonColumns.TMDB_ID
								+ " as " + PERSON_ID,
						personTable + "." + PersonColumns.ADULT
								+ " as " + PERSON_ADULT,
						personTable + "." + PersonColumns.NAME
								+ " as " + PERSON_NAME,
						personTable + "." + PersonColumns.PROFILE_PATH
								+ " as " + PERSON_PROFILE_PATH,
						castTable + "." + CastColumns._ID,
						castTable + "." + CastColumns.CHARACTER,
						castTable + "." + CastColumns.TMDB_MOVIE_ID,
						castTable + "." + CastColumns.TMDB_PERSON_ID,
						castTable + "." + CastColumns.LAST_UPDATE)
				.from(moviesTable, personTable, castTable)
				.where(selection
						+ String.format("%s.%s = %s.%s", moviesTable,
								Contract.MovieColumns.TMDB_ID, castTable,
								Contract.CastColumns.TMDB_MOVIE_ID)
						+ " AND "
						+ String.format("%s.%s = %s.%s", personTable,
								Contract.PersonColumns.TMDB_ID, castTable,
								Contract.CastColumns.TMDB_PERSON_ID));
				// .orderBy(sortOrder)
		if(!TextUtils.isEmpty(sortOrder)){
			sqlBuilder.groupBy(sortOrder);
		}
		String sql = sqlBuilder.getSql();
		Log.d(LOG_TAG, "query: " + sql);
		return rawQuery(getContractClass(), uri, sql, selectionArgs);
	}

	@Override
	protected Class<?> getContractClass() {
		return Contract.CastColumns.class;
	}

}
