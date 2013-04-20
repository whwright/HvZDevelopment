package edu.gatech.hvz.activities;

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
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.Mission;

public class MissionListActivity extends SherlockFragmentActivity {
	static final int ACTIVE = 0;
	static final int ISSUED = 1;
	static final int CLOSED = 2;

	private ViewPager mPager;
	TabsAdapter mTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_list);

		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Missions");

		// Pages setup
		mPager = (ViewPager) findViewById(R.id.missionlistactivity_pager);
		mTabsAdapter = new TabsAdapter(this, bar, mPager);
		
		//Add the tabs
		Bundle bundle = new Bundle();
		bundle.putInt("type", Mission.Status.ACTIVE.ordinal());
		mTabsAdapter.addTab(bar.newTab().setText("Active"), ArrayListFragment.class, bundle);
		bundle = new Bundle();
		bundle.putInt("type", Mission.Status.ISSUED.ordinal());
		mTabsAdapter.addTab(bar.newTab().setText("Issued"), ArrayListFragment.class, bundle);
		bundle = new Bundle();
		bundle.putInt("type", Mission.Status.CLOSED.ordinal());
		mTabsAdapter.addTab(bar.newTab().setText("Closed"), ArrayListFragment.class, bundle);
		
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

	// Fragment for each mission list
	public static class ArrayListFragment extends SherlockListFragment {
		int missionType;
		ResourceManager resources;
		Mission[] missions;
		AsyncTask<Integer, Void, Mission[]> task;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			missionType = getArguments() != null ? getArguments().getInt("type") : 0;
			resources = ResourceManager.getResourceManager();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.activity_mission_list_fragment,
					container, false);
		}
		
		@Override
		public void onPause() {
			super.onPause();
			if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
				task.cancel(true);
			}
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			if (missions == null) {
				((TextView) getListView().getEmptyView()).setText("Loading...");
				task = new MissionTask().execute(missionType);
			} else {
				TextView empty = (TextView) getListView().getEmptyView();
				empty.setText("No missions.");
			}
		}
		
		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Mission m = (Mission) getListAdapter().getItem(position);
			Log.i("MissionListActivity",
					"Trying to go to mission " + m.getName());
			Intent i = new Intent(this.getActivity(),
					MissionDetailActivity.class);
			i.putExtra("mission", m);
			startActivity(i);
		}

		private void setAdapter(Mission[] missions) {
			this.missions = missions;
			setListAdapter(new MissionAdapter(getActivity(),
					android.R.layout.simple_list_item_1, missions));
		}

		// ArrayAdapter for custom Mission views
		public class MissionAdapter extends ArrayAdapter<Mission> {

			private LayoutInflater inflater;

			public MissionAdapter(Context context, int textViewResourceId,
					Mission[] objects) {
				super(context, textViewResourceId, objects);
				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.activity_mission_list_item, null);
				}

				Mission mission = this.getItem(position);
				TextView name = (TextView) convertView
						.findViewById(R.id.missionlistactivityitem_name_textview);
				TextView location = (TextView) convertView
						.findViewById(R.id.missionlistactivityitem_location_textview);
				TextView time = (TextView) convertView
						.findViewById(R.id.missionlistactivityitem_time_textview);
				name.setText(mission.getName());
				location.setText(mission.getLocation());
				time.setText(EntityUtils.stringToFormattedDate(mission.getStart()) + " to "
						+ EntityUtils.stringToFormattedDate(mission.getEnd()));

				return convertView;
			}
		}

		// AsyncTask to grab Missions
		private class MissionTask extends AsyncTask<Integer, Void, Mission[]> {
			protected Mission[] doInBackground(Integer... status) {
				Log.i("MissionTask", "Trying to fetch missions: " + status[0]);
				switch (status[0]) {
				case ACTIVE:
					return resources.getDataManager().getMissions(
							Mission.Status.ACTIVE);
				case ISSUED:
					return resources.getDataManager().getMissions(
							Mission.Status.ISSUED);
				case CLOSED:
					return resources.getDataManager().getMissions(
							Mission.Status.CLOSED);
				default:
					return null;
				}
			}

			protected void onPostExecute(Mission[] missions) {
				if (missions != null) {
					Log.i("MissionTask", "Some missions retrieved");
					setAdapter(missions);
				} else {
					setAdapter(new Mission[0]);
					TextView empty = (TextView) ArrayListFragment.this
							.getListView().getEmptyView();
					empty.setText("No missions.");
				}
			}
		}
	}
}