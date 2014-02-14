package com.pingu.actionsAndObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import com.pingu.fragments.HomeFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pingu.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.pingu.fragments.HomeFragment;
import com.pingu.main.MainActivity;
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
<<<<<<< HEAD
=======
			// TODO Add ability to add message when pinging
			
<<<<<<< HEAD
>>>>>>> origin/mitchellvitez
=======
>>>>>>> origin/mitchellvitez
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
								// TODO Auto-generated catch block
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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 5c86292adf65b687c5a6d4c32578e71bf30e5b28
					.snippet(p.getMessage())
					.title(p.getName()+" | "+ p.getTime()));
=======
					.title("Sent by " + p.getName() + " at " + p.getTime())
					.snippet(p.getMessage()));
>>>>>>> 53deb2e26303833f1bd9a53145d9a04a332babe4
=======
					.title("Sent by " + p.getName() + " at " + p.getTime())
					.snippet(p.getMessage()));
>>>>>>> 53deb2e26303833f1bd9a53145d9a04a332babe4
<<<<<<< HEAD
=======
					.snippet(p.getMessage())
					.title(p.getName()+" | "+ p.getTime()));
>>>>>>> origin/mitchellvitez
=======
>>>>>>> 5c86292adf65b687c5a6d4c32578e71bf30e5b28
			m.setAnchor((float) .5, (float) .5);
			m.showInfoWindow();
		} else {
			Marker m = mapStored.addMarker(new MarkerOptions()
					.position(p.getLatlng())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pingicon))
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 5c86292adf65b687c5a6d4c32578e71bf30e5b28
							.snippet(p.getMessage())
					.title(p.getName()+" | "+ p.getTime()));
=======
					.title("Sent by " + p.getName() + " at " + p.getTime())
					.snippet(p.getMessage()));
>>>>>>> 53deb2e26303833f1bd9a53145d9a04a332babe4
=======
					.title("Sent by " + p.getName() + " at " + p.getTime())
					.snippet(p.getMessage()));
>>>>>>> 53deb2e26303833f1bd9a53145d9a04a332babe4
<<<<<<< HEAD
=======
							.snippet(p.getMessage())
					.title(p.getName()+" | "+ p.getTime()));
>>>>>>> origin/mitchellvitez
=======
>>>>>>> 5c86292adf65b687c5a6d4c32578e71bf30e5b28
			m.setAnchor((float) .5, (float) .5);
			m.showInfoWindow();
		}
	}

}
