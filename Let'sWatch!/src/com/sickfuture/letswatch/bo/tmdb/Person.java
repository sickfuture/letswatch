package com.sickfuture.letswatch.bo.tmdb;

public class Person {

	private boolean adult;
	private String[] also_known_as;
	private String biography, birthday, deathday, homepage, name,
			place_of_birth, profile_path;
	private int id;
	// additional
	private Casts credits;
	private Images images;

	public boolean isAdult() {
		return adult;
	}

	public String[] getAlso_known_as() {
		return also_known_as;
	}

	public String getBiography() {
		return biography;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getDeathday() {
		return deathday;
	}

	public String getHomepage() {
		return homepage;
	}

	public String getName() {
		return name;
	}

	public String getPlace_of_birth() {
		return place_of_birth;
	}

	public String getProfile_path() {
		return profile_path;
	}

	public int getId() {
		return id;
	}

	public Casts getCredits() {
		return credits;
	}

	public Images getImages() {
		return images;
	}
	
	// "adult": false,
	// "also_known_as": [],
	// "biography":
	// "From Wikipedia, the free encyclopedia.\n\nWilliam Bradley \"Brad\" Pitt (born December 18, 1963) is an American actor and film producer. Pitt has received two Academy Award nominations and four Golden Globe Award nominations, winning one. He has been described as one of the world's most attractive men, a label for which he has received substantial media attention.\n\nPitt began his acting career with television guest appearances, including a role on the CBS prime-time soap opera Dallas in 1987. He later gained recognition as the cowboy hitchhiker who seduces Geena Davis's character in the 1991 road movie Thelma & Louise. Pitt's first leading roles in big-budget productions came with A River Runs Through It (1992) and Interview with the Vampire (1994). He was cast opposite Anthony Hopkins in the 1994 drama Legends of the Fall, which earned him his first Golden Globe nomination. In 1995 he gave critically acclaimed performances in the crime thriller Seven and the science fiction film 12 Monkeys, the latter securing him a Golden Globe Award for Best Supporting Actor and an Academy Award nomination. Four years later, in 1999, Pitt starred in the cult hit Fight Club. He then starred in the major international hit as Rusty Ryan in Ocean's Eleven (2001) and its sequels, Ocean's Twelve (2004) and Ocean's Thirteen (2007). His greatest commercial successes have been Troy (2004) and Mr. & Mrs. Smith (2005). Pitt received his second Academy Award nomination for his title role performance in the 2008 film The Curious Case of Benjamin Button.\n\nFollowing a high-profile relationship with actress Gwyneth Paltrow, Pitt was married to actress Jennifer Aniston for five years. Pitt lives with actress Angelina Jolie in a relationship that has generated wide publicity. He and Jolie have six children—Maddox, Pax, Zahara, Shiloh, Knox, and Vivienne. Since beginning his relationship with Jolie, he has become increasingly involved in social issues both in the United States and internationally. Pitt owns a production company named Plan B Entertainment, whose productions include the 2007 Academy Award winning Best Picture, The Departed.\n\nDescription above from the Wikipedia article Brad Pitt, licensed under CC-BY-SA, full list of contributors on Wikipedia.",
	// "birthday": "1963-12-18",
	// "deathday": "",
	// "homepage": "http://simplybrad.com/",
	// "id": 287,
	// "name": "Brad Pitt",
	// "place_of_birth": "Shawnee, Oklahoma, United States",
	// "profile_path": "/w8zJQuN7tzlm6FY9mfGKihxp3Cb.jpg"
}
