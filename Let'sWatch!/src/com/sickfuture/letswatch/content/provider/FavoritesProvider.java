package com.sickfuture.letswatch.content.provider;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.sickfuture.letswatch.content.contract.Contract;

public class FavoritesProvider extends CommonProvider {

	@Override
	protected Class<?> getContractClass() {
		return Contract.FavoriteColumns.class;
	}

}
