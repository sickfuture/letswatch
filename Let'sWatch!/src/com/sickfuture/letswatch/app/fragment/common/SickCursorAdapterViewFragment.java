package com.sickfuture.letswatch.app.fragment.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.service.SourceResultReceiver;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class SickCursorAdapterViewFragment<T extends AbsListView>
		extends Fragment implements OnScrollListener, OnItemClickListener,
		LoaderCallbacks<Cursor> {

	protected T mAdapterView;
	protected SourceResultReceiver mResultReceiver;
	protected CursorAdapter mCursorAdapter;

	private SickImageLoader mImageLoader;

	private int mLoaderId;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mCursorAdapter = adapter();
		View view = inflater.inflate(fragmentResource(), null);
		mAdapterView = (T) view.findViewById(adapterViewResource());
		mAdapterView.setAdapter(mCursorAdapter);
		mAdapterView.setOnItemClickListener(this);

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

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mLoaderId = getClass().hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, null,
				this);

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
		super.onAttach(activity);
	}

	protected abstract int fragmentResource();

	protected abstract int adapterViewResource();

	public abstract CursorAdapter adapter();

	public int getmLoaderId() {
		return mLoaderId;
	}

}
