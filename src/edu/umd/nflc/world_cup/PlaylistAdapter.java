package edu.umd.nflc.world_cup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends BaseAdapter implements Constants {

	private final String[] titles;
	private final LayoutInflater inflater;
	private final OnClickListener listener;

	public PlaylistAdapter(LayoutInflater inflater, String[] titles, OnClickListener listener) {
		this.titles = titles;
		this.inflater = inflater;
		this.listener = listener;
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

		View playButton = convertView.findViewById(R.id.play);
		playButton.setOnClickListener(listener);
		playButton.setTag(titles[position]);
		
		return convertView;
	}

}