package com.sickfuture.letswatch.processor;

import android.content.ContentValues;

import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

/**
 * Created by Alex on 4.8.13.
 */
public class InfoProcessor extends BaseMovieProcessor {
    @Override
    protected ContentValues[] processSource(String source) {
        return parseMovie(source, 0); //todo resolve issue
    }

    @Override
    public String getKey() {
        return LetsWatchApplication.INFO_PROCESSOR_SERVICE;
    }
}
