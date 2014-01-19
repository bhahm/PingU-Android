package me.vitez.pingu_android;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zeng.pingu_android.Maps_and_Pinging;

public class DrawerItemClickListener extends FragmentActivity implements ListView.OnItemClickListener {
	public DrawerItemClickListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		// Toast.makeText(MainActivity.this, ((TextView) view).getText(), ///
		// THIS IS BROKE. WTF IS THIS SHIT?
		// Toast.LENGTH_LONG).show(); /// <-----
		// drawerLayout.closeDrawer(drawerListView); /// <-----
		selectItem(position);

	}

	public int d = Maps_and_Pinging.CONTEXT_INCLUDE_CODE;

	private void selectItem(int position) {

		if (position == 0) {
			Log.d("position 0 tapped;", "position 0 tapped;");
		} else if (position == 1) {
			Log.d("position 1 tapped;", "position 1 tapped;");
		}
	}

}
