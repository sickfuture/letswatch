package com.sickfuture.letswatch.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.TextUtils;

import com.android.sickfuture.sickcore.utils.L;

public class AgeHelper {

	private static final String LOG_TAG = AgeHelper.class.getSimpleName();
	
	public static int getAge(String birthday) {
		if(TextUtils.isEmpty(birthday))
			return -1;
		try {
			String format = "yyyy-MM-dd";
			Calendar calendar = Calendar.getInstance();
			int currYear = calendar.get(Calendar.YEAR);
			int currMonth = calendar.get(Calendar.MONTH);
			Calendar birth = Calendar.getInstance();
			birth.setTime(new SimpleDateFormat(format).parse(birthday));
			int age = currYear - birth.get(Calendar.YEAR);
			if (currMonth < birth.get(Calendar.MONTH)) {
				return age - 1;
			} else if (currMonth > birth.get(Calendar.MONTH)) {
				return age;
			} else {
				int currDay = calendar.get(Calendar.DAY_OF_MONTH);
				if (currDay >= birth.get(Calendar.DAY_OF_MONTH)) {
					return age;
				} else
					return age - 1;
			}
		} catch (ParseException e) {
			 L.e(LOG_TAG, "getAge: " + e.toString());
		}
		return -1;
	}

	public static int getAge(String birthday, String deathday) {
		return 0;
	}

	/**
	 * 
	 * @param date in format "yyyy-MM-dd"
	 * @param dateStyle style constant from type DateFormat
	 * @return
	 */
	public static String formatDate(String date, int dateStyle) {
		if(TextUtils.isEmpty(date))
			return null;
		try {
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(format,
					Locale.getDefault());
			Date d = sdf.parse(date);
			return DateFormat.getDateInstance(
					dateStyle, Locale.getDefault()).format(d);
		} catch (ParseException e) {
			L.e(LOG_TAG, "formatDate: " + e.toString());
		}
		return null;
	}
}
