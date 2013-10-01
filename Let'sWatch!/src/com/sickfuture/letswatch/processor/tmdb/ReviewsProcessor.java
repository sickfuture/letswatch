package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.source.IProcessor;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.ResultsReviews;
import com.sickfuture.letswatch.bo.tmdb.Review;
import com.sickfuture.letswatch.content.contract.Contract;

public class ReviewsProcessor implements
		IProcessor<InputStream, ContentValues[]> {

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_REVIEWS_PROCESSOR_SERVICE;
	}

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		ResultsReviews reviews = gson.fromJson(new InputStreamReader(data),
				ResultsReviews.class);
		return processReviews(reviews);
	}

	public ContentValues[] processReviews(ResultsReviews reviews) {
		if (reviews.getResults().size() == 0)
			return null;
		int i = 0;
		long id = reviews.getId();
		ContentValues[] values = new ContentValues[reviews.getResults().size()];
		for (Review review : reviews.getResults()) {
			values[i] = ProcessorHelper.processReview(review);
			values[i].put(Contract.ReviewColumns.MEDIA_ID, id);
			i++;
		}
		return values;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver()
				.bulkInsert(
						ContractUtils
								.getProviderUriFromContract(Contract.ReviewColumns.class),
						result);
		return true;
	}

}
