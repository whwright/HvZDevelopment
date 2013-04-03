package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MissionListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_page, menu);
		menu.add(0,0,0,"Show Detail");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case 0:
			Intent lineIntent = new Intent(MissionListActivity.this,MissionDetailActivity.class);
			startActivity(lineIntent);
			return true;
		}
		return false;
	}

}
