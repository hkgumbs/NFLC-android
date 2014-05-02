package edu.umd.nflc.world_cup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, View.OnClickListener {

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
		purchased = getPreferences(Context.MODE_PRIVATE).getBoolean("purchased_" + teamId, false);
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
					.setPositiveButton("Buy", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							if (getPreferences(Context.MODE_PRIVATE).edit().putBoolean("purchased_" + teamId, true)
									.commit()) {

								// TODO payment processing stuff

								finish();
								startActivity(getIntent());

							} else
								Toast.makeText(TeamActivity.this, "Something went wrong! Try again later.",
										Toast.LENGTH_LONG).show();
						}

					}).setNegativeButton(android.R.string.cancel, null).create();
		}
		setContentView(root);

		// TODO change from 0
		songNames = lookup.getAllSongs(teamId);

		// TODO change from 0
		songSources = lookup.getAllSources(teamId);

		ListView content = (ListView) findViewById(R.id.list);
		chants = ChantPlayer.get(songSources);
		int songLimit = purchased ? songNames.length : 3;
		content.setAdapter(new PlaylistAdapter(this, songNames, chants, songLimit));
		content.setOnItemClickListener(this);

		ActionBar ab = getSupportActionBar();
		ab.setTitle(teamName);
		ab.setIcon(iconId);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.download:
			if (!purchased)
				dialog.show();
			else {
				// TODO toggle save
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
