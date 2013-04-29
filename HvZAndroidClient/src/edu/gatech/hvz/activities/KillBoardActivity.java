package edu.gatech.hvz.activities;

import java.util.LinkedList;
import java.util.List;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidhive.imagefromurl.ImageLoader;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Player;

/**
 * An activity for viewing every player's name, faction, and slogan.
 */
public class KillBoardActivity extends SherlockFragmentActivity {
	static final int ZOMBIE = 1;
	static final int HUMAN = 2;

	private ViewPager mPager;
	private TabsAdapter mTabsAdapter;
	private ResourceManager resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kill_board);
		resources = ResourceManager.getResourceManager();
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Kill Board");

		// Pages setup
		mPager = (ViewPager) findViewById(R.id.killboardactivity_pager);
		mTabsAdapter = new TabsAdapter(this, bar, mPager);
		
		//Add the tabs
		Bundle bundle = new Bundle();
		bundle.putInt("type", HUMAN);
		mTabsAdapter.addTab(bar.newTab().setText("Humans"), ArrayListFragment.class, bundle);
		bundle = new Bundle();
		bundle.putInt("type", ZOMBIE);
		mTabsAdapter.addTab(bar.newTab().setText("Zombies"), ArrayListFragment.class, bundle);

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
		       intent.putExtra ("text_id", R.string.topic_killboard_section);
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

	/**
	 * A fragment for each player list, either Human or Zombie.
	 */
	public static class ArrayListFragment extends SherlockListFragment {
		
		int type;
		ResourceManager resources;
		PlayerAdapter adapter;
		List<Player> players;
		AsyncTask<Integer, Void, List<Player>> task;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			this.setRetainInstance(true);
			
			//Get the type of the fragment, either "HUMAN" or "ZOMBIE"
			type = getArguments() != null ? getArguments().getInt("type") : 1;
			resources = ResourceManager.getResourceManager();

			//Set up the ListFragment backing
			players = new LinkedList<Player>();
			adapter = new PlayerAdapter(getActivity(), android.R.layout.simple_list_item_1, players);
			setListAdapter(adapter);
			
			//Grab the players
			task = new PeopleGetTask().execute(type);
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

		@Override
		public void onPause() {
			super.onPause();
			if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
				task.cancel(true);
			}
		}
		
		
		//ArrayAdapter for custom Player views
		public class PlayerAdapter extends ArrayAdapter<Player> {

			private LayoutInflater inflater;
			private ImageLoader imgLoader;
			RelativeLayout.LayoutParams lp; 
			public PlayerAdapter(Context context, int textViewResourceId, List<Player> objects) {
				super(context, textViewResourceId, objects);
				inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				imgLoader = new ImageLoader(getActivity());
				lp = new RelativeLayout.LayoutParams(100, 100);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.activity_kill_board_item, null);
				}

				Player player = this.getItem(position);
				
				ImageView avatar = (ImageView) convertView.findViewById(R.id.killboardlistactivityitem_avatar_imageview);
				TextView name = (TextView) convertView.findViewById(R.id.killboardlistactivityitem_name_textview);
				TextView slogan = (TextView) convertView.findViewById(R.id.killboardlistactivityitem_slogan_textview);
				TextView starvetime = (TextView) convertView.findViewById(R.id.killboardlistactivityitem_starvetime_textview);
				avatar.setLayoutParams(lp);
				imgLoader.DisplayImage(resources.getDataManager().getPlayerAvatar(player), R.drawable.ic_launcher, avatar);
				name.setText(player.getPlayerName());
				slogan.setText(player.getSlogan());
				//Set extra data if player is a Zombie
				if (player.getFaction().equals(FactionType.ZOMBIE.name())) {
					String starveString = "Starves: " + EntityUtils.stringToFormattedDate(player.getStarveTime());
					starveString += ", " + player.getKills() + " " + ((player.getKills() == 1) ? "kill" : "kills");
					starvetime.setText(starveString);
				}
				return convertView;
			}
		}

		//AsyncTask to grab Players
		private class PeopleGetTask extends AsyncTask<Integer, Void, List<Player>> {
			protected List<Player> doInBackground(Integer ... status) {
				Log.i("PeopleGetTask", "Trying to fetch people: " + status[0]);
				List<Player> playerlist = null;
				switch (status[0]) {
				case HUMAN:
					playerlist = resources.getDataManager().getHumans();
					break;
				case ZOMBIE:
					playerlist = resources.getDataManager().getZombies("starve_time");
					break;
				}
				return playerlist;
			}

			protected void onPostExecute(List<Player> playerlist) {
				if (playerlist != null && playerlist.size() > 0) {
					Log.i("PeopleGetTask", "Some people retrieved");
					players.addAll(playerlist);
					adapter.notifyDataSetChanged();
				} else {
					Log.i("PeopleGetTask", "None people retrieved");
					TextView empty = (TextView)ArrayListFragment.this.getListView().getEmptyView();
					empty.setText("No players.");
				}
			}
		}
	}
}