package edu.gatech.hvz.activities;

import java.util.Arrays;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Achievement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AchievementActivity extends SherlockActivity {
	
	private ResourceManager resources;
	private AsyncTask<Void, Void, Boolean> task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement);
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Achievements");
		
		resources = ResourceManager.getResourceManager();
		
		task = new AchievementTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	protected void onPause() {
		super.onPause();
		if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}
	

	private class AchievementTask extends AsyncTask<Void, Void, Boolean> {
		private Achievement[] achievements;
		
		protected Boolean doInBackground(Void ... voids) {
			try {
				achievements = resources.getDataManager().getAchievements();
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		protected void onPostExecute(Boolean success) {
			if (success) {
				Log.i("AchievementActivity", Arrays.toString(achievements));
			} else {
				Toast.makeText(AchievementActivity.this, "There was an error fetching your achievements", Toast.LENGTH_LONG).show();
			}
		}
	}
}
