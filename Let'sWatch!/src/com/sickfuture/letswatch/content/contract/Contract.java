package com.sickfuture.letswatch.content.contract;

import com.android.sickfuture.sickcore.annotations.ContentInfo;
import com.android.sickfuture.sickcore.annotations.db.DBTableName;
import com.android.sickfuture.sickcore.annotations.db.contract.DBContract;
import com.android.sickfuture.sickcore.db.BaseColumns;

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
	public static final class MovieColumns implements BaseColumns {

		public MovieColumns() {
		}

		public static final String MOVIE_ID = _ID;
		public static final String MOVIE_TITLE = "MOVIE_TITLE";
		public static final String YEAR = "YEAR";
		public static final String MPAA = "MPAA";
		public static final String RUNTIME = "RUNTIME";
		public static final String RELEASE_DATE_THEATER = "RELEASE_DATE_THEATER";
		public static final String RELEASE_DATE_DVD = "RELEASE_DATE_DVD";
		public static final String CRITICS_CONSENSUS = "CRITICS_CONSENSUS";
		public static final String SYNOPSIS = "SYNOPSIS";
		public static final String RATING_CRITICS = "RATING_CRITICS";
		public static final String RATING_CRITICS_SCORE = "RATING_CRITICS_SCORE";
		public static final String RATING_AUDIENCE = "RATING_AUDIENCE";
		public static final String RATING_AUDIENCE_SCORE = "RATING_AUDIENCE_SCORE";
		public static final String POSTERS_THUMBNAIL = "POSTERS_THUMBNAIL";
		public static final String POSTERS_PROFILE = "POSTERS_PROFILE";
		public static final String POSTERS_DETAILED = "POSTERS_DETAILED";
		public static final String POSTERS_ORIGINAL = "POSTERS_ORIGINAL";
		public static final String CAST_IDS = "CAST_IDS";
		public static final String ALTERNATE_IDS = "ALTERNATE_IDS";
		public static final String LINK_SELF = "LINK_SELF";
		public static final String LINK_ALTRENATE = "LINK_ALTRENATE";
		public static final String LINK_CAST = "LINK_CAST";
		public static final String LINK_CLIPS = "LINK_CLIPS";
		public static final String LINK_REVIEWS = "LINK_REVIEWS";
		public static final String LINK_SIMILAR = "LINK_SIMILAR";
		public static final String SECTION = "SECTION";
		public static final String IS_FAVORITE = "IS_FAVORITE";
		public static final String GENRES = "GENRES";
		public static final String STUDIO = "STUDIO";
		public static final String DIRECTORS = "DIRECTORS";

		public final static String[] COLUMNS = {
				Contract.MovieColumns.MOVIE_ID,
				Contract.MovieColumns.MOVIE_TITLE, Contract.MovieColumns.YEAR,
				Contract.MovieColumns.MPAA, Contract.MovieColumns.RUNTIME,
				Contract.MovieColumns.RELEASE_DATE_THEATER,
				Contract.MovieColumns.RELEASE_DATE_DVD,
				Contract.MovieColumns.CRITICS_CONSENSUS,
				Contract.MovieColumns.SYNOPSIS,
				Contract.MovieColumns.RATING_CRITICS,
				Contract.MovieColumns.RATING_CRITICS_SCORE,
				Contract.MovieColumns.RATING_AUDIENCE,
				Contract.MovieColumns.RATING_AUDIENCE_SCORE,
				Contract.MovieColumns.POSTERS_THUMBNAIL,
				Contract.MovieColumns.POSTERS_PROFILE,
				Contract.MovieColumns.POSTERS_DETAILED,
				Contract.MovieColumns.POSTERS_ORIGINAL,
				Contract.MovieColumns.CAST_IDS,
				Contract.MovieColumns.ALTERNATE_IDS,
				Contract.MovieColumns.LINK_SELF,
				Contract.MovieColumns.LINK_ALTRENATE,
				Contract.MovieColumns.LINK_CAST,
				Contract.MovieColumns.LINK_CLIPS,
				Contract.MovieColumns.LINK_REVIEWS,
				Contract.MovieColumns.LINK_SIMILAR,
				Contract.MovieColumns.SECTION,
				Contract.MovieColumns.IS_FAVORITE,
				Contract.MovieColumns.GENRES, Contract.MovieColumns.DIRECTORS,
				Contract.MovieColumns.STUDIO };

	}

}
