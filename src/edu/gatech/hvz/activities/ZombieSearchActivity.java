package edu.gatech.hvz.activities;

import java.util.ArrayList;
import java.util.List;
import edu.gatech.hvz.R;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zombie_search, menu);
		menu.removeItem(menu.getItem(0).getItemId());
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
	}
	
	/**
	 * Custom adapter for zombieList
	 * @author whwright
	 *
	 */
	private class ZombieAdapter extends ArrayAdapter<Player>
	{
		public ZombieAdapter(Context context, int textViewResourceId)
		{
			super(context, textViewResourceId);
			// TODO Auto-generated constructor stub
		}
		
		private List<Player> zombies;
		
		public ZombieAdapter(Context context, int resource, List<Player> zombies)
		{
			super(context, resource, zombies);
			this.zombies = zombies;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			
			if( v == null )
			{
				LayoutInflater inflator = LayoutInflater.from(getContext());
				v = inflator.inflate(R.layout.activity_zombie_search_list_item, null);
			}
			
			Player zombie = zombies.get(position);
			
			if( zombie != null )
			{
				TextView nameTextView = (TextView) v.findViewById(R.id.zombielistitem_zombiename_textview);
				if( nameTextView != null )
				{
					nameTextView.setText( zombie.getPlayerName() );
				}
				
				TextView feedTextView = (TextView) v.findViewById(R.id.zombielistitem_zombiefeedtime_textview);
				if( feedTextView != null )
				{
					feedTextView.setText( zombie.getStarveTimeFormatted() );
				}
			}
			
			return v;
		}
		
	}
	

}
