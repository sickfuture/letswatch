package com.sickfuture.letswatch.bo.models;

import java.util.List;

public class ReviewList {

	private int total;
	private List<Review> reviews;
	private Links links;

	public int getTotal() {
		return total;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public Links getLinks() {
		return links;
	}

}
