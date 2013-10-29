package com.sickfuture.letswatch.app.fragment.tmdb.movie;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.MoviePosterGridAdapter;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.content.contract.Contract;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;

public class SimilarMoviesFragment extends CommonGridFragment {

	public static final String SIMILAR_IDS = "similar_ids";
	private static final Uri moviesUri = ContractUtils
			.getProviderUriFromContract(Contract.MovieColumns.class);
	private static final String[] similarProjection = new String[] {
			MovieColumns._ID, MovieColumns.TMDB_ID, MovieColumns.TITLE,
			MovieColumns.TITLE_ORIGINAL, MovieColumns.POSTER_PATH,
			Contract.MovieColumns.BACKDROP_PATH,
			Contract.MovieColumns.VOTE_AVERAGE };
	private String mSimilarIds;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(SIMILAR_IDS)) {
			mSimilarIds = getArguments().getString(SIMILAR_IDS);
		}
	}

	@Override
	protected Uri getUri() {
		return moviesUri;
	}

	@Override
	public CursorAdapter cursorAdapter() {
		return new MoviePosterGridAdapter(getActivity(), null);
	}

	@Override
	protected String[] getProjection() {
		return similarProjection;
	}

	@Override
	protected String getSelection() {
		return Contract.MovieColumns.TMDB_ID + " IN (" + mSimilarIds + ")";
	}

	@Override
	protected void loadData(int page) {
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
	protected String getPagingPrefsCurrKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPagingMaxPrefsKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean hasPaging() {
		return false;
	}

}
