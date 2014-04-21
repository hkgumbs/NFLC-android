package edu.umd.nflc.world_cup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, OnClickListener {

	String[] songNames;
	String teamName;
	int teamId;
	int iconId;

	final ChantPlayer player = new ChantPlayer();

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		ViewGroup root = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_list, null);

		// show buy button if not bought
		SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
		if (!sp.getBoolean(Integer.toString(teamId), false)) {
			View button = getLayoutInflater().inflate(R.layout.button_buy, root);
			addContentView(button, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			button.setOnClickListener(this);
		}
		setContentView(root);

		// TODO get song list from Bas' class
		songNames = getResources().getStringArray(R.array.test_chants);

		ListView content = (ListView) findViewById(R.id.list);
		content.setAdapter(new PlaylistAdapter(getLayoutInflater(), songNames, this));
		content.setOnItemClickListener(this);

		teamName = getIntent().getExtras().getString("teamName");
		teamId = getIntent().getExtras().getInt("teamID");
		iconId = getIntent().getExtras().getInt("iconId");

		ActionBar ab = getSupportActionBar();
		ab.setTitle(teamName);
		ab.setIcon(iconId);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent i = new Intent(this, PlayActivity.class);
		i.putExtra("songName", songNames[position]);
		i.putExtra("songId", position);
		i.putExtra("teamId", teamId);
		i.putExtra("iconId", iconId);
		startActivity(i);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.play:
			player.toggleSong(teamId, (Integer) v.getTag(), (ImageButton) v);
			break;

		case R.id.buy:
			// TODO payment processing stuff
			break;
		}

	}

}
