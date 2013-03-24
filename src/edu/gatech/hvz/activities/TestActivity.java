package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Kill;
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
		
		Button button = (Button) findViewById(R.id.landingpage_reportkill_button);	
		button.setOnClickListener(new Button.OnClickListener() {
	   		 public void onClick(View v) {
	   			 doKill();
	   		 }
		});
		
		((EditText)findViewById(R.id.reportkill_playercode_textfield)).setText("kshah45");		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	
	
	public void doKill() {
		String player = ((EditText)findViewById(R.id.reportkill_playercode_textfield)).getText().toString();
		new KillRequest().execute(player);
	}
		
	private class KillRequest extends AsyncTask<String, Void, Kill[]> {
	     protected Kill[] doInBackground(String ... player) {
	    	 return resources.getDataManager().getKillsByPlayer(player[0]); 
	     }

	     protected void onProgressUpdate(Void ... voids) {
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
