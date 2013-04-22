package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidhive.imagefromurl.ImageLoader;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends SherlockActivity {

	private Player player;
	private ResourceManager resources;
	private ImageLoader imgLoader;
	private ImageView avatar;
	private ImageButton achievements_button;
	private ImageButton messages_button;
	private ImageButton friend_list_button;
	
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
		
		
		achievements_button = (ImageButton)findViewById(R.id.profileactivity_achievements);
		achievements_button.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileActivity.this, AchievementActivity.class));
			}
		});
		
		messages_button = (ImageButton)findViewById(R.id.profileactivity_messages);
		messages_button.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileActivity.this, MessagingActivity.class));
			}
		});
		
		String user = "";
		user+=player.getPlayerName()+" - "+player.getFaction();
		((TextView)findViewById(R.id.profileactivity_name_textview)).setText(user);		
		((TextView)findViewById(R.id.profileactivity_slogan_textview)).setText(player.getSlogan());
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
