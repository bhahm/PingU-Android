package com.pingu.fragments;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pingu.actionsAndObjects.Useful;
import com.zeng.pingu_android.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

/**
 * Any code that runs on the settings page goes here
 * 
 * @author Mitchell Vitez, Steven Zeng Android has built in "preferences" which
 *         we can use for our settings page.
 */
public class PrefsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	private ParseObject pingInParse;
	public boolean isUsed;
	private ParseObject po;

	public PrefsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);

		// Context context;
		// context = getActivity().getApplicationContext();
		// Preferences prefs = (Preferences)
		// context.getSharedPreferences("prefs", 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	public boolean onPreferenceChange(String prefBeingChangedKey, String oldUsername,
			String newValue) throws ParseException {
		if (prefBeingChangedKey.equals("username")) {
			if (usernameAlreadyUsed(newValue) || isUsed) {
				Toast.makeText(this.getActivity().getApplicationContext(),
						"Unable to use this name", Toast.LENGTH_LONG).show();
				Log.i("key", "bad user");
				return false;
			} else {
				deleteOldFromParse(oldUsername);
				sendUserToParse(newValue);
				Useful.setUsername(newValue);
				Toast.makeText(this.getActivity().getApplicationContext(),
						"Username set to: " + newValue, Toast.LENGTH_LONG)
						.show();
				Log.i("key", "new user");
				return true;
			}
		}

		return false;
	}

	private void deleteOldFromParse(String oldUsername) throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		List<ParseObject> results = query.find();
		boolean del = false;
		Log.i("oldU", oldUsername);
		Log.i("oldU", oldUsername);
		Log.i("oldU", oldUsername);
		Log.i("oldU", oldUsername);
		Log.i("oldU", oldUsername);
		Log.i("oldU", oldUsername);
		for (ParseObject parseObj : results) {
			if (parseObj.get("username").equals(oldUsername)) {
				po = parseObj;
				del = true;
			}
		}
		if (del)
			po.deleteInBackground();
	}

	public void sendUserToParse(String newValue) {
		pingInParse = new ParseObject("User");
		pingInParse.put("username", newValue);
		pingInParse.saveInBackground();
	}

	public boolean usernameAlreadyUsed(final String testName)
			throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		List<ParseObject> results = query.find();
		isUsed = false;
		for (ParseObject parseObj : results) {
			if (parseObj.get("username").equals(testName)) {
				isUsed = true;
			}
		}
		return isUsed;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		String oldUsername = Useful.getUsername();
		try {
			onPreferenceChange(key, oldUsername,
					sharedPreferences.getString(key, "DEFAULT_USERNAME"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}