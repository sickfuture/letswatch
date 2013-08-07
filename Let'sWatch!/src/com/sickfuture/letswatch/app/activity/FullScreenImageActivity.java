package com.sickfuture.letswatch.app.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.custom.TouchImageView;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.uiutils.SystemUiHider;

public class FullScreenImageActivity extends SherlockActivity {

	// private TouchImageView mFullScreenImageView;

	private RecyclingImageView mFullScreenImageView;
	private ProgressBar mProgressBar;

	private Intent mIntent;

	private SickImageLoader mImageLoader;

	public static final String POSTERS_PROFILE = "posters_profile";
	public static final String POSTERS_ORIGINAL = "posters_original";

	private static final boolean AUTO_HIDE = true;
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
	private static final boolean TOGGLE_ON_CLICK = true;
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_screen_image_view);

		mImageLoader = (SickImageLoader) AppUtils.get(this, LetsWatchApplication.IMAGE_LOADER_SERVICE);
		
		final View controlsView = findViewById(R.id.layout_fullscreen_controls);
		final View contentView = findViewById(R.id.image_view_full_screen);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.

		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_full_screen_image);
		mFullScreenImageView = (RecyclingImageView) findViewById(R.id.image_view_full_screen);
		// mProgressBar.setVisibility(View.VISIBLE);
		mIntent = getIntent();
		String source = mIntent.getStringExtra(POSTERS_PROFILE);
		Log.d("Full screen image intent", source);
		// ImageLoader.getInstance(this).bind(mFullScreenImageView, source,
		// new ParamCallback<Void>() {
		//
		// @Override
		// public void onSuccess(Void c) {
		// mProgressBar.setVisibility(View.GONE);
		// loadHiRes();
		// }
		//
		// @Override
		// public void onError(Throwable e) {
		// mProgressBar.setVisibility(View.GONE);
		// }
		// });
		mImageLoader.loadBitmap(mFullScreenImageView,
				mIntent.getStringExtra(POSTERS_ORIGINAL));

	}

	protected void loadHiRes() {
		String original = mIntent.getStringExtra(POSTERS_ORIGINAL);
		Log.d("Full screen image intent", original);
		if (!TextUtils.isEmpty(original)) {
			mProgressBar.setVisibility(View.VISIBLE);
			// ImageLoader.getInstance(this).bind(mFullScreenImageView,
			// original,
			// new ParamCallback<Void>() {
			//
			// @Override
			// public void onSuccess(Void c) {
			// mProgressBar.setVisibility(View.GONE);
			// }
			//
			// @Override
			// public void onError(Throwable e) {
			// mProgressBar.setVisibility(View.GONE);
			// }
			// });
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
