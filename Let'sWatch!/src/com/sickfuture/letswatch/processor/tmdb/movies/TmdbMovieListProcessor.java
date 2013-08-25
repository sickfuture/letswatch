package com.sickfuture.letswatch.processor.tmdb.movies;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.sickfuture.letswatch.bo.tmdb.Movie;
import com.sickfuture.letswatch.bo.tmdb.ResultsMovies;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.processor.BaseMovieListProcessor;

public abstract class TmdbMovieListProcessor extends
		BaseMovieListProcessor<InputStream, ContentValues[]> {

	@Override
	public ContentValues[] process(InputStream data) {
		if (data == null) {
			return null;
		}
		Gson gson = new Gson();
		ResultsMovies movieList = gson.fromJson(new InputStreamReader(data),
				ResultsMovies.class);
		List<Movie> movies = movieList.getResults();
		ContentValues[] values = new ContentValues[movies.size()];
		ArrayList<ContentValues> values2 = new ArrayList<ContentValues>();
		for (int i = 0; i < movies.size(); i++) {
			values[i] = new ContentValues();
			values[i].put(MovieColumns.TMDB_ID, movies.get(i).getId());

			values2.add(ProcessorHelper.processMovie(movies.get(i)));
		}
		processNewMovies(values2);
		return values;

	}

	@Override
	protected String getMovieIdField() {
		return Contract.MovieColumns.TMDB_ID;
	}

}
