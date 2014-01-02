package me.vitez.pingu_android;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;
import com.zeng.pingu_android.Maps_and_Pinging;

public class PingActions extends Activity {
	private static boolean isCurrentLocPingSet = false;
	private static PingObject currentLocPingObj;
	static Marker currentLocPingMarker;

	static public void pingCurrentLocation(GoogleMap mapStored,
			LatLng latLngStored) {
		if (!isCurrentLocPingSet) {
			currentLocPingMarker = mapStored.addMarker(new MarkerOptions().position(
					latLngStored).title("Current Location "));
			isCurrentLocPingSet = true;
			String datetime = Useful.getCurrentTimeAsString();
			Useful u = new Useful();
			String user = u.getUsername();
			if (user == null) user = "DEFAULT_USER";
			currentLocPingObj = new PingObject(datetime, user,
					latLngStored);
			currentLocPingObj.sendPingObjToParse();
		}
	}

	static public void unpingCurrentLocation(GoogleMap mapStored,
			LatLng latLngStored) {
		if (isCurrentLocPingSet) {
			currentLocPingMarker.remove();
			currentLocPingObj.removePingObjFromParse();
			isCurrentLocPingSet = false;
		}
	}
	
	
}
