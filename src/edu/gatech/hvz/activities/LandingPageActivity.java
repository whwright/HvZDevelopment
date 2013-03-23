package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LandingPageActivity extends Activity {
	
	private Button reportKillButton;
	private Button showMyCodeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
		
		reportKillButton = (Button) findViewById(R.id.landingpage_reportkill_button);
		reportKillButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, ReportKillActivity.class));
				finish();
			}
		});
		
		showMyCodeButton = (Button) findViewById(R.id.landingpage_showmycode_button);
		showMyCodeButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingPageActivity.this, ShowMyCodeActivity.class));
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_page, menu);
		return true;
	}

}
