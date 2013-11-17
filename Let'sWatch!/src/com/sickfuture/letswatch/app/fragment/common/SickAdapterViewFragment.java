package com.sickfuture.letswatch.app.fragment.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.service.SourceResultReceiver;
import com.android.sickfuture.sickcore.utils.AndroidVersionsUtils;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;

public abstract class SickAdapterViewFragment<T extends AbsListView> extends
		Fragment implements OnScrollListener, OnItemClickListener {

	private T mAdapterView;
	protected SourceResultReceiver mResultReceiver;
	private BaseAdapter mAdapter;

	private SickImageLoader mImageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageLoader = (SickImageLoader) AppUtils.get(getActivity(),
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
			mImageLoader.setPauseWork(true);
		} else {
			mImageLoader.setPauseWork(false);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageLoader.setPauseWork(false);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAdapter = adapter();
		View view = inflater.inflate(fragmentResource(), null);
		mAdapterView = (T) view.findViewById(adapterViewResource());
		if (AndroidVersionsUtils.hasHoneycomb()) {
			mAdapterView.setAdapter(mAdapter);
		} else {
			if (mAdapterView instanceof ListView) {
				((ListView) mAdapterView).setAdapter(mAdapter);
			} else if (mAdapterView instanceof GridView) {
				((GridView) mAdapterView).setAdapter(mAdapter);
			} else
				throw new IllegalStateException(
						"Pre-Honeycomb issue. Can't set adapter to adapter view. Use grid or list view.");
		}
		mAdapterView.setOnItemClickListener(this);
		mAdapterView.setOnScrollListener(this);

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

	protected abstract void start(Bundle bundle);

	protected abstract void error(Exception exception);

	protected abstract void done(Bundle result);

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof IListClickable))
			throw new IllegalArgumentException(
					"Activity must implements IListClickable");
		super.onAttach(activity);
	}

	protected abstract int fragmentResource();

	protected abstract int adapterViewResource();

	public abstract BaseAdapter adapter();

	public T getAdapterView() {
		return mAdapterView;
	}

	public SourceResultReceiver getResultReceiver() {
		return mResultReceiver;
	}

	public BaseAdapter getAdapter() {
		return mAdapter;
	}

}
