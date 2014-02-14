package com.pingumobile.actionsAndObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pingumobile.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.pingumobile.fragments.HomeFragment;
import com.pingumobile.main.MainActivity;
import com.zeng.pingu_android.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Static functions that act on pings, called by buttons in our main page
 * listener
 * 
 * @author Mitchell Vitez
 */
public class PingActions extends Activity {
	private static boolean isCurrentLocPingSet = false;
	private static PingObject currentLocPingObj;
	static Marker currentLocPingMarker;
	static ArrayList<Marker> pingMarkers;
	public static GoogleMap mapStored;
	public static LatLng latlngStored;

	static public void pingCurrentLocation(GoogleMap mapStoredIn,
			LatLng latLngStoredIn, String message) {
		mapStored = mapStoredIn;
		latlngStored = latLngStoredIn;
		unpingCurrentLocation(mapStored, latlngStored);
		if (!isCurrentLocPingSet) {
			isCurrentLocPingSet = true;
			String datetime = Useful.getCurrentTimeAsString();
			String user = Useful.getUsername();
			if (user == null)
				user = "DEFAULT_USER";
			currentLocPingObj = new PingObject(datetime, user, latlngStored,
					HomeFragment.message);
			currentLocPingObj.sendPingObjToParse();
		}
	}

	static public void unpingCurrentLocation(GoogleMap mapStoredIn,
			LatLng latLngStoredIn) {
		mapStored = mapStoredIn;
		latlngStored = latLngStoredIn;
		if (isCurrentLocPingSet) {
			currentLocPingObj.removePingObjFromParse();
			isCurrentLocPingSet = false;
		}
	}

	static public void refreshPings(GoogleMap mapStoredIn)
			throws ParseException {
		mapStored = mapStoredIn;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("CurLocPing");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> results, ParseException e) {
				if (e != null) {
					// There was an error
				} else {
					for (ParseObject parseObj : results) {
						Date createdAt = parseObj.getCreatedAt();
						Date now = new Date();
						if (now.getTime() - createdAt.getTime() >= 30 * 60 * 1000) {
							parseObj.deleteInBackground();
							// break;
						}
						boolean inList = false;
						String creator = (String) parseObj.get("creator");
						if (creator.equals(Useful.getUsername())) {
							inList = true;
						} else {
							PingHelper ph = new PingHelper(MainActivity
									.getAppContext());
							ArrayList<FriendObject> list = new ArrayList<FriendObject>();
							try {
								list = ph.getAllFriends();
							} catch (FriendDoesNotExistException e1) {
								e1.printStackTrace();
							}
							for (FriendObject f : list) {
								Log.i("fo", "workedddd");
								if (f.getUsername().equals(creator)) {
									inList = true;
								}
							}
						}
						if (inList) {
							int longTime = 0;
							if (now.getTime() - createdAt.getTime() >= 15 * 60 * 1000) {
								longTime = 1;
							}
							String pingTime = (String) parseObj.get("pingTime");
							// String creator = (String)
							// parseObj.get("creator");
							double lat = (Double) parseObj.get("latitude");
							double lng = (Double) parseObj.get("longitude");
							LatLng latlng = new LatLng(lat, lng);
							String message = (String) parseObj.get("message");
							PingObject pingObj = new PingObject(pingTime,
									creator, latlng, message);
							showOnMap(pingObj, longTime);
						}
					}
				}
			}

		});

	}

	static public void showOnMap(PingObject p, int duration) {
		if (duration == 1) {
			Marker m = mapStored.addMarker(new MarkerOptions()
					.position(p.getLatlng())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pingiconfaded))
					.title(p.getName() + " | " + p.getTime())
					.snippet(p.getMessage()));
			m.setAnchor((float) .5, (float) .5);
			m.showInfoWindow();
		} else {
			Marker m = mapStored.addMarker(new MarkerOptions()
					.position(p.getLatlng())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pingicon))
					.title(p.getName() + " | " + p.getTime())
					.snippet(p.getMessage()));
			m.setAnchor((float) .5, (float) .5);
			m.showInfoWindow();
		}
	}

}
