package edu.gatech.hvz.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.gatech.hvz.entities.*;
import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.networking.CASAuthenticator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup.LayoutParams;


public class ReportKillActivity extends Activity {
	
    private static final int CAMERA_REQUEST = 1888; 
    private static final int ZOMBIE_SEARCH_REQUEST = 9270; 
    	
	private Button reportKillButton, captureQrButton;
	private ImageButton zombieOneButton, zombieTwoButton;
	private Uri imageUri;
	
	private ArrayList<Player> zombies;
	private Player zombie1;
	private Player zombie2;
	
	private String zombieToChange;

	private ProgressDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_kill);
		
		new ZombieRequest().execute();
		loadingDialog = ProgressDialog.show(this, "Loading...", "Fetching Zombie names", false);
		
		captureQrButton = (Button) findViewById(R.id.reportkill_qrpicture_button);
		captureQrButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				doCaptureQr();
			}
		});
		
		reportKillButton = (Button) findViewById(R.id.reportkill_reportkill_button);
		reportKillButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				doReportKill();
			}
		});
		
		zombieOneButton = (ImageButton) findViewById(R.id.reportkill_zombie1_imagebutton);
		zombieOneButton.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchZombie("one");
			}			
		});
		
		zombieTwoButton = (ImageButton) findViewById(R.id.reportkill_zombie2_imagebutton);
		zombieTwoButton.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchZombie("two");
			}			
		});
		
	}

	private void setZombieFieldsToClosestToDeath() {
		setZombie1(zombies.get(0));
		setZombie2(zombies.get(1));
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
	
	private void searchZombie(String zombieNumber) 
	{
		zombieToChange = zombieNumber;
		Intent i = new Intent(ReportKillActivity.this, ZombieSearchActivity.class);
		i.putExtra("ZombiesList", zombies);
		startActivityForResult(i, ZOMBIE_SEARCH_REQUEST);
	}
	
	private void setZombie1(Player zombie)
	{
		zombie1 = zombie;
		EditText textToChange = (EditText) findViewById(R.id.reportkill_zombie1_edittext);
		textToChange.setText( zombie.getPlayerName(), TextView.BufferType.EDITABLE);
	}
	
	private void setZombie2(Player zombie)
	{
		zombie2 = zombie;
		EditText textToChange = (EditText) findViewById(R.id.reportkill_zombie2_edittext);
		textToChange.setText( zombie.getPlayerName(), TextView.BufferType.EDITABLE);
	}
	
	/**
	 * This method handles the result of startActivityForResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if( requestCode == ZOMBIE_SEARCH_REQUEST )
		{
			if( resultCode == RESULT_OK )
			{
				Player zombie = data.getExtras().getParcelable("ZombieObject");
				if( zombie == null )
				{
					Toast.makeText(ReportKillActivity.this, "Zombie came back null =[", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if( zombieToChange.equals("one") )
					{
						setZombie1(zombie);
					}
					else if( zombieToChange.equals("two") )
					{
						setZombie2(zombie);
					}
				}
			}
			else if( resultCode == RESULT_CANCELED )
			{
				
			}
			
		}
	}

	/**
	 * Async class that handles sending kills to the server
	 * @author whwright
	 *
	 */
	private class KillRequest extends AsyncTask<String, Void, Boolean> 
	{
		private String errorMessage;
		
		@Override
		protected Boolean doInBackground(String... params) {
			//CHECK VICTIM CODE IS EMPTY
			String victimPlayerCode = ((EditText) findViewById(R.id.reportkill_playercode_edittext)).getText().toString();
			if( victimPlayerCode.equals("") )
			{
				errorMessage = "The player code of your victim is required.";
				return false;
			}
						
			//CHECKING VICTIM ON SERVER
			Player victim = ResourceManager.getResourceManager().getDataManager().getPlayerByCode( victimPlayerCode );
			if( victim == null )
			{
				errorMessage = "Invalid player code! Try again.  If the problem persits, contact the admins.";
				return false;
			}
			else if( victim.getFaction() != "HUMAN" )
			{
				errorMessage = "That human's a spy! (The code you entered does not belong to an active human)";
				return false;
			}
			
			return null;
		}
		
		protected void onProgressUpdate(Void ... stuff) {

		}
		
		protected void onPostExecute(Boolean success) {
			if( success )
			{
				
			}
			else
			{
				Toast.makeText(ReportKillActivity.this, 
						errorMessage, 
						Toast.LENGTH_SHORT).show();
			}
			reportKillButton.setEnabled(true);
			reportKillButton.setText( getString(R.string.reportkill_reportkill_string) );
			
		}
	}
	
	/**
	 * Async class that handles getting the list of active Zombies
	 * @author whwright
	 *
	 */
	private class ZombieRequest extends AsyncTask<Void, Void, Boolean>
	{
		protected Boolean doInBackground(Void... params) 
		{
			zombies = (ArrayList<Player>) ResourceManager.getResourceManager().getDataManager().getZombies("starve_time");
			if( zombies != null )
			{
				return true;
			}
			return false;
		}
		
		protected void onProgressUpdate(Void ... voids) 
		{
	    }

	    protected void onPostExecute(Boolean success) 
	    {
	    	loadingDialog.dismiss();
	    	if( success )
	    	{
	    		setZombieFieldsToClosestToDeath();
	    	}
	    	else
	    	{
	    		Toast.makeText(ReportKillActivity.this, "Error fetching Zombie names.", Toast.LENGTH_SHORT).show();
	    	}
	    }
		
	}
	
}
