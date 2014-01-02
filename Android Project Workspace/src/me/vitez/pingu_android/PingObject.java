package me.vitez.pingu_android;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

public class PingObject {
	private String pingTime;
	private String creator;
	// TODO: groups or categories of pings
	private double lat;
	private double lng;
	private ParseObject pingInParse;

	public PingObject(String pingTime, String creator, LatLng latlng) {
		this.pingTime = pingTime;
		this.creator = creator;
		this.lat = latlng.latitude;
		this.lng = latlng.longitude;
	}
	
	public void sendPingObjToParse() {
		pingInParse = new ParseObject("CurLocPing");
		pingInParse.put("pingTime", pingTime);
		pingInParse.put("creator", creator);
		pingInParse.put("latitude", lat);
		pingInParse.put("longitude", lng);
		pingInParse.saveInBackground();
	}
	
	public void removePingObjFromParse() {
		pingInParse.deleteEventually();
	}
}
