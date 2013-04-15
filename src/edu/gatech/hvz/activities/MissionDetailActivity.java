package edu.gatech.hvz.activities;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;
import edu.gatech.hvz.R;
import edu.gatech.hvz.entities.Mission;

public class MissionDetailActivity extends Activity {

	private Mission mission;
	private TextView name, faction, location, time, description;
	Format formatter;

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

		formatter = new SimpleDateFormat("EEE hh:mm aa", Locale.US);

		name.setText(mission.getName());
		faction.setText("Faction: " + mission.getFaction());
		location.setText("Location: " + mission.getLocation());
		time.setText("Time: " + formatter.format(mission.getStartDate()) + 
				" to " + formatter.format(mission.getEndDate()));
		description.setText(Html.fromHtml(mission.getDescription()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mission_detail, menu);
		menu.removeItem(menu.getItem(0).getItemId());
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
	}

}
