package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.List;

public class ResultsIdNames {

	private int page, total_pages, total_results;
	private List<IdName> results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public List<IdName> getResults() {
		return results;
	}
}
