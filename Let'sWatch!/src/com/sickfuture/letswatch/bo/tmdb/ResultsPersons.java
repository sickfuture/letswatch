package com.sickfuture.letswatch.bo.tmdb;

public class ResultsPersons {

	private int page, total_pages, total_results;
	private Person[] results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public Person[] getResults() {
		return results;
	}
}
