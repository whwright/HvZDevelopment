package edu.gatech.hvz.activities;

import java.util.Map;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import edu.gatech.hvz.entities.Player;
import edu.gatech.hvz.networking.CASAuthenticator;
import edu.gatech.hvz.ResourceManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen activity to let the user sign in with their GTID
 */
public class LoginActivity extends SherlockActivity {

	private ResourceManager resources;
	private Button loginButton;
	private EditText userNameEditText;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setTitle("Login");

		resources = ResourceManager.getResourceManager();

		//Login button listener
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
		getSupportMenuInflater().inflate(R.menu.no_login_menu, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.no_login_help:
			       Intent intent = (new Intent(this, TopicActivity.class));
			       intent.putExtra ("text_id", R.string.topic_login_section);
			       startActivity (intent);
				return true;
		}
		return false;
	}
	@Override
	protected void onPause() {
		super.onPause();
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * Attempt to login with username and password entered in textfields.
	 */
	public void doLogin() {
		dialog = ProgressDialog.show(this, "Logging in", "Logging in and loading your data", true, false);
		String user, pass;
		user = ((EditText) findViewById(R.id.edittext_username)).getText().toString();
		pass = ((EditText) findViewById(R.id.edittext_password)).getText().toString();
		new LoginRequest().execute(user, pass);
	}


	/*
	 * Asynchronous Task for making a login request
	 */
	private class LoginRequest extends AsyncTask<String, Void, Boolean> {

		protected Boolean doInBackground(String ... info) {
			Map<String, String> cookies = new CASAuthenticator(info[0], info[1]).connect();
			if (cookies != null) {
				//Save the successful login id for later use
				Editor editor = getSharedPreferences("HvZGaTechSettings",0).edit();
				resources.getNetworkManager().setCookies(cookies);
				editor.putString("gt_name", info[0]);
				editor.commit();

				//Get player data
				Player p = resources.getDataManager().getPlayerByName(info[0]);
				if (p != null) {
					resources.setPlayer(p);
					return true;
				}
			}
			return false;
		}

		protected void onPostExecute(Boolean success) {
			dialog.dismiss();
			if (success) {
				startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
				LoginActivity.this.finish();
			} else {
				Toast.makeText(LoginActivity.this, "There was an error logging in to your account", Toast.LENGTH_LONG).show();
			}
		}
	}
	
}
