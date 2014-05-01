package edu.umd.nflc.world_cup;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainFragments {

	public static final int BROWSE = 1 << 0;
	public static final int FAVORITES = 1 << 1;
	public static final int PURCHASES = 1 << 2;

	public static Fragment get(String type) {
		
		if (type.equals("Browse"))
			return new BrowseFragment();
		
		else if (type.equals("Favorites"))
			return new FavoritesFragment();
		
		else // type.equals("Purchases")
			return new PurchasesFragment();

	}

	public static class BrowseFragment extends Fragment implements ListView.OnItemClickListener {

		private String[] teamNames;
		private TypedArray teamFlags;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			// TODO Bas' class
			teamNames = getResources().getStringArray(R.array.team_names);
			teamFlags = getResources().obtainTypedArray(R.array.team_flags);

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			content.setAdapter(new TypedArrayAdapter(inflater, teamNames, teamFlags));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			teamFlags.recycle();
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getActivity(), TeamActivity.class);
			i.putExtra("teamName", teamNames[position]);
			i.putExtra("teamId", position);
			i.putExtra("iconId", teamFlags.getResourceId(position, 0));
			startActivity(i);
		}

	}

	public static class FavoritesFragment extends Fragment implements ListView.OnItemClickListener {

		private String[] teamNames;
		private TypedArray teamFlags;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			// TODO Bas' class
			teamNames = getResources().getStringArray(R.array.team_names);
			teamFlags = getResources().obtainTypedArray(R.array.team_flags);

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			content.setAdapter(new TypedArrayAdapter(inflater, teamNames, teamFlags));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			teamFlags.recycle();
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getActivity(), TeamActivity.class);
			i.putExtra("teamName", teamNames[position]);
			i.putExtra("teamId", position);
			i.putExtra("iconId", teamFlags.getResourceId(position, 0));
			startActivity(i);
		}

	}
	
	public static class PurchasesFragment extends Fragment implements ListView.OnItemClickListener {

		private String[] teamNames;
		private TypedArray teamFlags;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			// TODO Bas' class
			teamNames = getResources().getStringArray(R.array.team_names);
			teamFlags = getResources().obtainTypedArray(R.array.team_flags);

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			content.setAdapter(new TypedArrayAdapter(inflater, teamNames, teamFlags));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			teamFlags.recycle();
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getActivity(), TeamActivity.class);
			i.putExtra("teamName", teamNames[position]);
			i.putExtra("teamId", position);
			i.putExtra("iconId", teamFlags.getResourceId(position, 0));
			startActivity(i);
		}

	}
	
}
