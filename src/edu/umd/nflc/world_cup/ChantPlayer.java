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
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ChantPlayer {

	MediaPlayer player = null;
	ImageView button = null; // last button pressed

	/**
	 * Sets chant lyrics to container
	 * 
	 * @param teamId
	 * @param songId
	 * @param container
	 */
	public void getLyrics(int teamId, int songId, final TextView container) {

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				// TODO get url OR get file path for lyrics
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
	public void getSongSize(int teamId, int songId, final TextView container) {

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

	/**
	 * Prepares and starts the media player
	 * 
	 * @param countryId
	 * @param songId
	 * @param button
	 *            button pressed to invoke method
	 * @return true if successful
	 */
	public void toggleSong(int teamId, int songId, final ImageButton button) {

		// TODO use AsyncTask
		// TODO get url OR get file path for chant
		String source = "http://storage.googleapis.com/testbucket1111/samples/sample1.wav";

		if (player == null || !stopAudio(button))
			startAudio(button, source);
	}

	/**
	 * 
	 * @param v
	 *            button pressed to invoke method
	 * @return true if successful, false otherwise
	 */
	private boolean startAudio(final ImageView v, String source) {
		try {
			player = new MediaPlayer();
			player.setDataSource(source);
			player.prepare();
			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					stopAudio(v);
				}
			});

			player.start();
			button = v;
			button.setImageResource(R.drawable.icon_stop);

		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param v
	 *            button pressed to invoke method
	 * @return true if audio was running
	 */
	private boolean stopAudio(ImageView v) {
		player.stop();
		player.release();
		player = null;
		button.setImageResource(R.drawable.icon_play);

		boolean result = v.equals(button);
		button = null;

		return result;
	}

}
