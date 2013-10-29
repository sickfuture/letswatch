package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import java.io.InputStream;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.MoviesGridCursorAdapter;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.common.CommonMovieListFragment;
import com.sickfuture.letswatch.app.fragment.common.SickGridCursorFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.PopularTmdbColumns;

public class PopularMoviesFragment extends CommonGridFragment {

	private static final String LOG_TAG = PopularMoviesFragment.class
			.getSimpleName();

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(PopularTmdbColumns.class);
	}

	@Override
	protected void loadData(int page) {
		String url = MovieApis.TmdbApi.getPopularMovies(Locale.getDefault()
				.getLanguage(), page);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_POPULAR_PROCESSOR_SERVICE,
				getResultReceiver());
	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).setTitle(getResources().getString(
				R.string.popular));
		super.onStart();
	}

	@Override
	protected String[] getProjection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPagingPrefsCurrKey() {
		return getActivity().getResources().getString(
				R.string.prefs_paging_pop_curr_page_count_key);
	}

	@Override
	protected String getPagingMaxPrefsKey() {
		return getActivity().getResources().getString(
				R.string.prefs_paging_pop_max_page_count_key);
	}

	@Override
	protected boolean hasPaging() {
		return true;
	}

}
