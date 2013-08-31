package com.sickfuture.letswatch.content.contract;

import com.android.sickfuture.sickcore.annotations.ContentInfo;
import com.android.sickfuture.sickcore.annotations.db.DBTableName;
import com.android.sickfuture.sickcore.annotations.db.DBUnique;
import com.android.sickfuture.sickcore.annotations.db.contract.DBContract;
import com.android.sickfuture.sickcore.annotations.db.types.DBIntegerType;
import com.android.sickfuture.sickcore.annotations.db.types.DBLongType;
import com.android.sickfuture.sickcore.annotations.db.types.DBVarcharType;
import com.android.sickfuture.sickcore.content.contract.CoreBaseColumns;

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

		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
		@DBUnique
		@DBIntegerType
		public static final String ROTTEN_ID = "ROTTEN_ID";
		@DBUnique
		@DBIntegerType
		public static final String TMDB_ID = "TMDB_ID";
		@DBVarcharType
		public static final String TITLE = "TITLE";
		@DBVarcharType
		public static final String TITLE_ORIGINAL = "TITLE_ORIGINAL";
		@DBIntegerType
		public static final String YEAR = "YEAR";
		@DBIntegerType
		public static final String ADULT = "ADULT";
		@DBIntegerType
		public static final String BUDGET = "BUDGET";
		@DBIntegerType
		public static final String REVENUE = "REVENUE";
		@DBVarcharType
		public static final String STATUS = "STATUS";
		@DBIntegerType
		public static final String VOTE_COUNT = "VOTE_COUNT";
		@DBVarcharType
		public static final String VOTE_AVERAGE = "VOTE_AVERAGE";
		// @DBIntegerType
		// public static final String COLLECTION_ID = "COLLECTION_ID";
		@DBVarcharType
		public static final String POPULARITY = "POPULARITY";
		@DBVarcharType
		public static final String GENRES_IDS = "GENRES_IDS";
		@DBVarcharType
		public static final String KEYWORDS_IDS = "KEYWORDS_IDS";
		// @DBVarcharType
		// public static final String COMPANY_IDS = "COMPANY_IDS";
		// @DBVarcharType
		// public static final String COUNTRY_IDS = "COUNTRY_IDS";
		@DBVarcharType
		public static final String SIMILAR_IDS = "SIMILAR_IDS";
		@DBVarcharType
		public static final String LIST_IDS = "LIST_IDS";
		@DBVarcharType
		public static final String SPOKEN_LANGS = "SPOKEN_LANGS";
		@DBVarcharType
		public static final String BACKDROP_PATH = "BACKDROP_PATH";
		@DBVarcharType
		public static final String POSTER_PATH = "POSTER_PATH";
		@DBVarcharType
		public static final String TAGLINE = "TAGLINE";
		@DBVarcharType
		public static final String HOMEPAGE = "HOMEPAGE";
		@DBVarcharType
		public static final String IMDB_ID = "IMDB_ID";
		@DBVarcharType
		public static final String TAGS = "TAGS";
		@DBVarcharType
		public static final String MPAA = "MPAA";
		@DBIntegerType
		public static final String RUNTIME = "RUNTIME";
		@DBVarcharType
		public static final String RELEASE_DATE = "RELEASE_DATE";
		@DBVarcharType
		public static final String RELEASE_DATE_THEATER = "RELEASE_DATE_THEATER";
		@DBVarcharType
		public static final String RELEASE_DATE_DVD = "RELEASE_DATE_DVD";
		@DBVarcharType
		public static final String CRITICS_CONSENSUS = "CRITICS_CONSENSUS";
		@DBVarcharType
		public static final String SYNOPSIS = "SYNOPSIS";
		@DBVarcharType
		public static final String OVERVIEW = "OVERVIEW";
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
		@DBIntegerType
		public static final String IN_WATCHLIST = "IN_WATCHLIST";
		@DBIntegerType
		public static final String USER_RATE = "USER_RATE";
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

	// TODO provider
	@DBTableName(tableName = "UPCOMING_TMDB_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/UPCOMING_TMDB_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./UPCOMING_TMDB_TABLE")
	public static final class UpcomingTmdbColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_TMDB_ID = "TMDB_ID";
	}

	// TODO provider
	@DBTableName(tableName = "NOW_PLAYING_TMDB_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/NOW_PLAYING_TMDB_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./NOW_PLAYING_TMDB_TABLE")
	public static final class NowPlayingTmdbColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_TMDB_ID = "TMDB_ID";
	}

	@DBTableName(tableName = "POPULAR_TMDB_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/POPULAR_TMDB_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.TmdbPopularProvider/POPULAR_TMDB_TABLE")
	public static final class PopularTmdbColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_TMDB_ID = "TMDB_ID";
	}

	// TODO provider
	@DBTableName(tableName = "TOP_RATED_TMDB_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/TOP_RATED_TMDB_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./TOP_RATED_TMDB_TABLE")
	public static final class TopRatedTmdbColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String MOVIE_TMDB_ID = "TMDB_ID";
	}

	// TODO provider
	@DBTableName(tableName = "POPULAR_PEOPLE_TMDB_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/POPULAR_PEOPLE_TMDB_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./POPULAR_PEOPLE_TMDB_TABLE")
	public static final class PopularPeopleTmdbColumns implements
			CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String PERSON_TMDB_ID = "PERSON_TMDB_ID";
	} 

	@DBTableName(tableName = "ALT_TITLES_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/ALT_TITLES_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.TitlesProvider/ALT_TITLES_TABLE")
	public static final class AltTitlesColumns implements CoreBaseColumns {

		@DBLongType
		public static final String TMDB_MOVIE_ID = "TMDB_MOVIE_ID";
		@DBVarcharType
		public static final String ISO_3166_1 = "ISO_3166_1";
		@DBVarcharType
		public static final String TITLE = "TITLE";
	}

	// TODO provider
	@DBTableName(tableName = "PERSON_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/PERSON_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./PERSON_TABLE")
	public static final class PersonColumns implements CoreBaseColumns {

		@DBIntegerType
		public static final String ADULT = "ADULT";
		@DBUnique
		@DBLongType
		public static final String TMDB_ID = "TMDB_ID";
		@DBVarcharType
		public static final String NAME = "NAME";
		@DBVarcharType
		public static final String BIIOGRAPHY = "BIIOGRAPHY";
		@DBVarcharType
		public static final String BIRTHDAY = "BIRTHDAY";
		@DBVarcharType
		public static final String DEATHDAY = "DEATHDAY";
		@DBVarcharType
		public static final String HOMEPAGE = "HOMEPAGE";
		@DBVarcharType
		public static final String PROFILE_PATH = "PROFILE_PATH";
		@DBVarcharType
		public static final String PLACE_OF_BIRTH = "PLACE_OF_BIRTH";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	@DBTableName(tableName = "CAST_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/CAST_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.CastProvider/CAST_TABLE")
	public static final class CastColumns implements CoreBaseColumns {

		@DBLongType
		public static final String TMDB_PERSON_ID = "TMDB_PERSON_ID";
		@DBLongType
		public static final String TMDB_MOVIE_ID = "TMDB_MOVIE_ID";
		@DBVarcharType
		public static final String NAME = "NAME";
		@DBVarcharType
		public static final String CHARACTER = "CHARACTER";
		@DBVarcharType
		public static final String PROFILE_PATH = "PROFILE_PATH";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	@DBTableName(tableName = "CREW_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/CREW_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.CrewProvider/CREW_TABLE")
	public static final class CrewColumns implements CoreBaseColumns {

		@DBLongType
		public static final String TMDB_PERSON_ID = "TMDB_PERSON_ID";
		@DBLongType
		public static final String TMDB_MOVIE_ID = "TMDB_MOVIE_ID";
		@DBVarcharType
		public static final String NAME = "NAME";
		@DBVarcharType
		public static final String DEPARTMENT = "DEPARTMENT";
		@DBVarcharType
		public static final String JOB = "JOB";
		@DBVarcharType
		public static final String PROFILE_PATH = "PROFILE_PATH";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	@DBTableName(tableName = "IMAGES_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/IMAGES_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.ImagesProvider/IMAGES_TABLE")
	public static final class ImageColumns implements CoreBaseColumns {

		@DBLongType
		public static final String TMDB_PERSON_ID = "TMDB_PERSON_ID";
		@DBLongType
		public static final String TMDB_MOVIE_ID = "TMDB_MOVIE_ID";
		@DBLongType
		public static final String TMDB_COLLECTION_ID = "TMDB_COLLECTION_ID";
		@DBVarcharType
		public static final String TYPE = "TYPE";
		@DBVarcharType
		public static final String WIDTH = "WIDTH";
		@DBVarcharType
		public static final String HEIGHT = "HEIGHT";
		@DBVarcharType
		public static final String FILE_PATH = "FILE_PATH";
		@DBVarcharType
		public static final String ISO_639_1 = "ISO_639_1";
		@DBIntegerType
		public static final String VOTE_COUNT = "VOTE_COUNT";
		@DBVarcharType
		public static final String VOTE_AVERAGE = "VOTE_AVERAGE";
		@DBVarcharType
		public static final String ASPECT_RATIO = "ASPECT_RATIO";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	@DBTableName(tableName = "VIDEO_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/VIDEO_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.TrailersProvider/VIDEO_TABLE")
	public static final class VideoColumns implements CoreBaseColumns {

		@DBLongType
		public static final String TMDB_MOVIE_ID = "TMDB_MOVIE_ID";
		@DBVarcharType
		public static final String NAME = "NAME";
		@DBVarcharType
		public static final String SIZE = "SIZE";
		@DBVarcharType
		public static final String SOURCE = "SOURCE";
	}

	@DBTableName(tableName = "REVIEW_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/REVIEW_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.ReviewsProvider/REVIEW_TABLE")
	public static final class ReviewColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String TMDB_ID = "TMDB_ID";
		@DBLongType
		public static final String TMDB_MEDIA_ID = "TMDB_MEDIA_ID";
		@DBVarcharType
		public static final String AUTHOR = "AUTHOR";
		@DBVarcharType
		public static final String CONTENT = "CONTENT";
		@DBVarcharType
		public static final String URL = "URL";
		@DBVarcharType
		public static final String ISO_639_1 = "ISO_639_1";
		@DBVarcharType
		public static final String MEDIA_TYPE = "MEDIA_TYPE";
		@DBIntegerType
		public static final String MEDIA_ID = "MEDIA_ID";
		@DBVarcharType
		public static final String MEDIA_TITLE = "MEDIA_TITLE";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	// TODO provider
	@DBTableName(tableName = "LIST_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/LIST_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./LIST_TABLE")
	public static final class ListColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String TMDB_ID = "TMDB_ID";
		@DBLongType
		public static final String TMDB_MEDIA_ID = "TMDB_MEDIA_ID";
		@DBVarcharType
		public static final String AUTHOR = "AUTHOR";
		@DBVarcharType
		public static final String DESCRIPTION = "DESCRIPTION";
		@DBVarcharType
		public static final String TYPE = "TYPE";
		@DBVarcharType
		public static final String ISO_639_1 = "ISO_639_1";
		@DBVarcharType
		public static final String NAME = "NAME";
		@DBVarcharType
		public static final String POSTER_PATH = "POSTER_PATH";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	// TODO provider
	@DBTableName(tableName = "COLLECTOIN_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/COLLECTOIN_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./COLLECTOIN_TABLE")
	public static final class CollectoinColumns implements CoreBaseColumns {

		@DBUnique
		@DBLongType
		public static final String TMDB_ID = "TMDB_ID";
		@DBVarcharType
		public static final String NAME = "NAME";
		@DBVarcharType
		public static final String POSTER_PATH = "POSTER_PATH";
		@DBVarcharType
		public static final String BACKDROP_PATH = "BACKDROP_PATH";
		@DBIntegerType
		public static final String LAST_UPDATE = "LAST_UPDATE";
	}

	// TODO provider
	@DBTableName(tableName = "MOVIE_TO_COLLECTION_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/MOVIE_TO_COLLECTION_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./MOVIE_TO_COLLECTION_TABLE")
	public static final class MovieToCollectionColumns implements
			CoreBaseColumns {

		@DBLongType
		public static final String MOVIE_TMDB_ID = "MOVIE_TMDB_ID";
		@DBLongType
		public static final String COLLECTION_TMDB_ID = "COLLECTION_TMDB_ID";

	}

	// TODO provider
	@DBTableName(tableName = "MOVIE_TO_LIST_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/MOVIE_TO_LIST_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./MOVIE_TO_LIST_TABLE")
	public static final class MovieToListColumns implements CoreBaseColumns {

		@DBLongType
		public static final String MOVIE_TMDB_ID = "MOVIE_TMDB_ID";
		@DBLongType
		public static final String LIST_TMDB_ID = "LIST_TMDB_ID";

	}

	// TODO provider
	@DBTableName(tableName = "MOVIE_TO_CAST_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/MOVIE_TO_CAST_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./MOVIE_TO_CAST_TABLE")
	public static final class MovieToCastColumns implements CoreBaseColumns {

		@DBLongType
		public static final String MOVIE_TMDB_ID = "MOVIE_TMDB_ID";
		@DBLongType
		public static final String CAST_TMDB_ID = "CAST_TMDB_ID";

	}

	// TODO provider
	@DBTableName(tableName = "MOVIE_TO_CREW_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/MOVIE_TO_CREW_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./MOVIE_TO_CREW_TABLE")
	public static final class MovieToCrewColumns implements CoreBaseColumns {

		@DBLongType
		public static final String MOVIE_TMDB_ID = "MOVIE_TMDB_ID";
		@DBLongType
		public static final String CREW_TMDB_ID = "CREW_TMDB_ID";

	}

	// TODO provider
	@DBTableName(tableName = "MOVIE_TO_COMPANY_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/MOVIE_TO_COMPANY_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider./MOVIE_TO_COMPANY_TABLE")
	public static final class MovieToCompanyColumns implements CoreBaseColumns {

		@DBLongType
		public static final String MOVIE_TMDB_ID = "MOVIE_TMDB_ID";
		@DBLongType
		public static final String COMPANY_TMDB_ID = "COMPANY_TMDB_ID";

	}

	@DBTableName(tableName = "KEYWORDS_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/KEYWORDS_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.KeywordsProvider/KEYWORDS_TABLE")
	public static final class KeywordsColumns implements CoreBaseColumns {

		@DBLongType
		public static final String TMDB_ID = "TMDB_ID";
		@DBVarcharType
		public static final String NAME = "COMPANY_TMDB_ID";

	}

	@DBTableName(tableName = "RELEASES_TABLE")
	@ContentInfo(contentType = "vnd.android.cursor.dir/RELEASES_TABLE", contentUri = "content://com.sickfuture.letswatch.content.provider.tmdb.ReleasesProvider/RELEASES_TABLE")
	public static final class ReleasesColumns implements CoreBaseColumns {

		@DBLongType
		public static final String MOVIE_TMDB_ID = "MOVIE_TMDB_ID";
		@DBVarcharType
		public static final String CERTIFICATION = "CERTIFICATION";
		@DBVarcharType
		public static final String  RELEASE_DATE = "RELEASE_DATE";
		@DBVarcharType
		public static final String ISO_3166_1 = "ISO_3166_1";

	}

}
