package com.sickfuture.letswatch.animations;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.android.sickfuture.sickcore.asynctask.AsyncTask;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AndroidVersionsUtils;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.android.sickfuture.sickcore.view.CellLayout;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.BACKDROP;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class AnimatedCellLayout extends CellLayout {

	private Cursor mCursor;

	private final Random mRandom = new Random();
	private SickImageLoader mImageLoader;

	private static final int DEFAULT_DURATION = 500;
	private int mDuration;

	private static final long DEFAULT_ANIMATION_DELAY = 5000l;
	private long mAnimationDelay;

	public AnimatedCellLayout(Context context) {
		super(context);
	}

	public AnimatedCellLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AnimatedCellLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		mDuration = DEFAULT_DURATION;
		mAnimationDelay = DEFAULT_ANIMATION_DELAY;
	}

	public int getDuration() {
		return mDuration;
	}

	public void setDuration(int duration) {
		this.mDuration = duration;
	}

	public long getAnimationDelay() {
		return mAnimationDelay;
	}

	public void setAnimationDelay(long delay) {
		this.mAnimationDelay = delay;
	}

	public void swapCursor(Cursor cursor) {
		if (cursor == null) {
			return;
		}
		notify(cursor);
		if (AndroidVersionsUtils.hasHoneycombMR1()) {
			// TODO start task
			// new AnimationTask().start();
		}
	}

	private void notify(Cursor cursor) {
		if (cursor == null) {
			return;
		}
		mCursor = cursor;
		for (int i = 0; i < getChildCount(); i++) {
			// TODO set appropriate uri
			RecyclingImageView child = (RecyclingImageView) getChildAt(i);
			cursor.moveToPosition(i);
			String path = cursor.getString(cursor
					.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
			String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);
			mImageLoader.loadBitmap(child, posterUrl);
		}
	}

	private static final Interpolator accelerator = new AccelerateInterpolator();
	private static final Interpolator decelerator = new DecelerateInterpolator();

	public class AnimationTask extends AsyncTask<String, Void, Bitmap> {

		public AnimationTask() {
		}

		public void start() {
			executeOnExecutor(SERIAL_EXECUTOR);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				Thread.sleep(mAnimationDelay);
			} catch (InterruptedException e) {
				// can be ignored
			}
			if (mCursor == null) {
				return null;
			}
			String path = mCursor.getString(mCursor
					.getColumnIndex(Contract.MovieColumns.BACKDROP_PATH));
			String posterUrl = TmdbApi.getBackdrop(path, BACKDROP.W300);
			Bitmap result = mImageLoader.loadBitmapSync(posterUrl);
			return result;
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(final Bitmap result) {
			if (result == null) {
				return;
			}
			int randomCursorPosition = mRandom.nextInt(mCursor.getCount() - 1);
			int randomViewPosition = mRandom.nextInt(getChildCount() - 1);
			if (!mCursor.moveToPosition(randomCursorPosition)) {
				return;
			}
			final RecyclingImageView childToAnimate = (RecyclingImageView) getChildAt(randomViewPosition);
			if (childToAnimate == null) {
				return;
			}
			ObjectAnimator visToInvis = ObjectAnimator.ofFloat(childToAnimate,
					"rotationY", 0f, 90f);
			visToInvis.setDuration(500);
			visToInvis.setInterpolator(accelerator);
			final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(
					childToAnimate, "rotationY", -90f, 0f);
			invisToVis.setDuration(DEFAULT_DURATION);
			invisToVis.setInterpolator(decelerator);
			visToInvis.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator anim) {
					invisToVis.start();
					childToAnimate.setImageBitmap(result);
				}
			});
			visToInvis.start();
			new AnimationTask().start();
		}
	}
}
