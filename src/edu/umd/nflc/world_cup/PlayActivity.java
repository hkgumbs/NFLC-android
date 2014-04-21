package edu.umd.nflc.world_cup;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayActivity extends ActionBarActivity implements OnClickListener {

	String songName;
	int songId;
	int teamId;
	int iconId;

	final ChantPlayer player = new ChantPlayer();

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.fragment_play);

		songName = getIntent().getExtras().getString("songName");
		songId = getIntent().getExtras().getInt("songId");
		teamId = getIntent().getExtras().getInt("teamId");
		iconId = getIntent().getExtras().getInt("iconId");

		ActionBar ab = getSupportActionBar();
		ab.setTitle(songName);
		ab.setIcon(iconId);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		player.getLyrics(teamId, songId, (TextView) findViewById(R.id.lyrics));
		player.getSongSize(teamId, songId, (TextView) findViewById(R.id.size));

		findViewById(R.id.previous).setOnClickListener(this);
		findViewById(R.id.play).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
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
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.previous:
			break;

		case R.id.play:
			player.toggleSong(teamId, songId, (ImageButton) v);
			break;

		case R.id.next:
			break;
		}

	}

}
