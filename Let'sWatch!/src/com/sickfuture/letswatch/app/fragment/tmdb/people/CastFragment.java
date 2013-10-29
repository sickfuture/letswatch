package com.sickfuture.letswatch.app.fragment.tmdb.people;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.android.sickfuture.sickcore.utils.CursorUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.adapter.tmdb.CastGridAdapter;
import com.sickfuture.letswatch.adapter.tmdb.CredsCursorAdapter;
import com.sickfuture.letswatch.app.callback.IListClickable;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.app.fragment.tmdb.movie.MovieFragment;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;
import com.sickfuture.letswatch.content.contract.Contract.MovieColumns;
import com.sickfuture.letswatch.content.contract.Contract.PersonColumns;
import com.sickfuture.letswatch.content.provider.tmdb.CastProvider;

public class CastFragment extends CommonGridFragment {

	private static final Uri sUri = ContractUtils
			.getProviderUriFromContract(CastColumns.class);

	protected String mid, pid;
	protected boolean isMovieCasts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsNotPaginating(true);
		if (getArguments().containsKey("mid")) {
			mid = getArguments().getString("mid");
			pid = null;
			isMovieCasts = true;
		} else if (getArguments().containsKey("pid")) {
			pid = getArguments().getString("pid");
			mid = null;
			isMovieCasts = false;
		} else
			throw new IllegalStateException(
					"Arguments don't contain required key");
	}

	@Override
	protected Uri getUri() {
		return sUri;
	}

	@Override
	protected void loadData(int page) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String[] getProjection() {
		return null;
	}

	@Override
	protected String getSelection() {
		if (isMovieCasts) {
			return CastColumns.TMDB_MOVIE_ID + " = " + mid;
		} else {
			return CastColumns.TMDB_PERSON_ID + " = " + pid;
		}
	}

	@Override
	public CursorAdapter cursorAdapter() {
		if (isMovieCasts) {
			return new CastGridAdapter(getActivity(), null);
		} else {
			return new CredsCursorAdapter(getActivity(), null);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		Bundle args = new Bundle();
		if (isMovieCasts) {
			Cursor cursor = (Cursor) list.getItemAtPosition(position);
			long id_ = cursor.getLong(cursor
					.getColumnIndex(CastColumns.TMDB_PERSON_ID));
			args.putString(PersonFragment.PERSON_ID, String.valueOf(id_));
		} else {
			Cursor cursor = (Cursor) list.getItemAtPosition(position);
			long id_ = cursor.getLong(cursor
					.getColumnIndex(CastColumns.TMDB_MOVIE_ID));
			args.putString(MovieFragment.MOVIE_ID, String.valueOf(id_));
		}
		((IListClickable) getActivity()).onItemListClick(args);

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		CursorUtils.logCursor(data, CastColumns.class);
		super.onLoadFinished(loader, data);
	}

	@Override
	protected String getSortOrder() {
		return CastProvider.PERSON_ID;
	}

	@Override
	protected String[] getSelectionArgs() {
		// TODO Auto-generated method stub
		return null;
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
