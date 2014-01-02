package me.vitez.pingu_android;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zeng.pingu_android.Maps_and_Pinging;

/**
 * Library of useful static methods that may be called anywhere else
 * @author Mitchell Vitez
 * Make sure these are all STATIC.
 * Also, descriptive names are essential.
 * Don't outsource procedures here, outsource small, repeatable tasks
 */

public class Useful extends Activity {
	private static String username;
	
	public static String getCurrentTimeAsString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = (Date) Calendar.getInstance().getTime();        
		return df.format(today);
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static void setUsername(String u){
		username = u;
	}
}
