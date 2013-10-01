package com.sickfuture.letswatch.helpers;

import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sickfuture.sickcore.image.SickImageLoader;
import com.sickfuture.letswatch.R;

public class UIHelper {

	private static void setText(TextView textView, String text, int flag) {
		if (!TextUtils.isEmpty(text)) {
			textView.setText(text);
			textView.setVisibility(View.VISIBLE);
		} else
			textView.setVisibility(flag);
	}

	public static void setTextOrGone(TextView textView, String text) {
		setText(textView, text, View.GONE);
	}

	public static void setTextOrGone(TextView textView, Cursor cursor,
			String column) {
		setTextOrGone(textView, cursor.getString(cursor.getColumnIndex(column)));
	}

	public static void setTextOrInvisible(TextView textView, String text) {
		setText(textView, text, View.INVISIBLE);
	}

	public static void setTextOrInvisible(TextView textView, Cursor cursor,
			String column) {
		setTextOrInvisible(textView,
				cursor.getString(cursor.getColumnIndex(column)));
	}

	public static void setImage(ImageView imageView, SickImageLoader loader,
			String url) {
		if (!TextUtils.isEmpty(url)) {
			loader.loadBitmap(imageView, url);
		}
	}

	public static void setImage(ImageView imageView, SickImageLoader loader,
			Cursor cursor, String column) {
		setImage(imageView, loader,
				cursor.getString(cursor.getColumnIndex(column)));
	}
}
