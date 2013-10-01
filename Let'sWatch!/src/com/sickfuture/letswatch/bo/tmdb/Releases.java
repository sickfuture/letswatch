package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.List;

public class Releases {

	private int id;
	private List<Country> countries;

	public List<Country> getCountries() {
		return countries;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
