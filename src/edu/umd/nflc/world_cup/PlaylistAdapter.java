package edu.umd.nflc.world_cup;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaylistAdapter extends BaseAdapter implements OnClickListener {

	static final int ICON_SIZE = 64;

	private final String[] titles;
	private final MediaPlayer[] players;
	private final boolean[] prepared;
	private final LayoutInflater inflater;

	int playing = -1;
	ImageView lastButton = null;

	public PlaylistAdapter(LayoutInflater inflater, String[] titles) {
		this.titles = titles;
		this.inflater = inflater;

		players = new MediaPlayer[titles.length];
		prepared = new boolean[titles.length];

		for (int i = 0; i < players.length; i++) {

			players[i] = new MediaPlayer();

			// TODO get url OR get file path for chant
			String source = "http://storage.googleapis.com/testbucket1111/samples/sample1.wav";

			try {
				players[i].setDataSource(source);
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

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		return titles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_item_compound, null);

		TextView label = (TextView) convertView.findViewById(R.id.text);
		label.setText(titles[position]);

		final View progressBar = convertView.findViewById(R.id.progressBar);
		final View playButton = convertView.findViewById(R.id.play);
		playButton.setOnClickListener(this);
		playButton.setTag(position);

		if (!prepared[position]) {
			players[position].setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					progressBar.setVisibility(View.GONE);
					playButton.setVisibility(View.VISIBLE);
					playButton.setEnabled(true);
				}
			});
			players[position].prepareAsync();
			prepared[position] = true;
		}

		return convertView;
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

	public void release() {
		for (MediaPlayer mp : players)
			mp.release();
	}

}