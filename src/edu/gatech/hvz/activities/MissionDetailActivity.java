package edu.gatech.hvz.activities;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;
import edu.gatech.hvz.R;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.Mission;

public class MissionDetailActivity extends Activity {

	private Mission mission;
	private TextView name, faction, location, time, description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_detail);

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
		description.setText(Html.fromHtml(mission.getDescription()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
