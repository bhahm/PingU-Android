package com.zeng.pingu_android;

import me.vitez.pingu_android.PrefsActivity;

import com.zeng.pingu_android.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.*;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Maps_and_Pinging extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	TextView textView;
	 private final static int
     CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	 private GoogleMap map;
	 private LocationClient mLocationClient;
	 private Location myLocation;
	 @Override
   protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_and__pinging);
		mLocationClient = new LocationClient(this, this, this);
		
		Button btnPrefs = (Button) findViewById(R.id.btnPrefs);
		Button btnGetPrefs = (Button) findViewById(R.id.btnGetPreferences);
		
		textView = (TextView) findViewById(R.id.txtPrefs);
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			   public void onClick(View v) {
			   switch (v.getId()) {
			   case R.id.btnPrefs:
			      final Intent intent = new Intent(Maps_and_Pinging.this,
			      PrefsActivity.class);
			      startActivity(intent);
			      break;
			 
			   case R.id.btnGetPreferences:
			      displaySharedPreferences();
			      break;
			 
			   default:
			     break;
			   }
			   }
			   };
			 btnPrefs.setOnClickListener(listener);
			 btnGetPrefs.setOnClickListener(listener);

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
* Handle results returned to the FragmentActivity
* by Google Play services
*/
@Override
protected void onActivityResult(
     int requestCode, int resultCode, Intent data) {
 // Decide what to do based on the original request code
 switch (requestCode) {
     
     case CONNECTION_FAILURE_RESOLUTION_REQUEST :
     /*
      * If the result code is Activity.RESULT_OK, try
      * to connect again
      */
         switch (resultCode) {
             case Activity.RESULT_OK :
             /*
              * Try the request again
              */
          
             break;
         }
 }
}
private boolean servicesConnected() {
 // Check that Google Play services is available
 int resultCode =
         GooglePlayServicesUtil.
                 isGooglePlayServicesAvailable(this);
 // If Google Play services is available
 if (ConnectionResult.SUCCESS == resultCode) {
     // In debug mode, log the status
     Log.d("Location Updates",
             "Google Play services is available.");
     // Continue
     return true;
 // Google Play services was not available for some reason
 } else {
	 ConnectionResult connectionResult = new ConnectionResult(resultCode, null);
	// Get the error code
     int errorCode = connectionResult.getErrorCode();
     // Get the error dialog from Google Play services
     Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
             errorCode,
             this,
             CONNECTION_FAILURE_RESOLUTION_REQUEST);

     // If Google Play services can provide an error dialog
     if (errorDialog != null) {
         // Create a new DialogFragment for the error dialog
         ErrorDialogFragment errorFragment =
                 new ErrorDialogFragment();
         // Set the dialog in the DialogFragment
         errorFragment.setDialog(errorDialog);
         // Show the error dialog in the DialogFragment
         errorFragment.show(getFragmentManager(),
                 "Location Updates");
     }
 }
 return false;
}

	

	private void setUpMapIfNeeded() {
		if (map == null)
		{
			map = ((MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
	        myLocation = mLocationClient.getLastLocation();
	        LatLng sydney = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

	        map.setMyLocationEnabled(true);
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
	        
	        map.addMarker(new MarkerOptions()
	                .title("my location")
	                .snippet("I am currently here")
	                .position(sydney));
		}
		else
		{
			setUpMap();
		}
	    }
	 


	 /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        map.addMarker(new MarkerOptions().position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).title("Marker"));
    }
    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
  
    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
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
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
        	showDialog(connectionResult.getErrorCode());
        }
    }
    
    private void displaySharedPreferences() {
    	   SharedPreferences prefs = PreferenceManager
    	    .getDefaultSharedPreferences(Maps_and_Pinging.this);
    	 
    	   String username = prefs.getString("username", "Default NickName");
    	   String passw = prefs.getString("password", "Default Password");
    	   boolean checkBox = prefs.getBoolean("checkBox", false);
    	   String listPrefs = prefs.getString("listpref", "Default list prefs");
    	 
    	   StringBuilder builder = new StringBuilder();
    	   builder.append("Username: " + username + "\n");
    	   builder.append("Password: " + passw + "\n");
    	   builder.append("Keep me logged in: " + String.valueOf(checkBox) + "\n");
    	   builder.append("List preference: " + listPrefs);
    	 
    	   textView.setText(builder.toString());
    	}

}
   