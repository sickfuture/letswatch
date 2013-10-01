package com.sickfuture.letswatch.processor.tmdb;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.*;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.processor.tmdb.movies.MovieResultsProcessor;
import com.sickfuture.letswatch.processor.tmdb.persons.PersonCreditsProcessor;

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
		if (movie.getGenres() != null) {
			put(value, MovieColumns.GENRES_IDS, movie.getGenresIds());
			put(value, MovieColumns.GENRES, movie.getGenresString());
		}
		if (movie.getProduction_companies() != null) {

		}
		if (movie.getProduction_countries() != null) {

		}
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
			ReleasesProcessor rProc = (ReleasesProcessor) AppUtils.get(
					getCtx(),
					LetsWatchApplication.TMDB_RELEASES_PROCESSOR_SERVICE);
			rProc.cache(rProc.processReleases(releases), getCtx());
		}
		if (movie.getTrailers() != null) {
			Trailers trailers = movie.getTrailers();
			trailers.setId(id);
			TrailersProcessor tProc = (TrailersProcessor) AppUtils.get(
					getCtx(),
					LetsWatchApplication.TMDB_TRAILERS_PROCESSOR_SERVICE);
			tProc.cache(tProc.processTrailers(trailers), getCtx());
		}
		if (movie.getSimilar_movies() != null) {
			value.put(Contract.MovieColumns.SIMILAR_IDS, movie.getSimilarIds());
			MovieResultsProcessor processor = (MovieResultsProcessor) AppUtils
					.get(getCtx(),
							LetsWatchApplication.TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE);
			processor.processMovieList(movie.getSimilar_movies().getResults(),
					null, null, null);
		}
		if (movie.getReviews() != null) {
			ResultsReviews reviews = movie.getReviews();
			reviews.setId(id);
			ReviewsProcessor processor = (ReviewsProcessor) AppUtils.get(
					getCtx(),
					LetsWatchApplication.TMDB_REVIEWS_PROCESSOR_SERVICE);
			processor.cache(processor.processReviews(reviews), getCtx());
		}
		if (movie.getLists() != null) {
			ResultsLists lists = movie.getLists();
			lists.setId(id);
			ListsProcessor processor = (ListsProcessor) AppUtils.get(getCtx(),
					LetsWatchApplication.TMDB_LISTS_PROCESSOR_SERVICE);
			processor.cache(processor.processLists(lists), getCtx());
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
		put(value, Contract.PersonColumns.BIIOGRAPHY, person.getBiography());
		put(value, Contract.PersonColumns.BIRTHDAY, person.getBirthday());
		put(value, Contract.PersonColumns.DEATHDAY, person.getDeathday());
		put(value, Contract.PersonColumns.HOMEPAGE, person.getHomepage());
		String name = person.getName();
		value.put(Contract.PersonColumns.NAME, name);
		put(value, Contract.PersonColumns.PLACE_OF_BIRTH,
				person.getPlace_of_birth());
		String profile = person.getProfile_path();
		put(value, Contract.PersonColumns.PROFILE_PATH, profile);
		int id = person.getId();
		value.put(Contract.PersonColumns.TMDB_ID, id);
		if (person.getImages() != null) {
			processImages(person.getImages());
		}
		if (person.getCredits() != null) {
			person.getCredits().setId(id);
			PersonCreditsProcessor processor = (PersonCreditsProcessor) AppUtils
					.get(getCtx(),
							LetsWatchApplication.TMDB_PERSON_CREDITS_PROCESSOR_SERVICE);
			processor.processCredits(person.getCredits());
			// TODO remove commented
			// java.util.List<Cast> cast = person.getCredits().getCast();
			// long time = System.currentTimeMillis();
			// if (cast != null && cast.size() > 0) {
			// ContentValues[] castsValues = new ContentValues[cast.size()];
			// int i = 0;
			// for (Cast c : cast) {
			// castsValues[i] = processCast(c);
			// castsValues[i].put(Contract.CastColumns.LAST_UPDATE, time);
			// castsValues[i].put(Contract.CastColumns.TMDB_PERSON_ID, id);
			// i++;
			// }
			// CastsProcessor castsProcessor = (CastsProcessor) AppUtils.get(
			// getCtx(),
			// LetsWatchApplication.TMDB_CASTS_PROCESSOR_SERVICE);
			// castsProcessor.cache(castsValues, getCtx());
			// }
			// java.util.List<Crew> crew = person.getCredits().getCrew();
			// if (crew != null && crew.size() > 0) {
			// ContentValues[] crewValues = new ContentValues[crew.size()];
			// int i = 0;
			// for (Crew cr : crew) {
			// crewValues[i] = processCrew(cr);
			// crewValues[i].put(Contract.CrewColumns.LAST_UPDATE, time);
			// crewValues[i].put(Contract.CrewColumns.TMDB_PERSON_ID, id);
			// i++;
			// }
			// getCtx().getContentResolver()
			// .bulkInsert(
			// ContractUtils
			// .getProviderUriFromContract(Contract.CrewColumns.class),
			// crewValues);
			// }
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
		if (cast.getName() != null) {
			value.put(Contract.CrewColumns.PERSON_NAME, cast.getName());
			value.put(Contract.CrewColumns.PERSON_PROFILE_PATH,
					cast.getProfile_path());
			value.put(Contract.CrewColumns.TMDB_PERSON_ID, cast.getId());
		} else if (cast.getTitle() != null || cast.getOriginal_title() != null) {
			value.put(Contract.CrewColumns.MOVIE_TITLE, cast.getTitle());
			value.put(Contract.CrewColumns.MOVIE_ORIGINAL_TITLE,
					cast.getOriginal_title());
			value.put(Contract.CrewColumns.MOVIE_POSTER_PATH,
					cast.getPoster_path());
			value.put(Contract.CrewColumns.MOVIE_ADULT, cast.isAdult() ? 1 : 0);
			value.put(Contract.CrewColumns.TMDB_MOVIE_ID, cast.getId());
		}
		return value;
	}

	public static ContentValues processCrew(Crew crew) {
		ContentValues value = new ContentValues();
		value.put(Contract.CrewColumns.DEPARTMENT, crew.getDepartment());
		value.put(Contract.CrewColumns.JOB, crew.getJob());
		if (crew.getName() != null) {
			value.put(Contract.CrewColumns.PERSON_NAME, crew.getName());
			value.put(Contract.CrewColumns.PERSON_PROFILE_PATH,
					crew.getProfile_path());
			value.put(Contract.CrewColumns.TMDB_PERSON_ID, crew.getId());
		} else if (crew.getTitle() != null || crew.getOriginal_title() != null) {
			value.put(Contract.CrewColumns.MOVIE_TITLE, crew.getTitle());
			value.put(Contract.CrewColumns.MOVIE_ORIGINAL_TITLE,
					crew.getOriginal_title());
			value.put(Contract.CrewColumns.MOVIE_POSTER_PATH,
					crew.getPoster_path());
			value.put(Contract.CrewColumns.MOVIE_ADULT, crew.isAdult() ? 1 : 0);
			value.put(Contract.CrewColumns.TMDB_MOVIE_ID, crew.getId());
		}
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
		put(value, Contract.ListColumns.AUTHOR, list.getCreated_by());
		put(value, Contract.ListColumns.DESCRIPTION, list.getDescription());
		put(value, Contract.ListColumns.ISO_639_1, list.getIso_639_1());
		value.put(Contract.ListColumns.NAME, list.getName());
		put(value, Contract.ListColumns.POSTER_PATH, list.getPoster_path());
		String id = list.getId();
		value.put(Contract.ListColumns.TMDB_ID, id);
		put(value, Contract.ListColumns.TYPE, list.getList_type());
		if (list.getItems() != null && list.getItems().size() > 0) {
			MovieResultsProcessor processor = (MovieResultsProcessor) AppUtils
					.get(getCtx(),
							LetsWatchApplication.TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE);
			processor
					.processMovieList(
							list.getItems(),
							ContractUtils
									.getProviderUriFromContract(Contract.MovieToListColumns.class),
							Contract.MovieToListColumns.LIST_TMDB_ID, id);
		}
		return value;
	}

	public static ContentValues processCollection(Collection collection) {
		ContentValues value = new ContentValues();
		put(value, Contract.CollectoinColumns.BACKDROP_PATH,
				collection.getBackdrop_path());
		value.put(Contract.CollectoinColumns.NAME, collection.getName());
		put(value, Contract.CollectoinColumns.POSTER_PATH,
				collection.getPoster_path());
		int id = collection.getId();
		value.put(Contract.CollectoinColumns.TMDB_ID, id);
		if (collection.getImages() != null) {
			processImages(collection.getImages());
		}
		if (collection.getParts() != null && collection.getParts().size() > 0) {
			MovieResultsProcessor processor = (MovieResultsProcessor) AppUtils
					.get(getCtx(),
							LetsWatchApplication.TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE);
			processor
					.processMovieList(
							collection.getParts(),
							ContractUtils
									.getProviderUriFromContract(Contract.MovieToCollectionColumns.class),
							Contract.MovieToCollectionColumns.COLLECTION_TMDB_ID,
							String.valueOf(id));
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
		value.put(Contract.ReleasesColumns.CERTIFICATION,
				country.getCertification());
		value.put(Contract.ReleasesColumns.ISO_3166_1, country.getIso_3166_1());
		value.put(Contract.ReleasesColumns.RELEASE_DATE,
				country.getRelease_date());
		return value;
	}

}
