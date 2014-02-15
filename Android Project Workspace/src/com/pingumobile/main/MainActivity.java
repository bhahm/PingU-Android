package com.pingumobile.main;

import java.util.ArrayList;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import com.zeng.pingu_android.R;
import com.bugsense.trace.BugSenseHandler;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.pingumobile.actionsAndObjects.PingActions;
import com.pingumobile.actionsAndObjects.Useful;
import com.pingumobile.fragments.AboutFragment;
import com.pingumobile.fragments.AddFriendFragment;
import com.pingumobile.fragments.DebugFragment;
import com.pingumobile.fragments.FriendsFragment;
import com.pingumobile.fragments.HomeFragment;
import com.pingumobile.fragments.NoNetworkFragment;
import com.pingumobile.fragments.PrefsFragment;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

//@author Steven Zeng, Mitchell Vitez
//Main activity of the entire app that instantiates the navigation bar and displays fragments accordingly.
public class MainActivity extends FragmentActivity {

	TextView textView;
	public static Context c;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String TAG = "MainActivity";
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;
	private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		c = this;

		setContentView(R.layout.activity_main);

		PushService.setDefaultPushCallback(c, MainActivity.class);
		ParseAnalytics.trackAppOpened(getIntent());
		ParseInstallation.getCurrentInstallation().saveInBackground();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(MainActivity.this);
		String username = prefs.getString("username", "DEFAULT_USERNAME");
		Useful.setUsername(username);
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
				R.string.app_name // nav drawer close - description for
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				closeOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				openOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		if (!isNetworkOnline()) {
			Log.v(TAG, "no network");
			fragment = new NoNetworkFragment();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}
	}

	public void onResume() {
		super.onResume();
		if (!PrefsFragment.isUsernameSet()) {
			showSetUserDialog();
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		int id = item.getItemId();
		if (id == R.id.refresh_pings) {
				doRefreshPings();
				Intent intent = getIntent();
				finish();
				startActivity(intent);
				doRefreshPings();
				return true;
		}
		else if (id == R.id.delete_ping) {
				PingActions.deleteMyPing();
				return true;
		}
		else {
				return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		if (!isNetworkOnline()) {
			Log.v(TAG, "no network");
			fragment = new NoNetworkFragment();
		} else {
			switch (position) {
			case 0:
				Fragment currentFrag = getFragmentManager().findFragmentById(
						R.id.map);

				if (currentFrag != null) {
					getFragmentManager().beginTransaction().remove(currentFrag)
							.commit();
				}
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new FriendsFragment();
				break;
			case 2:
				fragment = new AddFriendFragment();
				break;
			case 3:
				fragment = new PrefsFragment();
				break;
			case 4:
				fragment = new AboutFragment();
				break;
			case 5:
				fragment = new DebugFragment();
				break;

			default:
				break;
			}
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
		//

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void doRefreshPings() {
		HomeFragment.refreshMyPings();
		System.out.println("REFRESHED PINGS");
	}

	static public Context getAppContext() {
		return c;
	}

	public void showSetUserDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Set Username");
		alert.setMessage("You should set a username before using the application");

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				displayView(3);

			}

		});

		alert.show();
	}

	private boolean isNetworkOnline() {
		boolean status = false;
		boolean gps_enabled = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) this
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null
					&& netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null
						&& netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (status == true) {
			LocationManager lm = null;
			if (lm == null) {
				lm = (LocationManager) c
						.getSystemService(Context.LOCATION_SERVICE);
			}
			try {
				gps_enabled = lm
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
						|| lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
				Log.v(TAG, "gps status: " + gps_enabled);
			} catch (Exception ex) {
				Log.e(TAG, "error checking for gps, e");
			}

		}
		return status && gps_enabled;
	}

}