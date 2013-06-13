package com.sickfuture.letswatch.bo.models;

import java.util.List;

public class Movie {

	private int id, year, runtime;
	private String title, mpaa_rating, critics_consensus, studio;
	private List<String> genres;
	private ReleaseDates release_dates;
	private Ratings ratings;
	private String synopsis;
	private Posters posters;
	private List<Person> abridged_cast, abridged_directors;
	private AlternateIds alternate_ids;
	private Links links;

	public Links getLinks() {
		return links;
	}

	public int getId() {
		return id;
	}

	public int getYear() {
		return year;
	}

	public int getRuntime() {
		return runtime;
	}

	public String getTitle() {
		return title;
	}

	public String getMpaaRating() {
		return mpaa_rating;
	}

	public String getCriticsConsensus() {
		return critics_consensus;
	}

	public String getStudio() {
		return studio;
	}

	public List<String> getGenres() {
		return genres;
	}

	public ReleaseDates getReleaseDates() {
		return release_dates;
	}

	public Ratings getRatings() {
		return ratings;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public Posters getPosters() {
		return posters;
	}

	public List<Person> getAbridgedCast() {
		return abridged_cast;
	}

	public List<Person> getAbridgedDirectors() {
		return abridged_directors;
	}

	public AlternateIds getAlternateIds() {
		return alternate_ids;
	}
}