package edu.gatech.hvz.activities;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Player;

public class KillBoardActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	static final int ZOMBIE = 0;
	static final int HUMAN = 1;
	static final int NUM_TABS = 2;

	private MyAdapter mAdapter;
	private ViewPager mPager;

	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kill_board);
		//Pages setup
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.killboardactivity_pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);

		//Tab setup
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		makeTab("ACTIVE", "Zombie");
		makeTab("ACTIVE", "Human");
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
		private Fragment[] playerFragments;
		
		public MyAdapter(FragmentManager fm) {
			super(fm);
			playerFragments = new Fragment[NUM_TABS];
		}

		@Override
		public int getCount() {
			return NUM_TABS;
		}

		@Override
		public Fragment getItem(int position) {
			if (playerFragments[position] == null) {
				playerFragments[position] = ArrayListFragment.newInstance(position);
			}
			return playerFragments[position];
		}
	}

	//Fragment for each player list
	public static class ArrayListFragment extends ListFragment {
		int mNum;
		ResourceManager resources;
		Player[] players;
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
			new PeopleGetTask().execute(mNum);
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.activity_kill_board_fragment, container, false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
		//TODO on click expand
		/* maybe cant click
		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Mission m = (Mission) getListAdapter().getItem(position);
			Log.i("MissionListActivity", "Trying to go to mission " + m.getName());
			Intent i = new Intent(this.getActivity(), MissionDetailActivity.class);
			i.putExtra("mission", m);
			startActivity(i);
		}
		*/
		private void setAdapter(Player[] players) {
			this.players = players;
			setListAdapter(new PlayerAdapter(getActivity(), android.R.layout.simple_list_item_1, players));
		}
		
		//ArrayAdapter for custom Player views
		public class PlayerAdapter extends ArrayAdapter<Player> {

			private LayoutInflater inflater;

			public PlayerAdapter(Context context, int textViewResourceId, Player[] objects) {
				super(context, textViewResourceId, objects);
				inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.activity_kill_board_item, null);
				}

				Player player = this.getItem(position);
				TextView name = (TextView) convertView.findViewById(R.id.killboardactivityitem_name_textview);
				name.setText(player.getPlayerName());
				//TODO the drawable
				Drawable ico = getResources().getDrawable(resources.getDataManager().getPlayerIcon(player));
				ico.setBounds(0, 0, 50, 50);
				name.setCompoundDrawables(ico, null, null, null);
				return convertView;
			}
		}


		//AsyncTask to grab Players
		private class PeopleGetTask extends AsyncTask<Integer, Void, Player[]> {
			protected Player[] doInBackground(Integer ... status) {
				Log.i("PeopleGetTask", "Trying to fetch people: " + status[0]);
				switch (status[0]) {
				case HUMAN:
				{
					List<Player> playerlist = resources.getDataManager().getHumans();
					if(playerlist == null){
						return null;
					}
					return playerlist.toArray(new Player[0]);
				}
				case ZOMBIE:
				{
					List<Player> playerlist = resources.getDataManager().getZombies("gt_name");
					if(playerlist == null){
						return null;
					}
					return playerlist.toArray(new Player[0]);
				}
				default:
					return null;
				}
			}

			protected void onPostExecute(Player[] players) {
				if (players != null) {
					Log.i("PeopleGetTask", "Some people retrieved");
					setAdapter(players);
				} else {
					Log.i("PeopleGetTask", "None people retrieved");
					TextView empty = (TextView)ArrayListFragment.this.getListView().getEmptyView();
					empty.setText("No players.");
				}
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_kill_board, menu);
		menu.removeItem(menu.getItem(0).getItemId());
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
	}
}