package edu.umd.nflc.world_cup;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener {

	// Nav drawer
	private String[] pageTitles;
	private TypedArray pageIcons;
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
		pageTitles = getResources().getStringArray(R.array.page_titles);
		pageIcons = getResources().obtainTypedArray(R.array.page_icons);

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

		// Set current page to display bold in drawer
		drawerList.post(new Runnable() {
			@Override
			public void run() {
				((TextView) drawerList.getChildAt(currentPage)).setTypeface(null, Typeface.BOLD);
			}
		});

		/* COUNTRY LIST */
		pageFragments = new Fragment[pageTitles.length];
		for (int i = 0; i < pageTitles.length; i++) {
			Fragment fragment = new ContentFragment();
			Bundle arg = new Bundle();
			arg.putInt("page", i);
			fragment.setArguments(arg);
			pageFragments[i] = fragment;
		}

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, pageFragments[currentPage]).commit();
	}

	@Override
	public void onStop() {
		super.onStop();
		pageIcons.recycle();
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

	public static class ContentFragment extends Fragment implements ListView.OnItemClickListener {

		int page;
		private String[] teamNames;
		private TypedArray teamFlags;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			page = getArguments().getInt("page");

			teamNames = getResources().getStringArray(R.array.team_names);
			teamFlags = getResources().obtainTypedArray(R.array.team_flags);

			// TODO filter array based on page

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			content.setAdapter(new TypedArrayAdapter(inflater, teamNames, teamFlags));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getActivity(), TeamActivity.class);
			i.putExtra("teamName", teamNames[position]);
			i.putExtra("teamId", position);
			i.putExtra("iconId", teamFlags.getResourceId(position, 0));
			startActivity(i);
		}

	}

}
