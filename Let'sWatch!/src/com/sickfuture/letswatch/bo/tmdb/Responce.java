package com.sickfuture.letswatch.bo.tmdb;

import com.sickfuture.letswatch.R;

public class Responce {

	private int status_code;
	private String status_message;
	private String list_id;

	public String getList_id() {
		return list_id;
	}

	public int getStatus_code() {
		return status_code;
	}

	public String getStatus_message() {
		return status_message;
	}

	// "status_code": 12,
	// "status_message": "The item/record was updated successfully"
}
