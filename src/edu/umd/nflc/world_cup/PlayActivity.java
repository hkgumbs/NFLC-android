package edu.umd.nflc.world_cup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

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

		// http://stackoverflow.com/questions/17141829/android-read-text-file-from-internet
		new Thread() {
			@Override
			public void run() {
				String path = "http://storage.googleapis.com/testbucket1111/samples/sample1.txt";
				URL u = null;
				try {
					u = new URL(path);
					HttpURLConnection c = (HttpURLConnection) u.openConnection();
					c.setRequestMethod("GET");
					c.connect();
					InputStream in = c.getInputStream();
					final ByteArrayOutputStream bo = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					in.read(buffer); // Read from Buffer.
					bo.write(buffer); // Write Into Buffer.

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TextView text = (TextView) findViewById(R.id.lyric);
							text.setText(bo.toString());
							try {
								bo.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}.start();
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
