package com.sickfuture.letswatch.content.provider.theaters;

import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.provider.CommonSectionProvider;

public class InTheatersProvider extends CommonSectionProvider {

	@Override
	protected Class<?> getContractClass() {
		return Contract.TheatersColumns.class;
	}

}