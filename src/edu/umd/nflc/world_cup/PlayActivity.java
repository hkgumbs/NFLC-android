package edu.umd.nflc.world_cup;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class PlayActivity extends ActionBarActivity {

	String title;
	int iconId;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.fragment_play);

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

}
