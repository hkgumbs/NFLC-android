package edu.umd.nflc.world_cup;

import android.os.AsyncTask;

public class Request {

	public static final String HOST = "";

	// Result flags
	public static int OK = 1 << 0;
	public static int NETWORK_ERROR = 1 << 1;
	public static int MEMORY_ERROR = 1 << 2;

	public void downloadTrack(final String teamName, final String trackName, final Callback c) {
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				c.onComplete(OK, result);
			}

		}.execute();
	}

	public interface Callback {
		public void onComplete(int flag, String... results);
	}

}
