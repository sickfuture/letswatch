package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;

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

	public void setName(String name) {
		this.name = name;
	}

	public void setProfile_path(String profile_path) {
		this.profile_path = profile_path;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	// "id": 7469,
	// "name": "Jim Uhls",
	// "department": "Writing",
	// "job": "Author",
	// "profile_path": null
}
