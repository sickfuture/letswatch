package com.sickfuture.letswatch.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.ViewHolderCursorAdapter.ViewHolder;

public class CriticsRepliesCursorAdapter extends ViewHolderCursorAdapter {

	public CriticsRepliesCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public ArrayList<String> getTableColumns() {
		ArrayList<String> list = new ArrayList<String>();
		//list.add(Contract.);
		return list;
	}

	@Override
	public HashMap<String, Integer> getViewPack() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("critics_name", R.id.textview_adapter_info_critics_name);
		map.put("critics_reply", R.id.textview_adapter_info_critics_reply);
		return map;
	}

	@Override
	public View getNewView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return View.inflate(context, R.layout.view_movie_details_header, null);
	}

}
