package com.sickfuture.letswatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.sickfuture.sickcore.adapter.BaseCursorAdapter;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.activity.FullScreenImageActivity;
import com.sickfuture.letswatch.content.contract.Contract;

public class BoxOfficeCursorAdapter extends BaseCursorAdapter {

	public static final String POSTERS_PROFILE = "posters_profile";
	public static final String POSTERS_ORIGINAL = "posters_original";

	private static final int TEXT_VIEW_TITLE = R.id.box_office_title_text_view;
	private static final int TEXT_VIEW_SYNOPSIS = R.id.box_office_synopsis_text_view;
	private static final int TEXT_VIEW_MPAA = R.id.box_office_mpaa_text_view;
	private static final int TEXT_VIEW_CAST = R.id.box_office_cast_text_view;
	private static final int IMAGE_VIEW_POSTER = R.id.box_office_poster_image_view;

	public BoxOfficeCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View view = View.inflate(context, R.layout.adapter_box_office, null);
		return view;
	}

	@Override
	protected int[] getViewsIds() {
		return new int[] { TEXT_VIEW_TITLE, TEXT_VIEW_SYNOPSIS, TEXT_VIEW_MPAA,
				TEXT_VIEW_CAST, IMAGE_VIEW_POSTER };
	}

	@Override
	public void bindData(View view, final Context context, Cursor cursor,
			ViewHolder holder) {
		final String posterUrl = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.POSTERS_PROFILE));
		final String orinalUrl = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.POSTERS_ORIGINAL));
		if (!TextUtils.isEmpty(posterUrl)) {
			holder.getViewById(IMAGE_VIEW_POSTER).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							FullScreenImageActivity.class);
					intent.putExtra(POSTERS_PROFILE, posterUrl);
					intent.putExtra(POSTERS_ORIGINAL, orinalUrl);
					context.startActivity(intent);
					return;
				}
			});
			SickImageLoader.getInstance(mContext).loadBitmap(
					(RecyclingImageView) holder.getViewById(IMAGE_VIEW_POSTER), posterUrl);
		}
		((TextView) holder.getViewById(TEXT_VIEW_CAST)).setText(cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.CAST_IDS)));
		((TextView) holder.getViewById(TEXT_VIEW_TITLE)).setText(cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.MOVIE_TITLE)));
		String consensus = cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.CRITICS_CONSENSUS));
		TextView synopsis = (TextView) holder.getViewById(TEXT_VIEW_SYNOPSIS);
		if (!TextUtils.isEmpty(consensus)) {
			synopsis.setVisibility(View.VISIBLE);
			synopsis.setText(consensus);
		} else {
			synopsis.setText(cursor.getString(cursor
					.getColumnIndex(Contract.MovieColumns.SYNOPSIS)));
		}
		((TextView) holder.getViewById(TEXT_VIEW_MPAA)).setText(cursor.getString(cursor
				.getColumnIndex(Contract.MovieColumns.MPAA)));
		
	}
}
