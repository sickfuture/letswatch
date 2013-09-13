package com.sickfuture.letswatch.app.fragment.tmdb.people;

import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.NetworkHelper;
import com.android.sickfuture.sickcore.utils.NetworkHelper.NetworkCallback;
import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.PeopleGridCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class PopularPersonsFragment extends SickGridCursorFragment implements
		RefreshActionListener {

	private RefreshActionItem mRefreshActionItem;

	private String sortOrder;
	private String[] selectionArgs;
	private String selection;
	private String[] projection;
	private final static Uri mUri = ContractUtils
			.getProviderUriFromContract(Contract.PopularPeopleTmdbColumns.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void start(Bundle bundle) {
		mRefreshActionItem.showProgress(true);
	}

	@Override
	protected void error(Exception exception) {
		Toast.makeText(getActivity(), exception.toString(), Toast.LENGTH_SHORT)
				.show();
		mRefreshActionItem.showProgress(false);
	}

	@Override
	protected void done(Bundle result) {
		mRefreshActionItem.showProgress(false);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.menu_refresh);
		mRefreshActionItem = (RefreshActionItem) MenuItemCompat
				.getActionView(item);
		mRefreshActionItem.setMenuItem(item);
		mRefreshActionItem
				.setProgressIndicatorType(ProgressIndicatorType.INDETERMINATE);
		mRefreshActionItem.setRefreshActionListener(this);
		super.onPrepareOptionsMenu(menu);
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
		Cursor c = (Cursor) list.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putString(PersonColumns.TMDB_ID,
				c.getString(c.getColumnIndex(PersonColumns.TMDB_ID)));
		clickable.onItemListClick(bundle);

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
		if (NetworkHelper.checkConnection(getActivity())) {
			String url = MovieApis.TmdbApi.getPopularPersons(0);
			DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
					url);
			request.setIsCacheable(true);
			SourceService.execute(getActivity(), request,
					LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
					LetsWatchApplication.TMDB_POPULAR_PERSON_PROCESSOR_SERVICE,
					mResultReceiver);
		}
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new PeopleGridCursorAdapter(getActivity(), null);
	}

	@Override
	public void onRefreshButtonClick(RefreshActionItem sender) {
		NetworkHelper.checkAndConnect(getActivity(), new NetworkCallback() {

			@Override
			public void processTask(Context context) {
				loadData();
			}

			@Override
			public void onError(Context context, Exception e) {

			}

			@Override
			public void onCancel(Context context) {

			}
		});

	}

}
