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
import com.sickfuture.letswatch.bo.tmdb.IdName;
import com.sickfuture.letswatch.bo.tmdb.Keywords;
import com.sickfuture.letswatch.content.contract.Contract;

public class KeywordsProcessor implements
		IProcessor<InputStream, ContentValues[]> {

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		Keywords keywords = gson.fromJson(new InputStreamReader(data),
				Keywords.class);
		return processKeywords(keywords);
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver()
				.bulkInsert(
						ContractUtils
								.getProviderUriFromContract(Contract.KeywordsColumns.class),
						result);
		return true;
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_KEYWORDS_PROCESSOR_SERVICE;
	}

	public ContentValues[] processKeywords(Keywords keywords) {
		if (keywords.getKeywords().size() == 0)
			return null;
		ContentValues[] values = new ContentValues[keywords.getKeywords()
				.size()];
		int i = 0;
		for (IdName kw : keywords.getKeywords()) {
			values[i] = ProcessorHelper.processKeywords(kw);
			i++;
		}
		return values;
	}

}
