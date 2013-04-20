package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LandingPageActivity extends Activity {

	private ResourceManager resources;
	private ImageButton missionListButton,reportKillButton,profileButton,chatroomButton,killBoardButton,killMapButton;
	
	private TextView welcomeTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
		
		resources = ResourceManager.getResourceManager();
		
		if (resources.getPlayer() == null) {
			Log.e("LandingPageActivity", "Player was null, redirecting to login page");
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		} else {
		
			welcomeTV = (TextView) findViewById(R.id.landing_welcome_text);
			welcomeTV.setText("Welcome, " + resources.getPlayer().getFName());
			
			missionListButton = (ImageButton) findViewById(R.id.landing_mission);
			missionListButton.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LandingPageActivity.this,MissionListActivity.class));
				}
			});
			
			reportKillButton = (ImageButton) findViewById(R.id.landing_report);
			reportKillButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(resources.getPlayer().getFaction().equals("ZOMBIE")){
						startActivity(new Intent(LandingPageActivity.this, ReportKillActivity.class));
					}
					else{
						startActivity(new Intent(LandingPageActivity.this, ShowMyCodeActivity.class));
					}
				}
			});
			
			profileButton = (ImageButton) findViewById(R.id.landing_profile);
			profileButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LandingPageActivity.this, ProfileActivity.class));
				}
			});
			
			chatroomButton = (ImageButton) findViewById(R.id.landing_chatroom);
			chatroomButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LandingPageActivity.this, ChatroomActivity.class));
				}
			});
			
			killBoardButton = (ImageButton) findViewById(R.id.landing_killboard);
			killBoardButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LandingPageActivity.this, KillBoardActivity.class));
				}
			});
			
			killMapButton = (ImageButton) findViewById(R.id.landing_killmap);
			killMapButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LandingPageActivity.this, KillMapActivity.class));
				}
			});
		
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.menu_contact:
				Intent lineIntent = new Intent(LandingPageActivity.this, ContactAdminsActivity.class);
				startActivity(lineIntent);
				return true;
			case R.id.menu_messages:
				Intent messages = new Intent(LandingPageActivity.this, MessageListActivity.class);
				startActivity(messages);
				return true;
			case R.id.menu_logout:
				resources.resetData();
				Intent login = new Intent(LandingPageActivity.this, LoginActivity.class);
				startActivity(login);
				finish();
				return true;
		}
		return false;
	}
}
