package edu.umd.nflc.world_cup;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TypedArrayAdapter extends BaseAdapter {

	private final String[] titles;
	private final LayoutInflater inflater;
	private final int[] icons;

	public TypedArrayAdapter(LayoutInflater inflater, String[] titles, int[] icons) {
		this.titles = titles;
		this.inflater = inflater;
		this.icons = icons;
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

		TextView row;
		if (convertView == null)
			row = (TextView) inflater.inflate(R.layout.list_item_simple, null);
		else
			row = (TextView) convertView;

		Drawable icon = inflater.getContext().getResources().getDrawable(icons[position]);
		icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
		row.setCompoundDrawables(icon, null, null, null);

		row.setText(titles[position]);
		return row;
	}

}