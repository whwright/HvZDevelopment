package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * A simple activity to display an About page.  This contains
 * contact information, as well as licenses for software used.
 */
public class AboutActivity extends SherlockActivity {

	private ResourceManager resources;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		resources = ResourceManager.getResourceManager();
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle(getString(R.string.title_activity_about));
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
