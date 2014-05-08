package edu.umd.nflc.world_cup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Environment;
import android.util.Log;

public class Lookup {

	public static final int LYRICS = 0;
	public static final int TRANSLATION = 1;
	public static final int TRANSLITERATION = 2;
	public static final int SONG = 3;
	public static final String[] FILENAMES = new String[] { "lyrics.txt", "translation.txt", "transliteration.txt",
			"song.mp3" };

	private final Context context;

	public Lookup(Context context) {
		this.context = context;
	}

	public boolean download(int country) {
		boolean result = true;
		int length = getAllSongs(country).length;
		for (int i = 0; i < length; i++)
			if (!download(country, i))
				result = false;
		return result;

	}

	public boolean download(int country, int song) {
		if (isExternalStorageMounted()) {
			try {
				// clean directory
				delete(country, song);

				URL[] urls = new URL[FILENAMES.length];
				urls[LYRICS] = new URL(getLyrics(country, song));
				urls[SONG] = new URL(getSource(country, song));
				urls[TRANSLATION] = new URL(getTranslation(country, song));
				urls[TRANSLITERATION] = new URL(getTransliterations(country, song));

				for (int i = 0; i < urls.length; i++) {

					HttpURLConnection connection = (HttpURLConnection) urls[i].openConnection();
					connection.connect();
					// expect HTTP 200 OK, so we don't mistakenly save error
					// report
					// instead of the file
					if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
						return false;

					File file = new File(getFolder(country, song), FILENAMES[i]);
					FileOutputStream output = new FileOutputStream(file);

					InputStream input = connection.getInputStream();
					byte data[] = new byte[1024];
					int count;
					while ((count = input.read(data)) != -1)
						output.write(data, 0, count);

					connection.disconnect();
					output.close();

				}

				return true;

			} catch (Exception e) {
				return false;
			}

		} else
			return false;
	}

	public boolean delete(int country) {
		boolean result = true;
		int length = getAllSongs(country).length;
		for (int i = 0; i < length; i++)
			if (!delete(country, i))
				result = false;
		return result;
	}

	public boolean delete(int country, int song) {
		boolean result = true;
		File folder = getFolder(country, song);
		for (File file : folder.listFiles())
			if (!file.delete())
				result = false;
		return result;
	}

	public String getDownloaded(int country, int song, int fileType) {
		if (isExternalStorageMounted()) {
			File folder = getFolder(country, song);
			File[] children = folder.listFiles();
			for (int i = 0; i < children.length; i++) {
				Log.d("files", children[i].getName());
				if (children[i].getName().equals(FILENAMES[fileType]))
					return children[i].getAbsolutePath();
			}
		}
		return null;
	}

	public boolean isDownloaded(int country, int song) {
		// File folder = getFolder(country, song);
		// if (folder == null)
		// return false;
		//
		// for (int i = 0; i < FILENAMES.length; i++)
		// if (!new File(folder, FILENAMES[i]).isFile())
		// return false;
		// return true;

		for (int i = 0; i < FILENAMES.length; i++)
			if (getDownloaded(country, song, i) == null)
				return false;
		return true;
	}

	public boolean isDownloaded(int country) {
		int length = getAllSongs(country).length;
		for (int i = 0; i < length; i++)
			if (isDownloaded(country, i))
				return true;
		return false;
	}

	public String[] getAllTeams() {
		return context.getResources().getStringArray(R.array.team_names);
	}

	public String getTeam(int country) {
		return getAllTeams()[country];
	}

	public int[] getAllFlagIds() {
		TypedArray flags = context.getResources().obtainTypedArray(R.array.team_flags);
		int[] ids = new int[flags.length()];
		for (int i = 0; i < ids.length; i++)
			ids[i] = flags.getResourceId(i, 0);
		flags.recycle();
		return ids;
	}

	public int getFlagId(int country) {
		TypedArray flags = context.getResources().obtainTypedArray(R.array.team_flags);
		int id = flags.getResourceId(country, 0);
		flags.recycle();
		return id;
	}

	public String[] getAllSongs(int country) {

		switch (country) {
		case 0: // ALGERIA
			return context.getResources().getStringArray(R.array.algeria_songs);
		case 1: // ARGENTINA
			return context.getResources().getStringArray(R.array.argentina_songs);
		case 2: // AUSTRALIA
			return context.getResources().getStringArray(R.array.australia_songs);
		case 3: // BELGIUM
			return context.getResources().getStringArray(R.array.belgium_songs);
		case 4: // BOSNIA AND HERZEGOVINA
			return context.getResources().getStringArray(R.array.bosnia_songs);
		case 5: // BRAZIL
			return context.getResources().getStringArray(R.array.brazil_songs);
		case 6: // CAMEROON
			return context.getResources().getStringArray(R.array.cameroon_songs);
		case 7: // CHILE
			return context.getResources().getStringArray(R.array.chile_songs);
		case 8: // COLOMBIA
			return context.getResources().getStringArray(R.array.colombia_songs);
		case 9: // IVORY COAST (COTE D'IVORE)
			return context.getResources().getStringArray(R.array.cote_d_songs);
		case 10: // COSTA RICA
			return context.getResources().getStringArray(R.array.costa_rica_songs);
		case 11: // CROATIA
			return context.getResources().getStringArray(R.array.croatia_songs);
		case 12: // ECUADOR
			return context.getResources().getStringArray(R.array.ecuador_songs);
		case 13: // ENGLAND
			return context.getResources().getStringArray(R.array.england_songs);
		case 14: // FRANCE
			return context.getResources().getStringArray(R.array.france_songs);
		case 15: // GERMANY
			return context.getResources().getStringArray(R.array.germany_songs);
		case 16: // GHANA
			return context.getResources().getStringArray(R.array.ghana_songs);
		case 17: // GREECE
			return context.getResources().getStringArray(R.array.greece_songs);
		case 18: // HONDURAS
			return context.getResources().getStringArray(R.array.honduras_songs);
		case 19: // IRAN
			return context.getResources().getStringArray(R.array.iran_songs);
		case 20: // ITALY
			return context.getResources().getStringArray(R.array.italy_songs);
		case 21: // JAPAN
			return context.getResources().getStringArray(R.array.japan_songs);
		case 22: // MEXICO
			return context.getResources().getStringArray(R.array.mexico_songs);
		case 23: // NETHERLANDS
			return context.getResources().getStringArray(R.array.netherlands_songs);
		case 24: // NIGERIA
			return context.getResources().getStringArray(R.array.nigeria_songs);
		case 25: // PORTUGAL
			return context.getResources().getStringArray(R.array.portugal_songs);
		case 26: // RUSSIA
			return context.getResources().getStringArray(R.array.russia_songs);
		case 27: // SOUTH KOREA
			return context.getResources().getStringArray(R.array.south_korea_songs);
		case 28: // SPAIN
			return context.getResources().getStringArray(R.array.spain_songs);
		case 29: // SWITZERLAND
			return context.getResources().getStringArray(R.array.switzerland_songs);
		case 30: // URUGUAY
			return context.getResources().getStringArray(R.array.uruguay_songs);
		case 31: // USA
			return context.getResources().getStringArray(R.array.usa_songs);
		default:
			return null;
		}
	}

	public String[] getAllSources(int country) {

		String[] result = null;

		switch (country) {
		case 0: // ALGERIA
			result = context.getResources().getStringArray(R.array.algeria_audios);
			break;
		case 1: // ARGENTINA
			result = context.getResources().getStringArray(R.array.argentina_audios);
			break;
		case 2: // AUSTRALIA
			result = context.getResources().getStringArray(R.array.australia_audios);
			break;
		case 3: // BELGIUM
			result = context.getResources().getStringArray(R.array.belgium_audios);
			break;
		case 4: // BOSNIA AND HERZEGOVINA
			result = context.getResources().getStringArray(R.array.bosnia_audios);
			break;
		case 5: // BRAZIL
			result = context.getResources().getStringArray(R.array.brazil_audios);
			break;
		case 6: // CAMEROON
			result = context.getResources().getStringArray(R.array.cameroon_audios);
			break;
		case 7: // CHILE
			result = context.getResources().getStringArray(R.array.chile_audios);
			break;
		case 8: // COLOMBIA
			result = context.getResources().getStringArray(R.array.colombia_audios);
			break;
		case 9: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_audios);
			break;
		case 10: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_audios);
			break;
		case 11: // CROATIA
			result = context.getResources().getStringArray(R.array.croatia_audios);
			break;
		case 12: // ECUADOR
			result = context.getResources().getStringArray(R.array.ecuador_audios);
			break;
		case 13: // ENGLAND
			result = context.getResources().getStringArray(R.array.england_audios);
			break;
		case 14: // FRANCE
			result = context.getResources().getStringArray(R.array.france_audios);
			break;
		case 15: // GERMANY
			result = context.getResources().getStringArray(R.array.germany_audios);
			break;
		case 16: // GHANA
			result = context.getResources().getStringArray(R.array.ghana_audios);
			break;
		case 17: // GREECE
			result = context.getResources().getStringArray(R.array.greece_audios);
			break;
		case 18: // HONDURAS
			result = context.getResources().getStringArray(R.array.honduras_audios);
			break;
		case 19: // IRAN
			result = context.getResources().getStringArray(R.array.iran_audios);
			break;
		case 20: // ITALY
			result = context.getResources().getStringArray(R.array.italy_audios);
			break;
		case 21: // JAPAN
			result = context.getResources().getStringArray(R.array.japan_audios);
			break;
		case 22: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_audios);
			break;
		case 23: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_audios);
			break;
		case 24: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_audios);
			break;
		case 25: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_audios);
			break;
		case 26: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_audios);
			break;
		case 27: // SOUTH KOREA
			result = context.getResources().getStringArray(R.array.south_korea_audios);
			break;
		case 28: // SPAIN
			result = context.getResources().getStringArray(R.array.spain_audios);
		case 29: // SWITZERLAND
			result = context.getResources().getStringArray(R.array.switzerland_audios);
			break;
		case 30: // URUGUAY
			result = context.getResources().getStringArray(R.array.uruguay_audios);
			break;
		case 31: // USA
			result = context.getResources().getStringArray(R.array.usa_audios);
			break;
		}

		mergeDownloaded(result, country, SONG);
		return result;
	}

	public String[] getAllLyrics(int country) {

		String[] result = null;

		switch (country) {
		case 0: // ALGERIA
			result = context.getResources().getStringArray(R.array.algeria_lyrics);
			break;
		case 1: // ARGENTINA
			result = context.getResources().getStringArray(R.array.argentina_lyrics);
			break;
		case 2: // AUSTRALIA
			result = context.getResources().getStringArray(R.array.australia_lyrics);
			break;
		case 3: // BELGIUM
			result = context.getResources().getStringArray(R.array.belgium_lyrics);
			break;
		case 4: // BOSNIA AND HERZEGOVINA
			result = context.getResources().getStringArray(R.array.bosnia_lyrics);
			break;
		case 5: // BRAZIL
			result = context.getResources().getStringArray(R.array.brazil_lyrics);
			break;
		case 6: // CAMEROON
			result = context.getResources().getStringArray(R.array.cameroon_lyrics);
			break;
		case 7: // CHILE
			result = context.getResources().getStringArray(R.array.chile_lyrics);
			break;
		case 8: // COLOMBIA
			result = context.getResources().getStringArray(R.array.colombia_lyrics);
			break;
		case 9: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_lyrics);
			break;
		case 10: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_lyrics);
			break;
		case 11: // CROATIA
			result = context.getResources().getStringArray(R.array.croatia_lyrics);
			break;
		case 12: // ECUADOR
			result = context.getResources().getStringArray(R.array.ecuador_lyrics);
			break;
		case 13: // ENGLAND
			result = context.getResources().getStringArray(R.array.england_lyrics);
			break;
		case 14: // FRANCE
			result = context.getResources().getStringArray(R.array.france_lyrics);
			break;
		case 15: // GERMANY
			result = context.getResources().getStringArray(R.array.germany_lyrics);
			break;
		case 16: // GHANA
			result = context.getResources().getStringArray(R.array.ghana_lyrics);
			break;
		case 17: // GREECE
			result = context.getResources().getStringArray(R.array.greece_lyrics);
			break;
		case 18: // HONDURAS
			result = context.getResources().getStringArray(R.array.honduras_lyrics);
			break;
		case 19: // IRAN
			result = context.getResources().getStringArray(R.array.iran_lyrics);
			break;
		case 20: // ITALY
			result = context.getResources().getStringArray(R.array.italy_lyrics);
			break;
		case 21: // JAPAN
			result = context.getResources().getStringArray(R.array.japan_lyrics);
			break;
		case 22: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_lyrics);
			break;
		case 23: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_lyrics);
			break;
		case 24: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_lyrics);
			break;
		case 25: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_lyrics);
			break;
		case 26: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_lyrics);
			break;
		case 27: // SOUTH KOREA
			result = context.getResources().getStringArray(R.array.south_korea_lyrics);
			break;
		case 28: // SPAIN
			result = context.getResources().getStringArray(R.array.spain_lyrics);
			break;
		case 29: // SWITZERLAND
			result = context.getResources().getStringArray(R.array.switzerland_lyrics);
			break;
		case 30: // URUGUAY
			result = context.getResources().getStringArray(R.array.uruguay_lyrics);
			break;
		case 31: // USA
			result = context.getResources().getStringArray(R.array.usa_lyrics);
			break;
		}

		mergeDownloaded(result, country, LYRICS);
		return result;
	}

	public String[] getAllTranslations(int country) {

		String[] result = null;

		switch (country) {
		case 0: // ALGERIA
			result = context.getResources().getStringArray(R.array.algeria_translation);
			break;
		case 1: // ARGENTINA
			result = context.getResources().getStringArray(R.array.argentina_translation);
			break;
		case 2: // AUSTRALIA
			result = context.getResources().getStringArray(R.array.australia_translation);
			break;
		case 3: // BELGIUM
			result = context.getResources().getStringArray(R.array.belgium_translation);
			break;
		case 4: // BOSNIA AND HERZEGOVINA
			result = context.getResources().getStringArray(R.array.bosnia_translation);
			break;
		case 5: // BRAZIL
			result = context.getResources().getStringArray(R.array.brazil_translation);
			break;
		case 6: // CAMEROON
			result = context.getResources().getStringArray(R.array.cameroon_translation);
			break;
		case 7: // CHILE
			result = context.getResources().getStringArray(R.array.chile_translation);
			break;
		case 8: // COLOMBIA
			result = context.getResources().getStringArray(R.array.colombia_translation);
			break;
		case 9: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_translation);
			break;
		case 10: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_translation);
			break;
		case 11: // CROATIA
			result = context.getResources().getStringArray(R.array.croatia_translation);
			break;
		case 12: // ECUADOR
			result = context.getResources().getStringArray(R.array.ecuador_translation);
			break;
		case 13: // ENGLAND
			result = context.getResources().getStringArray(R.array.england_translation);
			break;
		case 14: // FRANCE
			result = context.getResources().getStringArray(R.array.france_translation);
			break;
		case 15: // GERMANY
			result = context.getResources().getStringArray(R.array.germany_translation);
			break;
		case 16: // GHANA
			result = context.getResources().getStringArray(R.array.ghana_translation);
			break;
		case 17: // GREECE
			result = context.getResources().getStringArray(R.array.greece_translation);
			break;
		case 18: // HONDURAS
			result = context.getResources().getStringArray(R.array.honduras_translation);
			break;
		case 19: // IRAN
			result = context.getResources().getStringArray(R.array.iran_translation);
			break;
		case 20: // ITALY
			result = context.getResources().getStringArray(R.array.italy_translation);
			break;
		case 21: // JAPAN
			result = context.getResources().getStringArray(R.array.japan_translation);
			break;
		case 22: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_translation);
			break;
		case 23: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_translation);
			break;
		case 24: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_translation);
			break;
		case 25: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_translation);
			break;
		case 26: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_translation);
			break;
		case 27: // SOUTH KOREA
			result = context.getResources().getStringArray(R.array.south_korea_translation);
			break;
		case 28: // SPAIN
			result = context.getResources().getStringArray(R.array.spain_translation);
		case 29: // SWITZERLAND
			result = context.getResources().getStringArray(R.array.switzerland_translation);
			break;
		case 30: // URUGUAY
			result = context.getResources().getStringArray(R.array.uruguay_translation);
			break;
		case 31: // USA
			result = context.getResources().getStringArray(R.array.usa_translation);
			break;
		}

		mergeDownloaded(result, country, TRANSLATION);
		return result;
	}

	public String[] getAllTransliterations(int country) {

		String[] result = null;

		switch (country) {
		case 0: // ALGERIA
			result = context.getResources().getStringArray(R.array.algeria_transliteration);
			break;
		case 1: // ARGENTINA
			result = context.getResources().getStringArray(R.array.argentina_transliteration);
			break;
		case 2: // AUSTRALIA
			result = context.getResources().getStringArray(R.array.australia_transliteration);
			break;
		case 3: // BELGIUM
			result = context.getResources().getStringArray(R.array.belgium_transliteration);
			break;
		case 4: // BOSNIA AND HERZEGOVINA
			result = context.getResources().getStringArray(R.array.bosnia_transliteration);
			break;
		case 5: // BRAZIL
			result = context.getResources().getStringArray(R.array.brazil_transliteration);
			break;
		case 6: // CAMEROON
			result = context.getResources().getStringArray(R.array.cameroon_transliteration);
			break;
		case 7: // CHILE
			result = context.getResources().getStringArray(R.array.chile_transliteration);
			break;
		case 8: // COLOMBIA
			result = context.getResources().getStringArray(R.array.colombia_transliteration);
			break;
		case 9: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_transliteration);
			break;
		case 10: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_transliteration);
			break;
		case 11: // CROATIA
			result = context.getResources().getStringArray(R.array.croatia_transliteration);
			break;
		case 12: // ECUADOR
			result = context.getResources().getStringArray(R.array.ecuador_transliteration);
			break;
		case 13: // ENGLAND
			result = context.getResources().getStringArray(R.array.england_transliteration);
			break;
		case 14: // FRANCE
			result = context.getResources().getStringArray(R.array.france_transliteration);
			break;
		case 15: // GERMANY
			result = context.getResources().getStringArray(R.array.germany_transliteration);
			break;
		case 16: // GHANA
			result = context.getResources().getStringArray(R.array.ghana_transliteration);
			break;
		case 17: // GREECE
			result = context.getResources().getStringArray(R.array.greece_transliteration);
			break;
		case 18: // HONDURAS
			result = context.getResources().getStringArray(R.array.honduras_transliteration);
			break;
		case 19: // IRAN
			result = context.getResources().getStringArray(R.array.iran_transliteration);
			break;
		case 20: // ITALY
			result = context.getResources().getStringArray(R.array.italy_transliteration);
			break;
		case 21: // JAPAN
			result = context.getResources().getStringArray(R.array.japan_transliteration);
			break;
		case 22: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_transliteration);
			break;
		case 23: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_transliteration);
			break;
		case 24: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_transliteration);
			break;
		case 25: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_transliteration);
			break;
		case 26: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_transliteration);
			break;
		case 27: // SOUTH KOREA
			result = context.getResources().getStringArray(R.array.south_korea_transliteration);
			break;
		case 28: // SPAIN
			result = context.getResources().getStringArray(R.array.spain_transliteration);
			break;
		case 29: // SWITZERLAND
			result = context.getResources().getStringArray(R.array.switzerland_transliteration);
			break;
		case 30: // URUGUAY
			result = context.getResources().getStringArray(R.array.uruguay_transliteration);
			break;
		case 31: // USA
			result = context.getResources().getStringArray(R.array.usa_transliteration);
			break;

		}

		mergeDownloaded(result, country, TRANSLITERATION);
		return result;
	}

	public String getSong(int country, int song) {
		return getAllSongs(country)[song];
	}

	public String getSource(int country, int song) {
		return getAllSources(country)[song];
	}

	public String getLyrics(int country, int song) {
		return getAllLyrics(country)[song];
	}

	public String getTranslation(int country, int song) {
		return getAllTranslations(country)[song];
	}

	public String getTransliterations(int country, int song) {
		return getAllTransliterations(country)[song];
	}

	// File IO stuff

	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageMounted() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * Get folder to save files to or read files from
	 * 
	 * based on http://developer.android.com/guide/topics/data/data-storage
	 * .html#filesExternal
	 * 
	 * @param country
	 *            name of folder
	 * @param environment
	 *            either Environment.DIRECTORY_MUSIC or
	 *            Environment.DIRECTORY_DOCUMENTS
	 * @return
	 */
	private File getFolder(int country) {
		if (!isExternalStorageMounted())
			return null;
		else {
			File folder = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name)
					+ File.separator + "Country " + country);
			return (folder.mkdirs() || folder.isDirectory()) ? folder : null;
		}
	}

	private File getFolder(int country, int song) {
		File parent = getFolder(country);
		if (parent == null)
			return null;
		else {
			File folder = new File(parent, "Song " + song);
			return (folder.mkdirs() || folder.isDirectory()) ? folder : null;
		}
	}

	private void mergeDownloaded(String[] defaultURLs, int country, int type) {
		for (int i = 0; i < defaultURLs.length; i++) {
			String path = getDownloaded(country, i, type);
			if (path != null) {
				defaultURLs[i] = path;
				Log.d("downloaded " + i, path);
			}
		}
	}

	public void mergeDownloaded(String[] defaultURLs, int[] countries, int[] songs, int type) {
		for (int i = 0; i < defaultURLs.length; i++) {
			String path = getDownloaded(countries[i], songs[i], type);
			if (path != null)
				defaultURLs[i] = path;
		}
	}
}
