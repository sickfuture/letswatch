package com.sickfuture.letswatch.app.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.FavoritesGridAdapter;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.content.contract.Contract;

public class FavoritesFragment extends Fragment implements
		LoaderCallbacks<Cursor>, OnItemClickListener {

	private IListClickable mActivity;
	private GridView mGridView;
	private BaseCursorAdapter mAdapter;

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof IListClickable))
			throw new IllegalArgumentException(
					"Activity must implements IListClickable");
		mActivity = (IListClickable) activity;
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mAdapter = new FavoritesGridAdapter(getActivity(), null);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_favorites, null);
		mGridView = (GridView) view.findViewById(R.id.grid_view_favorites);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// use later projection and implement sort order
		return new CursorLoader(
				getActivity(),
				ContractUtils
						.getProviderUriFromContract(Contract.MovieColumns.class),
				null, Contract.MovieColumns.IS_FAVORITE+" = TRUE", null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Bundle arguments = new Bundle();
		// put values
		mActivity.onItemListClick(arguments);
	}

}
