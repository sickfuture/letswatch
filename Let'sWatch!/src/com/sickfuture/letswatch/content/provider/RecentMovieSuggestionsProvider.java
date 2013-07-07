package com.sickfuture.letswatch.content.provider;

import android.content.SearchRecentSuggestionsProvider;

public class RecentMovieSuggestionsProvider extends
		SearchRecentSuggestionsProvider {

	public final static String AUTHORITY = "com.sickfuture.letswatch.content.provider.RecentMovieSuggestionsProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public RecentMovieSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
