package me.vitez.pingu_android;

import com.zeng.pingu_android.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Any code that runs on the settings page goes here
 * @author Mitchell Vitez
 * Android has built in "preferences" which we can use for our settings
 * page.
 */
public class PrefsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
}