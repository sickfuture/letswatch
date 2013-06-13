package com.sickfuture.letswatch.processor;

import android.content.ContentValues;

public interface IProcessable {

	public ContentValues[] getValues(String source, int marker);
}
