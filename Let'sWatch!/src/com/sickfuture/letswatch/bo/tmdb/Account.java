package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;

public class Account {

	private int id;
	private String name, username, iso_3166_1, iso_639_1;
	private boolean include_adult;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUsername() {
		return username;
	}
	public String getIso_3166_1() {
		return iso_3166_1;
	}
	public String getIso_639_1() {
		return iso_639_1;
	}
	public boolean isInclude_adult() {
		return include_adult;
	}
	
//	"id": 36,
//    "include_adult": false,
//    "iso_3166_1": "US",
//    "iso_639_1": "en",
//    "name": "John Doe",
//    "username": "johndoe"
}
