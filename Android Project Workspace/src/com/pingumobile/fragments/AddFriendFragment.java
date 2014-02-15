package com.pingumobile.fragments;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pingumobile.actionsAndObjects.FriendObject;
import com.pingumobile.actionsAndObjects.PingHelper;
import com.pingumobile.actionsAndObjects.Useful;
import com.pingumobile.actionsAndObjects.PingHelper.FriendDoesNotExistException;
import com.zeng.pingu_android.R;

/**
 * Add new friends by username
 * @author Mitchell Vitez
 *
 */
@SuppressLint("ShowToast")
public class AddFriendFragment extends Fragment {
	private ParseObject pingInParse;
	public int isFriend;
	
	View v;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v = inflater.inflate(R.layout.addfriend, container, false);
		setUpFriendAdder();
		return v;
	}

	public void setUpFriendAdder(){;
        
        final EditText et = (EditText) v.findViewById(R.id.friendUsernameText);
        
        Button addFriendB = (Button) v.findViewById(R.id.addFriendButton);
        addFriendB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String txt = et.getText().toString();
            	addFriend(txt);
            }
        });
        
        onUpdateRequest();
        
	}
	
	public void onUpdateRequest() {
    	try {
			checkFriendStatus();
		} catch (ParseException e) {
			Log.w("AddFriend", "Bad friend status - Parse Exception");
			e.printStackTrace();
		} catch (FriendDoesNotExistException e) {
			Log.w("AddFriend", "Bad friend status - FriendDoesNotExist Exception");
			e.printStackTrace();
		}
    }
	
	public void addFriend(String username) {
		if (username!=null && username!="") {
			pingInParse = new ParseObject("Friend");
			pingInParse.put("from", Useful.getUsername());
			pingInParse.put("to", username);
			pingInParse.put("status", 0);
			pingInParse.saveInBackground();
			Toast.makeText(this.getActivity(), "Friend request sent", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this.getActivity(), "Null string", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void checkFriendStatus() throws ParseException, FriendDoesNotExistException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Friend");
		List<ParseObject> results = query.find();
		for (ParseObject parseObj : results) {
			if (! parseObj.get("to").equals(parseObj.get("from"))) {
				if (parseObj.get("to").equals(Useful.getUsername())) {
					if (! parseObj.get("status").equals(1)) {
							if (! alreadyAFriend((String) parseObj.get("from"))) {
								addFriendDialog((String) parseObj.get("from"));
								parseObj.put("status", 1);
								parseObj.saveEventually();
							}
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
	
	public boolean alreadyAFriend(String username) throws FriendDoesNotExistException {
		PingHelper ph = new PingHelper(v.getContext());
	    FriendObject fr = new FriendObject(username);
	    for (FriendObject f : ph.getAllFriends()) {
	    	if (f.equals(fr))
	    		return true;
	    }
	    return false;
	}
	
	public void addToFriendDatabase(String username) throws FriendDoesNotExistException {
		PingHelper ph = new PingHelper(v.getContext());
	    FriendObject fr = new FriendObject(username);
	    ph.addUser(fr);
	}
	
	public void addFriendDialog(final String from) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            try {
						addToFriendDatabase(from);
					} catch (FriendDoesNotExistException e) {
						Log.w("AddFriend", "FriendDoesNotExist Exception");
						e.printStackTrace();
					}
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
		        }
		    }
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setMessage("You received a friend request. Add \"" + from +"\" as friend?").setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
	}
	
}
