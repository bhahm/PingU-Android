package com.pingu.actionsAndObjects;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pingu.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.pingu.main.MainActivity;
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
        
        
        
		ListView listviewFriends = (ListView) getView().findViewById(
				R.id.friendslist);

		PingHelper PH = new PingHelper(null);
		List<FriendObject> friends = null;
		try {
			friends = PH.getAllFriends();
		} catch (FriendDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
////////////////////////////////////////////////////////////////////////////////////////////////
		FriendObject f = new FriendObject("Mitchell Vitez");
		friends.add(f);
		FriendObject g = new FriendObject("TestFriendWithARelativelyLongUsername");
		friends.add(g);
		FriendObject h = new FriendObject("Dude");
		friends.add(h);
////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		String[] values = {"test", "test2", "test3"};
		for (int i = 0; i < friends.size(); i++) {
			values[i] = friends.get(i).getUsername();
		}
		

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_list_item_1, values);

		// Assign adapter to ListView
		listviewFriends.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private Context getApplicationContext() {
		return MainActivity.getAppContext();
	}
}