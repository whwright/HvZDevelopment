package edu.gatech.hvz.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import edu.gatech.hvz.R;

public class MissionDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mission_detail, menu);
		return true;
	}

}
