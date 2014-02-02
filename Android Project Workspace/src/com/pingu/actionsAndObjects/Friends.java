package com.pingu.actionsAndObjects;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
public class Friends extends Activity {

	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
        setContentView(R.layout.friends);

        ListView lv = (ListView) findViewById(R.id.friendslist);

        // Instanciating an array list (you don't need to do this, 
        // you already have yours).
        ArrayList<String> listOfFriends = new ArrayList<String>();
        listOfFriends.add("foo");
        listOfFriends.add("bar");

        // This is the array adapter, it takes the context of the activity as a 
        // first parameter, the type of list view as a second parameter and your 
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, 
                android.R.layout.simple_list_item_1,
                listOfFriends );

        lv.setAdapter(arrayAdapter); 
   }
}