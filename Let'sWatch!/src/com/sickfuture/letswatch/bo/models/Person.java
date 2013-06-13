package com.sickfuture.letswatch.bo.models;

import java.util.List;

public class Person {

	private int id;
	private String name;
	private List<String> characters;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getCharacters() {
		return characters;
	}
}