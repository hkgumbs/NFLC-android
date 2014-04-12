package edu.umd.nflc.world_cup;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener {

	private String[] pageTitles;
	private int currentPage;

	private DrawerLayout drawer;
	private ListView list;
	private ActionBarDrawerToggle toggle;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_main);

		pageTitles = getResources().getStringArray(R.array.pages);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		list = (ListView) findViewById(R.id.left_drawer);
		list.setAdapter(new MenuAdapter());
		list.setOnItemClickListener(this);

		toggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_drawer, R.string.menu_opened,
				R.string.menu_closed) {

			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(pageTitles[currentPage]);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(getString(R.string.app_name));
			}
		};

		drawer.setDrawerListener(toggle);
		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		toggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		toggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (toggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	// Swaps fragments in the main content view
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (position != currentPage) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragment = new Fragment();

			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

			// Bold current item in list & unbold brevious
			((TextView) list.getChildAt(position)).setTypeface(null, Typeface.BOLD);
			((TextView) list.getChildAt(currentPage)).setTypeface(null, Typeface.NORMAL);
			currentPage = position;

			// Highlight the selected item, update the title, and close the
			// drawer
			list.setItemChecked(position, true);
			setTitle(pageTitles[position]);
		}

		drawer.closeDrawer(list);
	}

	// DEBUG
	// private void toast(String s) {
	// Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	// }

	private class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return pageTitles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			TextView row;
			if (convertView == null)
				row = (TextView) getLayoutInflater().inflate(R.layout.list_item, null);
			else
				row = (TextView) convertView;

			if (position == currentPage)
				row.setTypeface(null, Typeface.BOLD);

			row.setText(pageTitles[position]);
			return row;
		}

	}

}
