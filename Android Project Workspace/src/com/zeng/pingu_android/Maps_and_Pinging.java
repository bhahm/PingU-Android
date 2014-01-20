package com.zeng.pingu_android;

import me.vitez.pingu_android.Friends;
import me.vitez.pingu_android.PingActions;
import me.vitez.pingu_android.PrefsActivity;
import me.vitez.pingu_android.Useful;

import com.zeng.pingu_android.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;

import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.*;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Maps_and_Pinging extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	TextView textView;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private Location myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_and__pinging);
		mLocationClient = new LocationClient(this, this, this);

		Parse.initialize(this, "91HmkBQniLXG81hN5ww3ARr15sNofBNbvG9ZgOqJ",
				"1j5IFHb6N6basAB6pnA03QaQuqDGZluMZjvamWN2");
		ParseAnalytics.trackAppOpened(getIntent());
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Maps_and_Pinging.this);
		String username = prefs.getString("username", "Default NickName");
		Useful.setUsername(username);
<<<<<<< HEAD
		
		Button btnPrefs = (Button) findViewById(R.id.btnPrefs);
		Button btnFriends = (Button) findViewById(R.id.btnFriends);
=======
<<<<<<< HEAD
<<<<<<< HEAD

		/*
		 * setUpMapIfNeeded(); try { PingActions.refreshPings(mapStored); }
		 * catch (ParseException e1) { 
		 * e1.printStackTrace(); }
		 */
=======
=======
>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING
		
		Button btnPrefs = (Button) findViewById(R.id.btnPrefs);
		Button btnFriends = (Button) findViewById(R.id.btnFriends);
>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING
>>>>>>> 1c9aa4fb98811684205314c4ee259c177829489f
		Button btnPing = (Button) findViewById(R.id.btnPing);
		Button btnUnPing = (Button) findViewById(R.id.btnUnPing);
		Button btnRefresh = (Button) findViewById(R.id.btnRefresh);

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				if (id == R.id.btnPrefs) {
					final Intent intent = new Intent(Maps_and_Pinging.this,
							PrefsActivity.class);
					startActivity(intent);
				} else if (id == R.id.btnFriends) {
					final Intent intent2 = new Intent(Maps_and_Pinging.this,
							Friends.class);
					startActivity(intent2);
				} else if (id == R.id.btnPing) {
					setUpMapIfNeeded();
					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(Maps_and_Pinging.this);
					String username = prefs.getString("username",
							"Default NickName");
					Useful.setUsername(username);
					PingActions.pingCurrentLocation(mapStored, latLngStored);
				} else if (id == R.id.btnUnPing) {
					setUpMapIfNeeded();
					PingActions.unpingCurrentLocation(mapStored, latLngStored);
<<<<<<< HEAD
				} else if (id == R.id.btnRefresh) {
					setUpMapIfNeeded();
					try {
						PingActions.refreshPings(mapStored);
					} catch (ParseException e) {
						e.printStackTrace();
					}
=======
<<<<<<< HEAD
=======
				} else if (id == R.id.btnRefresh) {
					setUpMapIfNeeded();
					refreshMap();
<<<<<<< HEAD
>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING
=======
>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING
>>>>>>> 1c9aa4fb98811684205314c4ee259c177829489f
				} else {
					
				}
			}
		};
		btnPrefs.setOnClickListener(listener);
		btnFriends.setOnClickListener(listener);
		btnPing.setOnClickListener(listener);
		btnUnPing.setOnClickListener(listener);
<<<<<<< HEAD
		btnRefresh.setOnClickListener(listener);
=======
<<<<<<< HEAD
=======
		btnRefresh.setOnClickListener(listener);
		
	}
	
	

	public void refreshMap() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("CurLocPing");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> results, ParseException e) {
				if (e != null) {
					// There was an error
				} else {
					for (ParseObject parseObj : results) {
						String pingTime = (String) parseObj.get("pingTime");
						String creator = (String) parseObj.get("creator");
						double lat = (Double) parseObj.get("latitude");
						double lng = (Double) parseObj.get("longitude");
						LatLng latlng = new LatLng(lat, lng);
						
						mapStored.addMarker(new MarkerOptions().position(
								latlng).title(creator +" "+ pingTime));
						// pingObj = new PingObject(pingTime, creator,
						//		latlng);
						// record.add(pingObj);
					}
				}
			}
		});
		
	}

>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING

>>>>>>> 1c9aa4fb98811684205314c4ee259c177829489f
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		setUpMapIfNeeded();
	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	// Define a DialogFragment that displays the error dialog

public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}

	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				/*
				 * Try the request again
				 */
				break;
			}
		}
	}

	private void setUpMapIfNeeded() {
		if (map == null) {
			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			myLocation = mLocationClient.getLastLocation();
			LatLng myLatLng = new LatLng(myLocation.getLatitude(),
					myLocation.getLongitude());

			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 13));

		} else {
			setUpMap();
		}
	}

	public GoogleMap mapStored;
	public LatLng latLngStored;

	private void setUpMap() {
		mapStored = map;
		latLngStored = new LatLng(myLocation.getLatitude(),
				myLocation.getLongitude());
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			showDialog(connectionResult.getErrorCode());
		}
	}

}
