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
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.app.activity.FullScreenImageActivity;
import com.sickfuture.letswatch.content.contract.Contract;

public class UpcomingCursorAdapter extends BaseCursorAdapter {

    private static final String LOG_TAG = UpcomingCursorAdapter.class
            .getSimpleName();

    public static final String POSTERS_PROFILE = "posters_profile";
    public static final String POSTERS_ORIGINAL = "posters_original";

    private static final int TEXT_VIEW_TITLE = R.id.upcoming_title_text_view;
    private static final int TEXT_VIEW_SYNOPSIS = R.id.upcoming_synopsis_text_view;
    private static final int TEXT_VIEW_RELEASE_DATE = R.id.upcoming_release_date_text_view;
    private static final int TEXT_VIEW_MPAA = R.id.upcoming_mpaa_text_view;
    private static final int TEXT_VIEW_CAST = R.id.upcoming_cast_text_view;
    private static final int IMAGE_VIEW_POSTER = R.id.upcoming_poster_image_view;

    private SickImageLoader mImageLoader;

    public UpcomingCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mImageLoader = (SickImageLoader) AppUtils.get(context, LetsWatchApplication.IMAGE_LOADER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = View.inflate(context, R.layout.adapter_upcoming, null);
        return view;
    }

    @Override
    public void bindData(View view, final Context context, Cursor cursor,
                         ViewHolder holder) {

        final String posterUrl = cursor.getString(cursor
                .getColumnIndex(Contract.MovieColumns.POSTERS_PROFILE));
        final String original = cursor.getString(cursor
                .getColumnIndex(Contract.MovieColumns.POSTERS_ORIGINAL));
        if (!TextUtils.isEmpty(posterUrl)) {
            holder.getViewById(IMAGE_VIEW_POSTER).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,
                                    FullScreenImageActivity.class);
                            intent.putExtra(POSTERS_PROFILE, posterUrl);
                            intent.putExtra(POSTERS_ORIGINAL, original);
                            context.startActivity(intent);
                        }
                    });
            mImageLoader.loadBitmap((RecyclingImageView) holder.getViewById(IMAGE_VIEW_POSTER),
                    posterUrl);
        }
        ((TextView) holder.getViewById(TEXT_VIEW_TITLE)).setText(cursor
                .getString(cursor
                        .getColumnIndex(Contract.MovieColumns.MOVIE_TITLE)));
        ((TextView) holder.getViewById(TEXT_VIEW_SYNOPSIS)).setText(cursor
                .getString(cursor
                        .getColumnIndex(Contract.MovieColumns.SYNOPSIS)));
        ((TextView) holder.getViewById(TEXT_VIEW_MPAA)).setText(cursor
                .getString(cursor.getColumnIndex(Contract.MovieColumns.MPAA)));
        ((TextView) holder.getViewById(TEXT_VIEW_RELEASE_DATE))
                .setText(cursor.getString(cursor
                        .getColumnIndex(Contract.MovieColumns.RELEASE_DATE_THEATER)));
        String cast = cursor.getString(cursor
                .getColumnIndex(Contract.MovieColumns.CAST_IDS));
        ((TextView) holder.getViewById(TEXT_VIEW_CAST)).setText(cast);

    }

    @Override
    protected int[] getViewsIds() {
        return new int[]{TEXT_VIEW_TITLE, TEXT_VIEW_SYNOPSIS,
                TEXT_VIEW_RELEASE_DATE, TEXT_VIEW_MPAA, TEXT_VIEW_CAST,
                IMAGE_VIEW_POSTER};
    }

}
