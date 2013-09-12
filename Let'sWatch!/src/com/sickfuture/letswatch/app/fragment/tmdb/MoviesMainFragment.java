package com.sickfuture.letswatch.app.fragment.tmdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.SickListFragment;

public class MoviesMainFragment extends SickListFragment {

	private final static String IMAGE_RESOURCE = "IMAGE", TITLE = "TITLE";
	private List<Map<String, Object>> data;
	private String[] from = new String[] { IMAGE_RESOURCE, TITLE };
	private final String[] titles = new String[] { "upcoming", "now playing",
			"popular", "top rated" };
	private int[] to = new int[] { R.id.image_view_section_backdrop,
			R.id.text_view_section_title };

	@Override
	protected void start(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void error(Exception exception) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void done(Bundle result) {
		// TODO Auto-generated method stub

	}

	@Override
	public BaseAdapter adapter() {
		data = new ArrayList<Map<String, Object>>(titles.length);
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put(IMAGE_RESOURCE, R.color.green);
			m.put(TITLE, titles[i]);
			data.add(m);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
				R.layout.adapter_movie_grid, from, to);
		return adapter;
	}

	@Override
	public void onListItemClick(AdapterView<?> list, View view, int position,
			long id, IListClickable clickable) {
		switch (position) {
		case 0:
			//replaceFragment();
			break;

		default:
			break;
		}

	}

	@Override
	protected int fragmentResource() {
		return R.layout.fragment_list;
	}

	@Override
	protected int listViewResource() {
		return R.id.list_view_fragment_list;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				getResources().getString(R.string.app_name));
		super.onStart();
	}


}
