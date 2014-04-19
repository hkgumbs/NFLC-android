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
import android.widget.Toast;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, OnClickListener {

	String[] chantTitles;
	String title;
	int iconId;

	MediaPlayer player;
	ImageView button;
	boolean playing;

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

		if (!playing)
			startAudio(v);

		else if (!stopAudio(v))
			startAudio(v);

	}

	private boolean startAudio(final View v) {
		try {
			player = new MediaPlayer();
			player.setDataSource((String) v.getTag());
			player.prepare();
			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stopAudio(v);
				}
			});

			button = (ImageView) v;
			button.setImageResource(R.drawable.icon_stop);
			playing = true;

		} catch (IllegalStateException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean stopAudio(View v) {
		player.stop();
		player.release();
		player = null;
		button.setImageResource(R.drawable.icon_play);
		playing = false;

		boolean result = v.equals(button);
		button = null;

		return result;
	}
}
