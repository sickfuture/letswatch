package com.sickfuture.letswatch.content.provider.tmdb;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.sickfuture.letswatch.content.contract.Contract;

public class MovieToCrewProvider extends CommonProvider {

	@Override
	protected Class<?> getContractClass() {
		return Contract.MovieToCrewColumns.class;
	}

}
