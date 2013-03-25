package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class ReportKillActivity extends Activity {
	
    private static final int CAMERA_REQUEST = 1888; 
	
	private Button reportKillButton, captureQrButton;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_kill);
		/*
		captureQrButton = (Button) findViewById(R.id.reportkill_qrpicture_button);
		captureQrButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				doCaptureQr();
			}
		});
		*/
		
		reportKillButton = (Button) findViewById(R.id.reportkill_reportkill_button);
		reportKillButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				doReportKill();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_kill, menu);
		return true;
	}
	
	private void doReportKill()
	{
		reportKillButton.setEnabled(false);
		reportKillButton.setText( getString(R.string.reportkill_reportkillprogress_string) );
		new KillRequest().execute();
	}
	
	private void doCaptureQr()
	{
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	private class KillRequest extends AsyncTask<String, Void, Void> 
	{
		private boolean success;

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		protected void onProgressUpdate(Void ... stuff) {

		}
		
		protected void onPostExecute(Void stuff) {
			reportKillButton.setEnabled(true);
			reportKillButton.setText( getString(R.string.reportkill_reportkill_string) );
		}
	}
}
