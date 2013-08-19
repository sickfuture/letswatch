package com.sickfuture.letswatch.bo.tmdb;

public class Collection {

	private int id;
	private String backdrop_path, name, poster_path;
	private Movie[] parts;
	private Images images;

	public Images getImages() {
		return images;
	}

	public int getId() {
		return id;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public String getName() {
		return name;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public Movie[] getParts() {
		return parts;
	}

	// "backdrop_path": "/mOTtuakUTb1qY6jG6lzMfjdhLwc.jpg",
	// "id": 10,
	// "name": "Star Wars Collection",
	// "parts": [{
	// "backdrop_path": "/mOTtuakUTb1qY6jG6lzMfjdhLwc.jpg",
	// "id": 11,
	// "poster_path": "/qoETrQ73Jbd2LDN8EUfNgUerhzG.jpg",
	// "release_date": "1977-12-27",
	// "title": "Star Wars: Episode IV: A New Hope"
	// }],
	// "poster_path": "/6rddZZpxMQkGlpQYVVxb2LdQRI3.jpg"
}
