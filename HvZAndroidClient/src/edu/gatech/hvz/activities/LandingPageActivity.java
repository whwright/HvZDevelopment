package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A landing page activity to let the user navigate to other activities.
 */
public class LandingPageActivity extends SherlockActivity {

	private ResourceManager resources;
	private ImageButton missionListButton,reportKillButton,profileButton,chatroomButton,killBoardButton,achievementsButton;
	
	private TextView welcomeTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setTitle("Humans Vs. Zombies");
		
		resources = ResourceManager.getResourceManager();
		//Redirect to the login page if the cookie is outdated or nonexistent
		if (resources.getPlayer() == null) {
			Log.e("LandingPageActivity", "Player was null, redirecting to login page");
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		} else {
			//populate the user's name
			welcomeTV = (TextView) findViewById(R.id.landing_welcome_text);
			welcomeTV.setText("Welcome, " + resources.getPlayer().getFName());
			
			/*Setup buttons and its listeners*/
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
			
			achievementsButton = (ImageButton) findViewById(R.id.landing_achievements);
			achievementsButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LandingPageActivity.this, AchievementActivity.class));
				}
			});
		
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.menu_contact:
			Intent lineIntent = new Intent(this, ContactAdminsActivity.class);
			startActivity(lineIntent);
			return true;
		case R.id.menu_about:
			Intent aboutintent = new Intent(this, AboutActivity.class);
			startActivity(aboutintent);
			return true;
		case R.id.menu_logout:
			resources.resetData();
			Intent login = new Intent(this, LoginActivity.class);
			startActivity(login);
			finish();
			return true;
		}
		return false;
	}
}
