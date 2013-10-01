package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.List;
import com.sickfuture.letswatch.bo.tmdb.ResultsLists;
import com.sickfuture.letswatch.content.contract.Contract;

public class ListsProcessor implements IProcessor<InputStream, ContentValues[]> {

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_LISTS_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		ResultsLists lists = gson.fromJson(new InputStreamReader(data),
				ResultsLists.class);
		return processLists(lists);
	}

	public ContentValues[] processLists(ResultsLists lists) {
		if (lists.getResults().size() == 0)
			return null;
		long movieId = lists.getId();
		int i = 0;
		ContentValues[] values = new ContentValues[lists.getResults().size()];
		ContentValues[] values2 = new ContentValues[lists.getResults().size()];
		for (List list : lists.getResults()) {
			values[i].put(Contract.MovieToListColumns.MOVIE_TMDB_ID, movieId);
			values[i].put(Contract.MovieToListColumns.MOVIE_TMDB_ID, movieId);
			values2[i] = ProcessorHelper.processList(list);
			i++;
		}
		// TODO putLists(values2);
		return values;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		// TODO cache
		return false;
	}

}
