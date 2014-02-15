package com.pingu.fragments;

import com.pingu.actionsAndObjects.PingObject;
import com.pingu.main.MainActivity;
import com.zeng.pingu_android.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class NotificationActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void createNotification(PingObject ping) {
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		Notification noti = new Notification.Builder(this)
				.setContentTitle("New ping from " + ping.getName())
				.setContentText(ping.getMessage())
				.setSmallIcon(R.drawable.pingiconfaded)
				.setContentIntent(pIntent)
				.addAction(R.drawable.pingicon, "Click", pIntent).build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}
}
