package com.pingumobile.actionsAndObjects;

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
	// TODO: categories of pings
	private double lat;
	private double lng;
	private ParseObject pingInParse;
	private String message;

	/* public PingObject(String pingTime, String creator, LatLng latlng) {
		this.pingTime = pingTime;
		this.creator = creator;
		this.lat = latlng.latitude;
		this.lng = latlng.longitude;
	} */
	
	public PingObject(String pingTime, String creator, LatLng latlng, String message) {
		this.pingTime = pingTime;
		this.creator = creator;
		this.lat = latlng.latitude;
		this.lng = latlng.longitude;
		this.message = message;
	}
	
	public void sendPingObjToParse() {
		pingInParse = new ParseObject("CurLocPing");
		pingInParse.put("pingTime", pingTime);
		pingInParse.put("creator", creator);
		pingInParse.put("latitude", lat);
		pingInParse.put("longitude", lng);
		pingInParse.put("message", message);
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
	
	public String getTime() {
		return pingTime;
	}
	
	public String getMessage() {
		return message;
	}
}