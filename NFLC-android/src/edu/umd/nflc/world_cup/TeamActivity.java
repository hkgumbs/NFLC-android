package edu.umd.nflc.world_cup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import edu.umd.nflc.world_cup.inappbilling.util.IabHelper;
import edu.umd.nflc.world_cup.inappbilling.util.IabResult;
import edu.umd.nflc.world_cup.inappbilling.util.Purchase;

public class TeamActivity extends ActionBarActivity implements ListView.OnItemClickListener, View.OnClickListener,
		DialogInterface.OnClickListener, IabHelper.OnIabPurchaseFinishedListener {

	String[] songNames;
	String[] songSources;
	String teamName;

	int teamId;
	int iconId;
	boolean purchased;

	ChantPlayer chants;
	AlertDialog dialog;
	BaseAdapter adapter;
	Lookup lookup;

	// In-App Billing Variables
	private static final String TAG = "edu.umd.nflc.world_cup.inappbilling";
	IabHelper mHelper;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		// Your Google Play License Key for this app should be inserted here
		String base64EncodedPublicKey = "<your license key here>";

		// Setting up Google Play Billing in the Application
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					Log.d(TAG, "In-app Billing setup failed: " + result);
				} else {
					Log.d(TAG, "In-app Billing is set up OK");
				}
			}
		});

		lookup = new Lookup(this);
		teamName = getIntent().getExtras().getString("teamName");
		teamId = getIntent().getExtras().getInt("teamId");
		iconId = getIntent().getExtras().getInt("iconId");

		ViewGroup root = (ViewGroup) getLayoutInflater().inflate(R.layout.fragment_list, null);

		// show buy button if not bought
		purchased = getSharedPreferences("purchased", Context.MODE_PRIVATE).contains(Integer.toString(teamId));
		if (!purchased) {
			String priceTag = "Buy now to unlock!";
			View container = getLayoutInflater().inflate(R.layout.button_buy, root);
			Button buy = ((Button) container.findViewById(R.id.buy));
			buy.setText(priceTag);
			buy.setOnClickListener(this);
			addContentView(container, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));

			Spanned description = Html.fromHtml("Buying this country pack will give you access to <br />"
					+ "&#8226; All of this country's chants<br />&#8226; Lyrics for each chant<br />"
					+ "&#8226; Option for offline access<br />&#8226; Translations and transliterations");
			dialog = new AlertDialog.Builder(this).setTitle(priceTag).setMessage(description)
					.setPositiveButton("Buy", this).setNegativeButton(android.R.string.cancel, null).create();
		}
		setContentView(root);

		songNames = lookup.getAllSongs(teamId);
		songSources = lookup.getAllSources(teamId);

		ListView content = (ListView) findViewById(R.id.list);
		int songLimit = purchased ? songNames.length : 3;
		chants = new ChantPlayer(songSources);
		adapter = new PlaylistAdapter(this, songNames, chants, songLimit);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(teamName);
		actionBar.setIcon(iconId);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		invalidateOptionsMenu();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (isFinishing())
			chants.release();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.team, menu);
		if (lookup.isDownloaded(teamId))
			menu.findItem(R.id.download).setIcon(R.drawable.icon_delete);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.download:
			if (!purchased)
				dialog.show();

			else {
				new AsyncTask<Void, Void, Boolean>() {

					boolean download;
					ProgressDialog progress;

					@Override
					public void onPreExecute() {
						download = lookup.isDownloaded(teamId);
						progress = new ProgressDialog(TeamActivity.this);
						progress.setMessage(download ? "Deleting..." : "Downloading...");
						progress.setCancelable(false);
						progress.show();
					}

					@Override
					protected Boolean doInBackground(Void... params) {
						return download ? lookup.delete(teamId) : lookup.download(teamId);
					}

					@Override
					public void onPostExecute(Boolean result) {
						progress.dismiss();
						String message;
						if (!result) // error case
							message = (download ? "Deletion" : "Download") + " failed! Try again later.";

						else {
							message = "All of this team's chants have been "
									+ (download ? "deleted from" : "downloaded to") + " your device!";
							finish();
							startActivity(getIntent());
							overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
						}

						Toast.makeText(TeamActivity.this, message, Toast.LENGTH_SHORT).show();

					}

				}.execute();

			}

			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		chants.stop();

		if (purchased) {
			Intent intent = new Intent(this, PlayActivity.class);

			int[] iconIds = new int[songNames.length];
			int[] teamIds = new int[songNames.length];
			int[] songIds = new int[songNames.length];

			for (int i = 0; i < songNames.length; i++) {
				iconIds[i] = iconId;
				teamIds[i] = teamId;
				songIds[i] = i;
			}

			intent.putExtra("iconIds", iconIds);
			intent.putExtra("teamIds", teamIds);
			intent.putExtra("songIds", songIds);
			intent.putExtra("current", position);

			startActivity(intent);

		} else
			dialog.show();
	}

	@Override
	public void onClick(final View v) {
		dialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// Initiating a Google Play In-app Billing Purchase
		mHelper.launchPurchaseFlow(this, lookup.lookupSKU(teamId), 10001, this, "edu.umd.nflc");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
		if (result.isFailure() && result.getResponse() != 7) {
			Toast.makeText(this, "Something went wrong! Try again later.", Toast.LENGTH_LONG).show();

		} else if (result.getResponse() == 7 || purchase.getSku().equals(lookup.lookupSKU(teamId))) {
			// already purchased OR purchase successful
			getSharedPreferences("purchased", Context.MODE_PRIVATE).edit().putBoolean(Integer.toString(teamId), true)
					.commit();
			finish();
			startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

		}
	}
}
