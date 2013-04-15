package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Player;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	private Player player;
	private ResourceManager resources;
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
		
		//avatar = (ImageView)findViewById(R.id.profileactivity_avatar);
		
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
		
		friend_list_button = (ImageButton)findViewById(R.id.profileactivity_friends);
		friend_list_button.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileActivity.this, FriendListActivity.class));
			}
		});
		
		String user = "";
		user+=player.getPlayerName()+" - "+player.getFaction();
		((TextView)findViewById(R.id.profileactivity_name_textview)).setText(user);		
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
