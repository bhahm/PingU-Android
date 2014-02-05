package com.pingu.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
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
public class Friends extends Activity {

	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
        setContentView(R.layout.friends);
        
        PingHelper ph = new PingHelper(getApplicationContext());

        ListView lv = (ListView) findViewById(R.id.friendslist);
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
        }
       		
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                listOfFriends );

        lv.setAdapter(arrayAdapter); 
   }
}