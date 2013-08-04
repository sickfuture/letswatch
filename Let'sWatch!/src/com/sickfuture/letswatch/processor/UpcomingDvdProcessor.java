package com.sickfuture.letswatch.processor;

import android.content.ContentValues;

import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

/**
 * Created by Alex on 4.8.13.
 */
public class UpcomingDvdProcessor extends BaseMovieProcessor {
    @Override
    protected ContentValues[] processSource(String source) {
        return parseMovieList(source, Contract.UPCOMING_DVD_SECTION);
    }

    @Override
    public String getKey() {
        return LetsWatchApplication.UPCOMING_DVD_PROCESSOR_SERVICE;
    }
}
