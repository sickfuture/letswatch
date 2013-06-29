package com.sickfuture.letswatch.app.fragment.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.callback.IListClickable;

public abstract class SickListFragment extends SherlockFragment implements
		OnRefreshListener<ListView>, OnScrollListener,
		OnItemClickListener {

	private static final String LOG_TAG = SickListFragment.class
			.getSimpleName();

	protected PullToRefreshListView mListView;
	private IListClickable mClickable;
	private BroadcastReceiver mBroadcastReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(fragmentResource(), null);
		mListView = (PullToRefreshListView) view.findViewById(listViewResource());
		mListView.setAdapter(adapter());
		mListView.setOnItemClickListener(this);
		mListView.setOnRefreshListener(this);
		mBroadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				handleOnRecieve(context, intent);
				
			}
		};
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof IListClickable))
			throw new IllegalArgumentException(
					"Activity must implements IListClickable");
		mClickable = (IListClickable) activity;
		super.onAttach(activity);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		onListItemClick(list, view, position, id, mClickable);
	}

	@Override
	public void onPause() {
		getSherlockActivity().unregisterReceiver(mBroadcastReceiver);
		super.onPause();
	}

	@Override
	public void onResume() {
		getSherlockActivity().registerReceiver(mBroadcastReceiver, filter());
		super.onResume();
	}

	protected int fragmentResource() {
		return R.layout.fragment_common;
	}

	protected int listViewResource() {
		return R.id.list_view_common_fragment;
	}

	public abstract BaseAdapter adapter();
	
	public abstract void onListItemClick(AdapterView<?> list, View view, int position,
			long id, IListClickable clickable);
	
	public abstract IntentFilter filter();

	public abstract void handleOnRecieve(Context context, Intent intent);

}
