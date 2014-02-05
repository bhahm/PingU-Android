package com.pingu.activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pingu.actionsAndObjects.FriendObject;
import com.pingu.actionsAndObjects.PingHelper;
import com.pingu.actionsAndObjects.Useful;
import com.zeng.pingu_android.R;

/**
 * Add new friends by username
 * @author Mitchell Vitez
 *
 */
@SuppressLint("ShowToast")
public class AddFriend extends Activity {
	private ParseObject pingInParse;
	public int isFriend;

	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
        setContentView(R.layout.addfriend);
        
        final EditText et = (EditText) findViewById(R.id.friendUsernameText);
        
        Button addFriendB = (Button) findViewById(R.id.addFriendButton);
        addFriendB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String txt = et.getText().toString();
            	addFriend(txt);
            }
        });
        
        Button friendCheck = (Button) findViewById(R.id.addFriendChecker);
        friendCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
					checkFriendStatus();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
	}
	
	public void addFriend(String username) {
		if (username!=null && username!="") {
			pingInParse = new ParseObject("Friend");
			pingInParse.put("from", Useful.getUsername());
			pingInParse.put("to", username);
			pingInParse.put("status", 0);
			pingInParse.saveInBackground();
			Toast.makeText(this, "Friend request sent", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "Null string", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void checkFriendStatus() throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friend");
		List<ParseObject> results = query.find();
		for (ParseObject parseObj : results) {
			if (! parseObj.get("to").equals(parseObj.get("from"))) {
				if (parseObj.get("to").equals(Useful.getUsername())) {
					if (! parseObj.get("status").equals(1)) {
						addFriendDialog((String) parseObj.get("from"));
						parseObj.put("status", isFriend);
					}
				}
				if (parseObj.get("from").equals(Useful.getUsername())) {
					if (parseObj.get("status").equals(1)) {
						addToFriendDatabase((String) parseObj.get("to"));
						parseObj.deleteEventually();
					}
				}
			}
		}
	}
	
	public void addToFriendDatabase(String username) {
		PingHelper ph = new PingHelper(getApplicationContext());
	    FriendObject fr = new FriendObject(username);
	    ph.addUser(fr);
	}
	
	public void addFriendDialog(final String from) {
		isFriend = 0;
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            Log.i("y", "yes");
		            addToFriendDatabase(from);
		            isFriend = 1;
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		        	Log.i("n", "NO");
		        	isFriend = -1;
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Add \"" + from +"\" as friend?").setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
	}
	
}
