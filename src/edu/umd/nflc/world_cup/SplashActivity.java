package edu.umd.nflc.world_cup;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class SplashActivity extends Activity {

	// DELAY IN MILISECONDS BEFORE LAUNCHING MAIN
	private static int DELAY = 750;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
				finish();
			}
		}, DELAY);
	}
}
