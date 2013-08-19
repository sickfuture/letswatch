package com.sickfuture.letswatch.bo.tmdb;

public class GuestSession {

	private String expires_at, guest_session_id;
	private boolean success;
	
	public String getExpires_at() {
		return expires_at;
	}
	public String getSessionId() {
		return guest_session_id;
	}
	public boolean isSuccess() {
		return success;
	}

//	"success": true,
//    "guest_session_id": "0c550fd5da2fc3f321ab3bs9b60ca108",
//    "expires_at": "2012-12-04 22:51:19 UTC"
}
