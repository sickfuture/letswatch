package com.sickfuture.letswatch.adapter.pager;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.android.sickfuture.sickcore.image.view.RecyclingImageView;
import com.android.sickfuture.sickcore.utils.AppUtils;
import com.sickfuture.letswatch.R;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi;
import com.sickfuture.letswatch.api.MovieApis.TmdbApi.PROFILE;
import com.sickfuture.letswatch.app.LetsWatchApplication;
import com.sickfuture.letswatch.content.contract.Contract.CastColumns;

public class CursorPagerAdapter extends PagerAdapter {

	protected static final int IMAGE = R.id.image_view_hlist_item_picture;
	protected static final int TITLE = R.id.text_view_hlist_item_title;

	private Cursor mCursor;
	private LayoutInflater mInflater;
	private SickImageLoader mImageLoader;

	public CursorPagerAdapter(Context context, Cursor cursor) {
		this.mCursor = cursor;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageLoader = (SickImageLoader) AppUtils.get(context, LetsWatchApplication.IMAGE_LOADER_SERVICE);
	}

	// @Override
	// public F getItem(int position) {
	// if (cursor == null) // shouldn't happen
	// return null;
	//
	// cursor.moveToPosition(position);
	// F frag;
	// try {
	// frag = fragmentClass.newInstance();
	// } catch (Exception ex) {
	// throw new RuntimeException(ex);
	// }
	// Bundle args = new Bundle();
	// for (int i = 0; i < projection.length; ++i) {
	// args.putString(projection[i], cursor.getString(i));
	// }
	// frag.setArguments(args);
	// return frag;
	// }

	@Override
	public int getCount() {
		if (mCursor == null)
			return 0;
		else
			return mCursor.getCount();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public float getPageWidth(int position) {
		return 0.1f;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		mCursor.moveToPosition(position);
		View parent = mInflater.inflate(R.layout.adapter_horisontal_list, null);
		RecyclingImageView imageView = (RecyclingImageView) parent
				.findViewById(IMAGE);
		String path = mCursor.getString(mCursor
				.getColumnIndex(CastColumns.PERSON_PROFILE_PATH));
		if (!TextUtils.isEmpty(path)) {
			mImageLoader.loadBitmap(imageView,
					TmdbApi.getProfile(path, PROFILE.W185));
		}
		TextView titleView = (TextView) parent.findViewById(TITLE);
		String title = mCursor.getString(mCursor
				.getColumnIndex(CastColumns.PERSON_NAME));
		titleView.setText(title);
		return parent;
	}

	public void swapCursor(Cursor c) {
		if (mCursor == c)
			return;
		mCursor = c;
		notifyDataSetChanged();
	}

	public Cursor getCursor() {
		return mCursor;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
