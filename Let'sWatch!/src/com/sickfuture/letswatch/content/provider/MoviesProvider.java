package com.sickfuture.letswatch.content.provider;

import com.android.sickfuture.sickcore.content.CommonProvider;
import com.sickfuture.letswatch.content.contract.Contract;

public class MoviesProvider extends CommonProvider {

    @Override
    protected Class<?> getContractClass() {
        return Contract.MovieColumns.class;
    }

}
