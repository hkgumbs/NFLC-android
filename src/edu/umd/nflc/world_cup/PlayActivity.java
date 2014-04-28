package edu.umd.nflc.world_cup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PlayActivity extends ActionBarActivity implements OnPageChangeListener {

	private String[] songNames;
	private String[] songSources;

	private static int numSongs;
	private static int teamId;

	private static ChantPlayer chants;
	private static PagerAdapter adapter;
	private static ViewPager pager;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_play);

		teamId = getIntent().getExtras().getInt("teamId");
		int iconId = getIntent().getExtras().getInt("iconId");
		int songId = getIntent().getExtras().getInt("songId");

		// TODO get song list from Bas' class
		songNames = getResources().getStringArray(R.array.test_chants);
//		LookupURL lookup = new LookupURL();
//		songNames = lookup.getSongList(teamId).toArray(new String[]{});

		// TODO get song sources from Bas' class
		songSources = getResources().getStringArray(R.array.test_sources);
//		songSources = lookup.getAllAudioURLs(teamId).toArray(new String[]{});
//		
		numSongs = songNames.length;

		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(iconId);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		chants = new ChantPlayer(songSources);
		adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(this);
		pager.setCurrentItem(songId);
	}

	@Override
	public void onStop() {
		super.onStop();
		chants.release();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		setTitle(songNames[position]);
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new ContentFragment();
			Bundle arg = new Bundle();
			arg.putInt("songId", position);
			fragment.setArguments(arg);
			return fragment;
		}

		@Override
		public int getCount() {
			return songNames.length;
		}
	}

	public static class ContentFragment extends Fragment implements OnClickListener {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			int songId = getArguments().getInt("songId");

			View frame = inflater.inflate(R.layout.fragment_play, container, false);
			ChantPlayer.getLyrics(teamId, songId, (TextView) frame.findViewById(R.id.lyrics));
			ChantPlayer.getSongSize(teamId, songId, (TextView) frame.findViewById(R.id.size));

			View loading = frame.findViewById(R.id.loading);
			View error = frame.findViewById(R.id.error);
			View play = frame.findViewById(R.id.play);
			play.setTag(songId);
			play.setOnClickListener(chants);
			chants.prepare(songId, loading, play, error);

			View previous = frame.findViewById(R.id.previous);
			View next = frame.findViewById(R.id.next);
			previous.setTag(songId - 1);
			next.setTag(songId + 1);

			if (songId == 0) {
				previous.setVisibility(View.INVISIBLE);
				previous.setEnabled(false);
			} else
				previous.setOnClickListener(this);

			if (songId == numSongs - 1) {
				next.setVisibility(View.INVISIBLE);
				next.setEnabled(false);
			} else
				next.setOnClickListener(this);

			return frame;
		}

		@Override
		public void onClick(View v) {
			chants.stop();
			int page = (Integer) v.getTag();
			pager.setCurrentItem(page);
		}

	}

}
