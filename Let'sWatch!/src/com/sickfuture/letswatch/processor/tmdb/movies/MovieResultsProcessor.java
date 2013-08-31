package com.sickfuture.letswatch.processor.tmdb.movies;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Movie;
import com.sickfuture.letswatch.bo.tmdb.ResultsMovies;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

import android.content.ContentValues;
import android.content.Context;

public class MovieResultsProcessor extends TmdbMovieListProcessor {

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		return true;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		if (data == null) {
			return null;
		}
		Gson gson = new Gson();
		ResultsMovies movieList = gson.fromJson(new InputStreamReader(data),
				ResultsMovies.class);
		processMovieList(movieList);
		return null;

	}

	public void processMovieList(ResultsMovies movieList) {
		List<Movie> movies = movieList.getResults();
		ArrayList<ContentValues> values2 = new ArrayList<ContentValues>();
		for (int i = 0; i < movies.size(); i++) {
			values2.add(ProcessorHelper.processMovie(movies.get(i)));
		}
		processNewMovies(values2);
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE;
	}

}
