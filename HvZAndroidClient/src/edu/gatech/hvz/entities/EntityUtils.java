package edu.gatech.hvz.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Various utils used by entities.
 */
public class EntityUtils {

	private static Format formatter = new SimpleDateFormat("EEE hh:mm aa", Locale.US);
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	
	
	/**
	 * Convert a string representation of a date in MySQL format to a Java Date
	 * object.
	 * 
	 * @param date A SQL string representation of a date
	 * @return The parsed date object, and on exception the date of epoch(0)
	 */
	public static Date stringToDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException pe) {
			return new Date(0);
		}
	}
	
	/**
	 * Format from a MySQL date string to a friendly date string of the
	 * format DayOfWeek HH:MM AM/PM
	 * @param dateString MySQL date string
	 * @return Friendly formatted date string
	 */
	public static String stringToFormattedDate(String dateString) {
		Date date = EntityUtils.stringToDate(dateString);
		return formatter.format(date);
	}
	
	/**
	 * Format a string into a URL encoded string for use in
	 * GET params.
	 * @param text Plain text string
	 * @return URL encoded string
	 */
	public static String getEncodedText(String text) {
		try {
		return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			return text;
		}
	}
}
