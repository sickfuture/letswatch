package com.sickfuture.letswatch.bo.tmdb;
public class List {

	private String description, id, iso_639_1, list_type, name, poster_path,
			created_by;
	private int favorite_count, item_count;
	private java.util.List<Movie> items;
	// additional
	private boolean item_present;

	public String getCreated_by() {
		return created_by;
	}

	public java.util.List<Movie> getItems() {
		return items;
	}

	public boolean isItem_present() {
		return item_present;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String getIso_639_1() {
		return iso_639_1;
	}

	public String getList_type() {
		return list_type;
	}

	public String getName() {
		return name;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public int getFavorite_count() {
		return favorite_count;
	}

	public int getItem_count() {
		return item_count;
	}

	// "description":
	// "A list of films that earned its actresses the Razzie Award for Worst Actress of the year.",
	// "favorite_count": 2,
	// "id": "509fca90760ee3490f000a12",
	// "item_count": 40,
	// "iso_639_1": "en",
	// "list_type": "movie",
	// "name": "Films featuring a Razzie winning Worst Actress Performance",
	// "poster_path": "/eL61XP1l8H0qrBPJoKqhXdxumkw.jpg"
}
