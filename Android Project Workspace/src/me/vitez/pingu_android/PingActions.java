package me.vitez.pingu_android;

import java.util.Random;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PingActions {
	private static boolean isCurrentLocPingSet = false;
	static Marker currentLocPing;

	static public void pingCurrentLocation(GoogleMap mapStored,
			LatLng latLngStored) {
		if (!isCurrentLocPingSet) {
			currentLocPing = mapStored.addMarker(new MarkerOptions().position(
					latLngStored).title("Current Location "));
			isCurrentLocPingSet = true;
		}
	}

	static public void unpingCurrentLocation(GoogleMap mapStored,
			LatLng latLngStored) {
		currentLocPing.remove();
		isCurrentLocPingSet = false;
	}
}
