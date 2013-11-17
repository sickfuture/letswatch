package com.sickfuture.letswatch.app.fragment.common;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;

public abstract class SickGridCursorFragment extends SickAdapterViewFragment<GridView> implements
		LoaderCallbacks<Cursor> {

	private int mLoaderId;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mLoaderId = getClass().hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, getLoaderArgs(),
				this);
	}

	protected Bundle getLoaderArgs() {
		return null;
	}

	@Override
	public BaseAdapter adapter() {
		return cursorAdapter();
	}

	public int getLoaderId() {
		return mLoaderId;
	}

	public abstract CursorAdapter cursorAdapter();

}
