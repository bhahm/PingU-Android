package com.zeng.pingu_android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Maps_and_Pinging extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_and__pinging);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps_and__pinging, menu);
		return true;
	}

}
