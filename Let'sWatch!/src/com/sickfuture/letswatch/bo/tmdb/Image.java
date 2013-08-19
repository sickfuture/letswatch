package com.sickfuture.letswatch.bo.tmdb;

public class Image {

	private String file_path, iso_639_1;
	private int width, height, vote_count;
	private float aspect_ratio, vote_average;

	public String getFile_path() {
		return file_path;
	}

	public String getIso_639_1() {
		return iso_639_1;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getVote_count() {
		return vote_count;
	}

	public float getAspect_ratio() {
		return aspect_ratio;
	}

	public float getVote_average() {
		return vote_average;
	}

	// "file_path": "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg",
	// "width": 1280,
	// "height": 720,
	// "iso_639_1": null,
	// "aspect_ratio": 1.78,
	// "vote_average": 6.6470588235294121,
	// "vote_count": 17
}
