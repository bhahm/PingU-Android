package me.vitez.pingu_android;


import android.app.Activity;

import com.parse.ParseObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Static functions that act on pings, called by buttons in our main page listener
 * @author Mitchell Vitez
 */
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
			String user = Useful.getUsername();
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
	
	static public void refreshPings(GoogleMap mapStored) {
		
	}
	
	
}
