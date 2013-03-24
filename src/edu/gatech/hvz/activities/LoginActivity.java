package edu.gatech.hvz.activities;

import java.util.Map;

import edu.gatech.hvz.R;
import edu.gatech.hvz.networking.CASAuthenticator;
import edu.gatech.hvz.ResourceManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	private ResourceManager resources;
	private Button loginButton;
	private EditText userNameEditText;
	
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
		
		//Set the user's login if they have entered it successfully before
		userNameEditText = (EditText) findViewById(R.id.edittext_username);
		userNameEditText.setText(getSharedPreferences("HvZGaTechSettings",0).getString("gt_name", ""));
				
		
		
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
	
	
	private class LoginRequest extends AsyncTask<String, Void, Boolean> {

		protected Boolean doInBackground(String ... info) {
			Map<String, String> cookies = new CASAuthenticator(info[0], info[1]).connect();
			if (cookies != null) {
				//Save the successful login id for later use
				Editor editor = getSharedPreferences("HvZGaTechSettings",0).edit();
				resources.getNetworkManager().setCookies(cookies);
				editor.putString("gt_name", info[0]);
				editor.commit();
				return true;
			}
			return false;
		}
		
		protected void onProgressUpdate(Void ... stuff) {
		}
		
		protected void onPostExecute(Boolean success) {
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
