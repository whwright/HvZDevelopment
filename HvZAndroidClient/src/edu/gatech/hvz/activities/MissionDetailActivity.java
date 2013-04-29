package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.Mission;

public class MissionDetailActivity extends SherlockActivity {

	private Mission mission;
	private TextView name, faction, location, time, description;
	private ResourceManager resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_detail);
		resources = ResourceManager.getResourceManager();
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Mission Detail");

		mission = (Mission)this.getIntent().getSerializableExtra("mission");
		name = (TextView) findViewById(R.id.missiondetailactivity_name_textview);
		faction = (TextView) findViewById(R.id.missiondetailactivity_faction_textview);
		location = (TextView) findViewById(R.id.missiondetailactivity_location_textview);
		time = (TextView) findViewById(R.id.missiondetailactivity_time_textview);
		description = (TextView) findViewById(R.id.missiondetailactivity_description_textview);

		name.setText(mission.getName());
		faction.setText("Faction: " + mission.getFaction());
		location.setText("Location: " + mission.getLocation());
		time.setText("Time: " + EntityUtils.stringToFormattedDate(mission.getStart()) + 
				" to " + EntityUtils.stringToFormattedDate(mission.getEnd()));
		description.setText(mission.getDescription());
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
