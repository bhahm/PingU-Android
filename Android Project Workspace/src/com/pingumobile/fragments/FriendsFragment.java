package com.pingumobile.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pingumobile.actionsAndObjects.FriendObject;
import com.pingumobile.actionsAndObjects.PingHelper;
import com.pingumobile.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.zeng.pingu_android.R;

/**
 * Used to display friends on the friends page
 * 
 * @author Mitchell Vitez, Steven Zeng
 */
public class FriendsFragment extends Fragment {
	View v;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.friends, container, false);
		setUpFriendsList();
		return v;
	}
    
    void setUpFriendsList() {
        PingHelper ph = new PingHelper(this.getActivity());

        ListView lv = (ListView) v.findViewById(R.id.friendslist);
        ArrayList<String> listOfFriends = new ArrayList<String>();

        ArrayList<FriendObject> allFriends = new ArrayList<FriendObject>();
		try {

			allFriends = ph.getAllFriends();
		} catch (FriendDoesNotExistException e) {
			Log.w("FriendsFragment", "FriendDoesNotExistException");
			e.printStackTrace();
		}

        for (FriendObject f: allFriends) {
        	listOfFriends.add(f.getUsername());
        	Log.i("friend",f.getUsername());
        }
       		
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this.getActivity(), 
                android.R.layout.simple_list_item_1,
                listOfFriends );

        lv.setAdapter(arrayAdapter); 
        
        //this.setListAdapter(arrayAdapter);
        
   }
}