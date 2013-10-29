package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import java.io.InputStream;
import java.util.Locale;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.sickfuture.sickcore.service.DataSourceRequest;
import com.android.sickfuture.sickcore.service.SourceService;
import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;

public class TopRatedFragment extends CommonGridFragment {

	private static final String LOG_TAG = TopRatedFragment.class
			.getSimpleName();

	@Override
	protected Uri getUri() {
		return ContractUtils
				.getProviderUriFromContract(Contract.TopRatedTmdbColumns.class);
	}

	@Override
	protected void loadData(int page) {
		String url = MovieApis.TmdbApi.getTopRatedMovies(Locale.getDefault()
				.getLanguage(), page);

		Log.d(LOG_TAG, "loadData: " + url);
		DataSourceRequest<InputStream, ContentValues[]> request = new DataSourceRequest<InputStream, ContentValues[]>(
				url);
		request.setIsCacheable(true);
		SourceService.execute(getActivity(), request,
				LetsWatchApplication.HTTP_INPUT_STREAM_SERVICE_KEY,
				LetsWatchApplication.TMDB_TOP_RATED_PROCESSOR_SERVICE,
				getResultReceiver());

	}

	@Override
	public void onStart() {
		((ActionBarActivity) getActivity()).setTitle(
				getResources().getString(R.string.top_rated));
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
				R.string.prefs_paging_top_rat_curr_page_count_key);
	}

	@Override
	protected String getPagingMaxPrefsKey() {
		return getActivity().getResources().getString(
				R.string.prefs_paging_top_rat_max_page_count_key);
	}

	@Override
	protected boolean hasPaging() {
		return true;
	}

}
