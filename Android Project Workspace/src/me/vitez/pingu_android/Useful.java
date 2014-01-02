package me.vitez.pingu_android;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;

/**
 * Library of useful static methods that may be called anywhere else
 * @author Mitchell Vitez 
 * Make sure these are all STATIC. Also, descriptive
 * function names are essential, this is our miscellaneous function
 * library. Don't outsource procedures here, outsource small, repeatable
 * tasks.
 */

public class Useful extends Activity {
	private static String username;

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeAsString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = (Date) Calendar.getInstance().getTime();
		return df.format(today);
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String u) {
		username = u;
	}
}
