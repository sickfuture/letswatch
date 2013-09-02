package com.sickfuture.letswatch.processor.tmdb.movies;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Movie;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.processor.tmdb.InsertOrUpdateProcessor;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

public class TmdbMovieProcessor extends InsertOrUpdateProcessor<InputStream> {

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_MOVIE_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues process(InputStream data) {
		if (data == null) {
			return null;
		}
		Gson gson = new Gson();
		Movie movie = gson.fromJson(new InputStreamReader(data), Movie.class);
		return ProcessorHelper.processMovie(movie);
	}

	@Override
	public Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.MovieColumns.class);
	}

	@Override
	public String getIdField() {
		return Contract.MovieColumns.TMDB_ID;
	}

}
