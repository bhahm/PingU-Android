package com.pingu.main;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import com.bugsense.trace.BugSenseHandler;
import com.parse.Parse;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

@ReportsCrashes(formUri = "http://www.bugsense.com/api/acra?api_key=50b09570", formKey = "")
public class PingUApp extends Application {
	private final String TAG = PingUApp.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "91HmkBQniLXG81hN5ww3ARr15sNofBNbvG9ZgOqJ",
				"1j5IFHb6N6basAB6pnA03QaQuqDGZluMZjvamWN2");
		Log.v(TAG, "Parse connection initialized");
		ACRA.init(this); // initializes ACRA bug detector
		Log.v(TAG, "ACRA initialized");
		BugSenseHandler.I_WANT_TO_DEBUG = true;
		BugSenseHandler.initAndStartSession(this.getApplicationContext(),
				"50b09570");
		Log.v(TAG, "BugSense initialized");
		// Intent intent = new Intent(this, MainActivity.class);
		// startActivity(intent);
	}
}
