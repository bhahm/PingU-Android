package com.pingu.actionsAndObjects;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pingu.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.zeng.pingu_android.R;

/**
 * Used to display friends on the friends page
 * 
 * @author Mitchell Vitez, Steven Zeng TODO: Expand group functionality and so
 *         on
 */
public class Friends extends Fragment {

	public Friends() {
	}

	public void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		ListView listOfFriends = (ListView) getView().findViewById(
				R.id.friendslist);

		PingHelper PH = new PingHelper(null);
		List<FriendObject> friends = null;
		try {
			friends = PH.getAllFriends();
		} catch (FriendDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 ////////////////////////
		FriendObject f = new FriendObject("Mitchell Vitez");
		friends.add(f);
		FriendObject g = new FriendObject("TestFriendWithARelativelyLongUsername");
		friends.add(g);
		FriendObject h = new FriendObject("Dude");
		friends.add(h);
////////////////////////
		
		
		String[] values = null;
		for (int i = 0; i < friends.size(); i++) {
			values[i] = friends.get(i).getUsername();
		}
		

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				null, R.layout.friendrow, values);

		// Assign adapter to ListView
		listOfFriends.setAdapter(adapter);
	}
}