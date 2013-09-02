package com.sickfuture.letswatch.processor.tmdb.movies;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Movie;
import com.sickfuture.letswatch.bo.tmdb.ResultsMovies;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

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
		List<Movie> movies = movieList.getResults();
		processMovieList(movies, null, null, null);
		return null;

	}

	public void processMovieList(List<Movie> movies, Uri uri, String field,
			String id) {
		ArrayList<ContentValues> values2 = new ArrayList<ContentValues>();
		boolean x = false;
		ContentValues[] values = null;
		if (uri != null && field != null && id != null) {
			x = true;
			values = new ContentValues[movies.size()];
		}
		for (int i = 0; i < movies.size(); i++) {
			values2.add(ProcessorHelper.processMovie(movies.get(i)));
			if (x) {
				values[i].put(Contract.MovieColumns.TMDB_ID, movies.get(i)
						.getId());
				values[i].put(field, id);
			}
		}
		if (x) {
			ContextHolder.getInstance().getContext().getContentResolver()
					.bulkInsert(uri, values);
		}
		processNewMovies(values2);
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_MOVIE_RESULTS_PROCESSOR_SERVICE;
	}

}
