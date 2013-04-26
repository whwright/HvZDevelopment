package edu.gatech.hvz.activities;

import java.util.ArrayList;
import java.util.List;
import edu.gatech.hvz.R;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ZombieSearchActivity extends Activity {
	
	private ListView zombieList;
	private List<Player> zombies;
	//implement searchable
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zombie_search);
		
		zombies = new ArrayList<Player>();
		zombieList = (ListView) findViewById(R.id.zombiesearch_zombie_listview);
		
		getZombieData();
		updateListView();
	}

	private void getZombieData() 
	{
		Intent i = getIntent();
		zombies = i.getParcelableArrayListExtra("ZombiesList");
	}

	private void updateListView() 
	{
		zombieList.setAdapter( new ZombieAdapter(ZombieSearchActivity.this, R.layout.activity_zombie_search_list_item, zombies) );
		zombieList.setOnItemClickListener(new OnItemClickListener()
        {
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long myLong) 
			{
				Player selectedFromList = (Player) zombieList.getItemAtPosition(myItemInt);
				if( selectedFromList == null )
				{
					Intent returnIntent = new Intent();
					setResult(RESULT_CANCELED, returnIntent);        
				}
				else
				{
					Intent i = new Intent();
					i.putExtra("ZombieObject", selectedFromList);
					setResult(RESULT_OK, i);
				}
				finish();
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	/**
	 * Custom adapter for zombieList
	 * @author whwright
	 *
	 */
	private class ZombieAdapter extends ArrayAdapter<Player>
	{
		private List<Player> zombies;
		
		public ZombieAdapter(Context context, int resource, List<Player> zombies)
		{
			super(context, resource, zombies);
			this.zombies = zombies;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{		
			if( convertView == null )
			{
				LayoutInflater inflator = LayoutInflater.from(getContext());
				convertView = inflator.inflate(R.layout.activity_zombie_search_list_item, null);
			}
			
			Player zombie = zombies.get(position);
			
			TextView nameTextView = (TextView) convertView.findViewById(R.id.zombielistitem_zombiename_textview);
			nameTextView.setText( zombie.getPlayerName() );

			String starveString = "Starves: " + EntityUtils.stringToFormattedDate(zombie.getStarveTime());
			starveString += ", " + zombie.getKills() + " " + ((zombie.getKills() == 1) ? "kill" : "kills");
			TextView feedTextView = (TextView) convertView.findViewById(R.id.zombielistitem_zombiefeedtime_textview);
			feedTextView.setText(starveString);
			
			return convertView;
		}
		
	}
	

}
