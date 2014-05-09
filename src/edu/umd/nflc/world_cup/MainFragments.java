package edu.umd.nflc.world_cup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
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

		String[] teamNames;
		int[] icons;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			Lookup lookup = new Lookup(getActivity());
			teamNames = lookup.getAllTeams();
			icons = lookup.getAllFlagIds();

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			content.setAdapter(new TypedArrayAdapter(inflater, teamNames, icons));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getActivity(), TeamActivity.class);
			i.putExtra("teamName", teamNames[position]);
			i.putExtra("teamId", position);
			i.putExtra("iconId", icons[position]);
			startActivity(i);
		}

	}

	public static class FavoritesFragment extends Fragment implements ListView.OnItemClickListener {

		String[] songNames;
		String[] songSources;
		int[] teamIds;
		int[] songIds;
		int[] iconIds;

		ChantPlayer chants;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			Lookup lookup = new Lookup(getActivity());

			Map<String, ?> preferences = getActivity().getSharedPreferences("favorited", Context.MODE_PRIVATE).getAll();
			List<String> favorites = new ArrayList<String>(preferences.keySet());
			Collections.sort(favorites);

			teamIds = new int[favorites.size()];
			songNames = new String[favorites.size()];
			songSources = new String[favorites.size()];
			songIds = new int[favorites.size()];
			iconIds = new int[favorites.size()];

			for (int i = 0; i < favorites.size(); i++) {
				String value = favorites.get(i);
				teamIds[i] = Integer.parseInt(value.substring(0, value.indexOf(",")));
				songIds[i] = Integer.parseInt(value.substring(value.indexOf(",") + 1));
				songNames[i] = lookup.getSong(teamIds[i], songIds[i]);
				songSources[i] = lookup.getSource(teamIds[i], songIds[i]);
				iconIds[i] = lookup.getFlagId(teamIds[i]);
			}

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			
			chants = new ChantPlayer(songSources);
			content.setAdapter(new PlaylistAdapter(getActivity(), songNames, chants, songSources.length));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			chants.stop();
			chants.release();
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getActivity(), PlayActivity.class);

			i.putExtra("iconIds", iconIds);
			i.putExtra("teamIds", teamIds);
			i.putExtra("songIds", songIds);
			i.putExtra("current", position);

			startActivity(i);
		}
	}

	public static class PurchasesFragment extends Fragment implements ListView.OnItemClickListener {

		String[] teamNames;
		int[] teamIds;
		int[] icons;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			Lookup lookup = new Lookup(getActivity());

			Map<String, ?> preferences = getActivity().getSharedPreferences("purchased", Context.MODE_PRIVATE).getAll();
			List<String> purchased = new ArrayList<String>(preferences.keySet());
			Collections.sort(purchased);

			teamNames = new String[purchased.size()];
			teamIds = new int[purchased.size()];
			icons = new int[purchased.size()];

			int i = 0;
			for (String value : purchased) {
				teamIds[i] = Integer.parseInt(value);
				teamNames[i] = lookup.getTeam(teamIds[i]);
				icons[i] = lookup.getFlagId(teamIds[i]);
				i++;
			}

			View frame = inflater.inflate(R.layout.fragment_list, container, false);
			ListView content = (ListView) frame.findViewById(R.id.list);
			content.setAdapter(new TypedArrayAdapter(inflater, teamNames, icons));
			content.setOnItemClickListener(this);

			return frame;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getActivity(), TeamActivity.class);
			i.putExtra("teamName", teamNames[position]);
			i.putExtra("teamId", teamIds[position]);
			i.putExtra("iconId", icons[position]);
			startActivity(i);
		}

	}

}
