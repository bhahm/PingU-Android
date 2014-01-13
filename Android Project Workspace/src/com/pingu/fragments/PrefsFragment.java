package com.pingu.fragments;

import com.zeng.pingu_android.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Any code that runs on the settings page goes here
 * @author Mitchell Vitez, Steven Zeng
 * Android has built in "preferences" which we can use for our settings
 * page.
 */
public class PrefsFragment extends PreferenceFragment {

public PrefsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.xml.prefs, container, false);
         
        return rootView;
    }
}