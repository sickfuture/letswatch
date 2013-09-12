package com.sickfuture.letswatch.app.fragment.tmdb.people;

import java.io.InputStream;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.NetworkHelper;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.PeopleGridCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class PopularPersonsFragment extends SickGridCursorFragment implements
		RefreshActionListener {

	private String sortOrder;
	private String[] selectionArgs;
	private String selection;
	private String[] projection;
	private final static Uri mUri = ContractUtils
			.getProviderUriFromContract(Contract.PopularPeopleTmdbColumns.class);

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

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
	protected int fragmentResource() {
		return R.layout.fragment_person_grid;
	}

	@Override
	protected int gridViewResource() {
		return R.id.grid_view_fragment_persons;
	}

	@Override
	public void onListItemClick(AdapterView<?> list, View view, int position,
			long id, IListClickable clickable) {
		// TODO Auto-generated method stub

	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), mUri, projection, selection,
				selectionArgs, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.getCount() == 0) {
			if (NetworkHelper.checkConnection(getActivity())) {
				loadData();
			}
		} else {
			((CursorAdapter) mGridViewAdapter).swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) mGridViewAdapter).swapCursor(null);

	}

	private void loadData() {
		String url = MovieApis.TmdbApi.getPopularPersons(0);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_POPULAR_PERSON_PROCESSOR_SERVICE,
				mResultReceiver);

	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new PeopleGridCursorAdapter(getActivity(), null);
	}

	@Override
	public void onRefreshButtonClick(RefreshActionItem sender) {
		// TODO Auto-generated method stub

	}

}
