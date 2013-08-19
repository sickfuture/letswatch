package com.sickfuture.letswatch.bo.tmdb;

public class ResultsMovies {

	private int page, total_pages, total_results;
	private Movie[] results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public Movie[] getResults() {
		return results;
	}

}
