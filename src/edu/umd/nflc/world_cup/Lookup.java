package edu.umd.nflc.world_cup;

import android.content.Context;

public class Lookup {

	private final Context context;

	public Lookup(Context context) {
		this.context = context;
	}

	public String[] getAllSongs(int country) {

		switch (country) {
		case 0: // Algeria
			return context.getResources().getStringArray(R.array.alg_songs);

		case 1: // Argentina
			// return context.getResources().getStringArray(R.array.arg_names)

			// etc.

		default:
			return null;
		}

	}

	public String[] getAllSources(int country) {

		switch (country) {
		case 0: // Algeria
			return context.getResources().getStringArray(R.array.alg_sources);

		case 1: // Argentina
			// return context.getResources().getStringArray(R.array.arg_sources)

			// etc.

		default:
			return null;
		}

	}

	public String[] getAllLyrics(int country) {

		switch (country) {
		case 0: // Algeria
			return context.getResources().getStringArray(R.array.alg_lyrics);

		case 1: // Argentina
			// return context.getResources().getStringArray(R.array.arg_lyrics)

			// etc.

		default:
			return null;
		}

	}

	public String getSong(int country, int song) {

		String[] array = null;
		switch (country) {
		case 0: // Algeria
			array = context.getResources().getStringArray(R.array.alg_songs);

		case 1: // Argentina
			// array = context.getResources().getStringArray(R.array.arg_songs)

			// etc.
		}

		// remove when done
		return array[song];
	}

	public String getSource(int country, int song) {

		String[] array = null;
		switch (country) {
		case 0: // Algeria
			array = context.getResources().getStringArray(R.array.alg_sources);

		case 1: // Argentina
			// array =
			// context.getResources().getStringArray(R.array.arg_sources)

			// etc.
		}

		// remove when done
		return array[song];
	}

	public String getLyrics(int country, int song) {

		String[] array = null;
		switch (country) {
		case 0: // Algeria
			array = context.getResources().getStringArray(R.array.alg_lyrics);

		case 1: // Argentina
			// array = context.getResources().getStringArray(R.array.arg_lyrics)

			// etc.
		}

		// remove when done
		return array[song];
	}

}
