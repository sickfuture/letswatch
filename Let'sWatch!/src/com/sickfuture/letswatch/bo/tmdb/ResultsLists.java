package com.sickfuture.letswatch.bo.tmdb;

public class ResultsLists {

	private int page, total_pages, total_results;
	private List[] results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public List[] getResults() {
		return results;
	}

}
