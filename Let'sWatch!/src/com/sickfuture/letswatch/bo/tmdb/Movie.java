package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class Movie {

	private boolean adult;
	private String backdrop_path, homepage, imdb_id, original_title, overview,
			poster_path, release_date, status, tagline, title;
	private Collection belongs_to_collection;
	private int budget, id, revenue, runtime, vote_count;
	private List<IdName> genres;
	private float popularity, vote_average;
	private List<Company> production_companies;
	private List<Country> production_countries;
	private List<Language> spoken_languages;
	// additional
	private AltTitles alternative_titles;
	private Casts casts;
	private Images images;
	private Keywords keywords;
	private Releases releases;
	private Trailers trailers;
	private Translations translations;
	private ResultsMovies similar_movies;
	private ResultsReviews reviews;
	private ResultsLists lists;
	// another
	private boolean favorite, watchlist;
	private Rate rated;
	//
	private String character, department, job;

	public String getCharacter() {
		return character;
	}

	public String getDepartment() {
		return department;
	}

	public String getJob() {
		return job;
	}

	public AltTitles getAlternative_titles() {
		return alternative_titles;
	}

	public Casts getCasts() {
		return casts;
	}

	public Images getImages() {
		return images;
	}

	public Keywords getKeywords() {
		return keywords;
	}

	public Releases getReleases() {
		return releases;
	}

	public Trailers getTrailers() {
		return trailers;
	}

	public Translations getTranslations() {
		return translations;
	}

	public ResultsMovies getSimilar_movies() {
		return similar_movies;
	}

	public String getSimilarIds() {
		StringBuilder builder = new StringBuilder();
		for (Movie movie : similar_movies.getResults()) {
			builder.append(movie.getId()).append(",");
		}
		return builder.substring(0, builder.length() - 1);
	}

	public ResultsReviews getReviews() {
		return reviews;
	}

	public ResultsLists getLists() {
		return lists;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public boolean isWatchlist() {
		return watchlist;
	}

	public Rate getRated() {
		return rated;
	}

	public boolean isAdult() {
		return adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public Collection getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public String getHomepage() {
		return homepage;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public String getOverview() {
		return overview;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public String getRelease_date() {
		return release_date;
	}

	public String getStatus() {
		return status;
	}

	public String getTagline() {
		return tagline;
	}

	public String getTitle() {
		return title;
	}

	public int getBudget() {
		return budget;
	}

	public int getId() {
		return id;
	}

	public int getRevenue() {
		return revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public int getVote_count() {
		return vote_count;
	}

	public List<IdName> getGenres() {
		return genres;
	}

	public String getGenresIds() {
		if (genres.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for (IdName i : genres) {
				builder.append(i.getId()).append(",");
			}
			return builder.substring(0, builder.length() - 1);
		}
		return null;
	}

	public String getGenresString() {
		if (genres.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for (IdName i : genres) {
				builder.append(i.getName()).append(",");
			}
			return builder.substring(0, builder.length() - 1);
		}
		return null;
	}
	
	public float getPopularity() {
		return popularity;
	}

	public float getVote_average() {
		return vote_average;
	}

	public List<Company> getProduction_companies() {
		return production_companies;
	}

	public List<Country> getProduction_countries() {
		return production_countries;
	}

	public List<Language> getSpoken_languages() {
		return spoken_languages;
	}

	// "adult": false,
	// "backdrop_path": "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg",
	// "belongs_to_collection": null,
	// "budget": 63000000,
	// "genres": [
	// {
	// "id": 28,
	// "name": "Action"
	// },
	// {
	// "id": 18,
	// "name": "Drama"
	// },
	// {
	// "id": 53,
	// "name": "Thriller"
	// }
	// ],
	// "homepage": "",
	// "id": 550,
	// "imdb_id": "tt0137523",
	// "original_title": "Fight Club",
	// "overview":
	// "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
	// "popularity": 61151.745000000003,
	// "poster_path": "/2lECpi35Hnbpa4y46JX0aY3AWTy.jpg",
	// "production_companies": [
	// {
	// "name": "20th Century Fox",
	// "id": 25
	// }
	// ],
	// "production_countries": [
	// {
	// "iso_3166_1": "DE",
	// "name": "Germany"
	// },
	// {
	// "iso_3166_1": "US",
	// "name": "United States of America"
	// }
	// ],
	// "release_date": "1999-10-15",
	// "revenue": 100853753,
	// "runtime": 139,
	// "spoken_languages": [
	// {
	// "iso_639_1": "en",
	// "name": "English"
	// }
	// ],
	// "status": "Released",
	// "tagline":
	// "How much can you know about yourself if you've never been in a fight?",
	// "title": "Fight Club",
	// "vote_average": 9.0999999999999996,
	// "vote_count": 174
}