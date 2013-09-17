package com.sickfuture.letswatch.bo.tmdb;

public class Crew {

	private int id;
	private String name, department, job, profile_path;
	
	private String title, original_title, poster_path, release_date;
	private boolean adult;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public String getJob() {
		return job;
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

	// "id": 7469,
	// "name": "Jim Uhls",
	// "department": "Writing",
	// "job": "Author",
	// "profile_path": null
}
