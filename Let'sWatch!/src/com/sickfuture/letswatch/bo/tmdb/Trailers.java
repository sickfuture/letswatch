package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class Trailers {

	private int id;
	private List<Video> youtube;

	public List<Video> getYoutube() {
		return youtube;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// quicktime: [ ],
	// youtube: [
	// {
	// name: "Trailer 1",
	// size: "HD",
	// source: "SUXWAEX2jlg"
	// }
	// ]
}
