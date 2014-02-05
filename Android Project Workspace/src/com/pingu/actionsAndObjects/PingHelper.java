package com.pingu.actionsAndObjects;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PingHelper extends SQLiteOpenHelper {
	// database name
	public static final String DATABASE_NAME = "pingManager";

	// table names
	public static final String TABLE_PING = "pings";
	public static final String TABLE_USER = "users";

	// common columns
	public static final String KEY_ID = "id";

	// users table columns
	public static final String KEY_USERNAME = "usernames";
	public static final String KEY_GROUP = "group";

	// ping table columns
	public static final String KEY_TIME_RECEIVED = "timeReceived";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_SENDER = "sender";
	public static final String KEY_CONTENT = "content";

	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLE_PING = "CREATE TABLE "
			+ TABLE_PING + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_SENDER
			+ " TEXT, " + KEY_LATITUDE + " INTEGER NOT NULL, " + KEY_LONGITUDE
			+ " INTEGER NOT NULL, " + KEY_TIME_RECEIVED + " TEXT, "
			+ KEY_CONTENT + " TEXT" + ")";

	private static final String CREATE_TABLE_USER = "CREATE TABLE "
			+ TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_USERNAME + " TEXT" + ")";

	public PingHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE_PING);
		database.execSQL(CREATE_TABLE_USER);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new IllegalStateException("Database upgrade not supported");
	}

	public long addPing(PingObject ping) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		String username = ping.getName();
		double latitude = ping.getLatlng().latitude;
		double longitude = ping.getLatlng().longitude;
		String date = ping.getTime();

		values.put(KEY_CONTENT, ping.getMessage());
		values.put(KEY_TIME_RECEIVED, date);
		values.put(KEY_SENDER, username);
		values.put(KEY_LATITUDE, latitude);
		values.put(KEY_LONGITUDE, longitude);

		long id = db.insert(TABLE_PING, null, values);

		return id;
	}

	public long addUser(FriendObject friend) throws FriendDoesNotExistException {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		String username = friend.getUsername();
		ArrayList<FriendObject> allFriends = this.getAllFriends();
		for (FriendObject fo : allFriends) {
			if (fo.getUsername().equals(username)) {
				return 0;
			}
		}
		values.put(KEY_USERNAME, username);
	
		long id = db.insert(TABLE_USER, null, values);

		return id;

	}

	public class FriendDoesNotExistException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private FriendDoesNotExistException() {
		}; // doesn't do anything because we don't have a logger
	}

	public class PingDoesNotExistException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private PingDoesNotExistException() {
		}; // doesn't do anything because we don't have a logger
	}

	// Search database for a friend by username
	// Returns friendobject if friend exists
	// otherwise throws doesnotexistexception
	public FriendObject getFriend(String username)
			throws FriendDoesNotExistException {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_USERNAME },
				KEY_USERNAME + "=?", new String[] { username }, null, null,
				null);
		if (!cursor.moveToFirst()) {
			throw new FriendDoesNotExistException();
		}
		return new FriendObject(cursor.getString(0));
	}
	
	public ArrayList<FriendObject> getAllFriends()
			throws FriendDoesNotExistException {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_USER;
		Cursor cursor = db.rawQuery(selectQuery, null);
		ArrayList<FriendObject> friends = new ArrayList<FriendObject>();
		if (cursor.moveToFirst()){
			do{
				FriendObject friend = new FriendObject(cursor.getString(1));
				friends.add(friend);
			}
			while(cursor.moveToNext());
		}
		return friends;
	}

	public PingObject getPingBySender(String sender)
			throws PingDoesNotExistException {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_PING, new String[] { KEY_CONTENT,
				KEY_TIME_RECEIVED, KEY_SENDER, KEY_LATITUDE, KEY_LONGITUDE },
				KEY_SENDER + "=?", new String[] { sender }, null, null, null);
		if (cursor.moveToFirst()) {
			LatLng location = new LatLng(
					Double.parseDouble(cursor.getString(3)),
					Double.parseDouble(cursor.getString(4)));
			return new PingObject(cursor.getString(1), cursor.getString(2),
					location, cursor.getString(0));
		} else {
			throw new PingDoesNotExistException();
		}
	}

	public void deletePing(String sender) {
		SQLiteDatabase db = this.getWritableDatabase();
		int deleted = db.delete(TABLE_PING, KEY_SENDER + " =?",
				new String[] { sender });
		Log.d("Deleting pings...", deleted + " pings deleted");
		db.close();
	}

	public void deleteUser(String sender) {
		SQLiteDatabase db = this.getWritableDatabase();
		int deleted = db.delete(TABLE_USER, KEY_USERNAME + " =?",
				new String[] { sender });
		Log.d("Deleting users...", deleted + " pings deleted");
		db.close();
	}

}
