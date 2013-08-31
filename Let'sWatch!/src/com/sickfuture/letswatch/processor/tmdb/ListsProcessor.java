package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;

public class ListsProcessor implements IProcessor<InputStream, ContentValues[]> {

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_LISTS_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		
		return null;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		// TODO Auto-generated method stub
		return false;
	}

}
