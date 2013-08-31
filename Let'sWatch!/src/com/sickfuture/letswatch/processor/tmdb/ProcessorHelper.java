package com.sickfuture.letswatch.processor.tmdb;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.StringsUtils;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.*;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.*;
import com.sickfuture.letswatch.processor.tmdb.movies.MovieResultsProcessor;

public class ProcessorHelper {

	public static ContentValues processMovie(Movie movie) {
		ContentValues value = new ContentValues();
		int id = movie.getId();
		value.put(MovieColumns.TMDB_ID, id);
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
			AltTitles alternative_titles = movie.getAlternative_titles();
			alternative_titles.setId(id);
			TitlesProcessor tProc = (TitlesProcessor) AppUtils.get(getCtx(),
					LetsWatchApplication.TMDB_TITLES_PROCESSOR_SERVICE);
			tProc.cache(tProc.processTitles(alternative_titles), getCtx());
		}
		if (movie.getCasts() != null) {
			Casts casts = movie.getCasts();
			casts.setId(id);
			CastsProcessor castsProcessor = (CastsProcessor) AppUtils
					.get(getCtx(),
							LetsWatchApplication.TMDB_CASTS_PROCESSOR_SERVICE);
			castsProcessor.cache(castsProcessor.processCasts(casts), getCtx());
		}
		if (movie.getImages() != null) {
			Images images = movie.getImages();
			processImages(images);
		}
		if (movie.getKeywords() != null) {
			Keywords tags = movie.getKeywords();
			value.put(Contract.MovieColumns.KEYWORDS_IDS,
					tags.getIdsString(","));
			KeywordsProcessor kProc = (KeywordsProcessor) AppUtils.get(
					getCtx(),
					LetsWatchApplication.TMDB_KEYWORDS_PROCESSOR_SERVICE);
			kProc.cache(kProc.processKeywords(tags), getCtx());
		}
		if (movie.getReleases() != null) {
			Releases releases = movie.getReleases();
			releases.setId(id);
			ReleasesProcessor rProc = (ReleasesProcessor) AppUtils.get(getCtx(), LetsWatchApplication.TMDB_RELEASES_PROCESSOR_SERVICE);
			rProc.cache(rProc.processReleases(releases), getCtx());
		}
		if (movie.getTrailers() != null) {
			Trailers trailers = movie.getTrailers();
			trailers.setId(id);
			TrailersProcessor tProc = (TrailersProcessor) AppUtils.get(getCtx(), LetsWatchApplication.TMDB_TRAILERS_PROCESSOR_SERVICE);
			tProc.cache(tProc.processTrailers(trailers), getCtx());
		}
		if (movie.getSimilar_movies() != null) {
			value.put(Contract.MovieColumns.SIMILAR_IDS, movie.getSimilarIds());
			MovieResultsProcessor processor = (MovieResultsProcessor) AppUtils.get(getCtx(), LetsWatchApplication.TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE);
			processor.processMovieList(movie.getSimilar_movies());
		}
		if (movie.getReviews() != null) {
			ResultsReviews reviews = movie.getReviews();
			reviews.setId(id);
			ReviewsProcessor processor = (ReviewsProcessor) AppUtils.get(getCtx(), LetsWatchApplication.TMDB_REVIEWS_PROCESSOR_SERVICE);
			processor.cache(processor.processReviews(reviews), getCtx());
		}
		if (movie.getLists() != null) {
			// TODO process
		}
		return value;
	}

	private static void processImages(Images images) {
		ImagesProcessor iProc = (ImagesProcessor) AppUtils.get(getCtx(),
				LetsWatchApplication.TMDB_IMAGES_PROCESSOR_SERVICE);
		iProc.cache(iProc.processImages(images), getCtx());
		// TODO put id
	}

	private static Context getCtx() {
		return ContextHolder.getInstance().getContext();
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
		// value.put(Contract.ImageColumns.TMDB_MOVIE_ID, id);
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
		value.put(Contract.ReviewColumns.TMDB_ID, review.getId());
		value.put(Contract.ReviewColumns.URL, review.getUrl());
		put(value, Contract.ReviewColumns.ISO_639_1, review.getIso_639_1());
		put(value, Contract.ReviewColumns.MEDIA_ID, review.getMedia_id());
		put(value, Contract.ReviewColumns.MEDIA_TITLE, review.getMedia_title());
		put(value, Contract.ReviewColumns.MEDIA_TYPE, review.getMedia_type());
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

	public static ContentValues processKeywords(IdName keyword) {
		ContentValues value = new ContentValues();
		value.put(Contract.KeywordsColumns.TMDB_ID, keyword.getId());
		value.put(Contract.KeywordsColumns.NAME, keyword.getName());
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

	public static ContentValues processRelease(Country country) {
		ContentValues value = new ContentValues();
		value.put(Contract.ReleasesColumns.CERTIFICATION, country.getCertification());
		value.put(Contract.ReleasesColumns.ISO_3166_1, country.getIso_3166_1());
		value.put(Contract.ReleasesColumns.RELEASE_DATE, country.getRelease_date());
		return value;
	}

}
