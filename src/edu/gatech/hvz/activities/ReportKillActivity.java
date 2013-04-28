package edu.gatech.hvz.activities;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Player;

public class ReportKillActivity extends SherlockActivity {
    private static final int ZOMBIE_SEARCH_REQUEST = 9270;
    private static final int KILL_LOCATION_REQUEST = 9271;
	
	private Button reportKillButton, killLocationButton, captureQrButton;
	private int mapX = -1, mapY = -1;
	
	private ArrayList<Player> zombies;
	private Player zombie1;
	private Player zombie2;
	private String zombieToChange;

	private ProgressDialog loadingDialog;
	private ResourceManager resources;
	AsyncTask<Void, Void, Boolean> zombieTask;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_kill);
		
		resources = ResourceManager.getResourceManager();
		
		//ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Report Kill");
		
		//get list of zombies
		zombieTask = new ZombieRequest().execute();
		loadingDialog = ProgressDialog.show(this, "Loading...", "Fetching Zombie names", false);
				
		captureQrButton = (Button) findViewById(R.id.reportkill_qrpicture_button);
		captureQrButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				doCaptureQr();
			}
		});
		
		killLocationButton = (Button) findViewById(R.id.reportkill_map_button);
		killLocationButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ReportKillActivity.this, ReportKillMapActivity.class);
				startActivityForResult(i, KILL_LOCATION_REQUEST);
			}
		});	
		
		reportKillButton = (Button) findViewById(R.id.reportkill_reportkill_button);
		reportKillButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				doReportKill();
			}
		});
		
		EditText zombie1 = (EditText)findViewById(R.id.reportkill_zombie1_edittext);
		zombie1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchZombie("one");
			}
		});
		
		EditText zombie2 = (EditText)findViewById(R.id.reportkill_zombie2_edittext);
		zombie2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchZombie("two");
			}
		});
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		if( zombieTask != null && zombieTask.getStatus() == AsyncTask.Status.RUNNING )
		{
			zombieTask.cancel(true);
			if( loadingDialog.isShowing() ) {
				loadingDialog.dismiss();
			}
		}
	}

	/**
	 * Sets the 2 zombies that are closest to death.
	 */
	private void setZombieFieldsToClosestToDeath() {
		setZombie(1, zombies.get(0));
		setZombie(2, zombies.get(1));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Sends the kill to the server to be reported via KillRequest task.
	 */
	private void doReportKill()
	{
		reportKillButton.setEnabled(false);
		reportKillButton.setText( getString(R.string.reportkill_reportkillprogress_string) );
		new KillRequest().execute();
	}
	
	/**
	 * 
	 */
	private void doCaptureQr()
	{
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}
	
	/**
	 * Starts the ZombieSearchActivity for a result, and remembers which zombie it should change on return.
	 * @param zombieNumber
	 */
	private void searchZombie(String zombieNumber) 
	{
		zombieToChange = zombieNumber;
		Intent i = new Intent(ReportKillActivity.this, ZombieSearchActivity.class);
		i.putExtra("ZombiesList", zombies);
		startActivityForResult(i, ZOMBIE_SEARCH_REQUEST);
	}
	
	/**
	 * Sets the zombie 1 or 2, based on zombieNumber, to the Player object passed in. Also set's the
	 * corresponding text field that Player's name;
	 * @param zombieNumber
	 * @param zombie
	 */
	private void setZombie(int zombieNumber, Player zombie)
	{
		EditText textToChange;
		switch( zombieNumber ) {
			case 1:
				zombie1 = zombie;
				textToChange = (EditText) findViewById(R.id.reportkill_zombie1_edittext);
				textToChange.setText( zombie.getPlayerName(), TextView.BufferType.EDITABLE);
				break;
			case 2:
				zombie2 = zombie;
				textToChange = (EditText) findViewById(R.id.reportkill_zombie2_edittext);
				textToChange.setText( zombie.getPlayerName(), TextView.BufferType.EDITABLE);
				break;
			default:
				break;
		}
	}
	
	/**
	 * This method handles the result of startActivityForResult, which is called when the user launches ZombieSearchAcitivty,
	 * and chooses a Zombie.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (scanResult != null) {
			((EditText)findViewById(R.id.reportkill_playercode_edittext)).setText(scanResult.getContents());
		}
		else if( requestCode == ZOMBIE_SEARCH_REQUEST )
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
						//setZombie1(zombie);
						setZombie(1, zombie);
					}
					else if( zombieToChange.equals("two") )
					{
						//setZombie2(zombie);
						setZombie(2, zombie);
					}
				}
			}
			else if( resultCode == RESULT_CANCELED )
			{

			}
		} else if (requestCode == KILL_LOCATION_REQUEST && resultCode == RESULT_OK) {
			mapX = data.getIntExtra("mapX", 0);
			mapY = data.getIntExtra("mapY", 0);
		}
	}

	/**
	 * Asynchronous task that handles will send a kill to the server.
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
			
			//Check if coordinates have been picked
			if (mapX == -1 || mapY == -1) {
				errorMessage = "Please select your kill location.";
				return false;
			}

			Kill kill = new Kill(victimPlayerCode, zombie1.getGTName(), zombie2.getGTName(), mapX, mapY);
			Log.i("ReportKillActivity", kill.toString());
			resources.getDataManager().postKill(kill);
			return true;
		}

		protected void onPostExecute(Boolean success) {
			if( success )
			{
				Toast.makeText(ReportKillActivity.this,
						"Kill reported.",
						Toast.LENGTH_SHORT).show();
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
	 * Asynchronous task that gets the list of active Zombies sorted by starve time.
	 * @author whwright
	 */
	private class ZombieRequest extends AsyncTask<Void, Void, Boolean>
	{
		protected Boolean doInBackground(Void... params) 
		{
			zombies = (ArrayList<Player>) resources.getDataManager().getZombies("starve_time");
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
