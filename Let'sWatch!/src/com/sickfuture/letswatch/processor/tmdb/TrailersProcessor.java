package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Trailers;
import com.sickfuture.letswatch.bo.tmdb.Video;
import com.sickfuture.letswatch.content.contract.Contract;

public class TrailersProcessor implements IProcessor<InputStream, ContentValues[]>{

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_TRAILERS_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		Trailers trailers = gson.fromJson(new InputStreamReader(data), Trailers.class);
		return processTrailers(trailers);
	}

	public ContentValues[] processTrailers(Trailers trailers) {
		if(trailers.getYoutube().size() == 0)
		return null;
		long id = trailers.getId();
		ContentValues[] values = new ContentValues[trailers.getYoutube().size()];
		int i = 0;
		for(Video video : trailers.getYoutube()){
			values[i] = ProcessorHelper.processVideo(video);
			values[i].put(Contract.VideoColumns.TMDB_MOVIE_ID, id);
			i++;
		}
		return values;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

}
