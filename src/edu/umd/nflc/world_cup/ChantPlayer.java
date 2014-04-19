package edu.umd.nflc.world_cup;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ChantPlayer {

	MediaPlayer player = null;
	ImageView button = null; // last button pressed

	/**
	 * Prepares and starts the media player
	 * 
	 * @param countryId
	 * @param songId
	 * @param button
	 *            button pressed to invoke method
	 * @return true if successful
	 */
	public boolean invoke(int countryId, int songId, ImageButton button) {

		// TODO get url OR get file path for chant
		String source = "http://storage.googleapis.com/testbucket1111/samples/sample1.wav";

		if (player == null || !stopAudio(button))
			return startAudio(button, source);
		else
			return true;
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
