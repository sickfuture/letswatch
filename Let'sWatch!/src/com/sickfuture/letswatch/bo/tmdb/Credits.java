package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class Credits {

	private int id;
	private List<Cast> cast;
	private List<Crew> crew;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Cast> getCast() {
		return cast;
	}

	public List<Crew> getCrew() {
		return crew;
	}
}
