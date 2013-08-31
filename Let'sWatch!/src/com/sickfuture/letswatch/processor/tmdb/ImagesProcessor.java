package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Image;
import com.sickfuture.letswatch.bo.tmdb.Images;
import com.sickfuture.letswatch.content.contract.Contract;

public class ImagesProcessor implements IProcessor<InputStream, ContentValues[]>{

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_IMAGES_PROCESSOR_SERVICE ;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		Images images = gson.fromJson(new InputStreamReader(data), Images.class);
		return processImages(images);
	}

	public ContentValues[] processImages(Images images) {
		int id = images.getId();
		List<Image> bd = images.getBackdrops();
		List<Image> p = images.getPosters();
		List<Image> pr = images.getProfiles();
		ContentValues[] values = new ContentValues[bd.size()+p.size()+pr.size()];
		int i = 0;
		for(Image image : bd){
			values[i] = ProcessorHelper.processImage(image);
			values[i].put(Contract.ImageColumns.TYPE, Images.BACKDROP);
			i++;
		}
		for(Image image : p){
			values[i] = ProcessorHelper.processImage(image);
			values[i].put(Contract.ImageColumns.TYPE, Images.POSTER);
			i++;
		}
		for(Image image : pr){
			values[i] = ProcessorHelper.processImage(image);
			values[i].put(Contract.ImageColumns.TYPE, Images.PROFILE);
			i++;
		}
		return values;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		
		return true;
	}

}
