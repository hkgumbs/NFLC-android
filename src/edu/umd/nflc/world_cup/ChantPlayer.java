package edu.umd.nflc.world_cup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ChantPlayer implements OnClickListener {

	private static final int NOT_PREPARED = 0;
	private static final int PREPARING = 1;
	private static final int OK = 2;
	private static final int ERROR = 3;

	private final MediaPlayer[] players;
	private Listener[] listeners;
	private final int[] prepared;
	private ImageView lastButton;
	private int playing = -1;

	public ChantPlayer(String[] sources) {
		players = new MediaPlayer[sources.length];
		prepared = new int[sources.length];
		listeners = new Listener[sources.length];

		for (int i = 0; i < players.length; i++) {

			players[i] = new MediaPlayer();
			try {
				players[i].setDataSource(sources[i]);
				players[i].setLooping(true);
				listeners[i] = new Listener();

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

	public void prepare(final int songId, final View loading, final View button, final View onError) {
		switch (prepared[songId]) {
		case OK:
			button.setVisibility(View.VISIBLE);
			break;

		case ERROR:
			onError.setVisibility(View.VISIBLE);
			break;

		case NOT_PREPARED:
			prepared[songId] = PREPARING;
			players[songId].prepareAsync();

		case PREPARING:
			loading.setVisibility(View.VISIBLE);
			players[songId].setOnPreparedListener(listeners[songId].registerPreparedListener(songId, loading, button));
			players[songId].setOnErrorListener(listeners[songId].registerErrorListener(songId, loading, onError));
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (MediaPlayer mp : players)
					mp.release();
			}
		}).start();
	}

	private class Listener implements OnPreparedListener, OnErrorListener {

		private List<OnPreparedListener> preparedListeners = new ArrayList<OnPreparedListener>();
		private List<OnErrorListener> errorListeners = new ArrayList<OnErrorListener>();

		@Override
		public void onPrepared(MediaPlayer mp) {
			for (OnPreparedListener pl : preparedListeners)
				pl.onPrepared(mp);
			preparedListeners.clear();
		}

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			for (OnErrorListener el : errorListeners)
				el.onError(mp, what, extra);
			errorListeners.clear();
			return true;
		}

		public OnPreparedListener registerPreparedListener(final int songId, final View loading, final View button) {
			preparedListeners.add(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					loading.setVisibility(View.GONE);
					button.setVisibility(View.VISIBLE);
					prepared[songId] = OK;
					button.setEnabled(true);
				}

			});

			return this;
		}

		public OnErrorListener registerErrorListener(final int songId, final View loading, final View onError) {
			errorListeners.add(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					loading.setVisibility(View.GONE);
					onError.setVisibility(View.VISIBLE);
					prepared[songId] = ERROR;
					return true;
				}

			});

			return this;
		}
	}

}
