package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

public class MainMenuActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void login(View v){
		Intent i = new Intent(MainMenuActivity.this, LoginActivity.class);
		startActivity(i);
		
	}
	
}
