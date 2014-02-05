package com.pingu.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pingu.actionsAndObjects.FriendObject;
import com.pingu.actionsAndObjects.PingHelper;
import com.pingu.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.zeng.pingu_android.R;

/**
 * Used to display friends on the friends page
 * 
 * @author Mitchell Vitez, Steven Zeng TODO: Expand group functionality and so
 *         on
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
			// TODO Auto-generated catch block
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