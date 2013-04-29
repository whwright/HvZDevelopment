package edu.gatech.hvz.activities;

import java.util.LinkedList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidhive.imagefromurl.ImageLoader;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Achievement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

/**
 * An activity for allowing the user to view achievements.
 * In this activity, the user can view both unlocked and locked achievements.
 */
public class AchievementActivity extends SherlockFragmentActivity {
	
	private ViewPager mPager;
	TabsAdapter mTabsAdapter;
	private ResourceManager resources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement);
		resources = ResourceManager.getResourceManager();
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Achievements");

		// Pages setup
		mPager = (ViewPager) findViewById(R.id.achievementactivity_pager);
		mTabsAdapter = new TabsAdapter(this, bar, mPager);
		
		//Add the tabs
		Bundle bundle = new Bundle();
		bundle.putString("achievementType", "UNLOCKED");
		mTabsAdapter.addTab(bar.newTab().setText("Unlocked"), AchievementFragment.class, bundle);
		bundle = new Bundle();
		bundle.putString("achievementType", "LOCKED");
		mTabsAdapter.addTab(bar.newTab().setText("Locked"), AchievementFragment.class, bundle);
		
		if (savedInstanceState != null) {
			bar.setSelectedNavigationItem(savedInstanceState.getInt("tab"));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getSupportActionBar().getSelectedNavigationIndex());
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
		case R.id.menu_help:
		       Intent intent = (new Intent(this, TopicActivity.class));
		       intent.putExtra ("text_id", R.string.topic_achivement_section);
		       startActivity (intent);
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
		
	public static class AchievementFragment extends SherlockListFragment {
		private String achievementType;
		private AchievementAdapter adapter;
		private List<Achievement> achievementList;
		private ResourceManager resources;
		private AsyncTask<Void, Void, Boolean> task;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.setRetainInstance(true);
			resources = ResourceManager.getResourceManager();
			achievementType = getArguments() != null ? getArguments().getString("achievementType") : "UNLOCKED";
			achievementList = new LinkedList<Achievement>();
			adapter = new AchievementAdapter(getActivity(),	android.R.layout.simple_list_item_1, achievementList);
			setListAdapter(adapter);
			task = new AchievementTask().execute();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.activity_achievement_list_fragment,
					container, false);
		}
		
		@Override
		public void onPause() {
			super.onPause();
			if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
				task.cancel(true);
			}
		}

		// ArrayAdapter for custom Mission views
		public class AchievementAdapter extends ArrayAdapter<Achievement> {

			private LayoutInflater inflater;
			private ImageLoader imgLoader;

			public AchievementAdapter(Context context, int textViewResourceId,
					List<Achievement> objects) {
				super(context, textViewResourceId, objects);
				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				imgLoader = new ImageLoader(getActivity());
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.activity_achievement_list_item, null);
				}

				Achievement achievement = this.getItem(position);
				TextView name = (TextView) convertView.findViewById(R.id.achievementlistactivityitem_name_textview);
				TextView description = (TextView) convertView.findViewById(R.id.achievementlistactivityitem_description_textview);
				ImageView avatar = (ImageView) convertView.findViewById(R.id.achievementlistactivityitem_avatar_imageview);
				name.setText(achievement.getName());
				description.setText(achievement.getDescription());
				imgLoader.DisplayImage(achievement.getAvatarURL(), R.drawable.ic_launcher, avatar);
				
				return convertView;
			}
		}
		
		private class AchievementTask extends AsyncTask<Void, Void, Boolean> {
			private List<Achievement> achievements;
			
			protected Boolean doInBackground(Void ... voids) {
				try {
					Log.i("AchievementTask", "Getting achievements of type: " + achievementType);
					achievements = resources.getDataManager().getAchievements(achievementType);
					return true;
				} catch (Exception e) {
					return false;
				}
			}

			protected void onPostExecute(Boolean success) {
				if (success) {
					achievementList.addAll(achievements);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getActivity(), "There was an error fetching your achievements.", Toast.LENGTH_LONG).show();
				}
			}
		}
	}
}
