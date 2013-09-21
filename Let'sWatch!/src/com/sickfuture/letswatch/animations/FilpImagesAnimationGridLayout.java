package com.sickfuture.letswatch.animations;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

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
	private SickImageLoader mImageLoader;

	public FilpImagesAnimationGridLayout(Context context) {
		super(context);
	}

	public FilpImagesAnimationGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FilpImagesAnimationGridLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(Context context) {
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setOnClickListener(this);
		int childWidth = getMeasuredWidth() / getColumnCount();
		int leftMargin = 0;
		int rightMargin = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			if (view != null) {
				GridLayout.LayoutParams layoutParams = (LayoutParams) view
						.getLayoutParams();
				leftMargin = layoutParams.leftMargin;
				if (leftMargin == Integer.MIN_VALUE) {
					leftMargin = 0;
				}
				rightMargin = layoutParams.rightMargin;
				if (rightMargin == Integer.MIN_VALUE) {
					rightMargin = 0;
				}
				layoutParams.width = childWidth - (rightMargin + leftMargin);
			}
		}
	}

	public void swapCursor(Cursor cursor) {
		if (cursor == null) {
			return;
		}
		init(getContext());
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

	private Interpolator accelerator = new AccelerateInterpolator();
	private Interpolator decelerator = new DecelerateInterpolator();

	@SuppressLint("NewApi")
	private void flipAnimate() {
		try {
			final View view = getChildAt(mRandom.nextInt(getChildCount() - 1));
			if (!mCursor
					.moveToPosition(mRandom.nextInt(mCursor.getCount() - 1))) {
				return;
			}
			final ViewTreeObserver observer = view.getViewTreeObserver();
			observer.addOnPreDrawListener(new OnPreDrawListener() {

				@Override
				public boolean onPreDraw() {
					observer.removeOnPreDrawListener(this);
					String path = mCursor.getString(mCursor
							.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
					String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);
					mImageLoader.loadBitmap((RecyclingImageView) view,
							posterUrl);
					ObjectAnimator visToInvis = ObjectAnimator.ofFloat(view,
							"rotationY", 0f, 90f);
					visToInvis.setDuration(500);
					visToInvis.setInterpolator(accelerator);
					final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(
							view, "rotationY", -90f, 0f);
					invisToVis.setDuration(500);
					invisToVis.setInterpolator(decelerator);
					visToInvis.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator anim) {
							invisToVis.start();
						}
					});
					visToInvis.start();
					return true;
				}
			});
		} finally {
			// TODO close cursor
		}
	}

	@Override
	public void onClick(View v) {
		flipAnimate();
	}

}
