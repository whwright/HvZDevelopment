package edu.gatech.hvz.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EntityUtils {

	private static Format formatter = new SimpleDateFormat("EEE hh:mm aa", Locale.US);
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	
	public static Date stringToDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException pe) {
			return new Date(0);
		}
	}
	
	public static String stringToFormattedDate(String dateString) {
		Date date = EntityUtils.stringToDate(dateString);
		return formatter.format(date);
	}
	
	public static String getEncodedText(String text) {
		try {
		return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			return text;
		}
	}
}
