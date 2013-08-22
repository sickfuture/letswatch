package com.sickfuture.letswatch.bo.tmdb;

public class ResultsReviews {

	private int page, total_pages, total_results;
	private Review[] results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public Review[] getResults() {
		return results;
	}
}
