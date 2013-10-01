package com.sickfuture.letswatch.processor.tmdb.persons;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.google.gson.Gson;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.bo.tmdb.Person;
import com.sickfuture.letswatch.bo.tmdb.ResultsPersons;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.contract.Contract.PopularPeopleTmdbColumns;
import com.sickfuture.letswatch.processor.tmdb.BaseListProcessor;
import com.sickfuture.letswatch.processor.tmdb.ProcessorHelper;

public class PopularPersonsProcessor extends
		BaseListProcessor<InputStream, ContentValues[]> {

	@Override
	public ContentValues[] process(InputStream data) {
		Gson gson = new Gson();
		ResultsPersons resultsPersons = gson.fromJson(new InputStreamReader(data), ResultsPersons.class);
		List<Person> persons = resultsPersons.getResults();
		ContentValues[] values = new ContentValues[persons.size()];
		ArrayList<ContentValues> values2 = new ArrayList<ContentValues>();
		for (int i = 0; i < persons.size(); i++) {
			values[i] = new ContentValues();
			values[i].put(PopularPeopleTmdbColumns.PERSON_TMDB_ID, persons.get(i).getId());

			values2.add(ProcessorHelper.processPerson(persons.get(i)));
		}
		processNew(values2);
		return values;
	}

	@Override
	public boolean cache(ContentValues[] result, Context context) {
		Uri uri = ContractUtils.getProviderUriFromContract(Contract.PopularPeopleTmdbColumns.class);
		context.getContentResolver().bulkInsert(uri, result);
		return true;
	}

	@Override
	public String getKey() {
		return LetsWatchApplication.TMDB_POPULAR_PERSON_PROCESSOR_SERVICE;
	}

	@Override
	protected String getIdField() {
		return Contract.PersonColumns.TMDB_ID;
	}

	@Override
	protected Uri getUri() {
		return ContractUtils.getProviderUriFromContract(Contract.PersonColumns.class);
	}

}
