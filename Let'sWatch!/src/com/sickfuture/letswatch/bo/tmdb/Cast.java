package com.sickfuture.letswatch.bo.tmdb;

public class Cast {

	private int id, order;
	private String name, character, profile_path;
	
	private String title, original_title, poster_path, release_date;
	private boolean adult;

	public int getId() {
		return id;
	}

	public int getOrder() {
		return order;
	}

	public String getName() {
		return name;
	}

	public String getCharacter() {
		return character;
	}

	public String getProfile_path() {
		return profile_path;
	}

	public String getTitle() {
		return title;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public String getRelease_date() {
		return release_date;
	}

	public boolean isAdult() {
		return adult;
	}

	// "id": 819,
	// "name": "Edward Norton",
	// "character": "The Narrator",
	// "order": 0,
	// "profile_path": "/7cf2mCVI0qv2PnZVNbbEktS8Xae.jpg"
}
