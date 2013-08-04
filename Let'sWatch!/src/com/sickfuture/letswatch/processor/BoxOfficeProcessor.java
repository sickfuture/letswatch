package com.sickfuture.letswatch.processor;

import android.content.ContentValues;

import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

/**
 * Created by Alex on 3.8.13.
 */
public class BoxOfficeProcessor extends BaseMovieProcessor {
    @Override
    public String getKey() {
        return LetsWatchApplication.BOX_OFFICE_PROCESSOR_SERVICE;
    }

    @Override
    protected ContentValues[] processSource(String source) {
        return parseMovieList(source, Contract.BOX_OFFICE_SECTION);
    }
}
