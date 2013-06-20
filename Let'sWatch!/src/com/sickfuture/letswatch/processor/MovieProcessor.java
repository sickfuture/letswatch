package com.sickfuture.letswatch.processor;

import java.util.List;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.sickfuture.letswatch.bo.models.Movie;
import com.sickfuture.letswatch.bo.models.MovieList;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public class MovieProcessor {

	public static ContentValues[] parseMovieList (String source, int marker) {
		if(source!=null){
			Gson gson = new Gson();
			MovieList movieList = gson.fromJson(source, MovieList.class);
			List<Movie> movies = movieList.getMovies();
			ContentValues[] values = new ContentValues[movies.size()];
			for(int i = 0;i< movies.size();i++){
				values[i] = new ContentValues();
				values[i].put(MovieColumns._ID, movies.get(i).getId());
				values[i].put(MovieColumns.MOVIE_TITLE, movies.get(i).getTitle());
				values[i].put(MovieColumns.YEAR, movies.get(i).getYear());
				values[i].put(MovieColumns.MPAA, movies.get(i).getMpaaRating());
				values[i].put(MovieColumns.RUNTIME, movies.get(i).getRuntime());
				values[i].put(MovieColumns.RELEASE_DATE_THEATER, movies.get(i).getReleaseDates().getTheater());
				values[i].put(MovieColumns.RELEASE_DATE_DVD, movies.get(i).getReleaseDates().getDvd());
				values[i].put(MovieColumns.CRITICS_CONSENSUS, movies.get(i).getCriticsConsensus());
				values[i].put(MovieColumns.SYNOPSIS, movies.get(i).getSynopsis());
				values[i].put(MovieColumns.RATING_CRITICS, movies.get(i).getRatings().getCriticsRating());
				values[i].put(MovieColumns.RATING_CRITICS_SCORE, movies.get(i).getRatings().getCriticsScore());
				values[i].put(MovieColumns.RATING_AUDIENCE, movies.get(i).getRatings().getAudienceRating());
				values[i].put(MovieColumns.RATING_AUDIENCE_SCORE, movies.get(i).getRatings().getAudienceScore());
				values[i].put(MovieColumns.POSTERS_DETAILED, movies.get(i).getPosters().getDetailed());
				values[i].put(MovieColumns.POSTERS_ORIGINAL, movies.get(i).getPosters().getOriginal());
				values[i].put(MovieColumns.POSTERS_PROFILE, movies.get(i).getPosters().getProfile());
				values[i].put(MovieColumns.POSTERS_THUMBNAIL, movies.get(i).getPosters().getThumbnail());
				//values[i].put(MovieColumns.CAST_IDS, movies.get(i));
				values[i].put(MovieColumns.ALTERNATE_IDS, movies.get(i).getAlternateIds().getImdb());
				values[i].put(MovieColumns.LINK_ALTRENATE, movies.get(i).getLinks().getAlternate());
				values[i].put(MovieColumns.LINK_CAST, movies.get(i).getLinks().getCast());
				values[i].put(MovieColumns.LINK_CLIPS, movies.get(i).getLinks().getClips());
				values[i].put(MovieColumns.LINK_REVIEWS, movies.get(i).getLinks().getReviews());
				values[i].put(MovieColumns.LINK_SIMILAR, movies.get(i).getLinks().getSimilar());
				values[i].put(MovieColumns.SECTION, marker);
			}
			return values;
		}
		return null;
	}

	public static ContentValues[] parseMovie (String source, int marker) {
		if(source!=null){
			Gson gson = new Gson();
			Movie movie = gson.fromJson(source, Movie.class);
			ContentValues[] values = new ContentValues[1];
			values[0] = new ContentValues();
			values[0].put(MovieColumns._ID, movie.getId());
			values[0].put(MovieColumns.MOVIE_TITLE, movie.getTitle());
			values[0].put(MovieColumns.YEAR, movie.getYear());
			values[0].put(MovieColumns.MPAA, movie.getMpaaRating());
			values[0].put(MovieColumns.RUNTIME, movie.getRuntime());
			values[0].put(MovieColumns.RELEASE_DATE_THEATER, movie.getReleaseDates().getTheater());
			values[0].put(MovieColumns.RELEASE_DATE_DVD, movie.getReleaseDates().getDvd());
			values[0].put(MovieColumns.CRITICS_CONSENSUS, movie.getCriticsConsensus());
			values[0].put(MovieColumns.SYNOPSIS, movie.getSynopsis());
			values[0].put(MovieColumns.RATING_CRITICS, movie.getRatings().getCriticsRating());
			values[0].put(MovieColumns.RATING_CRITICS_SCORE, movie.getRatings().getCriticsScore());
			values[0].put(MovieColumns.RATING_AUDIENCE, movie.getRatings().getAudienceRating());
			values[0].put(MovieColumns.RATING_AUDIENCE_SCORE, movie.getRatings().getAudienceScore());
			values[0].put(MovieColumns.POSTERS_DETAILED, movie.getPosters().getDetailed());
			values[0].put(MovieColumns.POSTERS_ORIGINAL, movie.getPosters().getOriginal());
			values[0].put(MovieColumns.POSTERS_PROFILE, movie.getPosters().getProfile());
			values[0].put(MovieColumns.POSTERS_THUMBNAIL, movie.getPosters().getThumbnail());
			values[0].put(MovieColumns.ALTERNATE_IDS, movie.getAlternateIds().getImdb());
			values[0].put(MovieColumns.LINK_ALTRENATE, movie.getLinks().getAlternate());
			values[0].put(MovieColumns.LINK_CAST, movie.getLinks().getCast());
			values[0].put(MovieColumns.LINK_CLIPS, movie.getLinks().getClips());
			values[0].put(MovieColumns.LINK_REVIEWS, movie.getLinks().getReviews());
			values[0].put(MovieColumns.LINK_SIMILAR, movie.getLinks().getSimilar());
			values[0].put(MovieColumns.SECTION, marker);
			//value.put(MovieColumns.GENRES, movie.getGenres());
			//value.put(MovieColumns.CAST_IDS, movies.);
			//value.put(MovieColumns.DIRECTORS, movies.);
			values[0].put(MovieColumns.STUDIO, movie.getStudio());
			return values;
		}
		return null;
	}

}
