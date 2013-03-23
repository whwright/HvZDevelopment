package edu.gatech.hvz.activities;

import java.util.Map;

import edu.gatech.hvz.R;
import edu.gatech.hvz.networking.CASAuthenticator;
import edu.gatech.hvz.ResourceManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private ResourceManager resources;
	private Button loginButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		resources = ResourceManager.getResourceManager();
		
		//Uncomment this to bypass logging in for basic testing
		//Set the intent to whatever
//		startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
//		finish();
		
		loginButton = (Button) findViewById(R.id.button_login);
		loginButton.setOnClickListener(new Button.OnClickListener() {
	   		 public void onClick(View v) {
	   			 doLogin();
	   		 }
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	public void doLogin() {
		loginButton.setEnabled(false);
		loginButton.setText("Attempting login...");
		String user, pass;
		user = ((EditText) findViewById(R.id.edittext_username)).getText().toString();
		pass = ((EditText) findViewById(R.id.edittext_password)).getText().toString();
		new LoginRequest().execute(user, pass);
	}
	
	
	private class LoginRequest extends AsyncTask<String, Void, Void> {
		boolean success = false;
		
		protected Void doInBackground(String ... info) {
			Map<String, String> cookies = new CASAuthenticator(info[0], info[1]).connect();
			if (cookies != null) {
				success = true;
				resources.getNetworkManager().setCookies(cookies);
			}
			return null;
		}
		
		protected void onProgressUpdate(Void ... stuff) {
		}
		
		protected void onPostExecute(Void stuff) {
			if (success) {
				startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
				finish();
			} else {
				loginButton.setEnabled(true);
				loginButton.setText("Login (failed, try again)");
			}
		}
	 }
	
}
