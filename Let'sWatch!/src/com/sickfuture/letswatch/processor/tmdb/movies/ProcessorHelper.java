package com.sickfuture.letswatch.processor.tmdb.movies;

import android.content.ContentValues;

import com.sickfuture.letswatch.bo.tmdb.Cast;
import com.sickfuture.letswatch.bo.tmdb.Collection;
import com.sickfuture.letswatch.bo.tmdb.Crew;
import com.sickfuture.letswatch.bo.tmdb.Image;
import com.sickfuture.letswatch.bo.tmdb.List;
import com.sickfuture.letswatch.bo.tmdb.Movie;
import com.sickfuture.letswatch.bo.tmdb.Person;
import com.sickfuture.letswatch.bo.tmdb.Review;
import com.sickfuture.letswatch.bo.tmdb.Title;
import com.sickfuture.letswatch.bo.tmdb.Video;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

class ProcessorHelper {

	public static ContentValues processMovie(Movie movie) {
		ContentValues value = new ContentValues();
		value.put(MovieColumns.TMDB_ID, movie.getId());
		value.put(MovieColumns.TITLE, movie.getTitle());
		value.put(MovieColumns.TITLE_ORIGINAL, movie.getOriginal_title());
		value.put(MovieColumns.RELEASE_DATE, movie.getRelease_date());
		value.put(MovieColumns.ADULT, movie.isAdult());
		value.put(MovieColumns.BACKDROP_PATH, movie.getBackdrop_path());
		value.put(MovieColumns.POSTER_PATH, movie.getPoster_path());
		value.put(MovieColumns.POPULARITY, movie.getPopularity());
		value.put(MovieColumns.VOTE_AVERAGE, movie.getVote_average());
		value.put(MovieColumns.VOTE_COUNT, movie.getVote_count());
		put(value, MovieColumns.RUNTIME, movie.getRuntime());
		put(value, MovieColumns.HOMEPAGE, movie.getHomepage());
		put(value, MovieColumns.IMDB_ID, movie.getImdb_id());
		put(value, MovieColumns.OVERVIEW, movie.getOverview());
		put(value, MovieColumns.STATUS, movie.getStatus());
		put(value, MovieColumns.TAGLINE, movie.getTagline());
		put(value, MovieColumns.BUDGET, movie.getBudget());
		put(value, MovieColumns.REVENUE, movie.getRevenue());
		if (movie.getAlternative_titles() != null) {
			// TODO process
		}
		if (movie.getCasts() != null) {
			// TODO process
		}
		if (movie.getImages() != null) {
			// TODO process
		}
		if (movie.getKeywords() != null) {
			// TODO process
		}
		if (movie.getReleases() != null) {
			// TODO process
		}
		if (movie.getTrailers() != null) {
			// TODO process
		}
		if (movie.getSimilar_movies() != null) {
			// TODO process
		}
		if (movie.getReviews() != null) {
			// TODO process
		}
		if (movie.getLists() != null) {
			// TODO process
		}
		return value;
	}

	public static ContentValues processPerson(Person person) {
		ContentValues value = new ContentValues();
		value.put(Contract.PersonColumns.ADULT, person.isAdult());
		value.put(Contract.PersonColumns.BIIOGRAPHY, person.getBiography());
		value.put(Contract.PersonColumns.BIRTHDAY, person.getBirthday());
		value.put(Contract.PersonColumns.DEATHDAY, person.getDeathday());
		value.put(Contract.PersonColumns.HOMEPAGE, person.getHomepage());
		value.put(Contract.PersonColumns.NAME, person.getName());
		value.put(Contract.PersonColumns.PLACE_OF_BIRTH,
				person.getPlace_of_birth());
		value.put(Contract.PersonColumns.PROFILE_PATH, person.getProfile_path());
		value.put(Contract.PersonColumns.TMDB_ID, person.getId());
		if (person.getImages() != null) {
			// process images
		}
		if (person.getCredits() != null) {
			// process creds
		}
		return value;
	}

	public static ContentValues processTitle(Title title) {
		ContentValues value = new ContentValues();
		value.put(Contract.AltTitlesColumns.ISO_3166_1, title.getIso_3166_1());
		value.put(Contract.AltTitlesColumns.TITLE, title.getTitle());
		return value;
	}

	public static ContentValues processCast(Cast cast) {
		ContentValues value = new ContentValues();
		value.put(Contract.CastColumns.CHARACTER, cast.getCharacter());
		value.put(Contract.CastColumns.NAME, cast.getName());
		value.put(Contract.CastColumns.PROFILE_PATH, cast.getProfile_path());
		value.put(Contract.CastColumns.TMDB_MOVIE_ID, cast.getId());
		return value;
	}

	public static ContentValues processCrew(Crew crew) {
		ContentValues value = new ContentValues();
		value.put(Contract.CrewColumns.DEPARTMENT, crew.getDepartment());
		value.put(Contract.CrewColumns.JOB, crew.getJob());
		value.put(Contract.CrewColumns.NAME, crew.getName());
		value.put(Contract.CrewColumns.PROFILE_PATH, crew.getProfile_path());
		value.put(Contract.CrewColumns.TMDB_MOVIE_ID, crew.getId());
		return value;
	}

	public static ContentValues processImage(Image image) {
		ContentValues value = new ContentValues();
		value.put(Contract.ImageColumns.ASPECT_RATIO, image.getAspect_ratio());
		value.put(Contract.ImageColumns.FILE_PATH, image.getFile_path());
		value.put(Contract.ImageColumns.HEIGHT, image.getHeight());
		value.put(Contract.ImageColumns.ISO_639_1, image.getIso_639_1());
		value.put(Contract.ImageColumns.VOTE_AVERAGE, image.getVote_average());
		value.put(Contract.ImageColumns.VOTE_COUNT, image.getVote_count());
		value.put(Contract.ImageColumns.WIDTH, image.getWidth());
		return value;
	}

	public static ContentValues processVideo(Video video) {
		ContentValues value = new ContentValues();
		value.put(Contract.VideoColumns.NAME, video.getName());
		value.put(Contract.VideoColumns.SIZE, video.getSize());
		value.put(Contract.VideoColumns.SOURCE, video.getSource());
		return value;
	}

	public static ContentValues processReview(Review review) {
		ContentValues value = new ContentValues();
		value.put(Contract.ReviewColumns.AUTHOR, review.getAuthor());
		value.put(Contract.ReviewColumns.CONTENT, review.getContent());
		value.put(Contract.ReviewColumns.ISO_639_1, review.getIso_639_1());
		value.put(Contract.ReviewColumns.MEDIA_ID, review.getMedia_id());
		value.put(Contract.ReviewColumns.MEDIA_TITLE, review.getMedia_title());
		value.put(Contract.ReviewColumns.MEDIA_TYPE, review.getMedia_type());
		value.put(Contract.ReviewColumns.TMDB_ID, review.getId());
		value.put(Contract.ReviewColumns.URL, review.getUrl());
		return value;
	}

	public static ContentValues processList(List list) {
		ContentValues value = new ContentValues();
		value.put(Contract.ListColumns.AUTHOR, list.getCreated_by());
		value.put(Contract.ListColumns.DESCRIPTION, list.getDescription());
		value.put(Contract.ListColumns.ISO_639_1, list.getIso_639_1());
		value.put(Contract.ListColumns.NAME, list.getName());
		value.put(Contract.ListColumns.POSTER_PATH, list.getPoster_path());
		value.put(Contract.ListColumns.TMDB_ID, list.getId());
		value.put(Contract.ListColumns.TYPE, list.getList_type());
		if (list.getItems() != null && list.getItems().size() > 0) {
			// TODO process movies
		}
		return value;
	}

	public static ContentValues processCollection(Collection collection) {
		ContentValues value = new ContentValues();
		value.put(Contract.CollectoinColumns.BACKDROP_PATH,
				collection.getBackdrop_path());
		value.put(Contract.CollectoinColumns.NAME, collection.getName());
		value.put(Contract.CollectoinColumns.POSTER_PATH,
				collection.getPoster_path());
		value.put(Contract.CollectoinColumns.TMDB_ID, collection.getId());
		if (collection.getImages() != null) {
			// TODO process
		}
		if (collection.getParts() != null && collection.getParts().size() > 0) {
			// TODO process
		}
		return value;
	}

	private static void put(ContentValues values, String key, String value) {
		if (value != null && !value.equals("null")) {
			values.put(key, value);
		}
	}

	private static void put(ContentValues values, String key, int value) {
		if (value > 0) {
			values.put(key, value);
		}
	}

}
