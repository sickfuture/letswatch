package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.List;

public class ResultsCollections {
	private int page, total_pages, total_results;
	private List<Collection> results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public List<Collection> getResults() {
		return results;
	}
}
