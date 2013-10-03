package com.sickfuture.letswatch.animations;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.android.sickfuture.sickcore.asynctask.AsyncTask;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.BACKDROP;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract;

public class AnimatedCellLayout extends ViewGroup {

	private Cursor mCursor;

	private static final float DEFAULT_CELL_SIZE = 50;
	private float mCellSize = DEFAULT_CELL_SIZE;

	private final Random mRandom = new Random();
	private SickImageLoader mImageLoader;

	private static final int DEFAULT_COLUMNS_COUNT = 2;
	private int mColumnsCount = DEFAULT_COLUMNS_COUNT;

	private static final int DEFAULT_SPACING = 0;
	private int mSpacing = DEFAULT_SPACING;

	public AnimatedCellLayout(Context context) {
		super(context);
	}

	public AnimatedCellLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public AnimatedCellLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CellLayout, 0, 0);
		try {
			mColumnsCount = a.getInt(R.styleable.CellLayout_columns,
					DEFAULT_COLUMNS_COUNT);
			mSpacing = a.getDimensionPixelSize(R.styleable.CellLayout_spacing,
					DEFAULT_SPACING);
		} finally {
			a.recycle();
		}
		mImageLoader = (SickImageLoader) AppUtils.get(context,
				LetsWatchApplication.IMAGE_LOADER_SERVICE);
		new AnimationTask().start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int width = 0;
		int height = 0;

		if (widthMode == MeasureSpec.AT_MOST
				|| widthMode == MeasureSpec.EXACTLY) {
			width = MeasureSpec.getSize(widthMeasureSpec);
			mCellSize = (float) (getMeasuredWidth() - getPaddingLeft() - getPaddingRight())
					/ (float) mColumnsCount;
		} else {
			mCellSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					DEFAULT_CELL_SIZE, getResources().getDisplayMetrics());
			width = (int) (mColumnsCount * mCellSize);
		}

		int childCount = getChildCount();
		View child;

		int maxRow = 0;

		for (int i = 0; i < childCount; i++) {
			child = getChildAt(i);

			LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

			int top = layoutParams.top;
			int w = layoutParams.width;
			int h = layoutParams.height;

			int bottom = top + h;

			int childWidthSpec = MeasureSpec.makeMeasureSpec(
					(int) (w * mCellSize) - mSpacing * 2, MeasureSpec.EXACTLY);
			int childHeightSpec = MeasureSpec.makeMeasureSpec(
					(int) (h * mCellSize) - mSpacing * 2, MeasureSpec.EXACTLY);
			child.measure(childWidthSpec, childHeightSpec);

			if (bottom > maxRow) {
				maxRow = bottom;
			}
		}

		int measuredHeight = Math.round(maxRow * mCellSize) + getPaddingTop()
				+ getPaddingBottom();
		if (heightMode == MeasureSpec.EXACTLY) {
			height = MeasureSpec.getSize(heightMeasureSpec);
		} else if (heightMode == MeasureSpec.AT_MOST) {
			int atMostHeight = MeasureSpec.getSize(heightMeasureSpec);
			height = Math.min(atMostHeight, measuredHeight);
		} else {
			height = measuredHeight;
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int childCount = getChildCount();
		View childView;
		for (int i = 0; i < childCount; i++) {
			childView = getChildAt(i);

			LayoutParams layoutParams = (LayoutParams) childView
					.getLayoutParams();

			int childTop = (int) (layoutParams.top * mCellSize)
					+ getPaddingTop() + mSpacing;
			int childLeft = (int) (layoutParams.left * mCellSize)
					+ getPaddingLeft() + mSpacing;
			int childRight = (int) ((layoutParams.left + layoutParams.width) * mCellSize)
					+ getPaddingLeft() - mSpacing;
			int childBottom = (int) ((layoutParams.top + layoutParams.height) * mCellSize)
					+ getPaddingTop() - mSpacing;

			childView.layout(childLeft, childTop, childRight, childBottom);
		}
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new LayoutParams(getContext(), attrs);
	}

	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof LayoutParams;
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p) {
		return new LayoutParams(p);
	}

	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams();
	}

	public void swapCursor(Cursor cursor) {
		if (cursor == null) {
			return;
		}
		notify(cursor);
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
				Thread.sleep(5 * 1000);
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
			invisToVis.setDuration(500);
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

	public static class LayoutParams extends ViewGroup.LayoutParams {

		int top = 0;
		int left = 0;

		int width = 1;
		int height = 1;

		public LayoutParams(Context context, AttributeSet attrs) {
			super(context, attrs);
			TypedArray a = null;
			try {
				a = context.obtainStyledAttributes(attrs,
						R.styleable.CellLayout);
				left = a.getInt(R.styleable.CellLayout_layout_left, 0);
				top = a.getInt(R.styleable.CellLayout_layout_top, 0);
				height = a
						.getInt(R.styleable.CellLayout_layout_cellsHeight, -1);
				width = a.getInt(R.styleable.CellLayout_layout_cellsWidth, -1);
			} finally {
				if (a != null) {
					a.recycle();
				}
			}
		}

		public LayoutParams(ViewGroup.LayoutParams params) {
			super(params);
			if (params instanceof LayoutParams) {
				LayoutParams cellLayoutParams = (LayoutParams) params;
				left = cellLayoutParams.left;
				top = cellLayoutParams.top;
				height = cellLayoutParams.height;
				width = cellLayoutParams.width;
			}
		}

		public LayoutParams() {
			this(MATCH_PARENT, MATCH_PARENT);
		}

		public LayoutParams(int width, int height) {
			super(width, height);
		}

	}

}
