package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.List;

public class ResultsMovies {

	private Dates dates;
	private int page, total_pages, total_results;
	private List<Movie> results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public List<Movie> getResults() {
		return results;
	}

	public Dates getDates() {
		return dates;
	}

}

class Dates {

	private String minimum, maximum;

	public String getMinimum() {
		return minimum;
	}

	public String getMaximum() {
		return maximum;
	}
}