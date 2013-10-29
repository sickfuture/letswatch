package com.sickfuture.letswatch.processor.tmdb.movies;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.net.Uri;

import com.android.sickfuture.sickcore.preference.PreferencesHelper;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.bo.tmdb.Movie;
import com.sickfuture.letswatch.bo.tmdb.ResultsMovies;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.processor.tmdb.BaseListProcessor;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

public abstract class TmdbMovieListProcessor extends
		BaseListProcessor<InputStream, ContentValues[]> {

	private int mCurrPage, mTotalPages, mTotalResults;

	@Override
	public ContentValues[] process(InputStream data) {
		if (data == null) {
			return null;
		}
		Gson gson = new Gson();
		ResultsMovies movieList = gson.fromJson(new InputStreamReader(data),
				ResultsMovies.class);
		processPaging(movieList);
		List<Movie> movies = movieList.getResults();
		ContentValues[] values = new ContentValues[movies.size()];
		ArrayList<ContentValues> values2 = new ArrayList<ContentValues>();
		for (int i = 0; i < movies.size(); i++) {
			values[i] = new ContentValues();
			if (movies.get(i) == null) {
				values[i].put(getIdColumnName(), -1);
				continue;
			}
			values[i].put(getIdColumnName(), movies.get(i).getId());

			values2.add(ProcessorHelper.processMovie(movies.get(i)));
		}
		processNew(values2);
		return values;

	}

	private void processPaging(ResultsMovies movieList) {
		mCurrPage = movieList.getPage();
		mTotalPages = movieList.getTotal_pages();
		mTotalResults = movieList.getTotal_results();
	}

	public int getCurrPage() {
		return mCurrPage;
	}

	public int getTotalPages() {
		return mTotalPages;
	}

	public int getTotalResults() {
		return mTotalResults;
	}

	public abstract String getIdColumnName();

	@Override
	protected String getIdField() {
		return Contract.MovieColumns.TMDB_ID;
	}

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.MovieColumns.class);
	}

}
