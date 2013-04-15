package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	private Player player;
	private ResourceManager resources;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		resources = ResourceManager.getResourceManager();
		player = resources.getPlayer();
		((TextView)findViewById(R.id.profileactivity_name_textview)).setText(player.getPlayerName());		
		((TextView)findViewById(R.id.profileactivity_slogan_textview)).setText(player.getSlogan());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile, menu);
		menu.removeItem(menu.getItem(0).getItemId());
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
	}

}
