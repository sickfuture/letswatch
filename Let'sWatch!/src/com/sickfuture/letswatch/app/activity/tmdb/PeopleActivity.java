package com.sickfuture.letswatch.app.activity.tmdb;

import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.people.PopularPersonsFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class PeopleActivity extends DrawerActivity implements IListClickable {

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction()
				.replace(CONTENT_FRAME, new PopularPersonsFragment()).commit();
	}

	@Override
	protected int getActivityNumberInDrawer() {
		return ACTIVITY_PEOPLE;
	}

	@Override
	public void onItemListClick(Bundle arguments) {
		// TODO Auto-generated method stub
		
	}

}
