package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Player;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LandingPageActivity extends Activity {

	private ResourceManager resources;
	private ImageButton missionListButton;
	private ImageButton reportKillButton;
	private ImageButton profileButton;
	private ImageButton chatroomButton;
	private ImageButton killBoardButton;
	private ImageButton killMapButton;
	
	private ProgressDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
		
		missionListButton = (ImageButton) findViewById(R.id.landing_mission);
		missionListButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this,MissionList.class));
			}
		});
		
		reportKillButton = (ImageButton) findViewById(R.id.landing_report);
		reportKillButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, ReportKillActivity.class));
			}
		});
		
		profileButton = (ImageButton) findViewById(R.id.landing_profile);
		profileButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, Profile.class));
			}
		});
		
		chatroomButton = (ImageButton) findViewById(R.id.landing_chatroom);
		chatroomButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, Chatroom.class));
			}
		});
		
		killBoardButton = (ImageButton) findViewById(R.id.landing_killboard);
		killBoardButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, KillBoard.class));
			}
		});
		
		killMapButton = (ImageButton) findViewById(R.id.landing_killmap);
		killMapButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, KillMap.class));
			}
		});
		
		resources = ResourceManager.getResourceManager();
		
		getPlayerData();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_page, menu);
		return true;
	}
	
	private void getPlayerData() {
		String gt_name = getSharedPreferences("HvZGaTechSettings", 0).getString("gt_name", "");
		new PlayerRequest().execute(gt_name);
		loadingDialog = ProgressDialog.show(this, "Loading", "Fetching your player data.", false);
		
	}
	
	private class PlayerRequest extends AsyncTask<String, Void, Player> {
	     protected Player doInBackground(String ... player) {
	    	 return resources.getDataManager().getPlayerByName(player[0]); 
	     }

	     protected void onProgressUpdate(Void ... voids) {
	     }

	     protected void onPostExecute(Player p) { 
	    	 loadingDialog.dismiss();
	    	 if (p == null) {
	    		 Toast.makeText(LandingPageActivity.this,
	    				 "Could not fetch player data.",
	    				 Toast.LENGTH_SHORT).show();
	    	 } else {
	    		 resources.setPlayer(p);
	    		 TextView welcomeTV = (TextView) findViewById(R.id.landing_welcome_text);
	    		 welcomeTV.setText("Welcome, " + p.getFName());
	    	 }
	     }
	 }

}
