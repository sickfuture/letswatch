package com.sickfuture.letswatch.content.contract;

import com.android.sickfuture.sickcore.annotations.ContentInfo;
import com.android.sickfuture.sickcore.annotations.db.DBTableName;
import com.android.sickfuture.sickcore.annotations.db.DBUnique;
import com.android.sickfuture.sickcore.annotations.db.contract.DBContract;
import com.android.sickfuture.sickcore.annotations.db.types.DBBooleanType;
import com.android.sickfuture.sickcore.annotations.db.types.DBIntegerType;
import com.android.sickfuture.sickcore.annotations.db.types.DBLongType;
import com.android.sickfuture.sickcore.annotations.db.types.DBVarcharType;
import com.android.sickfuture.sickcore.content.contract.CoreBaseColumns;
import com.android.sickfuture.sickcore.content.contract.CoreContracts;

@DBContract
public class Contract {

	public static final int BOX_OFFICE_SECTION = 10;
	public static final int UPCOMING_SECTION = 11;
	public static final int IN_THEATRES_SECTION = 12;
	public static final int OPENING_SECTION = 13;
	public static final int TOP_RENTALS_SECTION = 20;
	public static final int UPCOMING_DVD_SECTION = 21;
	public static final int CURRENT_RELEASE_SECTION = 22;
	public static final int NEW_RELEASE_SECTION = 23;
	public static final int SEARCH = 30;

	public static final String ID = "ID";
	public static final String BOX_OFFICE_SECTION_MARK = "BOX_OFFICE";
	public static final String UPCOMING_SECTION_MARK = "UPCOMING";
	public static final String IN_THEATRES_SECTION_MARK = "THEATRES";
	public static final String OPENING_SECTION_MARK = "OPENING";
	public static final String TOP_RENTALS_SECTION_MARK = "TOP_RENTALS";
	public static final String UPCOMING_DVD_SECTION_MARK = "UPCOMING_DVD";
	public static final String CURRENT_RELEASE_SECTION_MARK = "CURRENT_RELEASE";
	public static final String NEW_RELEASE_SECTION_MARK = "NEW_RELEASE";
	public static final String SECTION = "SECTION";

	@DBTableName(tableName = "MOVIES_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/MOVIES_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.MoviesProvider/MOVIES_TABLE")
	public static final class MovieColumns implements CoreBaseColumns {

		@DBUnique
		@DBIntegerType
		public static final String MOVIE_ID = "MOVIE_ID";
		@DBVarcharType
		public static final String MOVIE_TITLE = "MOVIE_TITLE";
		@DBIntegerType
		public static final String YEAR = "YEAR";
		@DBVarcharType
		public static final String MPAA = "MPAA";
		@DBIntegerType
		public static final String RUNTIME = "RUNTIME";
		@DBVarcharType
		public static final String RELEASE_DATE_THEATER = "RELEASE_DATE_THEATER";
		@DBVarcharType
		public static final String RELEASE_DATE_DVD = "RELEASE_DATE_DVD";
		@DBVarcharType
		public static final String CRITICS_CONSENSUS = "CRITICS_CONSENSUS";
		@DBVarcharType
		public static final String SYNOPSIS = "SYNOPSIS";
		@DBVarcharType
		public static final String RATING_CRITICS = "RATING_CRITICS";
		@DBVarcharType
		public static final String RATING_CRITICS_SCORE = "RATING_CRITICS_SCORE";
		@DBVarcharType
		public static final String RATING_AUDIENCE = "RATING_AUDIENCE";
		@DBVarcharType
		public static final String RATING_AUDIENCE_SCORE = "RATING_AUDIENCE_SCORE";
		@DBVarcharType
		public static final String POSTERS_THUMBNAIL = "POSTERS_THUMBNAIL";
		@DBVarcharType
		public static final String POSTERS_PROFILE = "POSTERS_PROFILE";
		@DBVarcharType
		public static final String POSTERS_DETAILED = "POSTERS_DETAILED";
		@DBVarcharType
		public static final String POSTERS_ORIGINAL = "POSTERS_ORIGINAL";
		@DBVarcharType
		public static final String CAST_IDS = "CAST_IDS";
		@DBVarcharType
		public static final String ALTERNATE_IDS = "ALTERNATE_IDS";
		// @DBVarcharType
		// public static final String LINK_SELF = "LINK_SELF";
		@DBVarcharType
		public static final String LINK_ALTERNATE = "LINK_ALTERNATE";
		@DBVarcharType
		public static final String LINK_CAST = "LINK_CAST";
		@DBVarcharType
		public static final String LINK_CLIPS = "LINK_CLIPS";
		@DBVarcharType
		public static final String LINK_REVIEWS = "LINK_REVIEWS";
		@DBVarcharType
		public static final String LINK_SIMILAR = "LINK_SIMILAR";

		// @DBIntegerType
		// public static final String SECTION = "SECTION";

		@DBIntegerType
		public static final String IS_FAVORITE = "IS_FAVORITE";
		@DBVarcharType
		public static final String GENRES = "GENRES";
		@DBVarcharType
		public static final String STUDIO = "STUDIO";
		@DBVarcharType
		public static final String DIRECTORS = "DIRECTORS";

	}

	@DBTableName(tableName = "BOX_OFFICE_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/BOX_OFFICE_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.theaters.BoxOfficeProvider/BOX_OFFICE_TABLE")
	public static final class BoxOfficeColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "CURRENT_RELEASE_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/CURRENT_RELEASE_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.dvd.CurrentReleaseProvider/CURRENT_RELEASE_TABLE")
	public static final class CurrentReleaseColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "NEW_RELEASE_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/NEW_RELEASE_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.dvd.NewReleaseProvider/NEW_RELEASE_TABLE")
	public static final class NewReleaseColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "OPENING_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/OPENING_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.theaters.OpeningProvider/OPENING_TABLE")
	public static final class OpeningColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "SEARCH_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/SEARCH_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.SearchProvider/SEARCH_TABLE")
	public static final class SearchColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "THEATERS_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/THEATERS_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.theaters.InTheatersProvider/THEATERS_TABLE")
	public static final class TheatersColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "TOP_RENTALS_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/TOP_RENTALS_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.dvd.TopRentalsProvider/TOP_RENTALS_TABLE")
	public static final class TopRentalsColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "UPCOMING_DVD_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/UPCOMING_DVD_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.dvd.UpcomingDvdProvider/UPCOMING_DVD_TABLE")
	public static final class UpcomingDvdColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

	@DBTableName(tableName = "UPCOMING_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/UPCOMING_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.theaters.UpcomingProvider/UPCOMING_TABLE")
	public static final class UpcomingColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_ID = "MOVIE_ID";

	}

}
