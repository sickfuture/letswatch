package com.sickfuture.letswatch.animations;

import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.BACKDROP;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class FilpImagesAnimationGridLayout extends GridLayout implements
		OnClickListener {

	private Cursor mCursor;
	private final Random mRandom = new Random();
	private int mChildWidth;
	private SickImageLoader mImageLoader;

	public FilpImagesAnimationGridLayout(Context context) {
		super(context);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	public FilpImagesAnimationGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	public FilpImagesAnimationGridLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int leftAndRightMargins = ((LinearLayout.LayoutParams) getLayoutParams()).leftMargin
				+ ((LinearLayout.LayoutParams) getLayoutParams()).rightMargin;
		mChildWidth = getMeasuredWidth() / getColumnCount()
				- (getColumnCount() * leftAndRightMargins);
		for (int i = 0; i < getChildCount() - 1; i++) {
			View view = getChildAt(i);
			view.getLayoutParams().width = mChildWidth;
		}
	}

	public void swapCursor(Cursor cursor) {
		if (cursor == null) {
			return;
		}
		mCursor = cursor;
		notify(cursor);
	}

	private void notify(Cursor cursor) {
		for (int i = 0; i < getChildCount(); i++) {
			RecyclingImageView child = (RecyclingImageView) getChildAt(i);
			cursor.moveToPosition(i);
			String path = cursor.getString(cursor
					.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
			String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);
			mImageLoader.loadBitmap(child, posterUrl);
		}
	}

	private void flipAnimate() {
		try {
			View view = getChildAt(mRandom.nextInt(getChildCount() - 1));
			if (!mCursor
					.moveToPosition(mRandom.nextInt(mCursor.getCount() - 1))) {
				return;
			}
		} finally {
			// TODO close cursor
		}
	}

	private Animation getCurrentAnimation() {
		return new ScaleAnimation(0f, 1.0f, 0f, 1.0f);
	}

	@Override
	public void onClick(View v) {
		flipAnimate();
	}

}
