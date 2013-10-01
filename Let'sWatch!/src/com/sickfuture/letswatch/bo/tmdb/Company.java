package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;

public class Company {

	private int id;
	private String name, description, headquarters, logo_path, parent_company;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public String getLogo_path() {
		return logo_path;
	}

	public String getParent_company() {
		return parent_company;
	}

	// "description": null,
	// "headquarters": "San Francisco, California",
	// "homepage": "http://www.lucasfilm.com",
	// "id": 1,
	// "logo_path": "/8rUnVMVZjlmQsJ45UGotD0Uznxj.png",
	// "name": "Lucasfilm",
	// "parent_company": null
}
