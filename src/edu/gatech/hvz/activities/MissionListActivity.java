package edu.gatech.hvz.activities;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Locale;

import android.content.Context;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Mission;

public class MissionListActivity extends FragmentActivity {
	static final int ACTIVE = 0;
	static final int ISSUED = 1;
	static final int CLOSED = 2;
	static final int NUM_TABS = 3;

	MyAdapter mAdapter;

	ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_list);

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		// Watch for button clicks.
		Button button = (Button)findViewById(R.id.missionlistactivity_active_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(ACTIVE);
			}
		});
		button = (Button)findViewById(R.id.missionlistactivity_issued_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(ISSUED);
			}
		});
		button = (Button)findViewById(R.id.missionlistactivity_closed_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPager.setCurrentItem(CLOSED);
			}
		});
	}

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_TABS;
		}

		@Override
		public Fragment getItem(int position) {
			return ArrayListFragment.newInstance(position);
		}
	}

	public static class ArrayListFragment extends ListFragment {
		int mNum;
		ResourceManager resources;
		/**
		 * Create a new instance of CountingFragment, providing "num"
		 * as an argument.
		 */
		static ArrayListFragment newInstance(int num) {
			ArrayListFragment f = new ArrayListFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);
			return f;
		}


		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
			resources = ResourceManager.getResourceManager();
		}

		/**
		 * The Fragment's UI is just a simple text view showing its
		 * instance number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.activity_mission_list_fragment, container, false);
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			new MissionTask().execute(mNum);

		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Log.i("FragmentList", "Item clicked: " + id);
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
				Log.i("MissionTask", "Getting missions: " + status[0]);
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

			protected void onProgressUpdate(Void ... voids) {
			}

			protected void onPostExecute(Mission[] missions) {
				if (missions != null && ArrayListFragment.this.getListAdapter() == null) {
					Log.i("MissionTask", "Some missions retrieved");
					ArrayListFragment.this.setListAdapter(new MissionAdapter(getActivity(),
							android.R.layout.simple_list_item_1, missions));
				}
			}
		}
	}  
}