package edu.umd.nflc.world_cup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Environment;

public class Lookup {

	public static final int LYRICS = 0;
	public static final int TRANSLATION = 1;
	public static final int TRANSLITERATION = 2;
	public static final int SONG = 3;

	public static final String[] TYPES = new String[] { "Native text", "Translation", "Transliteration" };

	// Standard filenames for downloaded files
	public static final String[] FILENAMES = new String[] { "lyrics.txt", "translation.txt", "transliteration.txt",
			"song.mp3" };

	private final Context context;

	public Lookup(Context context) {
		this.context = context;
	}

	// Returns size that files occupy
	public String getSize(int country, int song) {

		long length = 0;

		if (isDownloaded(country, song)) {
			File folder = getFolder(country, song);
			for (File file : folder.listFiles())
				length += file.length();

		} else {
			try {
				URL[] urls = new URL[FILENAMES.length];
				urls[0] = new URL(getLyrics(country, song));
				urls[1] = new URL(getTranslation(country, song));
				urls[2] = new URL(getTransliterations(country, song));
				urls[3] = new URL(getSource(country, song));

				for (URL u : urls) {
					URLConnection conn = u.openConnection();
					length += conn.getContentLength();
				}

			} catch (IOException e) {
				length = -1;
			}
		}

		DecimalFormat df = new DecimalFormat("#.##");
		return length <= 0 ? null : df.format(length / ((long) 1024)) + " KB";
	}

	// download individual song to device
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

	// Download all songs in country pack
	public boolean download(int country) {
		boolean result = true;
		int length = getAllSongs(country).length;
		for (int i = 0; i < length; i++)
			if (!download(country, i))
				result = false;
		return result;

	}

	// Delete indivudal song from device
	public boolean delete(int country, int song) {
		boolean result = true;
		File folder = getFolder(country, song);
		for (File file : folder.listFiles())
			if (!file.delete())
				result = false;
		return result;
	}

	// Delete all songs in country pack
	public boolean delete(int country) {
		boolean result = true;
		int length = getAllSongs(country).length;
		for (int i = 0; i < length; i++)
			if (!delete(country, i))
				result = false;
		return result;
	}

	// Returns file path as string if downloaded, null otherwise
	private String getDownloaded(int country, int song, int fileType) {
		if (isExternalStorageMounted()) {
			File folder = getFolder(country, song);
			File[] children = folder.listFiles();
			for (int i = 0; i < children.length; i++)
				if (children[i].getName().equals(FILENAMES[fileType]))
					return children[i].getAbsolutePath();
		}
		return null;
	}

	// Determines whether ALL files in this song are downloaded
	public boolean isDownloaded(int country, int song) {
		for (int i = 0; i < FILENAMES.length; i++)
			if (getDownloaded(country, song, i) == null)
				return false;
		return true;
	}

	// Detemines whether ANY songs in this country are downloaded
	public boolean isDownloaded(int country) {
		int length = getAllSongs(country).length;
		for (int i = 0; i < length; i++)
			if (isDownloaded(country, i))
				return true;
		return false;
	}

	// Returns all team names
	public String[] getAllTeams() {
		return context.getResources().getStringArray(R.array.team_names);
	}

	// Returns specified team name
	public String getTeam(int country) {
		return getAllTeams()[country];
	}

	// Return resource ids for all flags
	public int[] getAllFlagIds() {
		TypedArray flags = context.getResources().obtainTypedArray(R.array.team_flags);
		int[] ids = new int[flags.length()];
		for (int i = 0; i < ids.length; i++)
			ids[i] = flags.getResourceId(i, 0);
		flags.recycle();
		return ids;
	}

	// Return resource id for specified flag
	public int getFlagId(int country) {
		TypedArray flags = context.getResources().obtainTypedArray(R.array.team_flags);
		int id = flags.getResourceId(country, 0);
		flags.recycle();
		return id;
	}

	// Return unique SKU for country
	public String lookupSKU(int country) {
		switch (country) {
		case 0: // ALGERIA
			return "algeria_package";
		case 1: // ARGENTINA
			return "argentina_package";
		case 2: // AUSTRALIA
			return "australia_package";
		case 3: // BELGIUM
			return "belgium_package";
		case 4: // BOSNIA AND HERZEGOVINA
			return "bosnia_package";
		case 5: // BRAZIL
			return "brazil_package";
		case 6: // CAMEROON
			return "cameroon_package";
		case 7: // CHILE
			return "chile_package";
		case 8: // COLOMBIA
			return "colombia_package";
		case 9: // COSTA RICA
			return "cota_rica_package";
		case 10: // IVORY COAST (COTE D'IVORE)
			return "ivory_coast_package";
		case 11: // CROATIA
			return "croatia_package";
		case 12: // ECUADOR
			return "ecuador_package";
		case 13: // ENGLAND
			return "england_package";
		case 14: // FRANCE
			return "france_package";
		case 15: // GERMANY
			return "germany_package";
		case 16: // GHANA
			return "ghana_package";
		case 17: // GREECE
			return "greece_package";
		case 18: // HONDURAS
			return "honduras_package";
		case 19: // IRAN
			return "iran_package";
		case 20: // ITALY
			return "italy_package";
		case 21: // JAPAN
			return "japan_package";
		case 22: // SOUTH KOREA (KOREA REPUBLIC)
			return "south_korea_package";
		case 23: // MEXICO
			return "mexico_package";
		case 24: // NETHERLANDS
			return "netherlands_package";
		case 25: // NIGERIA
			return "nigeria_chants_package";
		case 26: // PORTUGAL
			return "portugal_package";
		case 27: // RUSSIA
			return "russia_package";
		case 28: // SPAIN
			return "spain_package";
		case 29: // SWITZERLAND
			return "switzerland_package";
		case 30: // URUGUAY
			return "uruguay_package";
		case 31: // USA
			return "usa_package";
		default:
			return null;
		}
	}

	// Get all song names for a country
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
		case 9: // COSTA RICA
			return context.getResources().getStringArray(R.array.costa_rica_songs);
		case 10: // IVORY COAST (COTE D'IVORE)
			return context.getResources().getStringArray(R.array.cote_d_songs);
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
		case 22: // SOUTH KOREA (KOREA REPUBLIC)
			return context.getResources().getStringArray(R.array.south_korea_songs);
		case 23: // MEXICO
			return context.getResources().getStringArray(R.array.mexico_songs);
		case 24: // NETHERLANDS
			return context.getResources().getStringArray(R.array.netherlands_songs);
		case 25: // NIGERIA
			return context.getResources().getStringArray(R.array.nigeria_songs);
		case 26: // PORTUGAL
			return context.getResources().getStringArray(R.array.portugal_songs);
		case 27: // RUSSIA
			return context.getResources().getStringArray(R.array.russia_songs);
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

	// Get specified song name for country
	public String getSong(int country, int song) {
		return getAllSongs(country)[song];
	}

	// Get all song URLs (local or http) for given country
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
		case 9: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_audios);
			break;
		case 10: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_audios);
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
		case 22: // SOUTH KOREA (KOREA REPUBLIC)
			result = context.getResources().getStringArray(R.array.south_korea_audios);
			break;
		case 23: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_audios);
			break;
		case 24: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_audios);
			break;
		case 25: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_audios);
			break;
		case 26: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_audios);
			break;
		case 27: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_audios);
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

	// Get specified URL for song
	public String getSource(int country, int song) {
		return getAllSources(country)[song];
	}

	// Get all lyrics for country
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
		case 9: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_lyrics);
			break;
		case 10: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_lyrics);
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
		case 22: // SOUTH KOREA (KOREA REPUBLIC)
			result = context.getResources().getStringArray(R.array.south_korea_lyrics);
			break;
		case 23: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_lyrics);
			break;
		case 24: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_lyrics);
			break;
		case 25: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_lyrics);
			break;
		case 26: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_lyrics);
			break;
		case 27: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_lyrics);
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

	// Get specified lyrics for song
	public String getLyrics(int country, int song) {
		return getAllLyrics(country)[song];
	}

	// Get all translations for country
	public String[] getAllTranslations(int country) {

		String[] result = null;

		switch (country) {
		case 0: // ALGERIA
			result = context.getResources().getStringArray(R.array.algeria_translations);
			break;
		case 1: // ARGENTINA
			result = context.getResources().getStringArray(R.array.argentina_translations);
			break;
		case 2: // AUSTRALIA
			result = context.getResources().getStringArray(R.array.australia_translations);
			break;
		case 3: // BELGIUM
			result = context.getResources().getStringArray(R.array.belgium_translations);
			break;
		case 4: // BOSNIA AND HERZEGOVINA
			result = context.getResources().getStringArray(R.array.bosnia_translations);
			break;
		case 5: // BRAZIL
			result = context.getResources().getStringArray(R.array.brazil_translations);
			break;
		case 6: // CAMEROON
			result = context.getResources().getStringArray(R.array.cameroon_translations);
			break;
		case 7: // CHILE
			result = context.getResources().getStringArray(R.array.chile_translations);
			break;
		case 8: // COLOMBIA
			result = context.getResources().getStringArray(R.array.colombia_translations);
			break;
		case 9: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_translations);
			break;
		case 10: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_translations);
			break;
		case 11: // CROATIA
			result = context.getResources().getStringArray(R.array.croatia_translations);
			break;
		case 12: // ECUADOR
			result = context.getResources().getStringArray(R.array.ecuador_translations);
			break;
		case 13: // ENGLAND
			result = context.getResources().getStringArray(R.array.england_translations);
			break;
		case 14: // FRANCE
			result = context.getResources().getStringArray(R.array.france_translations);
			break;
		case 15: // GERMANY
			result = context.getResources().getStringArray(R.array.germany_translations);
			break;
		case 16: // GHANA
			result = context.getResources().getStringArray(R.array.ghana_translations);
			break;
		case 17: // GREECE
			result = context.getResources().getStringArray(R.array.greece_translations);
			break;
		case 18: // HONDURAS
			result = context.getResources().getStringArray(R.array.honduras_translations);
			break;
		case 19: // IRAN
			result = context.getResources().getStringArray(R.array.iran_translations);
			break;
		case 20: // ITALY
			result = context.getResources().getStringArray(R.array.italy_translations);
			break;
		case 21: // JAPAN
			result = context.getResources().getStringArray(R.array.japan_translations);
			break;
		case 22: // SOUTH KOREA (KOREA REPUBLIC)
			result = context.getResources().getStringArray(R.array.south_korea_translations);
			break;
		case 23: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_translations);
			break;
		case 24: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_translations);
			break;
		case 25: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_translations);
			break;
		case 26: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_translations);
			break;
		case 27: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_translations);
			break;
		case 28: // SPAIN
			result = context.getResources().getStringArray(R.array.spain_translations);
		case 29: // SWITZERLAND
			result = context.getResources().getStringArray(R.array.switzerland_translations);
			break;
		case 30: // URUGUAY
			result = context.getResources().getStringArray(R.array.uruguay_translations);
			break;
		case 31: // USA
			result = context.getResources().getStringArray(R.array.usa_translations);
			break;
		}

		mergeDownloaded(result, country, TRANSLATION);
		return result;
	}

	// Get specified translation for song
	public String getTranslation(int country, int song) {
		return getAllTranslations(country)[song];
	}

	// Get all transliterations for conutry
	public String[] getAllTransliterations(int country) {

		String[] result = null;

		switch (country) {
		case 0: // ALGERIA
			result = context.getResources().getStringArray(R.array.algeria_transliterations);
			break;
		case 1: // ARGENTINA
			result = context.getResources().getStringArray(R.array.argentina_transliterations);
			break;
		case 2: // AUSTRALIA
			result = context.getResources().getStringArray(R.array.australia_transliterations);
			break;
		case 3: // BELGIUM
			result = context.getResources().getStringArray(R.array.belgium_transliterations);
			break;
		case 4: // BOSNIA AND HERZEGOVINA
			result = context.getResources().getStringArray(R.array.bosnia_transliterations);
			break;
		case 5: // BRAZIL
			result = context.getResources().getStringArray(R.array.brazil_transliterations);
			break;
		case 6: // CAMEROON
			result = context.getResources().getStringArray(R.array.cameroon_transliterations);
			break;
		case 7: // CHILE
			result = context.getResources().getStringArray(R.array.chile_transliterations);
			break;
		case 8: // COLOMBIA
			result = context.getResources().getStringArray(R.array.colombia_transliterations);
			break;
		case 9: // COSTA RICA
			result = context.getResources().getStringArray(R.array.costa_rica_transliterations);
			break;
		case 10: // IVORY COAST (COTE D'IVORE)
			result = context.getResources().getStringArray(R.array.cote_d_transliterations);
			break;
		case 11: // CROATIA
			result = context.getResources().getStringArray(R.array.croatia_transliterations);
			break;
		case 12: // ECUADOR
			result = context.getResources().getStringArray(R.array.ecuador_transliterations);
			break;
		case 13: // ENGLAND
			result = context.getResources().getStringArray(R.array.england_transliterations);
			break;
		case 14: // FRANCE
			result = context.getResources().getStringArray(R.array.france_transliterations);
			break;
		case 15: // GERMANY
			result = context.getResources().getStringArray(R.array.germany_transliterations);
			break;
		case 16: // GHANA
			result = context.getResources().getStringArray(R.array.ghana_transliterations);
			break;
		case 17: // GREECE
			result = context.getResources().getStringArray(R.array.greece_transliterations);
			break;
		case 18: // HONDURAS
			result = context.getResources().getStringArray(R.array.honduras_transliterations);
			break;
		case 19: // IRAN
			result = context.getResources().getStringArray(R.array.iran_transliterations);
			break;
		case 20: // ITALY
			result = context.getResources().getStringArray(R.array.italy_transliterations);
			break;
		case 21: // JAPAN
			result = context.getResources().getStringArray(R.array.japan_transliterations);
			break;
		case 22: // SOUTH KOREA (KOREA REPUBLIC)
			result = context.getResources().getStringArray(R.array.south_korea_transliterations);
			break;
		case 23: // MEXICO
			result = context.getResources().getStringArray(R.array.mexico_transliterations);
			break;
		case 24: // NETHERLANDS
			result = context.getResources().getStringArray(R.array.netherlands_transliterations);
			break;
		case 25: // NIGERIA
			result = context.getResources().getStringArray(R.array.nigeria_transliterations);
			break;
		case 26: // PORTUGAL
			result = context.getResources().getStringArray(R.array.portugal_transliterations);
			break;
		case 27: // RUSSIA
			result = context.getResources().getStringArray(R.array.russia_transliterations);
			break;
		case 28: // SPAIN
			result = context.getResources().getStringArray(R.array.spain_transliterations);
			break;
		case 29: // SWITZERLAND
			result = context.getResources().getStringArray(R.array.switzerland_transliterations);
			break;
		case 30: // URUGUAY
			result = context.getResources().getStringArray(R.array.uruguay_transliterations);
			break;
		case 31: // USA
			result = context.getResources().getStringArray(R.array.usa_transliterations);
			break;

		}

		mergeDownloaded(result, country, TRANSLITERATION);
		return result;
	}

	// Get specified transliteration for song
	public String getTransliterations(int country, int song) {
		return getAllTransliterations(country)[song];
	}

	// Checks if external storage is available for read and write
	private boolean isExternalStorageMounted() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	// Get folder for specified country
	private File getFolder(int country) {
		if (!isExternalStorageMounted())
			return null;
		else {
			File folder = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.app_name)
					+ File.separator + "Country " + country);
			return (folder.mkdirs() || folder.isDirectory()) ? folder : null;
		}
	}

	// Get folder for specified song files
	private File getFolder(int country, int song) {
		File parent = getFolder(country);
		if (parent == null)
			return null;
		else {
			File folder = new File(parent, "Song " + song);
			return (folder.mkdirs() || folder.isDirectory()) ? folder : null;
		}
	}

	// Merge default URL array with local strings
	private void mergeDownloaded(String[] defaultURLs, int country, int type) {
		for (int i = 0; i < defaultURLs.length; i++) {
			String path = getDownloaded(country, i, type);
			if (path != null)
				defaultURLs[i] = path;
		}
	}

}
