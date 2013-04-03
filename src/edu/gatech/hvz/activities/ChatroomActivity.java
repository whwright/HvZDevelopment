package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChatroomActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatroom);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_chatroom, menu);
		return true;
	}

}
