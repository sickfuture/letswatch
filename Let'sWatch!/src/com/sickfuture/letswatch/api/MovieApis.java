package com.sickfuture.letswatch.api;

import android.text.TextUtils;

public class MovieApis {

    public static class RottenApi {
        private static final String API_KEY = "&api_key=7j5zn3t5z3e63q227mx3b495";
        private static final String URL_TEMPLATE = "http://api.rottentomatoes.com/api/public/v1.0%s?api_key=7j5zn3t5z3e63q227mx3b495";
        private static final String BOX_OFFICE = "/box_office";
        private static final String UPCOMING = "/upcoming";
        private static final String IN_THEATERS = "/in_theaters";
        private static final String OPENING = "/opening";
        private static final String TOP_RENTALS = "/top_rentals";
        private static final String CURRENT_RELEASES = "/current_releases";
        private static final String NEW_RELEASES = "/new_releases";
        private static final String LISTS = "/lists%s";
        private static final String MOVIES = "/movies";
        private static final String DVDS = "/dvds";
        private static final String PAGE_LIMIT = "page_limit=%s";
        private static final String PAGE = "page=%s";
        private static final String LIMIT = "limit=%s";
        private static final String COUNTRY = "country=%s";
        private static final String REVIEW_TYPE = "review_type=%s";
        private static final String QUERY = "q=%s";
        private static final String JSON = ".json";
        private static final String CAST = "/cast";
        private static final String CLIPS = "/clips";
        private static final String REVIEWS = "/reviews";
        private static final String SIMILAR = "/similar";

        public static final String TYPE_ALL = "all";
        public static final String TYPE_TOP_CRIRIC = "top_criric";
        public static final String TYPE_DVD = "dvd";

        public static String searchMovies(String query, int limit, int page) {
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, MOVIES));
            builder.append("&").append(String.format(QUERY, query));
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            return builder.toString();
        }

        public static String boxOfficeMovies(int limit, String country) {

            StringBuilder builder = getBuilder(MOVIES, BOX_OFFICE);
            if (limit > 0) {
                builder.append("&").append(String.format(LIMIT, limit));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String inTheatersMovies(int limit, int page,
                                              String country) {

            StringBuilder builder = getBuilder(MOVIES, IN_THEATERS);
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String openingMovies(int limit, String country) {

            StringBuilder builder = getBuilder(MOVIES, OPENING);
            if (limit > 0) {
                builder.append("&").append(String.format(LIMIT, limit));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String upcomingMovies(int limit, int page, String country) {

            StringBuilder builder = getBuilder(MOVIES, UPCOMING);
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String topRentalsDvds(int limit, String country) {

            StringBuilder builder = getBuilder(DVDS, TOP_RENTALS);
            if (limit > 0) {
                builder.append("&").append(String.format(LIMIT, limit));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String currentReleasesDvds(int limit, int page,
                                                 String country) {

            StringBuilder builder = getBuilder(DVDS, CURRENT_RELEASES);
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String newReleasesDvds(int limit, int page, String country) {
            StringBuilder builder = getBuilder(DVDS, NEW_RELEASES);
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String upcomingDvds(int limit, int page, String country) {
            StringBuilder builder = getBuilder(DVDS, UPCOMING);
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            return builder.toString();
        }

        public static String movieInfo(int movieId) {
            if (movieId <= 0) {
                throw new IllegalArgumentException("Illegal movie id");
            }
            return String.format(URL_TEMPLATE, MOVIES + movieId + JSON);
        }

        public static String movieCast(int movieId) {
            if (movieId <= 0) {
                throw new IllegalArgumentException("Illegal movie id");
            }
            return String.format(URL_TEMPLATE, MOVIES + movieId + CAST + JSON);
        }

        public static String movieClips(int movieId) {
            if (movieId <= 0) {
                throw new IllegalArgumentException("Illegal movie id");
            }
            return String.format(URL_TEMPLATE, MOVIES + movieId + CLIPS + JSON);
        }

        public static String movieReviews(int movieId, int limit, int page,
                                          String country, String reviewType) {
            if (movieId <= 0) {
                throw new IllegalArgumentException("Illegal movie id");
            }
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, MOVIES + movieId + REVIEWS + JSON));
            if (limit > 0) {
                builder.append("&").append(String.format(PAGE_LIMIT, limit));
            }
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
            if (!TextUtils.isEmpty(country)) {
                builder.append("&").append(String.format(COUNTRY, country));
            }
            if (!TextUtils.isEmpty(reviewType)) {
                builder.append("&").append(String.format(REVIEW_TYPE, country));
            }
            return builder.toString();
        }

        public static String movieSimilar(int movieId, int limit) {
            if (movieId <= 0) {
                throw new IllegalArgumentException("Illegal movie id");
            }
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, MOVIES + movieId + SIMILAR + JSON));
            if (limit > 0) {
                builder.append("&").append(String.format(LIMIT, limit));
            }
            return builder.toString();
        }

        private static StringBuilder getBuilder(String listType, String type) {
            return new StringBuilder(String.format(URL_TEMPLATE,
                    String.format(LISTS, listType + type + JSON)));
        }
        // <string
        // name="API_BOX_OFFICE_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=50&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_UPCOMING_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?page_limit=10&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_IN_THEATERS_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=10&amp;page=1&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_OPENING_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?limit=16&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_TOP_RENTALS_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/top_rentals.json?limit=10&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_CURRENT_RELEASES_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/current_releases.json?page_limit=16&amp;page=1&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_NEW_RELEASES_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=16&amp;page=1&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_UPCOMING_DVDS_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/upcoming.json?page_limit=16&amp;page=1&amp;country=us&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>
        // <string
        // name="API_SEARCH_REQUEST_URL">http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=%s&amp;apikey=7j5zn3t5z3e63q227mx3b495</string>

    }

    public static class TmdbApi {

        //        public static final String SORT_BY_CREATED_AT = "created_at";
//        public static final String SORT_ORDER_ASC = "asc";
//        public static final String SORT_ORDER_DESC = "desc";
        public static final String EXTERNAL_IDS = "/external_ids";
        public static final String INCLUDE_IMAGE_LANGUAGE = "include_image_language";
        private static final String POSTER_W92 = "w92";
        private static final String REVIEW = "/review";
        private static final String QUERY = "query";
        private static final String SEARCH_TYPE = "search_type";
        private static final String PRIMARY_RELEASE_YEAR = "primary_release_year";
        private static final String YEAR = "year";
        private static final String SEARCH = "/search";
        private static final String INCLUDE_ADULT = "include_adult=%s";
        private static final String DISCOVER = "/discover";
        private static final String KEYWORD = "/keyword";
        private static final String GENRE = "/genre";
        private static final String COMPANY = "/company";
        private static final String MOVIES = "/movies";
        private static final String REMOVE_ITEM = "/remove_item";
        private static final String ADD_ITEM = "/add_item";
        private static final String ITEM_STATUS = "/item_status";
        private static final String LIST = "/list";
        private static final String CREDITS = "/credits";
        private static final String PERSON = "/person";
        private static final String COLLECTION = "/collection";
        private static final String ACCOUNT_STATES = "/account_states";
        private static final String TOP_RATED = "/top_rated";
        private static final String POPULAR = "/popular";
        private static final String NOW_PLAYING = "/now_playing";
        private static final String UPCOMING = "/upcoming";
        private static final String LATEST = "/latest";
        private static final String REVIEWS = "/reviews";
        private static final String SIMILAR_MOVIES = "/similar_movies";
        private static final String TRANSLATIONS = "/translations";
        private static final String TRAILERS = "/trailers";
        private static final String RELEASES = "/releases";
        private static final String KEYWORDS = "/keywords";
        private static final String IMAGES = "/images";
        private static final String CASTS = "/casts";
        private static final String ALTERNATIVE_TITLES = "/alternative_titles";
        private static final String MOVIE = "/movie";
        private static final String MOVIE_WATCHLIST = "/movie_watchlist";
        private static final String RATED_MOVIES = "/rated_movies";
        private static final String SORT_ORDER = "sort_order=%s";
        private static final String SORT_BY = "sort_by=%s";
        private static final String LISTS = "/lists";
        private static final String FAVORITE_MOVIES = "/favorite_movies";
        private static final String LANGUAGE = "language=%s";
        private static final String SESSION_ID = "session_id=%s";
        private static final String ACCOUNT = "/account";
        private static final String GUEST_SESSION_NEW = "/guest_session/new";
        private static final String REQUEST_TOKEN = "request_token=%s";
        private static final String SESSION_NEW = "/session/new";
        private static final String TOKEN_NEW = "/token/new";
        private static final String AUTHENTICATION = "/authentication";
        private static final String URL_TEMPLATE = "http://api.themoviedb.org/3%s?api_key=8df0f158f56c4b9766eeb45fdbbe3b7d";
        private static final String CONFIGURATION = "/configuration";
//        private static final String APPEND = "append_to_response=%s";
//        private static final String PAGE = "page=%s";
//        private static final String FAVORITE = "/favorite";

        public static final String SORT_BY_CREATED_AT = "created_at";
        public static final String SORT_ORDER_ASC = "asc";
        public static final String SORT_ORDER_DESC = "desc";

        public static final String EXTERNAL_SOURCE_IMDB = "imdb_id";
        public static final String EXTERNAL_SOURCE_FREEBASE_MID = "freebase_mid";
        public static final String EXTERNAL_SOURCE_FREEBASE = "freebase_id";
        public static final String EXTERNAL_SOURCE_TVDB = "tvdb_id";
        public static final String EXTERNAL_SOURCE_TVRAGE = "tvrage_id";
        public static final String FIND = "/find";
        public static final String EXTERNAL_SOURCE = "external_source";
        public static final String COMBINED_CREDITS = "/combined_credits";
        public static final String MOVIE_CREDITS = "/movie_credits";
        private static final String APPEND = "append_to_response";
        private static final String PAGE = "page=%s";
        private static final String FAVORITE = "/favorite";
        public static final String TV = "/tv";
//        private static final String SECURE_BASE_URL = "https://d3gtl9l2a4fn1j.cloudfront.net/t/p/%s%s";

        public static String configutration() {
            return String.format(URL_TEMPLATE, CONFIGURATION);
        }

        /**
         * This method is used to generate a valid request token for user based
         * authentication. A request token is required in order to request a
         * session id. You can generate any number of request tokens but they
         * will expire after 60 minutes. As soon as a valid session id has been
         * created the token will be destroyed.
         *
         * @return url
         */
        public static String getToken() {
            return String.format(URL_TEMPLATE, AUTHENTICATION + TOKEN_NEW);
        }

        public static String getSession(String requestToken) {
            if (TextUtils.isEmpty(requestToken)) {
                throw new IllegalArgumentException("Token must be not null");
            }
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, AUTHENTICATION + SESSION_NEW));
            builder.append("&").append(
                    String.format(REQUEST_TOKEN, requestToken));
            return builder.toString();
        }

        public static String getGuestSession() {
            return String.format(URL_TEMPLATE, AUTHENTICATION
                    + GUEST_SESSION_NEW);
        }

        /**
         * Get the basic information for an account. You will need to have a
         * valid session id. Required Parameters api_key session_id
         *
         * @param sessionId
         * @return
         */
        public static String getAccount(String sessionId) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, ACCOUNT));
            addSessionId(sessionId, builder);
            return builder.toString();
        }

        /**
         * Get the lists that you have created and marked as a favorite.
         * Required Parameters api_key session_id Optional Parameters page
         * language ISO 639-1 code.
         *
         * @param id
         * @param sessionId
         * @param page
         * @param language
         * @return url
         */
        public static String getAccountLists(String id, String sessionId,
                                             int page, String language) {
            StringBuilder builder = getAccountBuilder(LISTS, id, sessionId);
            addSessionId(sessionId, builder);
            addPage(page, builder);
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * Get the list of favorite movies for an account. Required Parameters
         * session_id
         *
         * @param id
         * @param sessionId
         * @param sortOrder asc | desc
         * @param sortBy    Only 'created_at` is currently supported.
         * @param page
         * @param language  ISO 639-1 code.
         * @return url
         */
        public static String getAccountFavoriteMovies(String id,
                                                      String sessionId, String sortOrder, String sortBy, int page,
                                                      String language) {
            StringBuilder builder = getAccountBuilder(FAVORITE_MOVIES, id,
                    sessionId);
            addPage(page, builder);
            addLanguage(language, builder);
            addSortBy(sortBy, builder);
            addSortOrder(sortOrder, builder);
            return builder.toString();
        }

        /**
         * use POST request Add or remove a movie to an accounts favorite list.
         * Required Parameters id session_id Required JSON Body movie_id
         * favorite true | false
         *
         * @param id
         * @param sessionId
         * @return url
         */
        public static String setAccountFavoriteMovie(String id, String sessionId) {
            StringBuilder builder = getAccountBuilder(FAVORITE, id, sessionId);
            return builder.toString();
        }

        /**
         * Get the list of rated movies (and associated rating) for an account.
         * Required Parameters id session_id
         *
         * @param id
         * @param sessionId
         * @param sortOrder asc | desc
         * @param sortBy    Only 'created_at` is currently supported.
         * @param page
         * @param language  ISO 639-1 code.
         * @return url
         */
        public static String getAccountRatedMovies(String id, String sessionId,
                                                   String sortOrder, String sortBy, int page, String language) {
            StringBuilder builder = getAccountBuilder(RATED_MOVIES, id,
                    sessionId);
            addPage(page, builder);
            addLanguage(language, builder);
            addSortBy(sortBy, builder);
            addSortOrder(sortOrder, builder);
            return builder.toString();
        }

        /**
         * Get the list of movies on an accounts watchlist. Required Parameters
         * id session_id
         *
         * @param id
         * @param sessionId
         * @param sortOrder asc | desc
         * @param sortBy    Only 'created_at` is currently supported.
         * @param page
         * @param language  ISO 639-1 code.
         * @return url
         */
        public static String getAccountWatchlist(String id, String sessionId,
                                                 String sortOrder, String sortBy, int page, String language) {
            StringBuilder builder = getAccountBuilder(MOVIE_WATCHLIST, id,
                    sessionId);
            addPage(page, builder);
            addLanguage(language, builder);
            addSortBy(sortBy, builder);
            addSortOrder(sortOrder, builder);
            return builder.toString();
        }

        /**
         * use POST Add or remove a movie to an accounts watch list. Required
         * Parameters id session_id Required JSON Body movie_id movie_watchlist
         * true | false
         *
         * @param id
         * @param sessionId
         * @return url
         */
        public static String manageAccountWatchlist(String id, String sessionId) {
            StringBuilder builder = getAccountBuilder(MOVIE_WATCHLIST, id,
                    sessionId);
            return builder.toString();
        }

        /**
         * Get the primary information about a TV series by id.
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any tv series method
         * @return
         */
        public static String getTV(String id, String language,
                                   String... appendToResponse) {
            //todo check
            StringBuilder builder = getUrlBuilder(TV, null, id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the cast & crew information about a TV series. Just like the website,
         * we pull this information from the last season of the series.
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any tv series method
         * @return
         */
        public static String getTvCredits(String id, String language,
                                          String... appendToResponse) {
            //todo check
            StringBuilder builder = getUrlBuilder(TV, CREDITS, id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the external ids that we have stored for a TV series.
         *
         * @param id
         * @param language ISO 639-1 code.
         * @return
         */
        public static String getTvExternalIds(String id, String language) {
            //todo check
            StringBuilder builder = getUrlBuilder(TV, EXTERNAL_IDS, id);
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * Get the images (posters and backdrops) for a TV series.
         *
         * @param id
         * @param language             ISO 639-1 code.
         * @param includeImageLanguage Comma separated, a valid ISO 69-1.
         * @return
         */
        public static String getTvImages(String id, String language, String... includeImageLanguage) {
            //todo check
            StringBuilder builder = getUrlBuilder(TV, IMAGES, id);
            addLanguage(language, builder);
            appendCommaSeparatedParams(builder, INCLUDE_IMAGE_LANGUAGE, includeImageLanguage);
            return builder.toString();
        }

        /**
         * Get the list of translations that exist for a TV series.
         * These translations cascade down to the episode level.
         *
         * @param id
         * @return
         */
        public static String getTvTranslations(String id) {
            //todo check
            StringBuilder builder = getUrlBuilder(TV, IMAGES, id);
            return builder.toString();
        }

        /**
         * Get the list of top rated TV shows. By default, this
         * list will only include TV shows that have 2 or more votes.
         * This list refreshes every day.
         *
         * @param language ISO 639-1 code.
         * @param page
         * @return
         */
        public static String getTVsPopular(String language, int page) {
            StringBuilder builder = getUrlBuilder(TV, POPULAR, null);
            addLanguage(language, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Get the list of popular TV shows. This list refreshes every day.
         *
         * @param language ISO 639-1 code.
         * @param page
         * @return
         */
        public static String getTVsTopRated(String language, int page) {
            StringBuilder builder = getUrlBuilder(TV, TOP_RATED, null);
            addLanguage(language, builder);
            addPage(page, builder);
            return builder.toString();
        }

        // TV SEASONS

        /**
         * Get the primary information about a TV season by its season number.
         *
         * @param id
         * @param seasonNumber
         * @param language
         * @param appendToResponse
         * @return
         */
        public static String getTvSeason(String id, int seasonNumber, String language,
                                         String... appendToResponse) {
            String s = String.format("/season/%s", seasonNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the cast & crew credits for a TV season by season number.
         *
         * @param id
         * @param seasonNumber
         * @return
         */
        public static String getTvSeasonCredits(String id, int seasonNumber) {
            String s = String.format("/season/%s" + CREDITS, seasonNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            return builder.toString();
        }

        /**
         * Get the external ids that we have stored for a TV season by season number.
         *
         * @param id
         * @param seasonNumber
         * @return
         */
        public static String getTvSeasonExternalIds(String id, int seasonNumber) {
            String s = String.format("/season/%s" + EXTERNAL_IDS, seasonNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            return builder.toString();
        }

        /**
         * Get the external ids that we have stored for a TV season by season number.
         *
         * @param id
         * @param seasonNumber
         * @param language             ISO 639-1 code
         * @param includeImageLanguage comma-separated, ISO 639-1
         * @return
         */
        public static String getTvSeasonImages(String id, int seasonNumber,
                                               String language, String... includeImageLanguage) {
            String s = String.format("/season/%s" + IMAGES, seasonNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            addLanguage(language, builder);
            appendCommaSeparatedParams(builder, INCLUDE_IMAGE_LANGUAGE, includeImageLanguage);
            return builder.toString();
        }

        // TV EPISODES

        /**
         * Get the primary information about a TV episode by combination of a season and episode number.
         *
         * @param id
         * @param seasonNumber
         * @param episodeNumber
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any tv series method
         * @return
         */
        public static String getTvEpisode(String id, int seasonNumber, String episodeNumber,
                                          String language, String... appendToResponse) {
            String s = String.format("/season/%s" + "/episode/%s", seasonNumber, episodeNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the TV episode credits by combination of season and episode number.
         *
         * @param id
         * @param seasonNumber
         * @param episodeNumber
         * @return
         */
        public static String getTvEpisodeCredits(String id, int seasonNumber, String episodeNumber) {
            String s = String.format("/season/%s" + "/episode/%s" + CREDITS, seasonNumber, episodeNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            return builder.toString();
        }

        /**
         * Get the external ids for a TV episode by comabination of a season and episode number.
         *
         * @param id
         * @param seasonNumber
         * @param episodeNumber
         * @param language      ISO 639-1 code.
         * @return
         */
        public static String getTvEpisodeExternalIds(String id, int seasonNumber, String episodeNumber,
                                                     String language) {
            String s = String.format("/season/%s" + "/episode/%s" + EXTERNAL_IDS, seasonNumber, episodeNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * Get the images (episode stills) for a TV episode by combination of a season and episode number.
         * Since episode stills don't have a language, this call will always return all images.
         *
         * @param id
         * @param seasonNumber
         * @param episodeNumber
         * @return
         */
        public static String getTvEpisodeImages(String id, int seasonNumber, String episodeNumber) {
            String s = String.format("/season/%s" + "/episode/%s" + IMAGES, seasonNumber, episodeNumber);
            StringBuilder builder = getUrlBuilder(TV, s, id);
            return builder.toString();
        }

        /**
         * Get the basic movie information for a specific movie id. Required
         * Parameters id
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any movie method
         * @return url
         */
        public static String getMovie(String id, String language,
                                      String... appendToResponse) {
            StringBuilder builder = getMovieBuilder("", id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the alternative titles for a specific movie id. Required
         * Parameters id
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any movie method
         * @return
         */
        public static String getAltTitlesMovie(String id, String language,
                                               String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(ALTERNATIVE_TITLES, id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the cast information for a specific movie id. Required Parameters
         * id
         *
         * @param id
         * @param appendToResponse Comma separated, any movie method
         * @return
         */
        public static String getMovieCasts(String id,
                                           String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(CASTS, id);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the images (posters and backdrops) for a specific movie id.
         * Required Parameters id
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any movie method
         * @return url
         */
        public static String getMovieImages(String id, String language,
                                            String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(IMAGES, id);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the plot keywords for a specific movie id. Required Parameters id
         *
         * @param id
         * @param appendToResponse Comma separated, any movie method
         * @return url
         */
        public static String getMovieKeywords(String id,
                                              String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(KEYWORDS, id);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the release date by country for a specific movie id. Required
         * Parameters id
         *
         * @param id
         * @param appendToResponse Comma separated, any movie method
         * @return url
         */
        public static String getMovieReleases(String id,
                                              String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(RELEASES, id);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the trailers for a specific movie id. Required Parameters id
         *
         * @param id
         * @param appendToResponse Comma separated, any movie method
         * @return url
         */
        public static String getMovieTrailers(String id,
                                              String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(TRAILERS, id);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the translations for a specific movie id. Required Parameters id
         *
         * @param id
         * @param appendToResponse Comma separated, any movie method
         * @return url
         */
        public static String getMovieTranslations(String id,
                                                  String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(TRANSLATIONS, id);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the similar movies for a specific movie id. Required Parameters
         * id
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param page
         * @param appendToResponse Comma separated, any movie method
         * @return
         */
        public static String getMovieSimilar(String id, String language,
                                             int page, String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(SIMILAR_MOVIES, id);
            addLanguage(language, builder);
            addPage(page, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the reviews for a particular movie id. Required Parameters id
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param page
         * @param appendToResponse Comma separated, any movie method
         * @return
         */
        public static String getMovieReviews(String id, String language,
                                             int page, String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(REVIEWS, id);
            addLanguage(language, builder);
            addPage(page, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the lists that the movie belongs to. Required Parameters id
         *
         * @param id
         * @param language         ISO 639-1 code.
         * @param page
         * @param appendToResponse Comma separated, any movie method
         * @return
         */
        public static String getMoviesLists(String id, String language,
                                            int page, String... appendToResponse) {
            StringBuilder builder = getMovieBuilder(LISTS, id);
            addLanguage(language, builder);
            addPage(page, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the latest movie id. Required Parameters api_key
         *
         * @return url
         */
        public static String getMovieLatest() {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, MOVIE + LATEST));
            return builder.toString();
        }

        /**
         * Get the list of upcoming movies. This list refreshes every day. The
         * maximum number of items this list will include is 100. Required
         * Parameters id
         *
         * @param language ISO 639-1 code.
         * @param page
         * @return url
         */
        public static String getUpcomingMovies(String language, int page) {
            StringBuilder builder = getMovieBuilder(UPCOMING, null);
            addLanguage(language, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Get the list of movies playing in theatres. This list refreshes every
         * day. The maximum number of items this list will include is 100.
         * Required Parameters api_key Optional Parameters page language ISO
         * 639-1 code.
         *
         * @param language
         * @param page
         * @return url
         */
        public static String getNowPlayingMovies(String language, int page) {
            StringBuilder builder = getMovieBuilder(NOW_PLAYING, null);
            addLanguage(language, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Get the list of popular movies on The Movie Database. This list
         * refreshes every day. Required Parameters api_key Optional Parameters
         * page language ISO 639-1 code.
         *
         * @param language
         * @param page
         * @return
         */
        public static String getPopularMovies(String language, int page) {
            StringBuilder builder = getMovieBuilder(POPULAR, null);
            addLanguage(language, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Get the list of top rated movies. By default, this list will only
         * include movies that have 10 or more votes. This list refreshes every
         * day. Required Parameters api_key Optional Parameters page language
         * ISO 639-1 code.
         *
         * @param language
         * @param page
         * @return
         */
        public static String getTopRatedMovies(String language, int page) {
            StringBuilder builder = getMovieBuilder(TOP_RATED, null);
            addLanguage(language, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * This method lets users get the status of whether or not the movie has
         * been rated or added to their favorite or watch lists. A valid session
         * id is required. Required Parameters api_key session_id
         *
         * @param id
         * @param sessionId
         * @return url
         */
        public static String getAccountStatesMovie(String id, String sessionId) {
            StringBuilder builder = getMovieBuilder(ACCOUNT_STATES, id);
            addSessionId(sessionId, builder);
            return builder.toString();
        }

        /**
         * use POST This method lets users rate a movie. A valid session id or
         * guest session id is required. Required Parameters api_key session_id
         * guest_session_id Required JSON Body value
         *
         * @param id
         * @param sessionOrGuestId
         * @return
         */
        public static String setRatingMovie(String id, String sessionOrGuestId) {
            StringBuilder builder = getMovieBuilder(ACCOUNT_STATES, id);
            addSessionId(sessionOrGuestId, builder);
            return builder.toString();
        }

        /**
         * Get the basic collection information for a specific collection id.
         * You can get the ID needed for this method by making a /movie/{id}
         * request and paying attention to the belongs_to_collection hash. Movie
         * parts are not sorted in any particular order. If you would like to
         * sort them yourself you can use the provided release_date. Required
         * Parameters collectin_id
         *
         * @param collectionId
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any collection method
         * @return url
         */
        public static String getCollection(String collectionId,
                                           String language, String... appendToResponse) {
            StringBuilder builder = getCollectionBuilder("", collectionId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get all of the images for a particular collection by collection id.
         * Required Parameters id
         *
         * @param collectionId
         * @param language         ISO 639-1 code.
         * @param appendToResponse Comma separated, any collection method
         * @return
         */
        public static String getImagesCollectionImages(String collectionId,
                                                       String language, String... appendToResponse) {
            StringBuilder builder = getCollectionBuilder(IMAGES, collectionId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * The find method makes it easy to search for objects in our database by an external id. For instance, an IMDB ID. This will search all objects (movies, TV shows and people) and return the results in a single response. TV season and TV episode searches will be supported shortly.
         * The supported external sources for each object are as follows:
         * Movies: imdb_id
         * People: imdb_id, freebase_mid, freebase_id, tvrage_id
         * TV Series: imdb_id, freebase_mid, freebase_id, tvdb_id, tvrage_id
         *
         * @param externalSource
         * @param id
         * @param language       ISO 639-1 code.
         * @return
         */
        public static String find(String externalSource, String id, String language) {
            // todo check url
            StringBuilder builder = getUrlBuilder(FIND, null, id);
            addParam(EXTERNAL_SOURCE, externalSource, builder);
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * This method is used to retrieve the basic information about a TV network.
         * You can use this ID to search for TV shows with the discover.
         * At this time we don't have much but this will be fleshed out over time.
         *
         * @param id
         * @return
         */
        public static String getNetwork(String id) {
            // todo check url
            StringBuilder builder = getUrlBuilder("/network", null, id);
            return builder.toString();
        }

        /**
         * Get the general person information for a specific id. Required
         * Parameters api_key Optional Parameters append_to_response Comma
         * separated, any person method
         *
         * @param personId
         * @param appendToResponse
         * @return
         */
        public static String getPerson(String personId,
                                       String... appendToResponse) {
            StringBuilder builder = getPersonBuilder("", personId);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the credits for a specific person id. Required Parameters api_key
         * Optional Parameters language ISO 639-1 code. append_to_response Comma
         * separated, any person method
         *
         * @param personId
         * @param language
         * @param appendToResponse
         * @return url
         */
        @Deprecated
        public static String getPersonCredits(String personId, String language,
                                              String... appendToResponse) {
            StringBuilder builder = getPersonBuilder(CREDITS, personId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the combined (movie and TV) credits for a specific person id.
         * To get the expanded details for each TV record, call the /credit method with the provided credit_id.
         * This will provide details about which episode and/or season the credit is for.
         *
         * @param personId
         * @param language         ISO 639-1 code
         * @param appendToResponse Comma separated, any person method
         * @return
         */
        public static String getPersonCreditsCombined(String personId, String language,
                                                      String... appendToResponse) {
            StringBuilder builder = getPersonBuilder(COMBINED_CREDITS, personId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the movie credits for a specific person id.
         *
         * @param personId
         * @param language         ISO 639-1 code
         * @param appendToResponse Comma separated, any person method
         * @return
         */
        public static String getPersonCreditsMovies(String personId, String language,
                                                    String... appendToResponse) {
            StringBuilder builder = getPersonBuilder(MOVIE_CREDITS, personId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the TV credits for a specific person id.
         * To get the expanded details for each record, call the /credit method with the provided credit_id.
         * This will provide details about which episode and/or season the credit is for.
         *
         * @param personId
         * @param language         ISO 639-1 code
         * @param appendToResponse Comma separated, any person method
         * @return
         */
        public static String getPersonCreditsTV(String personId, String language,
                                                String... appendToResponse) {
            StringBuilder builder = getPersonBuilder("/tv_credits", personId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the images for a specific person id.
         */
        public static String getPersonImages(String personId) {
            return getPersonBuilder(IMAGES, personId).toString();
        }

        /**
         * Get the list of popular people on The Movie Database. This list
         * refreshes every day. Required Parameters api_key Optional Parameters
         * page
         *
         * @param page
         * @return url
         */
        public static String getPopularPersons(int page) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, PERSON + POPULAR));
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Get the latest person id. Required Parameters api_key
         *
         * @return url
         */
        public static String getLatestPerson() {
            return new StringBuilder(String.format(URL_TEMPLATE, PERSON
                    + LATEST)).toString();
        }

        /**
         * Get a list by id.
         *
         * @param listId
         * @return
         */
        public static String getList(String listId) {
            return getListBuilder("", listId).toString();
        }

        /**
         * Check to see if a movie ID is already added to a list. Required
         * Parameters api_key movie_id Check to see if this movie ID (integer)
         * is already part of a list or not.
         *
         * @param listId
         * @param movieId
         * @return
         */
        public static String getItemStatusList(String listId, String movieId) {
            StringBuilder builder = getListBuilder(ITEM_STATUS, listId);
            builder.append("&").append(movieId);
            return builder.toString();
        }

        /**
         * use POST This method lets users create a new list. A valid session id
         * is required. Required Parameters api_key session_id Required JSON
         * Body name description Optional JSON Body language
         *
         * @param sessionId
         * @return
         */
        public static String createList(String sessionId) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, LIST));
            addSessionId(sessionId, builder);
            return builder.toString();
        }

        /**
         * use POST This method lets users add new movies to a list that they
         * created. A valid session id is required. Required Parameters api_key
         * session_id Required JSON Body media_id A movie id
         *
         * @param listId
         * @param sessionId
         * @return
         */
        public static String addItemList(String listId, String sessionId) {
            StringBuilder builder = getListBuilder(ADD_ITEM, listId);
            addSessionId(sessionId, builder);
            return builder.toString();
        }

        /**
         * use POST This method lets users delete movies from a list that they
         * created. A valid session id is required. Required Parameters api_key
         * session_id Required JSON Body media_id A movie id
         *
         * @param listId
         * @param sessionId
         * @return
         */
        public static String removeItemList(String listId, String sessionId) {
            StringBuilder builder = getListBuilder(REMOVE_ITEM, listId);
            addSessionId(sessionId, builder);
            return builder.toString();
        }

        /**
         * use DELETE This method lets users delete a list that they created. A
         * valid session id is required. Required Parameters api_key session_id
         *
         * @param listId
         * @param sessionId
         * @return
         */
        public static String deleteList(String listId, String sessionId) {
            return getListBuilder("", listId).toString();
        }

        /**
         * This method is used to retrieve all of the basic information about a
         * company. Required Parameters api_key Optional Parameters
         * append_to_response Comma separated, any company method
         *
         * @param companyId
         * @param appendToResponse
         * @return
         */
        public static String getCompany(String companyId,
                                        String... appendToResponse) {
            StringBuilder builder = getUrlBuilder(COMPANY, "", companyId);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the list of movies associated with a particular company. Required
         * Parameters api_key Optional Parameters page Minimum value is 1,
         * expected value is an integer. language ISO 639-1 code.
         * append_to_response Comma separated, any company method
         *
         * @param companyId
         * @param language
         * @param appendToResponse
         * @return
         */
        public static String getCompanyMovies(String companyId,
                                              String language, String... appendToResponse) {
            StringBuilder builder = getUrlBuilder(COMPANY, MOVIES, companyId);
            addLanguage(language, builder);
            appendToResponse(builder, appendToResponse);
            return builder.toString();
        }

        /**
         * Get the list of genres. Required Parameters api_key Optional
         * Parameters language ISO 639-1 code.
         *
         * @param language
         * @return url
         */
        public static String getGenreList(String language) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, GENRE + LIST));
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * Get the list of movies for a particular genre by id. By default, only
         * movies with 10 or more votes are included. Required Parameters id
         *
         * @param genreId
         * @param page               Minimum value is 1, expected value is an integer.
         * @param language           ISO 639-1 code.
         * @param include_all_movies Toggle the inclusion of all movies and not just those with
         *                           10 or more ratings. Expected value is: true or false
         * @param include_adult      Toggle the inclusion of adult titles. Expected value is:
         *                           true or false
         * @return
         */
        public static String getGenreMovies(String genreId, int page,
                                            String language, boolean include_all_movies,
                                            boolean include_adult) {
            StringBuilder builder = getUrlBuilder(GENRE, MOVIES, genreId);
            addPage(page, builder);
            addLanguage(language, builder);
            builder.append("&").append(
                    String.format("include_all_movies=%s", include_all_movies));
            builder.append("&").append(
                    String.format(INCLUDE_ADULT, include_adult));
            return builder.toString();
        }

        /**
         * Get the basic information for a specific keyword id. Required
         * Parameters api_key
         *
         * @param wordId
         * @return url
         */
        public static String getKeyword(String wordId) {
            return getUrlBuilder(KEYWORD, "", wordId).toString();
        }

        /**
         * Get the list of movies for a particular keyword by id.
         *
         * @param wordId
         * @param page     Minimum value is 1, expected value is an integer.
         * @param language ISO 639-1 code.
         * @return url
         */
        public static String getKeywordMovies(String wordId, int page,
                                              String language) {
            StringBuilder builder = getUrlBuilder(KEYWORD, MOVIES, wordId);
            addPage(page, builder);
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * Discover movies by different types of data like average rating,
         * number of votes, genres and certifications.
         *
         * @param page                  Minimum value is 1, expected value is an integer.
         * @param language              ISO 639-1 code.
         * @param sort_by               Available options are vote_average.desc, vote_average.asc,
         *                              release_date.desc, release_date.asc, popularity.desc,
         *                              popularity.asc
         * @param include_adult         Toggle the inclusion of adult titles. Expected value is a
         *                              boolean, true or false
         * @param year                  Filter the results release dates to matches that include
         *                              this value. Expected value is a year.
         * @param primary_release_year  Filter the results so that only the primary release date
         *                              year has this value. Expected value is a year.
         * @param vote_count_gte        .gte Only include movies that are equal to, or have a vote
         *                              count higher than this value. Expected value is an
         *                              integer.
         * @param vote_average_gte      .gte Only include movies that are equal to, or have a
         *                              higher average rating than this value. Expected value is a
         *                              float.
         * @param with_genres           Only include movies with the specified genres. Expected
         *                              value is an integer (the id of a genre). Multiple values
         *                              can be specified. Comma separated indicates an 'AND'
         *                              query, while a pipe (|) separated value indicates an 'OR'.
         * @param release_date_gte      .gte The minimum release to include. Expected format is
         *                              YYYY-MM-DD.
         * @param release_date_lte      .lte The maximum release to include. Expected format is
         *                              YYYY-MM-DD.
         * @param certification_country Only include movies with certifications for a specific
         *                              country. When this value is specified, 'certification.lte'
         *                              is required. A ISO 3166-1 is expected.
         * @param certification_lte     .lte Only include movies with this certification and
         *                              lower. Expected value is a valid certification for the
         *                              specified 'certification_country'.
         * @param with_companies        Filter movies to include a specific company. Expected
         *                              value is an integer (the id of a company). They can be
         *                              comma separated to indicate an 'AND' query.
         * @return url
         */
        public static String discoverMovie(int page, String language,
                                           String sort_by, boolean include_adult, int year,
                                           int primary_release_year, int vote_count_gte,
                                           float vote_average_gte, String with_genres,
                                           String release_date_gte,
                                           String release_date_lte, String certification_country,
                                           String certification_lte, int... with_companies) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, DISCOVER + MOVIE));
            addPage(page, builder);
            addLanguage(language, builder);
            addSortBy(sort_by, builder);
            builder.append("&").append(
                    String.format(INCLUDE_ADULT, include_adult));
            addParam(YEAR, year, builder);
            addParam(PRIMARY_RELEASE_YEAR, primary_release_year, builder);
            addParam("vote_count.gte", vote_count_gte, builder);
            addParam("vote_average.gte", vote_average_gte, builder);
            addParam("with_genres", with_genres, builder);
            addParam("release_date.lte", release_date_lte, builder);
            addParam("release_date.gte", release_date_gte, builder);
            addParam("certification_country",
                    certification_country, builder);
            addParam("certification.lte", certification_lte, builder);
            if (with_companies.length > 0) {
                String a = "";
                for (int append : with_companies) {
                    a += append + ",";
                }
                a.substring(0, a.length() - 1);
                builder.append("&").append(
                        String.format("with_companies=%s", a));
            }
            return builder.toString();
        }

        /**
         * Discover TV shows by different types of data like average rating, number of votes,
         * genres, the network they aired on and air dates.
         *
         * @param page
         * @param language
         * @param sort_by             Available options are vote_average.desc, vote_average.asc, first_air_date.desc,
         *                            first_air_date.asc, popularity.desc, popularity.asc
         * @param first_air_date_year Filter the results release dates to matches that include this value.
         *                            Expected value is a year.
         * @param vote_count_gte      Only include TV shows that are equal to, or have a vote count higher than this value.
         *                            Expected value is an integer.
         * @param vote_average_gte    Only include TV shows that are equal to, or have a higher average rating than
         *                            this value. Expected value is a float.
         * @param with_genres         Only include TV shows with the specified genres. Expected value is an integer
         *                            (the id of a genre). Multiple values can be specified.
         *                            Comma separated indicates an 'AND' query, while a pipe (|)
         *                            separated value indicates an 'OR'.
         * @param first_air_date_gte  The minimum release to include. Expected format is YYYY-MM-DD.
         * @param first_air_date_lte  The maximum release to include. Expected format is YYYY-MM-DD.
         * @param with_networks       Filter TV shows to include a specific network. Expected value is an integer
         *                            (the id of a network). They can be comma separated to indicate an 'AND' query.
         * @return
         */
        public static String discoverTV(int page, String language,
                                        String sort_by, int first_air_date_year, int vote_count_gte,
                                        float vote_average_gte, String with_genres,
                                        String first_air_date_gte,
                                        String first_air_date_lte, int... with_networks) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, DISCOVER + TV));
            addPage(page, builder);
            addLanguage(language, builder);
            addSortBy(sort_by, builder);
            addParam("first_air_date_year", first_air_date_year, builder);
            addParam("vote_count.gte", vote_count_gte, builder);
            addParam("vote_average.gte", vote_average_gte, builder);
            addParam("with_genres", with_genres, builder);
            addParam("first_air_date.gte", first_air_date_gte, builder);
            addParam("first_air_date.lte", first_air_date_lte, builder);
            if (with_networks.length > 0) {
                String a = "";
                for (int append : with_networks) {
                    a += append + ",";
                }
                a.substring(0, a.length() - 1);
                builder.append("&").append(
                        String.format("with_networks=%s", a));
            }
            return builder.toString();
        }

        /**
         * Search for movies by title.
         *
         * @param query                CGI escaped string
         * @param page                 Minimum value is 1, expected value is an integer.
         * @param language             ISO 639-1 code.
         * @param include_adult        Toggle the inclusion of adult titles. Expected value is:
         *                             true or false
         * @param year                 Filter the results release dates to matches that include
         *                             this value.
         * @param primary_release_year Filter the results so that only the primary release dates
         *                             have this value.
         * @param search_type          By default, the search type is 'phrase'. This is almost
         *                             guaranteed the option you will want. It's a great all
         *                             purpose search type and by far the most tuned for every
         *                             day querying. For those wanting more of an "autocomplete"
         *                             type search, set this option to 'ngram'.
         * @return url
         */
        public static String searchMovie(String query, int page,
                                         String language, boolean include_adult, int year,
                                         int primary_release_year, String search_type) {
            StringBuilder builder = getSearchBuilder(MOVIE);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            addLanguage(language, builder);
            builder.append("&").append(
                    String.format(INCLUDE_ADULT, include_adult));
            addParam(YEAR, year, builder);
            addParam(PRIMARY_RELEASE_YEAR, primary_release_year, builder);
            addParam(SEARCH_TYPE, search_type, builder);
            return builder.toString();
        }

        /**
         * Search for TV shows by title.
         *
         * @param query               CGI escaped string
         * @param page                Minimum value is 1, expected value is an integer.
         * @param language            ISO 639-1 code.
         * @param first_air_date_year Filter the results to only match shows that have a air date with with value.
         * @param search_type         By default, the search type is 'phrase'.
         *                            This is almost guaranteed the option you will want.
         *                            It's a great all purpose search type and by far the most
         *                            tuned for every day querying. For those wanting more of an
         *                            "autocomplete" type search, set this option to 'ngram'.
         * @return
         */
        public static String searchTV(String query, int page,
                                      String language, int first_air_date_year,
                                      String search_type) {
            //todo refactor
            StringBuilder builder = getSearchBuilder(TV);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            addLanguage(language, builder);
            addParam("first_air_date_year", first_air_date_year, builder);
            addParam(SEARCH_TYPE, search_type, builder);
            return builder.toString();
        }

        /**
         * Search for collections by name.
         *
         * @param query    CGI escaped string
         * @param page     Minimum value is 1, expected value is an integer.
         * @param language ISO 639-1 code.
         * @return url
         */
        public static String searchCollection(String query, int page,
                                              String language) {
            StringBuilder builder = getSearchBuilder(COLLECTION);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            addLanguage(language, builder);
            return builder.toString();
        }

        /**
         * Search for people by name.
         *
         * @param query         CGI escaped string
         * @param page          Minimum value is 1, expected value is an integer.
         * @param include_adult Toggle the inclusion of adult titles. Expected value is:
         *                      true or false
         * @param search_type   By default, the search type is 'phrase'. This is almost
         *                      guaranteed the option you will want. It's a great all
         *                      purpose search type and by far the most tuned for every
         *                      day querying. For those wanting more of an "autocomplete"
         *                      type search, set this option to 'ngram'.
         * @return url
         */
        public static String searchPerson(String query, int page,
                                          boolean include_adult, String search_type) {
            StringBuilder builder = getSearchBuilder(PERSON);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            builder.append("&").append(
                    String.format(INCLUDE_ADULT, include_adult));
            addParam(SEARCH_TYPE, search_type, builder);
            return builder.toString();
        }

        /**
         * Search for lists by name and description.
         *
         * @param query         CGI escaped string. Optional:
         * @param page          Minimum value is 1, expected value is an integer.
         * @param include_adult Toggle the inclusion of adult titles. Expected value is:
         *                      true or false
         * @return url
         */
        public static String searchList(String query, int page,
                                        boolean include_adult) {
            StringBuilder builder = getSearchBuilder(LIST);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            builder.append("&").append(
                    String.format(INCLUDE_ADULT, include_adult));
            return builder.toString();
        }

        /**
         * Search for companies by name.
         *
         * @param query CGI escaped string. Optional:
         * @param page  Minimum value is 1, expected value is an integer.
         * @return url
         */
        public static String searchCompany(String query, int page) {
            StringBuilder builder = getSearchBuilder(COMPANY);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Search for keywords by name.
         *
         * @param query CGI escaped string. Optional:
         * @param page  Minimum value is 1, expected value is an integer.
         * @return url
         */
        public static String searchKeywords(String query, int page) {
            StringBuilder builder = getSearchBuilder(KEYWORD);
            if (TextUtils.isEmpty(query)) {
                throw new IllegalArgumentException("Query must be not empty");
            }
            addParam(QUERY, query, builder);
            addPage(page, builder);
            return builder.toString();
        }

        /**
         * Get the full details of a review by ID.
         *
         * @param reviewId
         * @return
         */
        public static String getReview(String reviewId) {
            return getUrlBuilder(REVIEW, "", reviewId).toString();
        }

        /**
         * Get a list of valid jobs.
         *
         * @return url
         */
        public static String getJobList() {
            return new StringBuilder(String.format(URL_TEMPLATE, "/job/list"))
                    .toString();
        }

        private static void appendCommaSeparatedParams(StringBuilder builder,
                                                       String paramName, String... params) {
            if (params.length > 0) {
                String a = "";
                for (String append : params) {
                    a += append + ",";
                }
                a.substring(0, a.length() - 1);
                builder.append("&").append(String.format("%s=%s", paramName, a));
            }
        }

        //todo check
        private static void appendToResponse(StringBuilder builder,
                                             String... appendToResponse) {
            appendCommaSeparatedParams(builder, APPEND, appendToResponse);
//            if (appendToResponse.length > 0) {
//                String a = "";
//                for (String append : appendToResponse) {
//                    a += append + ",";
//                }
//                a.substring(0, a.length() - 1);
//                builder.append("&").append(String.format(APPEND, a));
//            }
        }

        private static StringBuilder getAccountBuilder(String method,
                                                       String id, String sessionId) {
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE, ACCOUNT));
            return getSignedBuilder(method, id, sessionId, builder);
        }

        private static StringBuilder getSearchBuilder(String method) {
            return new StringBuilder(String.format(URL_TEMPLATE, SEARCH
                    + method));
        }

        private static StringBuilder getMovieBuilder(String method, String id) {
            return getUrlBuilder(MOVIE, method, id);
        }

        private static StringBuilder getCollectionBuilder(String method,
                                                          String id) {
            return getUrlBuilder(COLLECTION, method, id);
        }

        private static StringBuilder getPersonBuilder(String method, String id) {
            return getUrlBuilder(PERSON, method, id);
        }

        private static StringBuilder getListBuilder(String method, String id) {
            return getUrlBuilder(LIST, method, id);
        }

        private static StringBuilder getUrlBuilder(String content,
                                                   String method, String itemId) {
            if (TextUtils.isEmpty(itemId)) {
                return new StringBuilder(String.format(URL_TEMPLATE, content
                        + method));
            }
            StringBuilder builder = new StringBuilder(String.format(
                    URL_TEMPLATE,
                    content + String.format("/%s" + method, itemId)));
            return builder;
        }

        private static StringBuilder getSignedBuilder(String method, String id,
                                                      String sessionId, StringBuilder builder) {
            if (TextUtils.isEmpty(id)) {
                throw new IllegalArgumentException("Invalid movie id");
            }
            builder.append(String.format("/%s" + method, id));
            addSessionId(sessionId, builder);
            return builder;
        }

        private static void addSessionId(String sessionId, StringBuilder builder) {
            checkSessionId(sessionId);
            builder.append("&").append(String.format(SESSION_ID, sessionId));
        }

        private static void addSortOrder(String sortOrder, StringBuilder builder) {
            if (!TextUtils.isEmpty(sortOrder)) {
                builder.append("&")
                        .append(String.format(SORT_ORDER, sortOrder));
            }
        }

        private static void addSortBy(String sortBy, StringBuilder builder) {
            if (!TextUtils.isEmpty(sortBy)) {
                builder.append("&").append(String.format(SORT_BY, sortBy));
            }
        }

        private static void addLanguage(String language, StringBuilder builder) {
            if (!TextUtils.isEmpty(language)) {
                builder.append("&").append(String.format(LANGUAGE, language));
            }
        }

        private static void addPage(int page, StringBuilder builder) {
            if (page > 0) {
                builder.append("&").append(String.format(PAGE, page));
            }
        }

        private static void addParam(String paramName, Object param, StringBuilder builder) {
            String p = null;
            if (param instanceof String) {
                if (!TextUtils.isEmpty((String) param)) {
                    p = (String) param;
                }
            } else if (param instanceof Integer) {
                if ((Integer) param > 0) {
                    p = String.valueOf(param);
                }

            } else if (param instanceof Float) {
                if ((Float) param > 0) {
                    p = String.valueOf(param);
                }
            } else {
                try {
                    p = String.valueOf(param);
                } catch (Exception ignored) {
                    p = null;
                }
            }
            if (p != null) {
                builder.append(String.format("&%s=%s", paramName, p));
            }
        }

//        private static void addInt(StringBuilder builder, String field, int val) {
//            if (val > 0) {
//                builder.append("&").append(String.format(field, val));
//            }
//        }
//
//        private static void addFloat(StringBuilder builder, String field,
//                                     float val) {
//            if (val > 0) {
//                builder.append("&").append(String.format(field, val));
//            }
//        }
//
//        private static void addString(StringBuilder builder, String field,
//                                      String val) {
//            if (!TextUtils.isEmpty(val)) {
//                builder.append("&").append(String.format(field, val));
//            }
//        }

        private static void checkSessionId(String sessionId) {
            if (TextUtils.isEmpty(sessionId)) {
                throw new IllegalArgumentException(
                        "Session id must be not null");
            }
        }

        private static final String SECURE_BASE_URL = "https://d3gtl9l2a4fn1j.cloudfront.net/t/p/%s%s";

        public enum POSTER {
            W92("w92"), W154("w154"), W185("w185"), W342("w342"), W500("w500"), ORIGINAL(
                    "original");
            private final String size;

            POSTER(String path) {
                this.size = path;
            }

            public String getPath() {
                return size;
            }

        }

        public enum BACKDROP {
            W300("w300"), W780("w780"), W1280("w1280"), ORIGINAL("original");
            private final String size;

            BACKDROP(String path) {
                this.size = path;
            }

            public String getPath() {
                return size;
            }

        }

        public enum PROFILE {
            W45("w45"), W185("w185"), H632("h632"), ORIGINAL("original");
            private final String size;

            PROFILE(String path) {
                this.size = path;
            }

            public String getPath() {
                return size;
            }

        }

        public enum LOGO {
            W45("w45"), W154("w154"), W185("w185"), W92("w92"), W300("w300"), W500(
                    "w500"), ORIGINAL("original");
            private final String size;

            LOGO(String path) {
                this.size = path;
            }

            public String getPath() {
                return size;
            }

        }

        public static String getPoster(String path, POSTER size) {
            return String.format(SECURE_BASE_URL, size.getPath(), path);
        }

        public static String getBackdrop(String path, BACKDROP size) {
            return String.format(SECURE_BASE_URL, size.getPath(), path);
        }

        public static String getProfile(String path, PROFILE size) {
            return String.format(SECURE_BASE_URL, size.getPath(), path);
        }

        public static String getLogo(String path, LOGO size) {
            return String.format(SECURE_BASE_URL, size.getPath(), path);
        }

    }

}
