package me.vitez.pingu_android;

public class PingObject {
	private String name;
	private String pingTime;
	private String creator;
	private String group;
	private double lat;
	private double lng;

	public PingObject(String name, String pingTime, String creator,
			String group, double lat, double lng) {
		this.name = name;
		this.pingTime = pingTime;
		this.creator = creator;
		this.group = group;
		this.lat = lat;
		this.lng = lng;
	}
}
