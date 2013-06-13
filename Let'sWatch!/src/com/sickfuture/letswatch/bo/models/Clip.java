package com.sickfuture.letswatch.bo.models;

public class Clip {

	private String title, thumbnail;
	private int duration;

	public String getTitle() {
		return title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public int getDuration() {
		return duration;
	}

	public Links getLinks() {
		return links;
	}

	private Links links;
}
