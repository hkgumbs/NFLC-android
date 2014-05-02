package edu.umd.nflc.world_cup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
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

		else
			// type.equals("Purchases")
			return new PurchasesFragment();

	}

	public static class BrowseFragment extends Fragment implements ListView.OnItemClickListener {

		private String[] teamNames;
		private TypedArray teamFlags;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			Lookup lookup = new Lookup(getActivity());

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

		private String[] songNames;
		private String[] songSources;
		private int[] teamFlagIds;

		ChantPlayer chants;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			Lookup lookup = new Lookup(getActivity());

			List<String> favorites = new ArrayList<String>();
			Map<String, ?> preferences = getActivity().getPreferences(Context.MODE_PRIVATE).getAll();
			for (String value : preferences.keySet())
				if (value.startsWith("favorited_"))
					favorites.add(value.substring(11));

			songNames = new String[favorites.size()];
			String[] songSources = new String[favorites.size()];
			for (int i = 0; i < favorites.size(); i++) {
				String value = favorites.get(i);
				int countryId = Integer.parseInt(value.substring(0, value.indexOf(",")));
				int songId = Integer.parseInt(value.substring(value.indexOf(",") + 1));
				songNames[i] = lookup.getSong(countryId, songId);
				songSources[i] = lookup.getSource(countryId, songId);
			}

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			chants = ChantPlayer.get(songSources);
			content.setAdapter(new PlaylistAdapter(getActivity(), songSources, chants, songSources.length));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			chants.release();
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

			Lookup lookup = new Lookup(getActivity());

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
