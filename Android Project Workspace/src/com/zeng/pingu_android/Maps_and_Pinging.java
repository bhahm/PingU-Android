package com.zeng.pingu_android;

import java.util.ArrayList;
import me.vitez.pingu_android.Friends;
import me.vitez.pingu_android.PingActions;
import me.vitez.pingu_android.PingObject;
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
import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class Maps_and_Pinging extends FragmentActivity implements


		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	TextView textView;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private Location myLocation;
	ArrayList<PingObject> record;
	
	String[] drawerListViewItems;
		ListView drawerListView;
		Context currentContext = this;

		
		
		
		//ListView ls = (ListView) findViewById(R.id.left_drawer_1);
		//drawerListView.setOnItemClickListener(new DrawerItemClickListener()); 
		
	
	
	
	@Override			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_and__pinging);
		
		{
			// The following declarations are for use to debug drawer layout
			
			// The following code is to test the drawer layout
				// get list of items from strings.xml
				drawerListViewItems = getResources().getStringArray(R.array.items);
				// get listView defined in drawer_test.xml
				drawerListView = (ListView) findViewById(R.id.left_drawer);

				// set the adapter for the list view
				drawerListView.setAdapter(new ArrayAdapter<String>(this,
						R.layout.drawer_listview_item, drawerListViewItems));
				// Create the adapter that will return a fragment for each of the
				// three
				// primary sections of the app.
				// SectionsPagerAdapter mSectionsPagerAdapter = new
				// SectionsPagerAdapter(
				// getSupportFragmentManager());
				
				
				drawerListView.setOnItemClickListener(new DrawerItemClickListener());
		}
		
		
		
		mLocationClient = new LocationClient(this, this, this);

	
		
		Parse.initialize(this, "91HmkBQniLXG81hN5ww3ARr15sNofBNbvG9ZgOqJ",
				"1j5IFHb6N6basAB6pnA03QaQuqDGZluMZjvamWN2");
		ParseAnalytics.trackAppOpened(getIntent());

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(Maps_and_Pinging.this);
		String username = prefs.getString("username", "Default NickName");
		Useful.setUsername(username);
<<<<<<< HEAD

		/*
		 * setUpMapIfNeeded(); try { PingActions.refreshPings(mapStored); }
		 * catch (ParseException e1) { 
		 * e1.printStackTrace(); }
		 */
=======
		
		Button btnPrefs = (Button) findViewById(R.id.btnPrefs);
		Button btnFriends = (Button) findViewById(R.id.btnFriends);
>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING
		Button btnPing = (Button) findViewById(R.id.btnPing);
		Button btnUnPing = (Button) findViewById(R.id.btnUnPing);

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				 if (id == R.id.btnPing) {
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
=======
				} else if (id == R.id.btnRefresh) {
					setUpMapIfNeeded();
					refreshMap();
>>>>>>> parent of 0efd129... GOT REFRESH TOTALLY WORKING
				} else {

				}
			}
		};

		btnPing.setOnClickListener(listener);
		btnUnPing.setOnClickListener(listener);
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
			mDialog = new Dialog(null);
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

	
private class DrawerItemClickListener extends FragmentActivity implements ListView.OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}

		private void selectItem(int position) {

			if (position == 0) {
				Intent friends = new Intent(currentContext,
						Friends.class);
				currentContext.startActivity(friends);
				
			} else if (position == 1) {
				final Intent settings = new Intent(currentContext,
						PrefsActivity.class);
				currentContext.startActivity(settings);
			} 
			else if (position == 2) {
				setUpMapIfNeeded();
				try {
					PingActions.refreshPings(mapStored);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	
}






