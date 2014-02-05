package com.pingu.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pingu.actionsAndObjects.FriendObject;
import com.pingu.actionsAndObjects.PingHelper;
import com.zeng.pingu_android.R;

@SuppressLint("ShowToast")
public class AddFriend extends Activity {
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
	}
	
	public void addFriend(String username) {
		if (username!=null && username!="") {
			 PingHelper ph = new PingHelper(getApplicationContext());
			    FriendObject fr = new FriendObject(username);
			    ph.addUser(fr);
		}
		Toast.makeText(this, "Friend added", Toast.LENGTH_LONG).show();
	}
	
}
