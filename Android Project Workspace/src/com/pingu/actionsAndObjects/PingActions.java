package com.pingu.actionsAndObjects;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
		if (!isCurrentLocPingSet) {
			currentLocPingMarker = mapStored.addMarker(new MarkerOptions()
					.position(latlngStored).title("Current Ping")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.pingicon)));
			currentLocPingMarker.setAnchor((float) .5, (float) .5);
			isCurrentLocPingSet = true;
			String datetime = Useful.getCurrentTimeAsString();
			String user = Useful.getUsername();
			if (user == null)
				user = "DEFAULT_USER";
			// TODO Add ability to add message when pinging
			String message = "default message for now";
			currentLocPingObj = new PingObject(datetime, user, latlngStored, message);
			currentLocPingObj.sendPingObjToParse();
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
                                    if (now.getTime() - createdAt.getTime() >= 30*60*1000) {
                                    	parseObj.deleteInBackground();
                                    }
                                    else {
	                                    String pingTime = (String) parseObj.get("pingTime");
	                                    String creator = (String) parseObj.get("creator");
	                                    double lat = (Double) parseObj.get("latitude");
	                                    double lng = (Double) parseObj.get("longitude");
	                                    LatLng latlng = new LatLng(lat, lng);
	                                    String message = (String) parseObj.get("message");
	                                    PingObject pingObj = new PingObject(pingTime, creator,
	                                                    latlng, message);
	                                    showOnMap(pingObj);
                                    }
                            }
                    }
            }

    });

}

static public void showOnMap(PingObject p) {
    Marker m = mapStored.addMarker(new MarkerOptions()
    	.position(p.getLatlng())
    	
    	.icon(BitmapDescriptorFactory.fromResource(R.drawable.pingicon))
        .title("Sent by " + p.getName() + " at " + p.getTime()));
    m.setAnchor((float) .5, (float) .5);
    m.showInfoWindow();
}

}
