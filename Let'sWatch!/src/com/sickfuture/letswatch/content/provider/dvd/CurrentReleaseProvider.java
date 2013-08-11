package com.sickfuture.letswatch.content.provider.dvd;

import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.provider.CommonSectionProvider;

public class CurrentReleaseProvider extends CommonSectionProvider {

	@Override
	protected Class<?> getContractClass() {
		return Contract.CurrentReleaseColumns.class;
	}

}
