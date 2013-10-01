package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.ArrayList;

public class ResultsLists {

	private int id, page, total_pages, total_results;
	private ArrayList<List> results;

	public int getPage() {
		return page;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public ArrayList<List> getResults() {
		return results;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
