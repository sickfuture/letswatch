package com.sickfuture.letswatch.app.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.sickfuture.letswatch.R;

public class SearchActivity extends SherlockFragmentActivity {

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// 
		//searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      //doMySearch(query);
	    }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		//doMySearch(query);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}

}
