package com.sickfuture.letswatch.app.fragment.tmdb.common;

import com.android.sickfuture.sickcore.service.SourceResultReceiver;
import com.sickfuture.letswatch.processor.tmdb.casts.ICasts;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public abstract class CommonFragment extends Fragment {

	protected ViewGroup mCastsContainer, mCrewContainer;
	protected ProgressBar mProgressBarCrew, mProgressBarCast;
	protected SourceResultReceiver mReceiverCasts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReceiverCasts = new SourceResultReceiver(new Handler()) {

			@Override
			public void onStart(Bundle result) {
				mProgressBarCast.setVisibility(View.VISIBLE);
				mProgressBarCrew.setVisibility(View.VISIBLE);
			}

			@Override
			public void onError(Exception exception) {
				// mCastsContainer.setVisibility(View.VISIBLE);
				// mCrewContainer.setVisibility(View.VISIBLE);
			}

			@Override
			public void onDone(Bundle result) {
				mProgressBarCast.setVisibility(View.GONE);
				mProgressBarCrew.setVisibility(View.GONE);
				if (!result.containsKey(ICasts.RESULT_CAST)
						|| result.getInt(ICasts.RESULT_CAST) < 1)
					mCastsContainer.setVisibility(View.GONE);
				if (!result.containsKey(ICasts.RESULT_CAST)
						|| result.getInt(ICasts.RESULT_CREW) < 1)
					mCrewContainer.setVisibility(View.GONE);
			}
		};
	}
}
