package com.sickfuture.letswatch.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.sickfuture.sickcore.context.ContextHolder;
import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.IOUtils;
import com.android.sickfuture.sickcore.utils.L;
import com.android.sickfuture.sickcore.utils.StringsUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.bo.rotten.Movie;
import com.sickfuture.letswatch.bo.rotten.MovieList;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public abstract class BaseMovieProcessor implements
		IProcessor<InputStream, ContentValues[]> {

	private static final String LOG_TAG = BaseMovieProcessor.class
			.getSimpleName();
	private static final String UTF_8 = "UTF-8";
	private static final Uri mMoviesUri = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);

	public ContentValues[] parseMovieList(String source) {
		L.d(LOG_TAG, "parseList");
		if (source != null) {
			Gson gson = new Gson();
			MovieList movieList = gson.fromJson(source, MovieList.class);
			List<Movie> movies = movieList.getMovies();
			ContentValues[] values = new ContentValues[movies.size()];
			ArrayList<ContentValues> values2 = new ArrayList<ContentValues>();
			for (int i = 0; i < movies.size(); i++) {
				values[i] = new ContentValues();
				values[i].put(MovieColumns.MOVIE_ID, movies.get(i).getId());

				ContentValues value = new ContentValues();
				value.put(MovieColumns.MOVIE_ID, movies.get(i).getId());
				value.put(MovieColumns.MOVIE_TITLE, movies.get(i).getTitle());
				value.put(MovieColumns.YEAR, movies.get(i).getYear());
				value.put(MovieColumns.MPAA, movies.get(i).getMpaaRating());
				value.put(MovieColumns.RUNTIME, movies.get(i).getRuntime());
				value.put(MovieColumns.RELEASE_DATE_THEATER, movies.get(i)
						.getReleaseDates().getTheater());
				value.put(MovieColumns.RELEASE_DATE_DVD, movies.get(i)
						.getReleaseDates().getDvd());
				value.put(MovieColumns.CRITICS_CONSENSUS, movies.get(i)
						.getCriticsConsensus());
				value.put(MovieColumns.SYNOPSIS, movies.get(i).getSynopsis());
				value.put(MovieColumns.RATING_CRITICS, movies.get(i)
						.getRatings().getCriticsRating());
				value.put(MovieColumns.RATING_CRITICS_SCORE, movies.get(i)
						.getRatings().getCriticsScore());
				value.put(MovieColumns.RATING_AUDIENCE, movies.get(i)
						.getRatings().getAudienceRating());
				value.put(MovieColumns.RATING_AUDIENCE_SCORE, movies.get(i)
						.getRatings().getAudienceScore());
				value.put(MovieColumns.POSTERS_DETAILED, movies.get(i)
						.getPosters().getDetailed());
				value.put(MovieColumns.POSTERS_ORIGINAL, movies.get(i)
						.getPosters().getOriginal());
				value.put(MovieColumns.POSTERS_PROFILE, movies.get(i)
						.getPosters().getProfile());
				value.put(MovieColumns.POSTERS_THUMBNAIL, movies.get(i)
						.getPosters().getThumbnail());
				// values[i].put(MovieColumns.CAST_IDS, movies.get(i));
				value.put(MovieColumns.ALTERNATE_IDS, movies.get(i)
						.getAlternateIds().getImdb());
				value.put(MovieColumns.LINK_ALTERNATE, movies.get(i).getLinks()
						.getAlternate());
				value.put(MovieColumns.LINK_CAST, movies.get(i).getLinks()
						.getCast());
				value.put(MovieColumns.LINK_CLIPS, movies.get(i).getLinks()
						.getClips());
				value.put(MovieColumns.LINK_REVIEWS, movies.get(i).getLinks()
						.getReviews());
				value.put(MovieColumns.LINK_SIMILAR, movies.get(i).getLinks()
						.getSimilar());
				// values[i].put(MovieColumns.SECTION, marker);
				// L.d(LOG_TAG, i+"");
				values2.add(value);
			}
			processNewMovies(values2);
			return values;
		}
		return null;
	}

	public static ContentValues[] parseMovie(String source) {
		L.d(LOG_TAG, "parseMovie");
		if (source != null) {
			Gson gson = new Gson();
			Movie movie = gson.fromJson(source, Movie.class);
			ContentValues[] values = new ContentValues[1];
			values[0] = new ContentValues();
			values[0].put(MovieColumns.MOVIE_ID, movie.getId());
			values[0].put(MovieColumns.MOVIE_TITLE, movie.getTitle());
			values[0].put(MovieColumns.YEAR, movie.getYear());
			values[0].put(MovieColumns.MPAA, movie.getMpaaRating());
			values[0].put(MovieColumns.RUNTIME, movie.getRuntime());
			values[0].put(MovieColumns.RELEASE_DATE_THEATER, movie
					.getReleaseDates().getTheater());
			values[0].put(MovieColumns.RELEASE_DATE_DVD, movie
					.getReleaseDates().getDvd());
			values[0].put(MovieColumns.CRITICS_CONSENSUS,
					movie.getCriticsConsensus());
			values[0].put(MovieColumns.SYNOPSIS, movie.getSynopsis());
			values[0].put(MovieColumns.RATING_CRITICS, movie.getRatings()
					.getCriticsRating());
			values[0].put(MovieColumns.RATING_CRITICS_SCORE, movie.getRatings()
					.getCriticsScore());
			values[0].put(MovieColumns.RATING_AUDIENCE, movie.getRatings()
					.getAudienceRating());
			values[0].put(MovieColumns.RATING_AUDIENCE_SCORE, movie
					.getRatings().getAudienceScore());
			values[0].put(MovieColumns.POSTERS_DETAILED, movie.getPosters()
					.getDetailed());
			values[0].put(MovieColumns.POSTERS_ORIGINAL, movie.getPosters()
					.getOriginal());
			values[0].put(MovieColumns.POSTERS_PROFILE, movie.getPosters()
					.getProfile());
			values[0].put(MovieColumns.POSTERS_THUMBNAIL, movie.getPosters()
					.getThumbnail());
			values[0].put(MovieColumns.ALTERNATE_IDS, movie.getAlternateIds()
					.getImdb());
			values[0].put(MovieColumns.LINK_ALTERNATE, movie.getLinks()
					.getAlternate());
			values[0].put(MovieColumns.LINK_CAST, movie.getLinks().getCast());
			values[0].put(MovieColumns.LINK_CLIPS, movie.getLinks().getClips());
			values[0].put(MovieColumns.LINK_REVIEWS, movie.getLinks()
					.getReviews());
			values[0].put(MovieColumns.LINK_SIMILAR, movie.getLinks()
					.getSimilar());
			// values[0].put(MovieColumns.SECTION, marker);
			// value.put(MovieColumns.GENRES, movie.getGenres());
			// value.put(MovieColumns.CAST_IDS, movies.);
			// value.put(MovieColumns.DIRECTORS, movies.);
			values[0].put(MovieColumns.STUDIO, movie.getStudio());
			return values;
		}
		return null;
	}

	private void processNewMovies(ArrayList<ContentValues> array) {
		String ids = StringsUtils.join(array, Contract.MovieColumns.MOVIE_ID,
				",");
		Uri uri = ContractUtils
				.getProviderUriFromContract(Contract.MovieColumns.class);
		String field = Contract.MovieColumns.MOVIE_ID;
		Cursor cursor = ContextHolder.getInstance().getContext()
				.getContentResolver()
				.query(uri, null, field + " IN (" + ids + ")", null, null);
		ArrayList<Long> toUpdate = new ArrayList<Long>();
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Long id = Long.valueOf(cursor.getString(cursor
						.getColumnIndex(Contract.MovieColumns.MOVIE_ID)));
				toUpdate.add(id);
				cursor.moveToNext();
			}
		}
		cursor.close();

		updateOldAndPutNew(array, toUpdate);
	}

	private void updateOldAndPutNew(ArrayList<ContentValues> array,
			ArrayList<Long> toUpdate) {
		if (toUpdate.size() > 0) {
			ArrayList<ContentValues> forUpdate = new ArrayList<ContentValues>();
			for (Long id : toUpdate) {
				ContentValues toRemove = null;
				for (ContentValues v : array) {
					int i = (Integer) v.get(Contract.MovieColumns.MOVIE_ID);
					if (i == id) {
						forUpdate.add(v);
						toRemove = v;
						break;
					}
				}
				if (toRemove != null) {
					array.remove(toRemove);
				}
			}

			insertToDb(array);
			updateInDb(forUpdate);
		} else {
			insertToDb(array);
		}
	}

	private void updateInDb(ArrayList<ContentValues> forUpdate) {
		forUpdate.trimToSize();
		Context context = ContextHolder.getInstance().getContext();
		for (int i = 0; i < forUpdate.size(); i++) {
			String where = Contract.MovieColumns.MOVIE_ID
					+ " = "
					+ forUpdate.get(i).getAsString(
							Contract.MovieColumns.MOVIE_ID);
			context.getContentResolver().update(mMoviesUri, forUpdate.get(i),
					where, null);
		}

	}

	private void insertToDb(ArrayList<ContentValues> forInsert) {
		ContentValues[] values = forInsert.toArray(new ContentValues[forInsert
				.size()]);
		Context context = ContextHolder.getInstance().getContext();
		context.getContentResolver().bulkInsert(mMoviesUri, values);
	}

	private String getStringResponse(InputStream is) {
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName(UTF_8)));
			final StringBuilder sb = new StringBuilder();
			int cp;
			try {
				while ((cp = rd.read()) != -1) {
					sb.append((char) cp);
				}
			} catch (IOException e) {
				L.e(LOG_TAG, "can't get response string from source!");
			}
			final String jsonText = sb.toString();
			L.d(LOG_TAG, "source = " + jsonText);
			return jsonText;
		} finally {
			IOUtils.closeStream(is);
			IOUtils.closeStream(rd);
		}
	}

	@Override
	public ContentValues[] process(InputStream data) {
		String source = getStringResponse(data);
		return processSource(source);
	}

	protected ContentValues[] processSource(String source) {
		return parseMovieList(source);
	}

}
