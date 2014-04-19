package edu.umd.nflc.world_cup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, OnClickListener {

	String[] chantTitles;
	String title;
	int iconId;
	final ChantPlayer player = new ChantPlayer();

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
		// TODO make modular
		player.invoke(0, 0, (ImageButton) v);
	}

}
