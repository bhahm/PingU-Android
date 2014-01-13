package com.pingu.fragments;
//@author Steven Zeng
//fragment that displays the map and allows pinging. First screen when app starts.
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.pingu.main.MainActivity;
import com.zeng.pingu_android.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class HomeFragment extends Fragment implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	public HomeFragment(){}
	private GoogleMap map;
	private LocationClient mLocationClient;
	private Location myLocation;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	mLocationClient = new LocationClient(MainActivity.c, this, this); 
	
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.activity_maps_and__pinging, container, false);
        Button btnPing = (Button) rootView.findViewById(R.id.btnPing);
    	Button btnRefresh = (Button) rootView.findViewById(R.id.btnRefresh);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.btnPing) {
                    	setUpMapIfNeeded();
                        PingActions.pingCurrentLocation(mapStored, latLngStored);
                    }
                    else if (id == R.id.btnRefresh) {
                        setUpMapIfNeeded();
                        try {
                                PingActions.refreshPings(mapStored);
                        } catch (ParseException e) {
                                e.printStackTrace();
                        }
                    }
            }
        };
        btnPing.setOnClickListener(listener);
        btnRefresh.setOnClickListener(listener);
        return rootView;
    }
    @Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(this.getActivity(), "Connected", Toast.LENGTH_SHORT).show();
		setUpMapIfNeeded();
	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this.getActivity(), "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	public void onStop() {
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
				connectionResult.startResolutionForResult(this.getActivity(),
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
			getActivity().showDialog(connectionResult.getErrorCode());
		}
	}
}
  