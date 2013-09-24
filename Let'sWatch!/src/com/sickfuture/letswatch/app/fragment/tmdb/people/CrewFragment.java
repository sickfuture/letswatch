package com.sickfuture.letswatch.app.fragment.tmdb.people;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;

import com.android.sickfuture.sickcore.utils.ContractUtils;
import com.sickfuture.letswatch.adapter.tmdb.CredsHListAdapter;
import com.sickfuture.letswatch.adapter.tmdb.CrewGridAdapter;
import com.sickfuture.letswatch.app.fragment.tmdb.common.CommonGridFragment;
import com.sickfuture.letswatch.content.contract.Contract.CrewColumns;

public class CrewFragment extends CommonGridFragment {

	private static final Uri sUri = ContractUtils
			.getProviderUriFromContract(CrewColumns.class);

	private String mid, pid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey("mid")) {
			mid = getArguments().getString("mid");
			pid = null;
		} else if (getArguments().containsKey("pid")) {
			pid = getArguments().getString("pid");
			mid = null;
		}
	}

	@Override
	protected Uri getUri() {
		return sUri;
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String[] getProjection() {
		return null;
	}

	@Override
	protected String getSelection() {
		if (mid != null) {
			return CrewColumns.TMDB_MOVIE_ID + " = " + mid;
		} else if (pid != null) {
			return CrewColumns.TMDB_PERSON_ID + " = " + pid;
		} else
			return null;
	}

	@Override
	public CursorAdapter cursorAdapter() {
		if (mid != null) {
			return new CrewGridAdapter(getActivity(), null);
		} else if (pid != null) {
			return new CredsHListAdapter(getActivity(), null);
		} else
			return null;
	}

}
