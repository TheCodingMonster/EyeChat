package com.eyeball.utils.misc;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 
 * A Time Util class.
 * 
 * @author Eyeball
 *
 */
public class TimeUtils {

	private static HashMap<Integer, String> months = new HashMap<Integer, String>();

	static {
		months.put(1, "Jan");
		months.put(2, "Feb");
		months.put(3, "Mar");
		months.put(4, "Apr");
		months.put(5, "May");
		months.put(6, "June");
		months.put(7, "July");
		months.put(8, "August");
		months.put(9, "Sep");
		months.put(10, "Oct");
		months.put(11, "Nov");
		months.put(12, "Dec");

	}

	/**
	 * 
	 * Get the current time
	 * 
	 * @return The current time
	 * 
	 */
	public static String getTime() {
		String date = new Date().toString();
		return date.substring(11, 19);
	}

	/**
	 * 
	 * Get the time provided by the date passed in.
	 * 
	 * @param date
	 *            The given date.
	 * 
	 * @return The time from the date given.
	 */
	public static String getTime(Date date) {
		return date.toString().substring(11, 19);
	}

	/**
	 * 
	 * Get the current date.
	 * 
	 * @return The current date.
	 */
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		String s = months.get(c.get(Calendar.MONTH) + 1) + " " + c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR);
		return s;
	}

}
