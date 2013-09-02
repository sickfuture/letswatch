package com.sickfuture.letswatch.processor.tmdb.persons;

import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.net.Uri;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Person;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.processor.tmdb.InsertOrUpdateProcessor;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

public class PersonProcessor extends InsertOrUpdateProcessor<InputStream> {

	@Override
	public ContentValues process(InputStream data) {
		Gson gson = new Gson();
		Person person = gson
				.fromJson(new InputStreamReader(data), Person.class);
		return ProcessorHelper.processPerson(person);
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_PERSON_PROCESSOR_SERVICE;
	}

	@Override
	public Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.PersonColumns.class);
	}

	@Override
	public String getIdField() {
		return Contract.PersonColumns.TMDB_ID;
	}

}
