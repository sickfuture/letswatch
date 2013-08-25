package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class ImagesConfig {

	private String base_url, secure_base_url;
	private List<String> poster_sizes, backdrop_sizes, profile_sizes, logo_sizes;
	
	public String getBase_url() {
		return base_url;
	}
	public String getSecure_base_url() {
		return secure_base_url;
	}
	public List<String> getPoster_sizes() {
		return poster_sizes;
	}
	public List<String> getBackdrop_sizes() {
		return backdrop_sizes;
	}
	public List<String> getProfile_sizes() {
		return profile_sizes;
	}
	public List<String> getLogo_sizes() {
		return logo_sizes;
	}
	
	
//	"base_url": "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/",
//    "secure_base_url": "https://d3gtl9l2a4fn1j.cloudfront.net/t/p/",
//    "poster_sizes": [
//        "w92",
//        "w154",
//        "w185",
//        "w342",
//        "w500",
//        "original"
//    ],
//    "backdrop_sizes": [
//        "w300",
//        "w780",
//        "w1280",
//        "original"
//    ],
//    "profile_sizes": [
//        "w45",
//        "w185",
//        "h632",
//        "original"
//    ],
//    "logo_sizes": [
//        "w45",
//        "w92",
//        "w154",
//        "w185",
//        "w300",
//        "w500",
//        "original"
//    ]
}
