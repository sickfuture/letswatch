package com.sickfuture.letswatch.app.fragment.common;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.sickfuture.sickcore.service.SourceResultReceiver;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.callback.IListClickable;

public abstract class SickListFragment extends Fragment implements
		OnRefreshListener<ListView>, OnScrollListener, OnItemClickListener {

	private static final String LOG_TAG = SickListFragment.class
			.getSimpleName();

	protected PullToRefreshListView mListView;
	private IListClickable mClickable;
	protected SourceResultReceiver mResultReceiver;
	protected BaseAdapter mListViewAdapter;

	protected SickListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mListViewAdapter = adapter();
		View view = inflater.inflate(fragmentResource(), null);
		mListView = (PullToRefreshListView) view
				.findViewById(listViewResource());
		mListView.setAdapter(mListViewAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnRefreshListener(this);

		mResultReceiver = new SourceResultReceiver(new Handler()) {
			@Override
			public void onStart(Bundle result) {
				start(result);
			}

			@Override
			public void onError(Exception exception) {
				error(exception);
			}

			@Override
			public void onDone(Bundle result) {
				done(result);
			}
		};
		return view;
	}

	public SourceResultReceiver getResultReceiver() {
		return mResultReceiver;
	}

	protected abstract void start(Bundle bundle);

	protected abstract void error(Exception exception);

	protected abstract void done(Bundle result);

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

	protected int fragmentResource() {
		return R.layout.fragment_common;
	}

	protected int listViewResource() {
		return R.id.list_view_common_fragment;
	}

	public abstract BaseAdapter adapter();

	public abstract void onListItemClick(AdapterView<?> list, View view,
			int position, long id, IListClickable clickable);

}
