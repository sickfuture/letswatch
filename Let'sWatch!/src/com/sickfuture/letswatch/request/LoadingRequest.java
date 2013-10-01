package com.sickfuture.letswatch.request;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.sickfuture.letswatch.R;

public class LoadingRequest implements Parcelable {

	private RequestType mType;
	private String mUrl;
	private int mMarker;
	private String mProcessMethod;
	private Uri mContentUri;

	public LoadingRequest(RequestType requestType, String url, int marker,
			String processMethod, Uri contentUri) {
		super();
		this.mType = requestType;
		this.mUrl = url;
		this.mMarker = marker;
		this.mProcessMethod = processMethod;
		this.mContentUri = contentUri;
	}

	public LoadingRequest(Parcel source) {
		mType = (RequestType) source.readSerializable();
		mUrl = source.readString();
		mMarker = source.readInt();
		mProcessMethod = source.readString();
		mContentUri = Uri.CREATOR.createFromParcel(source);
	}
	
	public RequestType getType() {
		return mType;
	}

	public String getUrl() {
		return mUrl;
	}

	public int getMarker() {
		return mMarker;
	}

	public String getProcessMethod() {
		return mProcessMethod;
	}

	public Uri getContentUri() {
		return mContentUri;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeSerializable(mType);
		parcel.writeString(mUrl);
		parcel.writeInt(mMarker);
		parcel.writeString(mProcessMethod);
		mContentUri.writeToParcel(parcel, 0);
	}

	 public static final Parcelable.Creator<LoadingRequest> CREATOR = new Parcelable.Creator<LoadingRequest>() {

		 public LoadingRequest createFromParcel(Parcel in) {
			 //Log.d(LOG_TAG, "createFromParcel");
			 return new LoadingRequest(in);
		 }

		 public LoadingRequest[] newArray(int size) {
			 return new LoadingRequest[size];
		 }
	 };
		  
	public static class RequestHelper {
		public static final String PROCESS_MOVIE = "parseMovie",
				PROCESS_MOVIE_LIST = "parseMovieList", REQUEST_TYPE = "REQUEST_TYPE", URI = "URI";
		
	}
	
}
