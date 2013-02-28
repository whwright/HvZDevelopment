package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LandingPageActivity extends Activity {
	
	private Button reportKillButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
		
		reportKillButton = (Button) findViewById(R.id.landingpage_reportkill_button);
		reportKillButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				//new ChangeViewRequest().execute("ReportKill");
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_page, menu);
		return true;
	}
	
	private class ChangeViewRequest extends AsyncTask<String, Void, Void> 
	{

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onProgressUpdate(Void ... stuff) {
		}
		
		protected void onPostExecute(Void stuff) {
		}
		
	}

}
