package edu.umd.nflc.world_cup;

import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

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
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
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

	public void prepare(int songId, final View loading, final View button, final View onError) {
		if (!prepared[songId]) {
			loading.setVisibility(View.VISIBLE);
			players[songId].setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					loading.setVisibility(View.GONE);
					button.setVisibility(View.VISIBLE);
					button.setEnabled(true);
				}
			});
			players[songId].setOnErrorListener(new OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					loading.setVisibility(View.GONE);
					onError.setVisibility(View.VISIBLE);
					Log.e("prepareAsync ERROR", "sonething went wrong!");
					return true;
				}
			});
			players[songId].prepareAsync();
			prepared[songId] = true;
		
		} else
			button.setVisibility(View.VISIBLE);
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (MediaPlayer mp : players)
					mp.release();
			}
		}).start();;
	}

}
