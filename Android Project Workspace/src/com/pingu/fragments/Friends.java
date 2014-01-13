package com.pingu.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeng.pingu_android.R;

/**
 * Used to display friends on the friends page
 * @author Mitchell Vitez, Steven Zeng
 * TODO: Expand group functionality and so on
 */
public class Friends extends Fragment {

public Friends(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.xml.friends, container, false);
         
        return rootView;
    }
}