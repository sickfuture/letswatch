package com.sickfuture.letswatch.app;

import com.android.sickfuture.sickcore.app.SickApp;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.source.implemented.HttpInputStreamDataSource;
import com.sickfuture.letswatch.processor.BoxOfficeProcessor;
import com.sickfuture.letswatch.processor.CurrentReleaseProcessor;
import com.sickfuture.letswatch.processor.InfoProcessor;
import com.sickfuture.letswatch.processor.NewReleaseProcessor;
import com.sickfuture.letswatch.processor.OpeningProcessor;
import com.sickfuture.letswatch.processor.SearchProcessor;
import com.sickfuture.letswatch.processor.TheatersProcessor;
import com.sickfuture.letswatch.processor.TopRentalsProcessor;
import com.sickfuture.letswatch.processor.UpcomingDvdProcessor;
import com.sickfuture.letswatch.processor.UpcomingProcessor;

public class LetsWatchApplication extends SickApp {

    public static final String BASE_PROCESSOR_SERVICE = "sickcore:BaseMovieProcessor";
    public static final String BOX_OFFICE_PROCESSOR_SERVICE = "sickcore:BoxOfficeMovieProcessor";
    public static final String OPENING_PROCESSOR_SERVICE = "sickcore:OpeningMovieProcessor";
    public static final String THEATERS_PROCESSOR_SERVICE = "sickcore:TheatersMovieProcessor";
    public static final String UPCOMING_PROCESSOR_SERVICE = "sickcore:UpcomingMovieProcessor";
    public static final String TOP_RENTALS_PROCESSOR_SERVICE = "sickcore:TopRentalsProcessor";
    public static final String UPCOMING_DVD_PROCESSOR_SERVICE = "sickcore:UpcomingDvdProcessor";
    public static final String CURRENT_RELEASE_PROCESSOR_SERVICE = "sickcore:CurrentReleaseProcessor";
    public static final String NEW_RELEASE_PROCESSOR_SERVICE = "sickcore:NewReleaseProcessor";
    public static final String SEARCH_PROCESSOR_SERVICE = "sickcore:SearchProcessor";
    public static final String INFO_PROCESSOR_SERVICE = "sickcore:InfoProcessor";

    @Override
    public void register() {
        /** DATA_SOURCE */
        registerAppService(new HttpInputStreamDataSource());
        /***************/

        /** SUPER_IMAGE_LOADER */
        registerAppService(new SickImageLoader(this));
        /**********************/

        /** PROCESSORS */
        registerAppService(new BoxOfficeProcessor());
        registerAppService(new OpeningProcessor());
        registerAppService(new TheatersProcessor());
        registerAppService(new UpcomingProcessor());
        registerAppService(new TopRentalsProcessor());
        registerAppService(new UpcomingDvdProcessor());
        registerAppService(new CurrentReleaseProcessor());
        registerAppService(new NewReleaseProcessor());
        registerAppService(new SearchProcessor());
        registerAppService(new InfoProcessor());
    }
}
