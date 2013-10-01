package com.sickfuture.letswatch.processor.tmdb;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.List;
import com.sickfuture.letswatch.content.contract.Contract;

import android.content.ContentValues;
import android.net.Uri;

public class ListProcessor extends InsertOrUpdateProcessor<InputStream> {

	@Override
	public ContentValues process(InputStream data) {
		Gson gson = new Gson();
		List list = gson.fromJson(new InputStreamReader(data), List.class);
		return ProcessorHelper.processList(list);
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_LIST_PROCESSOR_SERVICE;
	}

	@Override
	public Uri getUri() {
		return ContractUtils.getProviderUriFromContract(Contract.ListColumns.class);
	}

	@Override
	public String getIdField() {
		return Contract.ListColumns.TMDB_ID;
	}

}
