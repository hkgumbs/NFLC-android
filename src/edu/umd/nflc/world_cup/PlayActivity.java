package edu.umd.nflc.world_cup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;

import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Toast;

public class PlayActivity extends ActionBarActivity implements OnPageChangeListener {

	private static String[] songNames;
	private static String[] songSources;
	private static String[] songLyrics;
	private static int[] songIds;
	private static int[] teamIds;
	private static int[] iconIds;
	private static int numSongs;
	private static int current;

	private static ChantPlayer chants;
	private static PagerAdapter adapter;
	private static ViewPager pager;

	Lookup lookup;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_play);

		lookup = new Lookup(this);

		iconIds = getIntent().getIntArrayExtra("iconIds");
		teamIds = getIntent().getIntArrayExtra("teamIds");
		songIds = getIntent().getIntArrayExtra("songIds");
		songNames = getIntent().getStringArrayExtra("songNames");
		songSources = getIntent().getStringArrayExtra("songSources");
		songLyrics = getIntent().getStringArrayExtra("songLyrics");
		current = getIntent().getExtras().getInt("current");
		numSongs = songNames.length;

		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(iconIds[current]);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		setTitle(songNames[current]);

		adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		chants = ChantPlayer.get(songSources);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(this);
		pager.setCurrentItem(current);
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
		inflater.inflate(R.menu.play, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		String key = "favorited_" + teamIds[current] + "," + songIds[current];
		if (getPreferences(Context.MODE_PRIVATE).contains(key))
			menu.findItem(R.id.favorite).setIcon(R.drawable.icon_fav_light);
		else
			menu.findItem(R.id.favorite).setIcon(R.drawable.icon_fav_dark);

		// TODO downloaded indicator

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		case R.id.favorite:
			String key = "favorited_" + teamIds[current] + "," + songIds[current];
			boolean favorite = getPreferences(Context.MODE_PRIVATE).contains(key);

			if (favorite) {
				getPreferences(Context.MODE_PRIVATE).edit().remove(key).commit();
				item.setIcon(R.drawable.icon_fav_dark);

			} else {
				getPreferences(Context.MODE_PRIVATE).edit().putBoolean(key, true).commit();
				item.setIcon(R.drawable.icon_fav_light);
			}

			Toast.makeText(this,
					"This chant has been " + (favorite ? "removed from" : "added to") + " your favorites!",
					Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onPageSelected(int position) {
		current = position;
		setTitle(songNames[current]);
		getSupportActionBar().setIcon(iconIds[current]);
		supportInvalidateOptionsMenu();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new ContentFragment();
			Bundle arg = new Bundle();
			arg.putInt("position", position);
			fragment.setArguments(arg);
			return fragment;
		}

		@Override
		public int getCount() {
			return numSongs;
		}
	}

	public static class ContentFragment extends Fragment implements OnClickListener {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			final View frame = inflater.inflate(R.layout.fragment_play, container, false);
			final int position = getArguments().getInt("position");

			// get lyrics
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {

					String source = songLyrics[position];

					try {
						URL u = new URL(source);
						HttpURLConnection c = (HttpURLConnection) u.openConnection();
						c.setRequestMethod("GET");
						c.connect();
						InputStream in = c.getInputStream();
						final ByteArrayOutputStream bo = new ByteArrayOutputStream();
						byte[] buffer = new byte[1024];
						in.read(buffer); // Read from Buffer.
						bo.write(buffer); // Write Into Buffer.
						String result = bo.toString();
						in.close();
						bo.close();
						return result;

					} catch (MalformedURLException e) {
						e.printStackTrace();
						return null;
					} catch (ProtocolException e) {
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}

				}

				@Override
				public void onPostExecute(String result) {
					TextView container = (TextView) frame.findViewById(R.id.lyrics);
					if (result == null)
						container.setText("Lyrics unavailable.");
					else
						container.setText(result);
				}

			}.execute();

			// get size
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					// TODO get size of song + lyric text size
					double size = 1.2345;
					DecimalFormat df = new DecimalFormat("#.##");
					return df.format(size) + " MB";
				}

				@Override
				public void onPostExecute(String result) {
					TextView container = (TextView) frame.findViewById(R.id.size);
					if (result == null)
						container.setText("Size unavailable.");
					else
						container.setText(result);
				}

			}.execute();

			View loading = frame.findViewById(R.id.loading);
			View error = frame.findViewById(R.id.error);
			View play = frame.findViewById(R.id.play);
			play.setTag(position);
			play.setOnClickListener(chants);
			chants.prepare(position, loading, play, error);

			View previous = frame.findViewById(R.id.previous);
			View next = frame.findViewById(R.id.next);
			previous.setTag(position - 1);
			next.setTag(position + 1);

			if (position == 0) {
				previous.setVisibility(View.INVISIBLE);
				previous.setEnabled(false);
			} else
				previous.setOnClickListener(this);

			if (position == numSongs - 1) {
				next.setVisibility(View.INVISIBLE);
				next.setEnabled(false);
			} else
				next.setOnClickListener(this);

			return frame;
		}

		@Override
		public void onClick(View v) {
			chants.stop();
			int position = (Integer) v.getTag();
			pager.setCurrentItem(position);
		}

	}

}
