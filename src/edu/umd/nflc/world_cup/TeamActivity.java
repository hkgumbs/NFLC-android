package edu.umd.nflc.world_cup;

import java.io.IOException;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, OnClickListener {

	String[] chantTitles;
	String title;
	int iconId;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		// might change this to master-detail layout later
		setContentView(R.layout.fragment_list);

		chantTitles = getResources().getStringArray(R.array.test_chants);

		ListView content = (ListView) findViewById(R.id.content);
		content.setAdapter(new PlaylistAdapter(getLayoutInflater(), chantTitles, this));
		content.setOnItemClickListener(this);

		title = getIntent().getExtras().getString("title");
		iconId = getIntent().getExtras().getInt("iconId");

		ActionBar ab = getSupportActionBar();
		ab.setTitle(title);
		ab.setIcon(iconId);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
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
		i.putExtra("title", chantTitles[position]);
		i.putExtra("iconId", iconId);
		startActivity(i);
	}

	@Override
	public void onClick(final View v) {
		// play button listener
		try {
			MediaPlayer player = new MediaPlayer();
			player.setDataSource("http://storage.googleapis.com/testbucket1111/samples/sample1.wav");
			player.prepare();
			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub

				}
			});
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!player.isPlaying()) {
			player.start();
			((ImageView) v).setImageResource(R.drawable.icon_stop);
		} else {
			player.stop();
			((ImageView) v).setImageResource(R.drawable.icon_play);
		}

	}
}
