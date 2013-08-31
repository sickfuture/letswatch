package com.sickfuture.letswatch.bo.tmdb;

import java.util.List;

public class Keywords {

	private List<IdName> keywords;

	public List<IdName> getKeywords() {
		return keywords;
	}
	
	public String getIdsString(String separator){
		StringBuilder builder = new StringBuilder();
		for(IdName i : keywords){
			builder.append(i.getId()).append(separator);
		}
		return builder.substring(0, builder.length()-separator.length());
	}
}
