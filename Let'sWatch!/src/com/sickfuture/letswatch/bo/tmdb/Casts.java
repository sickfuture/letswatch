package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;
import java.util.List;

public class Casts {

	private int id;
	private List<Cast> cast;
	private List<Crew> crew;

	public List<Cast> getCast() {
		return cast;
	}

	public List<Crew> getCrew() {
		return crew;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}