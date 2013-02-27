package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.R.layout;
import edu.gatech.hvz.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LandingPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_page, menu);
		return true;
	}

}
