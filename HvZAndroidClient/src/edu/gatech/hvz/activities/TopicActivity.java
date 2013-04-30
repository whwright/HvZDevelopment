package edu.gatech.hvz.activities;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;


/**
 * This activity displays help topics for an individual screen.
 */
public class TopicActivity extends SherlockActivity 
{
	static public final String ARG_TEXT_ID = "text_id";
	int mTextResourceId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView (R.layout.activity_topic);
	    
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Help");
	    
	    Log.d("no","way");
	    // Read the arguments from the Intent object.
	    Intent in = getIntent ();
	    mTextResourceId = in.getIntExtra (HelpActivity.ARG_TEXT_ID, 0);
	
	    
	    TextView textView = (TextView) findViewById (R.id.topic_text);
	    textView.setMovementMethod (LinkMovementMethod.getInstance());
	    textView.setText (Html.fromHtml (getString (mTextResourceId)));
	    
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				return true;
			default:
				return false;
		}
	}
}