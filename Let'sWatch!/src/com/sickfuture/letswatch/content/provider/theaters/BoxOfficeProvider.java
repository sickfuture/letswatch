package com.sickfuture.letswatch.content.provider.theaters;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.provider.CommonSectionProvider;

public class BoxOfficeProvider extends CommonSectionProvider {

	@Override
	protected Class<?> getContractClass() {
		return Contract.BoxOfficeColumns.class;
	}

}
