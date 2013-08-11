package com.sickfuture.letswatch.content.provider;

import com.sickfuture.letswatch.content.contract.Contract;

public class SearchProvider extends CommonSectionProvider {

	@Override
	protected Class<?> getContractClass() {
		return Contract.SearchColumns.class;
	}

}
