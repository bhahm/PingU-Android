package com.pingu.main;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import com.bugsense.trace.BugSenseHandler;

import android.app.Application;
import android.util.Log;

public class PingUApp extends Application {
	@Override
	public void onCreate() {
		Log.v("PingUApp", "fuck this fucking shit");
		super.onCreate();

	}
}
