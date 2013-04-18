package edu.gatech.hvz.activities;

import java.util.Arrays;
import java.util.Map;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Achievement;
import edu.gatech.hvz.entities.Player;
import edu.gatech.hvz.networking.CASAuthenticator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class AchievementActivity extends Activity {
	
	private ResourceManager resources;
	private AsyncTask<Void, Void, Boolean> task;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement);
		
		resources = ResourceManager.getResourceManager();
		
		task = new AchievementTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_achievement, menu);
		menu.removeItem(menu.getItem(0).getItemId());
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
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
