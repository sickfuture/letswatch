package com.sickfuture.letswatch.app.activity.tmdb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

public class DiscoverActivity extends DrawerActivity {

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, getRightDrawerView());
	}

	@Override
	protected int getActivityNumberInDrawer() {
		return ACTIVITY_DISCOVER;
	}

	@Override
	public void performSearch(String query) {
		
	}

}
