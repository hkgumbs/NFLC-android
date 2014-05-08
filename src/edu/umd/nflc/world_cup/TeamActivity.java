package edu.umd.nflc.world_cup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, View.OnClickListener,
		DialogInterface.OnClickListener {

	String[] songNames;
	String[] songSources;
	String teamName;
	int teamId;
	int iconId;

	boolean purchased;

	ChantPlayer chants;
	AlertDialog dialog;

	Lookup lookup;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		lookup = new Lookup(this);

		teamName = getIntent().getExtras().getString("teamName");
		teamId = getIntent().getExtras().getInt("teamId");
		iconId = getIntent().getExtras().getInt("iconId");

		ViewGroup root = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_list, null);

		// show buy button if not bought
		purchased = getSharedPreferences("purchased", Context.MODE_PRIVATE).contains(Integer.toString(teamId));
		if (!purchased) {
			String price = "Buy now for $1.99"; // TODO
			View container = getLayoutInflater().inflate(R.layout.button_buy, root);
			Button buy = ((Button) container.findViewById(R.id.buy));
			buy.setText(price);
			buy.setOnClickListener(this);
			addContentView(container, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));

			Spanned description = Html.fromHtml("Buying this country pack will give you access to <br />"
					+ "&#8226; All of this country's chants<br />&#8226; Lyrics for each chant<br />"
					+ "&#8226; Option for offline access<br />&#8226; Translations and transliterations");
			dialog = new AlertDialog.Builder(this).setTitle(price).setMessage(description)
					.setPositiveButton("Buy", this).setNegativeButton(android.R.string.cancel, null).create();
		}
		setContentView(root);

		songNames = lookup.getAllSongs(teamId);
		songSources = lookup.getAllSources(teamId);

		ListView content = (ListView) findViewById(R.id.list);
		chants = ChantPlayer.get(songSources);
		int songLimit = purchased ? songNames.length : 3;
		content.setAdapter(new PlaylistAdapter(this, songNames, chants, songLimit));
		content.setOnItemClickListener(this);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(teamName);
		actionBar.setIcon(iconId);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (isFinishing())
			chants.release();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.team, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (lookup.isDownloaded(teamId))
			menu.findItem(R.id.download).setIcon(R.drawable.icon_delete);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.download:
			if (!purchased)
				dialog.show();

			else {
				new AsyncTask<Void, Void, Boolean>() {

					boolean download;
					ProgressDialog progress;

					@Override
					public void onPreExecute() {
						download = lookup.isDownloaded(teamId);
						progress = new ProgressDialog(TeamActivity.this);
						progress.setMessage(download ? "Deleting..." : "Downloading...");
						progress.show();
					}

					@Override
					protected Boolean doInBackground(Void... params) {
						// return download ? lookup.delete(teamId) :
						// lookup.download(teamId);
						return true;
					}

					@Override
					public void onPostExecute(Boolean result) {
						String message;
						if (!result) // error case
							message = (download ? "Deletion" : "Download") + " failed! Try again later.";

						else {
							item.setIcon(download ? R.drawable.icon_download : R.drawable.icon_delete);
							message = "All of this team's chants have been "
									+ (download ? "deleted from" : "downloaded to") + " your device!";
						}

						progress.dismiss();
						Toast.makeText(TeamActivity.this, message, Toast.LENGTH_SHORT).show();
					}

				}.execute();

			}

			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		chants.stop();

		if (purchased) {
			Intent i = new Intent(this, PlayActivity.class);
			int size = songNames.length;

			i.putExtra("iconIds", toArray(iconId, size));
			i.putExtra("teamIds", toArray(teamId, size));
			i.putExtra("songIds", toArray(size));
			i.putExtra("songNames", songNames);
			i.putExtra("songSources", songSources);
			i.putExtra("songLyrics", lookup.getAllLyrics(teamId));
			i.putExtra("current", position);

			startActivity(i);

		} else
			dialog.show();

	}

	@Override
	public void onClick(final View v) {
		dialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		if (getSharedPreferences("purchased", Context.MODE_PRIVATE).edit().putBoolean(Integer.toString(teamId), true)
				.commit()) {

			// TODO payment processing stuff

			finish();
			startActivity(getIntent());

		} else
			Toast.makeText(TeamActivity.this, "Something went wrong! Try again later.", Toast.LENGTH_LONG).show();
	}

	private int[] toArray(int value, int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++)
			array[i] = value;
		return array;
	}

	private int[] toArray(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++)
			array[i] = i;
		return array;
	}

}
