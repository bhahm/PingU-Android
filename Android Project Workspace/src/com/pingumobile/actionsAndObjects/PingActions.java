package com.pingumobile.actionsAndObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.util.Log;

<<<<<<< HEAD:Android Project Workspace/src/com/pingumobile/actionsAndObjects/PingActions.java
=======
import com.pingu.fragments.HomeFragment;
import com.pingu.fragments.NotificationActivity;
>>>>>>> origin/tiaojon:Android Project Workspace/src/com/pingu/actionsAndObjects/PingActions.java
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
<<<<<<< HEAD:Android Project Workspace/src/com/pingumobile/actionsAndObjects/PingActions.java
import com.pingumobile.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.pingumobile.fragments.HomeFragment;
import com.pingumobile.main.MainActivity;
=======
import com.pingu.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.pingu.main.MainActivity;
>>>>>>> origin/tiaojon:Android Project Workspace/src/com/pingu/actionsAndObjects/PingActions.java
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
			LatLng latLngStoredIn) {
		mapStored = mapStoredIn;
		latlngStored = latLngStoredIn;
		unpingCurrentLocation(mapStored, latlngStored);
		try {
			unpingAllWithThisUsername();
		} catch (ParseException e) {
			Log.w("PingActions", "Parse Exception");
			e.printStackTrace();
		}
		if (!isCurrentLocPingSet) {
			currentLocPingMarker = mapStored.addMarker(new MarkerOptions()
					.position(latlngStored)
					.title("Current Ping")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pingicon)));
			currentLocPingMarker.setAnchor((float) .5, (float) .5);
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

	private static void unpingAllWithThisUsername() throws ParseException {
		String username = Useful.getUsername();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("CurLocPing")
				.whereEqualTo("username", username);
		List<ParseObject> results = query.find();
		for (ParseObject parseObj : results) {
			parseObj.deleteInBackground();
		}
	}

	static public void unpingCurrentLocation(GoogleMap mapStoredIn,
			LatLng latLngStoredIn) {
		mapStored = mapStoredIn;
		latlngStored = latLngStoredIn;
		if (isCurrentLocPingSet) {
			currentLocPingMarker.remove();
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
		int iconName = 0;
		if (duration == 1) {
<<<<<<< HEAD:Android Project Workspace/src/com/pingumobile/actionsAndObjects/PingActions.java
			iconName = R.drawable.pingiconfaded;
		} else {
			iconName = R.drawable.pingicon;
		}

		Marker m = mapStored.addMarker(new MarkerOptions()
				.position(p.getLatlng())
				.icon(BitmapDescriptorFactory.fromResource(iconName))
				.title(p.getName() + " | " + p.getTime())
				.snippet(p.getMessage()));
		m.setAnchor((float) .5, (float) .5);
		m.showInfoWindow();

	}
	
	static public void deleteMyPing() {
		try {
			unpingAllWithThisUsername();
		} catch (ParseException e) {
			Log.w("PingActions", "Delete ping exception");
			e.printStackTrace();
=======
			Marker m = mapStored.addMarker(new MarkerOptions()
					.position(p.getLatlng())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pingiconfaded))
					.title("Sent by " + p.getName() + " at " + p.getTime()));
			m.setAnchor((float) .5, (float) .5);
			m.showInfoWindow();
		} else {
			Marker m = mapStored.addMarker(new MarkerOptions()
					.position(p.getLatlng())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pingicon))
					.title("Sent by " + p.getName() + " at " + p.getTime()));
			m.setAnchor((float) .5, (float) .5);
			m.showInfoWindow();
>>>>>>> origin/tiaojon:Android Project Workspace/src/com/pingu/actionsAndObjects/PingActions.java
		}
	}

}
