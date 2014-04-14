package edu.umd.nflc.world_cup;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private final static int ICON_HEIGHT = 64;

	private final String[] titles;
	private final TypedArray icons;
	private final LayoutInflater inflater;
	private final boolean leftIcon;

	public ListAdapter(LayoutInflater inflater, String[] titles, TypedArray icons, boolean leftIcon) {
		this.titles = titles;
		this.icons = icons;
		this.inflater = inflater;
		this.leftIcon = leftIcon;
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
			row = (TextView) inflater.inflate(R.layout.list_item, null);
		else
			row = (TextView) convertView;

		Drawable icon = icons.getDrawable(position);
		int iconWidth = icon.getIntrinsicWidth() * ICON_HEIGHT / icon.getIntrinsicHeight();
		icon.setBounds(0, 0, iconWidth, ICON_HEIGHT);

		if (leftIcon)
			row.setCompoundDrawables(icon, null, null, null);
		else
			row.setCompoundDrawables(null, null, null, icon);

		row.setText(titles[position]);
		return row;
	}
}