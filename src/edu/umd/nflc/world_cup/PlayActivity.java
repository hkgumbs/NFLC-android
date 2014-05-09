package edu.umd.nflc.world_cup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.ProgressDialog;
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
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends ActionBarActivity implements OnPageChangeListener, OnNavigationListener {

	private static final String[] TYPES = new String[] { "Lyrics", "Translation", "Transliteration" };

	private static boolean downloadResult = false;
	private static boolean favoriteResult = false;

	private static String[] songNames;
	private static String[] songSources;
	private static String[] songLyrics;
	private static String[] songTranslations;
	private static String[] songTransliterations;
	private static int[] songIds;
	private static int[] teamIds;
	private static int[] iconIds;
	private static int numSongs;
	private static int current;
	private static int type;

	private static ActionBar actionBar;
	private static ActionBarAdapter actionBarAdapter;
	private static ChantPlayer chants;
	private static PagerAdapter pagerAdapter;
	private static ViewPager pager;

	private static Lookup lookup;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_play);

		iconIds = getIntent().getIntArrayExtra("iconIds");
		teamIds = getIntent().getIntArrayExtra("teamIds");
		songIds = getIntent().getIntArrayExtra("songIds");
		current = getIntent().getExtras().getInt("current");
		numSongs = songIds.length;
		type = b == null ? 0 : b.getInt("type");

		lookup = new Lookup(this);

		songNames = new String[songIds.length];
		songSources = new String[songIds.length];
		songLyrics = new String[songIds.length];
		songTranslations = new String[songIds.length];
		songTransliterations = new String[songIds.length];

		for (int i = 0; i < songIds.length; i++) {
			songNames[i] = lookup.getSong(teamIds[i], songIds[i]);
			songSources[i] = lookup.getSource(teamIds[i], songIds[i]);
			songLyrics[i] = lookup.getLyrics(teamIds[i], songIds[i]);
			songTranslations[i] = lookup.getTranslation(teamIds[i], songIds[i]);
			songTransliterations[i] = lookup.getTransliterations(teamIds[i], songIds[i]);
		}

		actionBarAdapter = new ActionBarAdapter();
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(iconIds[current]);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setListNavigationCallbacks(actionBarAdapter, this);
		actionBar.setSubtitle(TYPES[type]);

		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		chants = new ChantPlayer(songSources);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(this);
		pager.setCurrentItem(current);
	}

	@Override
	public void onStop() {
		super.onStop();
		chants.stop();
		chants.release();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("type", type);
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

		String key = teamIds[current] + "," + songIds[current];
		if (getSharedPreferences("favorited", Context.MODE_PRIVATE).contains(key)) {
			getSharedPreferences("favorited", Context.MODE_PRIVATE).edit().putBoolean(key, true).commit();
			menu.findItem(R.id.favorite).setIcon(R.drawable.icon_fav_light);
		} else {
			getSharedPreferences("favorited", Context.MODE_PRIVATE).edit().remove(key).commit();
			menu.findItem(R.id.favorite).setIcon(R.drawable.icon_fav_dark);
		}

		if (lookup.isDownloaded(teamIds[current], songIds[current]))
			menu.findItem(R.id.download).setIcon(R.drawable.icon_delete);
		else
			menu.findItem(R.id.download).setIcon(R.drawable.icon_download);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;

		case R.id.favorite:
			String key = teamIds[current] + "," + songIds[current];
			boolean favorite = getSharedPreferences("favorited", Context.MODE_PRIVATE).contains(key);

			if (favorite) {
				getSharedPreferences("favorited", Context.MODE_PRIVATE).edit().remove(key).commit();
				item.setIcon(R.drawable.icon_fav_dark);

			} else {
				getSharedPreferences("favorited", Context.MODE_PRIVATE).edit().putBoolean(key, true).commit();
				item.setIcon(R.drawable.icon_fav_light);
			}

			Toast.makeText(this,
					"This chant has been " + (favorite ? "removed from" : "added to") + " your favorites!",
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.download:
			new AsyncTask<Void, Void, Boolean>() {

				boolean download;
				ProgressDialog progress;

				@Override
				public void onPreExecute() {
					download = lookup.isDownloaded(teamIds[current], songIds[current]);
					progress = new ProgressDialog(PlayActivity.this);
					progress.setMessage(download ? "Deleting..." : "Downloading...");
					progress.setCancelable(false);
					progress.show();
				}

				@Override
				protected Boolean doInBackground(Void... params) {
					return download ? lookup.delete(teamIds[current], songIds[current]) : lookup.download(
							teamIds[current], songIds[current]);
				}

				@Override
				public void onPostExecute(Boolean result) {
					String message;
					if (!result) // error case
						message = (download ? "Deletion" : "Download") + " failed! Try again later.";

					else {
						item.setIcon(download ? R.drawable.icon_download : R.drawable.icon_delete);
						message = "This chant has been " + (download ? "deleted from" : "downloaded to")
								+ " your device!";
					}

					progress.dismiss();
					Toast.makeText(PlayActivity.this, message, Toast.LENGTH_SHORT).show();
				}

			}.execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		type = itemPosition;

		// TODO not sure if this is working
		pagerAdapter.notifyDataSetChanged();

		return true;
	}

	@Override
	public void onPageSelected(int position) {
		chants.stop();
		current = position;
		actionBarAdapter.notifyDataSetChanged();
		getSupportActionBar().setIcon(iconIds[current]);
		supportInvalidateOptionsMenu();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	private class ActionBarAdapter extends ArrayAdapter<String> {

		public ActionBarAdapter() {
			super(PlayActivity.this, android.R.layout.simple_spinner_dropdown_item, TYPES);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item_complex, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText(songNames[current]);
			holder.subtitle.setText(TYPES[position]);

			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView subtitle;
		}

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

					try {
						String source = songLyrics[position];
						Reader reader;

						if (lookup.isDownloaded(teamIds[position], songIds[position]))
							reader = new FileReader(source);
						else
							reader = new InputStreamReader(new URL(source).openStream());

						BufferedReader in = new BufferedReader(reader);
						StringBuilder result = new StringBuilder();
						String line;
						while ((line = in.readLine()) != null)
							result.append(line + "\n");

						in.close();
						return result.toString();

					} catch (MalformedURLException e) {
						return null;
					} catch (IOException e) {
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
					return lookup.getSize(teamIds[position], songIds[position]);
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
			int position = (Integer) v.getTag();
			pager.setCurrentItem(position);
		}

	}

}
