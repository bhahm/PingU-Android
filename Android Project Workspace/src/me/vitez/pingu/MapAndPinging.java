package me.vitez.pingu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MapAndPinging extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_and_pinging);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_and_pinging, menu);
		return true;
	}

}
