package com.sickfuture.letswatch.app;

import com.android.sickfuture.sickcore.app.SickApp;
import com.android.sickfuture.sickcore.image.SickImageLoader.ImageLoaderParamsBuilder;
import com.android.sickfuture.sickcore.source.implemented.HttpInputStreamDataSource;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.processor.rotten.BoxOfficeProcessor;
import com.sickfuture.letswatch.processor.rotten.CurrentReleaseProcessor;
import com.sickfuture.letswatch.processor.rotten.InfoProcessor;
import com.sickfuture.letswatch.processor.rotten.NewReleaseProcessor;
import com.sickfuture.letswatch.processor.rotten.OpeningProcessor;
import com.sickfuture.letswatch.processor.rotten.SearchProcessor;
import com.sickfuture.letswatch.processor.rotten.TheatersProcessor;
import com.sickfuture.letswatch.processor.rotten.TopRentalsProcessor;
import com.sickfuture.letswatch.processor.rotten.UpcomingDvdProcessor;
import com.sickfuture.letswatch.processor.rotten.UpcomingProcessor;
import com.sickfuture.letswatch.processor.tmdb.CastsProcessor;
import com.sickfuture.letswatch.processor.tmdb.ImagesProcessor;
import com.sickfuture.letswatch.processor.tmdb.KeywordsProcessor;
import com.sickfuture.letswatch.processor.tmdb.ListProcessor;
import com.sickfuture.letswatch.processor.tmdb.ListsProcessor;
import com.sickfuture.letswatch.processor.tmdb.ReleasesProcessor;
import com.sickfuture.letswatch.processor.tmdb.ReviewsProcessor;
import com.sickfuture.letswatch.processor.tmdb.TitlesProcessor;
import com.sickfuture.letswatch.processor.tmdb.TrailersProcessor;
import com.sickfuture.letswatch.processor.tmdb.movies.MovieResultsProcessor;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbMovieProcessor;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbNowPlayingProcessor;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbPopularProcessor;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbTopRatedProcessor;
import com.sickfuture.letswatch.processor.tmdb.movies.TmdbUpcomingProcessor;
import com.sickfuture.letswatch.processor.tmdb.persons.PersonCreditsProcessor;
import com.sickfuture.letswatch.processor.tmdb.persons.PersonProcessor;
import com.sickfuture.letswatch.processor.tmdb.persons.PopularPersonsProcessor;
import com.sickfuture.letswatch.processor.tmdb.search.SearchedMoviesProcessor;
import com.sickfuture.letswatch.processor.tmdb.search.SearchedPersonsProcessor;

public class LetsWatchApplication extends SickApp {

	public static final String BASE_PROCESSOR_SERVICE = "sickcore:BaseMovieProcessor";
	public static final String BOX_OFFICE_PROCESSOR_SERVICE = "sickcore:BoxOfficeMovieProcessor";
	public static final String OPENING_PROCESSOR_SERVICE = "sickcore:OpeningMovieProcessor";
	public static final String THEATERS_PROCESSOR_SERVICE = "sickcore:TheatersMovieProcessor";
	public static final String UPCOMING_PROCESSOR_SERVICE = "sickcore:UpcomingMovieProcessor";
	public static final String TOP_RENTALS_PROCESSOR_SERVICE = "sickcore:TopRentalsProcessor";
	public static final String UPCOMING_DVD_PROCESSOR_SERVICE = "sickcore:UpcomingDvdProcessor";
	public static final String CURRENT_RELEASE_PROCESSOR_SERVICE = "sickcore:CurrentReleaseProcessor";
	public static final String NEW_RELEASE_PROCESSOR_SERVICE = "sickcore:NewReleaseProcessor";
	public static final String SEARCH_PROCESSOR_SERVICE = "sickcore:SearchProcessor";
	public static final String INFO_PROCESSOR_SERVICE = "sickcore:InfoProcessor";

	public static final String TMDB_MOVIE_PROCESSOR_SERVICE = "sickcore:TmdbMovieProcessor";
	public static final String TMDB_POPULAR_PROCESSOR_SERVICE = "sickcore:TmdbPopularProcessor";
	public static final String TMDB_NOW_PLAYING_PROCESSOR_SERVICE = "sickcore:TmdbNowPlayingProcessor";
	public static final String TMDB_UPCOMING_PROCESSOR_SERVICE = "sickcore:TmdbUpcomingProcessor";
	public static final String TMDB_TOP_RATED_PROCESSOR_SERVICE = "sickcore:TmdbTopRatedProcessor";
	public static final String TMDB_TITLES_PROCESSOR_SERVICE = "sickcore:TmdbTitlesProcessor";
	public static final String TMDB_CASTS_PROCESSOR_SERVICE = "sickcore:TmdbCastsProcessor";
	public static final String TMDB_IMAGES_PROCESSOR_SERVICE = "sickcore:TmdbImagesProcessor";
	public static final String TMDB_KEYWORDS_PROCESSOR_SERVICE = "sickcore:TmdbKeywordsProcessor";
	public static final String TMDB_RELEASES_PROCESSOR_SERVICE = "sickcore:TmdbReleasesProcessor";
	public static final String TMDB_TRAILERS_PROCESSOR_SERVICE = "sickcore:TmdbTrailersProcessor";
	public static final String TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE = "sickcore:TmdbMovieResultsProcessor";
	public static final String TMDB_REVIEWS_PROCESSOR_SERVICE = "sickcore:TmdbReviewsProcessor";
	public static final String TMDB_LISTS_PROCESSOR_SERVICE = "sickcore:TmdbListsProcessor";
	public static final String TMDB_PERSON_PROCESSOR_SERVICE = "sickcore:TmdbPersonProcessor";
	public static final String TMDB_LIST_PROCESSOR_SERVICE = "sickcore:TmdbPersonProcessor";
	public static final String TMDB_POPULAR_PERSON_PROCESSOR_SERVICE = "sickcore:TmdbPopularPersonProcessor";
	public static final String TMDB_PERSON_CREDITS_PROCESSOR_SERVICE = "sickcore:TmdbPersonCreditsProcessor";
	public static final String TMDB_SEARCHED_MOVIES_PROCESSOR_SERVICE = "sickcore:TmdbSearchedMoviesProcessor";
	public static final String TMDB_SEARCHED_PERSONS_PROCESSOR_SERVICE = "sickcore:TmdbSearchedPersonsProcessor";

	@Override
	public void register() {
		/** DATA_SOURCE */
		registerAppService(new HttpInputStreamDataSource());
		/***************/

		/** SICK_IMAGE_LOADER */
		ImageLoaderParamsBuilder builder = new ImageLoaderParamsBuilder(
				getApplicationContext());
		builder.enableFadeIn(true).setDiscCacheEnabled(true)
				.setDiscCacheSize(10 * 1024 * 1024).setMemoryCacheEnabled(true)
				.setPartOfAvailibleMemoryCache(0.5f)
				.setLoadingImage(R.drawable.empty_photo);
		registerAppService(builder.build());
		/**********************/

		/** PROCESSORS */
		registerAppService(new BoxOfficeProcessor());
		registerAppService(new OpeningProcessor());
		registerAppService(new TheatersProcessor());
		registerAppService(new UpcomingProcessor());
		registerAppService(new TopRentalsProcessor());
		registerAppService(new UpcomingDvdProcessor());
		registerAppService(new CurrentReleaseProcessor());
		registerAppService(new NewReleaseProcessor());
		registerAppService(new SearchProcessor());
		registerAppService(new InfoProcessor());
		registerAppService(new TmdbMovieProcessor());
		registerAppService(new TmdbPopularProcessor());
		registerAppService(new TmdbNowPlayingProcessor());
		registerAppService(new TmdbTopRatedProcessor());
		registerAppService(new TmdbUpcomingProcessor());
		registerAppService(new TitlesProcessor());
		registerAppService(new CastsProcessor());
		registerAppService(new ImagesProcessor());
		registerAppService(new KeywordsProcessor());
		registerAppService(new ReleasesProcessor());
		registerAppService(new TrailersProcessor());
		registerAppService(new MovieResultsProcessor());
		registerAppService(new ReviewsProcessor());
		registerAppService(new ListsProcessor());
		registerAppService(new PersonProcessor());
		registerAppService(new ListProcessor());
		registerAppService(new PopularPersonsProcessor());
		registerAppService(new PersonCreditsProcessor());
		registerAppService(new SearchedMoviesProcessor());
		registerAppService(new SearchedPersonsProcessor());
	}

}
