package com.zeng.pingu_android;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;



public class Maps_and_Pinging extends Activity {
	private GoogleMap mMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_and__pinging);
		setUpMapIfNeeded();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps_and__pinging, menu);
		return true;
	}

}
