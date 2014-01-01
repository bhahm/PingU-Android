package me.vitez.pingu_android;

import java.util.List;

import com.zeng.pingu_android.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

public class PrefsActivity extends PreferenceActivity{
	 
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   addPreferencesFromResource(R.xml.prefs);
}
}