package com.sickfuture.letswatch.processor;

import android.content.ContentValues;

import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

/**
 * Created by Alex on 4.8.13.
 */
public class TopRentalsProcessor extends BaseMovieProcessor {
    @Override
    protected ContentValues[] processSource(String source) {
        return parseMovieList(source, Contract.TOP_RENTALS_SECTION);
    }

    @Override
    public String getKey() {
        return LetsWatchApplication.TOP_RENTALS_PROCESSOR_SERVICE;
    }
}
