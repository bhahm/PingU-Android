package me.vitez.pingu_android;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PingActions {
	static public void pingCurrentLocation(GoogleMap mapStored, LatLng latLngStored) {
		mapStored.addMarker(new MarkerOptions().position(latLngStored)).setTitle("Current Location");
	}
}
