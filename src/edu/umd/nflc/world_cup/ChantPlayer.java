package edu.umd.nflc.world_cup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ChantPlayer implements OnClickListener {

	protected final MediaPlayer[] players;
	private final boolean[] prepared;
	private ImageView lastButton;
	private int playing = -1;

	public ChantPlayer(String[] sources) {
		players = new MediaPlayer[sources.length];
		prepared = new boolean[sources.length];

		for (int i = 0; i < players.length; i++) {

			players[i] = new MediaPlayer();
			try {
				players[i].setDataSource(sources[i]);
				players[i].setLooping(true);

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Sets chant lyrics to container
	 * 
	 * @param teamId
	 * @param songId
	 * @param container
	 */
	public static void getLyrics(final int teamId, final int songId, final TextView container) {

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// TODO get url OR get file path for lyrics
//				LookupURL lookup = new LookupURL();
//				String source = lookup.getText(teamId, songId);
//				
				String source = "http://storage.googleapis.com/testbucket1111/samples/sample1.txt";

				try {
					URL u = new URL(source);
					HttpURLConnection c = (HttpURLConnection) u.openConnection();
					c.setRequestMethod("GET");
					c.connect();
					InputStream in = c.getInputStream();
					final ByteArrayOutputStream bo = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					in.read(buffer); // Read from Buffer.
					bo.write(buffer); // Write Into Buffer.
					String result = bo.toString();
					in.close();
					bo.close();
					return result;

				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				} catch (ProtocolException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}

			}

			@Override
			public void onPostExecute(String result) {
				if (result == null)
					container.setText("Lyrics unavailable.");
				else
					container.setText(result);
			}

		}.execute();
	}

	/**
	 * Sets total file size to container (in MB)
	 * 
	 * @param teamId
	 * @param songId
	 * @param container
	 */
	public static void getSongSize(int teamId, int songId, final TextView container) {

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// TODO get size of song + lyric text size
				double size = 1.2345;
				DecimalFormat df = new DecimalFormat("#.##");
				return df.format(size) + " MB";
			}

			@Override
			public void onPostExecute(String result) {
				if (result == null)
					container.setText("Size unavailable.");
				else
					container.setText(result);
			}

		}.execute();
	}

	@Override
	public void onClick(View v) {

		int songId = (Integer) v.getTag();
		ImageView button = (ImageView) v;

		if (playing == -1) {
			// nothing playing case
			playing = songId;
			lastButton = button;
			players[songId].start();
			button.setImageResource(R.drawable.icon_stop);

		} else {
			int oldId = playing;
			playing = -1;
			players[oldId].pause();
			players[oldId].seekTo(0);
			lastButton.setImageResource(R.drawable.icon_play);

			if (oldId != songId)
				// alternate play button was pressed
				onClick(v);
		}

	}

	public void prepare(int songId, final View toHide, final View toShow, final View onError) {
		if (!prepared[songId]) {
			players[songId].setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					toHide.setVisibility(View.GONE);
					toShow.setVisibility(View.VISIBLE);
					toShow.setEnabled(true);
				}
			});
			players[songId].setOnErrorListener(new OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					toHide.setVisibility(View.GONE);
					onError.setVisibility(View.VISIBLE);
					Log.e("prepareAsync ERROR", "sonething went wrong!");
					return true;
				}
			});
			players[songId].prepareAsync();
			prepared[songId] = true;
		}
	}

	public void stop() {
		if (playing != -1) {
			players[playing].pause();
			players[playing].seekTo(0);
			playing = -1;
			lastButton.setImageResource(R.drawable.icon_play);
		}
	}

	public void release() {
		for (MediaPlayer mp : players)
			mp.release();
	}

}
