package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidhive.imagefromurl.ImageLoader;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends SherlockActivity {

	private Player player;
	private ResourceManager resources;
	private ImageLoader imgLoader;
	private ImageView avatar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		resources = ResourceManager.getResourceManager();
		player = resources.getPlayer();
		imgLoader = new ImageLoader(this);
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Profile");
		
		avatar = (ImageView)findViewById(R.id.profileactivity_avatar);
		imgLoader.DisplayImage(resources.getDataManager().getPlayerAvatar(player), R.drawable.ic_launcher, avatar);
						
		((TextView)findViewById(R.id.profileactivity_name_textview)).setText(player.getPlayerName());		
		((TextView)findViewById(R.id.profileactivity_slogan_textview)).setText(player.getSlogan());
		((TextView)findViewById(R.id.profileactivity_faction_textview)).setText(player.getFaction());
		if (player.getFaction().equals("ZOMBIE")) {
			((TextView)findViewById(R.id.profileactivity_starve_textview)).setText("Starves: " + EntityUtils.stringToFormattedDate(player.getStarveTime()));
			((TextView)findViewById(R.id.profileactivity_kills_textview)).setText(player.getKills() + " " + ((player.getKills() == 1) ? "kill" : "kills"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

}
