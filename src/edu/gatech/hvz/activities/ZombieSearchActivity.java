package edu.gatech.hvz.activities;

import java.util.ArrayList;
import java.util.List;
import edu.gatech.hvz.R;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ZombieSearchActivity extends Activity {
	
	private ListView zombieList;
	private List<PlayerStringWrapper> zombies;
	//implement searchable
	
	private class PlayerStringWrapper
	{
		private Player player;
		private String name;
		
		public PlayerStringWrapper(Player zombie, String name) {
			this.player = zombie;
			this.name = name;
		}
		public String toString()
		{
			return name;
		}
		public Player getPlayer()
		{
			return player;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zombie_search);
		
		zombies = new ArrayList<PlayerStringWrapper>();
		zombieList = (ListView) findViewById(R.id.zombiesearch_zombie_listview);
		
		getZombieData();
		updateListView();
	}

	private void getZombieData() 
	{
		Intent i = getIntent();
		ArrayList<Player> zombies = i.getParcelableArrayListExtra("ZombiesList");
		for( Player z : zombies )
		{
			addToZombieNames(z);
		}
	}

	private void updateListView() 
	{
		zombieList.setAdapter(new ArrayAdapter<PlayerStringWrapper>(ZombieSearchActivity.this, android.R.layout.simple_list_item_1, zombies));
		zombieList.setOnItemClickListener(new OnItemClickListener()
        {
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long myLong) 
			{
				PlayerStringWrapper selectedFromList = (PlayerStringWrapper) zombieList.getItemAtPosition(myItemInt);
				if( selectedFromList == null )
				{
					Intent returnIntent = new Intent();
					setResult(RESULT_CANCELED, returnIntent);        
				}
				else
				{
					Intent i = new Intent();
					i.putExtra("ZombieObject", selectedFromList.getPlayer());
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
		return true;
	}
	
	private void addToZombieNames(Player zombie)
	{
		String name = zombie.getFName() + " " + zombie.getLName() + " - Starve Time: " + zombie.getStarveTime();
		zombies.add( new PlayerStringWrapper( zombie, name ));
	}
	
	

}
