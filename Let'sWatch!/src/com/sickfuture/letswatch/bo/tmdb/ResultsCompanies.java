package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class ResultsCompanies {

	private int page, total_pages, total_results;
	private List<Company> results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public List<Company> getResults() {
		return results;
	}
}
