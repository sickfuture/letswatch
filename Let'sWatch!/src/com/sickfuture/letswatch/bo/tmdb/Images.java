package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class Images {

	public static final int BACKDROP = 1;
	public static final int POSTER = 2;
	public static final int PROFILE = 3;
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private List<Image> backdrops, posters, profiles;

	public List<Image> getProfiles() {
		return profiles;
	}

	public List<Image> getPosters() {
		return posters;
	}

	public List<Image> getBackdrops() {
		return backdrops;
	}
	
}
