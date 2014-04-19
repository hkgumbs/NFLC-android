package edu.umd.nflc.world_cup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener {

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
		Drawable playIcon = getResources().getDrawable(R.drawable.icon_play);
		content.setAdapter(new PlaylistAdapter(getLayoutInflater(), chantTitles, playIcon));
		content.setOnItemClickListener(this);

		title = getIntent().getExtras().getString("title");
		iconId = getIntent().getExtras().getInt("iconId");

		ActionBar ab = getSupportActionBar();
		ab.setTitle(title);
		ab.setIcon(iconId);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent i = new Intent(this, PlayActivity.class);
		i.putExtra("title", chantTitles[position]);
		i.putExtra("iconId", iconId);
		startActivity(i);
	}

}
