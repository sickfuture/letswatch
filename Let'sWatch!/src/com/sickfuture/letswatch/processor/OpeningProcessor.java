package com.sickfuture.letswatch.processor;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

/**
 * Created by Alex on 4.8.13.
 */
public class OpeningProcessor extends BaseMovieProcessor {

	@Override
	public String getKey() {
		return LetsWatchApplication.OPENING_PROCESSOR_SERVICE;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver()
				.bulkInsert(
						ContractUtils
								.getProviderUriFromContract(Contract.OpeningColumns.class),
						result);
		return true;
	}
}
