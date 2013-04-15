package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class KillBoardActivity extends Activity {
	private ResourceManager resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resources = ResourceManager.getResourceManager();
		setContentView(R.layout.activity_kill_board);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_page, menu);
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case 0:
				Intent lineIntent = new Intent(KillBoardActivity.this, ContactAdminsActivity.class);
				startActivity(lineIntent);
				return true;
			case 1:
				Intent messages = new Intent(KillBoardActivity.this, MessageListActivity.class);
				startActivity(messages);
				return true;
			case 2:
				resources.resetData();
				Intent login = new Intent(KillBoardActivity.this, LoginActivity.class);
				startActivity(login);
				finish();
				return true;
		}
		return false;
	}

}
