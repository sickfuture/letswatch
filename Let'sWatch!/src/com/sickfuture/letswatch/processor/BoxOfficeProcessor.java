package com.sickfuture.letswatch.processor;

import android.content.ContentValues;
import android.content.Context;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

/**
 * Created by Alex on 3.8.13.
 */
public class BoxOfficeProcessor extends BaseMovieProcessor {
	@Override
	public String getKey() {
		return LetsWatchApplication.BOX_OFFICE_PROCESSOR_SERVICE;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		context.getContentResolver()
				.bulkInsert(
						ContractUtils
								.getProviderUriFromContract(Contract.BoxOfficeColumns.class),
						result);
		return true;
	}

}
