package com.sickfuture.letswatch.bo.tmdb.json;

public class RequestValues {

	public static String postMovieFavorite(String movieId, boolean favorite) {
		return String.format("{\"movie_id\":%s,\"favorite\":%s}", movieId,
				favorite);
	}

	public static String postMovieWatchlist(String movieId, boolean addToWatchlist) {
		return String.format("{\"movie_id\":%s,\"movie_watchlist\":%s}", movieId,
				addToWatchlist);
	}
	
	public static String postMovieRating(float rateValue) {
		return String.format("{\"value\":%s}",rateValue);
	}
	
	public static String postCreateList(String name, String description) {
		return String.format("{\"name\":%s,\"description\":%s}", name,
				description);
	}
	
	public static String addRremove(float id) {
		return String.format("{\"media_id\":%s}",id);
	}
}
