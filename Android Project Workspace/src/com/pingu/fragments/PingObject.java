package com.pingu.fragments;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

/**
 * A PingObject stores all the essential info on a single ping
 * @author Mitchell Vitez
 * PingObjects simplify the pinging process by keeping each ping separate
 * and easy to manage.
 */
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
	
	public LatLng getLatlng() {
		return new LatLng(lat,lng);
	}
	
	public void removePingObjFromParse() {
		pingInParse.deleteEventually();
	}

	public String getName() {
		return creator;
	}
}
