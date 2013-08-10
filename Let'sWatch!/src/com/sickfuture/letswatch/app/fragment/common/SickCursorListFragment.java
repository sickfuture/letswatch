package com.sickfuture.letswatch.app.fragment.common;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class SickCursorListFragment extends SickListFragment implements
		LoaderCallbacks<Cursor> {

	private int mLoaderId;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mLoaderId = getClass().hashCode();
		getActivity().getSupportLoaderManager().initLoader(mLoaderId, null,
				this);

	}

	@Override
	public BaseAdapter adapter() {
		return cursorAdapter();
	}

	public int getmLoaderId() {
		return mLoaderId;
	}

	public abstract CursorAdapter cursorAdapter();

}
