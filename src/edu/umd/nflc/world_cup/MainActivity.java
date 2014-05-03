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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener {

	// Nav drawer
	private String[] pageTitles;
	private int[] pageIcons;
	private int currentPage;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	// Country list fragments
	private Fragment[] pageFragments;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_main);

		/* NAV DRAWER INIT */
		currentPage = b == null ? 0 : b.getInt("page");
		pageTitles = new String[] { getString(R.string.browse), getString(R.string.favorites),
				getString(R.string.purcheses) };
		pageIcons = new int[] { R.drawable.icon_browse, R.drawable.icon_fav_dark, R.drawable.icon_buy_dark };

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		drawerList.setAdapter(new TypedArrayAdapter(getLayoutInflater(), pageTitles, pageIcons));
		drawerList.setOnItemClickListener(this);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.menu_opened,
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

		drawerLayout.setDrawerListener(drawerToggle);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		setTitle(pageTitles[currentPage]);

		// Set current page to display bold in drawer
		drawerList.post(new Runnable() {
			@Override
			public void run() {
				((TextView) drawerList.getChildAt(currentPage)).setTypeface(null, Typeface.BOLD);
			}
		});

		/* COUNTRY LIST */
		pageFragments = new Fragment[pageTitles.length];
		for (int i = 0; i < pageTitles.length; i++)
			pageFragments[i] = MainFragments.get(pageTitles[i]);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, pageFragments[currentPage]).commit();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("page", currentPage);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item))
			return true;
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (position != currentPage) {

			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, pageFragments[position]).commit();

			// Bold current item in list & unbold previous
			((TextView) drawerList.getChildAt(position)).setTypeface(null, Typeface.BOLD);
			((TextView) drawerList.getChildAt(currentPage)).setTypeface(null, Typeface.NORMAL);
			currentPage = position;

			// Highlight the selected item, update the title, and close the
			// drawer
			drawerList.setItemChecked(position, true);
			setTitle(pageTitles[position]);
		}

		drawerLayout.closeDrawer(drawerList);
	}

}
