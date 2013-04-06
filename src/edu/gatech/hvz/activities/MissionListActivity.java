package edu.gatech.hvz.activities;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Mission;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class MissionListActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	static final int ACTIVE = 0;
	static final int ISSUED = 1;
	static final int CLOSED = 2;
	static final int NUM_TABS = 3;

	private MyAdapter mAdapter;
	private ViewPager mPager;

	private TabHost mTabHost;
	
	private int lastTabPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_list);
		//Pages setup
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.missionlistactivity_pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);

		//Tab setup
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		makeTab("ACTIVE", "Active");
		makeTab("ACTIVE", "Issued");
		makeTab("ACTIVE", "Closed");
		mTabHost.setOnTabChangedListener(this);
	}

	private void makeTab(String tag, String text) {
		TabSpec tab = mTabHost.newTabSpec(tag);
		tab.setIndicator(text);
		tab.setContent(new TabFactory(this));
		mTabHost.addTab(tab);
	}
	@Override
	public void onTabChanged(String tabId) {
		int pos = this.mTabHost.getCurrentTab();
		this.mPager.setCurrentItem(pos);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {		
	}

	@Override
	public void onPageSelected(int position) {
		mTabHost.setCurrentTab(position);
	}

	public static class TabFactory implements TabContentFactory {

		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	//Adapter for the pager
	public static class MyAdapter extends FragmentPagerAdapter {
		private Fragment[] missionFragments;
		
		public MyAdapter(FragmentManager fm) {
			super(fm);
			missionFragments = new Fragment[3];
		}

		@Override
		public int getCount() {
			return NUM_TABS;
		}

		@Override
		public Fragment getItem(int position) {
			if (missionFragments[position] == null) {
				missionFragments[position] = ArrayListFragment.newInstance(position);
			}
			return missionFragments[position];
		}
	}

	//Fragment for each mission list
	public static class ArrayListFragment extends ListFragment {
		int mNum;
		ResourceManager resources;
		Mission[] missions;
		/**
		 * Create a new instance of CountingFragment, providing "num"
		 * as an argument.
		 */
		public static ArrayListFragment newInstance(int num) {
			ArrayListFragment f = new ArrayListFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);
			f.setRetainInstance(true);
			return f;
		}


		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
			resources = ResourceManager.getResourceManager();
			new MissionTask().execute(mNum);
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.activity_mission_list_fragment, container, false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Mission m = (Mission) getListAdapter().getItem(position);
			Log.i("MissionListActivity", "Trying to go to mission " + m.getName());
			Intent i = new Intent(this.getActivity(), MissionDetailActivity.class);
			i.putExtra("mission", m);
			startActivity(i);
		}
		
		private void setAdapter(Mission[] missions) {
			this.missions = missions;
			setListAdapter(new MissionAdapter(getActivity(), android.R.layout.simple_list_item_1, missions));
		}
		
		//ArrayAdapter for custom Mission views
		public class MissionAdapter extends ArrayAdapter<Mission> {

			private LayoutInflater inflater;
			private Format formatter;

			public MissionAdapter(Context context, int textViewResourceId, Mission[] objects) {
				super(context, textViewResourceId, objects);
				formatter = new SimpleDateFormat("EEE hh:mm aa", Locale.US);
				inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.activity_mission_list_item, null);
				}

				Mission mission = this.getItem(position);
				TextView name = (TextView) convertView.findViewById(R.id.missionlistactivityitem_name_textview);
				TextView location = (TextView) convertView.findViewById(R.id.missionlistactivityitem_location_textview);
				TextView time = (TextView) convertView.findViewById(R.id.missionlistactivityitem_time_textview);
				name.setText(mission.getName());
				location.setText(mission.getLocation());
				time.setText(formatter.format(mission.getStartDate()) + " to " + formatter.format(mission.getEndDate()));

				return convertView;
			}
		}


		//AsyncTask to grab Missions
		private class MissionTask extends AsyncTask<Integer, Void, Mission[]> {
			protected Mission[] doInBackground(Integer ... status) {
				Log.i("MissionTask", "Trying to fetch missions: " + status[0]);
				switch (status[0]) {
				case ACTIVE:
					return resources.getDataManager().getMissions(Mission.Status.ACTIVE);
				case ISSUED:
					return resources.getDataManager().getMissions(Mission.Status.ISSUED);
				case CLOSED:
					return resources.getDataManager().getMissions(Mission.Status.CLOSED);
				default:
					return null;
				}
			}

			protected void onPostExecute(Mission[] missions) {
				if (missions != null) {
					Log.i("MissionTask", "Some missions retrieved");
					setAdapter(missions);
				}
			}
		}
	}
}