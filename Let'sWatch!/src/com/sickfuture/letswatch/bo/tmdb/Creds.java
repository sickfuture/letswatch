package com.sickfuture.letswatch.bo.tmdb;

public class Creds {
	
	private String expires_at, request_token;
	private boolean success;
	
	public String getExpires_at() {
		return expires_at;
	}
	public String getRequest_token() {
		return request_token;
	}
	public boolean isSuccess() {
		return success;
	}
	
//	"expires_at": "2012-02-09 19:50:25 UTC",
//    "request_token": "641bf16c663db167c6cffcdff41126039d4445bf",
//    "success": true

}
