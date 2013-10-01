package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.List;

public class ResultsReviews {

	private int id;
	private int page, total_pages, total_results;
	private List<Review> results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public List<Review> getResults() {
		return results;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
