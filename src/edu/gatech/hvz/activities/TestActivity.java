package edu.gatech.hvz.activities;

import java.util.Map;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.networking.CASAuthenticator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends Activity {

	ResourceManager resources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		resources = ResourceManager.getResourceManager();
		
		Button button = (Button) findViewById(R.id.button1);
		button.setEnabled(false);		
		button.setOnClickListener(new Button.OnClickListener() {
	   		 public void onClick(View v) {
	   			 doKill();
	   		 }
		});
		
		button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new Button.OnClickListener() {
	   		 public void onClick(View v) {
	   			 doLogin();
	   		 }
		});
		
		((EditText)findViewById(R.id.editText1)).setText("kshah45");		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	
	
	public void doKill() {
		String player = ((EditText)findViewById(R.id.editText1)).getText().toString();
		new KillRequest().execute(player);
	}
	
	public void doLogin() {
		String user, pass;
		user = ((EditText) findViewById(R.id.username)).getText().toString();
		pass = ((EditText) findViewById(R.id.password)).getText().toString();
		new LoginRequest().execute(user, pass);
	}
	
	
	
	private class LoginRequest extends AsyncTask<String, Void, Void> {
		boolean success = false;
		
		protected Void doInBackground(String ... info) {
			Map<String, String> cookies = new CASAuthenticator(info[0], info[1]).connect();
			if (cookies != null) {
				success = true;
				edu.gatech.hvz.ResourceManager.getResourceManager().getNetworkManager().setCookies(cookies);
			}
			return null;
		}
		
		protected void onProgressUpdate(Void ... stuff) {
		}
		
		protected void onPostExecute(Void stuff) {
			if (success) {
				Button button = (Button) findViewById(R.id.button1);
				
				button.setEnabled(true);
			}
		}
	 }
	
	
	
	private class KillRequest extends AsyncTask<String, Void, Kill[]> {
	     protected Kill[] doInBackground(String ... player) {
	    	 return resources.getDataManager().getKillsByPLayer(player[0]); 
	     }

	     protected void onProgressUpdate(Integer... progress) {
	     }

	     protected void onPostExecute(Kill[] kills) { 
	 		String str = "";
			
			for (Kill k : kills) {
				str += k + "\n";
			}
			
			TextView textBox = (TextView) findViewById(R.id.textView2);
			textBox.setText(str);
	     }
	 }
	
}
