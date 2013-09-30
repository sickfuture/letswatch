package com.sickfuture.letswatch.app.fragment.tmdb.search;

import java.io.InputStream;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.NetworkHelper;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.PeopleGridCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.SearchActivity;
import com.sickfuture.letswatch.app.activity.tmdb.PeopleActivity;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;

public class SearchedPersonsFragment extends SickGridCursorFragment {

	private static final Uri sSearchedPersonsUri = ContractUtils
			.getProviderUriFromContract(Contract.SearchedPersonsColumns.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		search(getArguments());
	}

	private void search(Bundle arguments) {
		String query = arguments.getString(SearchManager.QUERY);
		String url = TmdbApi.searchPerson(query, 0, true, null);
		if (NetworkHelper.checkConnection(getActivity())) {
			getActivity().getContentResolver().delete(sSearchedPersonsUri,
					null, null);
			DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
					url);
			SourceService
					.execute(
							getActivity(),
							request,
							LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
							LetsWatchApplication.TMDB_SEARCHED_PERSONS_PROCESSOR_SERVICE,
							getResultReceiver());
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), sSearchedPersonsUri, null, null,
				null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		((CursorAdapter) getAdapter()).swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((CursorAdapter) getAdapter()).swapCursor(null);

	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		Cursor c = (Cursor) adapterView.getItemAtPosition(position);
		Intent intent = new Intent(getActivity(), PeopleActivity.class);
		intent.putExtra(SearchActivity.SEARCHED_PERSON_ID,
				c.getString(c.getColumnIndex(PersonColumns.TMDB_ID)));
		startActivity(intent);

	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new PeopleGridCursorAdapter(getActivity(), null);
	}

	@Override
	protected void start(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void error(Exception exception) {
		Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG)
				.show();

	}

	@Override
	protected void done(Bundle result) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int fragmentResource() {
		return R.layout.fragment_grid_posters;
	}

	@Override
	protected int adapterViewResource() {
		return R.id.grid_view_fragment_grid_posters;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if(menu!=null){
			menu.removeItem(R.id.menu_refresh);
		}
			
	}
}
