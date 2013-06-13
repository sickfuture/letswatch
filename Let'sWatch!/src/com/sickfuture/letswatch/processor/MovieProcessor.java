package com.sickfuture.letswatch.processor;

import java.util.List;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.sickfuture.letswatch.bo.models.Movie;
import com.sickfuture.letswatch.bo.models.MovieList;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public class MovieProcessor {

	public ContentValues[] parseMovieList (String source, int marker) {
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

	public ContentValues parseMovie (String source, int marker) {
		if(source!=null){
			Gson gson = new Gson();
			Movie movie = gson.fromJson(source, Movie.class);
			ContentValues value = new ContentValues();
			value.put(MovieColumns._ID, movie.getId());
			value.put(MovieColumns.MOVIE_TITLE, movie.getTitle());
			value.put(MovieColumns.YEAR, movie.getYear());
			value.put(MovieColumns.MPAA, movie.getMpaaRating());
			value.put(MovieColumns.RUNTIME, movie.getRuntime());
			value.put(MovieColumns.RELEASE_DATE_THEATER, movie.getReleaseDates().getTheater());
			value.put(MovieColumns.RELEASE_DATE_DVD, movie.getReleaseDates().getDvd());
			value.put(MovieColumns.CRITICS_CONSENSUS, movie.getCriticsConsensus());
			value.put(MovieColumns.SYNOPSIS, movie.getSynopsis());
			value.put(MovieColumns.RATING_CRITICS, movie.getRatings().getCriticsRating());
			value.put(MovieColumns.RATING_CRITICS_SCORE, movie.getRatings().getCriticsScore());
			value.put(MovieColumns.RATING_AUDIENCE, movie.getRatings().getAudienceRating());
			value.put(MovieColumns.RATING_AUDIENCE_SCORE, movie.getRatings().getAudienceScore());
			value.put(MovieColumns.POSTERS_DETAILED, movie.getPosters().getDetailed());
			value.put(MovieColumns.POSTERS_ORIGINAL, movie.getPosters().getOriginal());
			value.put(MovieColumns.POSTERS_PROFILE, movie.getPosters().getProfile());
			value.put(MovieColumns.POSTERS_THUMBNAIL, movie.getPosters().getThumbnail());
			value.put(MovieColumns.ALTERNATE_IDS, movie.getAlternateIds().getImdb());
			value.put(MovieColumns.LINK_ALTRENATE, movie.getLinks().getAlternate());
			value.put(MovieColumns.LINK_CAST, movie.getLinks().getCast());
			value.put(MovieColumns.LINK_CLIPS, movie.getLinks().getClips());
			value.put(MovieColumns.LINK_REVIEWS, movie.getLinks().getReviews());
			value.put(MovieColumns.LINK_SIMILAR, movie.getLinks().getSimilar());
			value.put(MovieColumns.SECTION, marker);
			//value.put(MovieColumns.GENRES, movie.getGenres());
			//value.put(MovieColumns.CAST_IDS, movies.);
			//value.put(MovieColumns.DIRECTORS, movies.);
			value.put(MovieColumns.STUDIO, movie.getStudio());
			return value;
		}
		return null;
	}

}
