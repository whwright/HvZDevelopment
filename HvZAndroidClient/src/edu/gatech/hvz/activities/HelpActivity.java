
package edu.gatech.hvz.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import edu.gatech.hvz.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the Help activity.
 * It is the main menu for the help screen and gives the user access to various help topics
 *
 */

public class HelpActivity extends SherlockActivity 
{

	static public final String ARG_TEXT_ID = "text_id";

	/**
	 * onCreate - called when the activity is first created.
	 * Called when the activity is first created. 
	 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
	 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
	 * 
	 * Always followed by onStart().
	 *
	 */
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_help);
	
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Help");
	    
	    // This is to handle html usage
	    TextView textView = (TextView) findViewById (R.id.help_page_intro);
	    if (textView != null) {
	       textView.setMovementMethod (LinkMovementMethod.getInstance());
	       textView.setText (Html.fromHtml (getString (R.string.help_page_intro_html)));
	      
	    }
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
    

	/**
	 * Handle the click of one of the help buttons on the page.
	 * Start an activity to display the help text for the topic selected.
	 * 
	 * @param v View
	 * @return void
	 */
	public void onClickHelp (View v)
	{
	    int id = v.getId ();
	    int textId = -1;
	    
	    switch (id) {
	      case R.id.help_button1 :
	           textId = R.string.topic_howdoi_aggregate;
	           break;
	      case R.id.help_button2 :
	         	setContentView(R.layout.activity_help_by_page);
	            //list view for the selection of topics by page
	            ListView lv = (ListView) findViewById(R.id.pageselectlv);
	            
	            String[] items = { "Achievements", "Chat Room", "Contact Admin", "Kill Board", "Kill Map","Login", 
	            		"Missions", "Player Profile", "Report Kill"};
	            
	            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                        android.R.layout.simple_list_item_1, items);
	            lv.setAdapter(adapter);
	            lv.setOnItemClickListener( new OnItemClickListener() {
	        		@Override
	        		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	        				long arg3) {
	        			int textId = -1;
	        			 textId = R.string.topic_achivement_section;
	        			switch(arg2){
	        			case 0:
	        				textId = R.string.topic_achivement_section;
	        				break;
	        			case 1:
	        				textId = R.string.topic_chat_section;
	        				break;
	        			case 2:
	        				textId = R.string.topic_contact_section;
	        				break;
	        			case 3:
	        				textId = R.string.topic_killboard_section;
	        				break;
	        			case 4:
	        				textId = R.string.topic_killmap_section;
	        				break;
	        			case 5:
	        				textId = R.string.topic_login_section;
	        				break;
	        			case 6:
	        				textId = R.string.topic_mission_list_section;
	        				break;
	        			case 7:
	        				textId = R.string.topic_profile_section;
	        				break;
	        			case 8:
	        				textId = R.string.topic_killrep_section;
	        				break;
	        			default:
	        				break;
	        			}
	        			if (textId >= 0) startInfoActivity (textId);
	        		    else toast ("Detailed Help for that topic is not available.", true);
	        		};
	            });
	            return;
	          
	      case R.id.help_button3 :
	           textId = R.string.topic_ts_aggregate;
	           break;
	      default: 
	    	   break;
	    }
	
	    if (textId >= 0) startInfoActivity (textId);
	    else toast ("Detailed Help for that topic is not available.", true);
	}

	/**
	 * Start a TopicActivity and show the text indicated by argument 1.
	 * 
	 * @param textId int - resource id of the text to show
	 * @return void
	 */
	
	public void startInfoActivity (int textId)
	{
	    if (textId >= 0) {
	       Intent intent = (new Intent(this, TopicActivity.class));
	       intent.putExtra (ARG_TEXT_ID, textId);
	       startActivity (intent);
	    } else {
	      toast ("No information is available for topic: " + textId, true);
	    }
	} // end startInfoActivity

	/**
	 * Show a string on the screen via Toast.
	 * 
	 * @param msg String
	 * @param longLength boolean - show message a long time
	 * @return void
	 */
	
	public void toast (String msg, boolean longLength)
	{
	    Toast.makeText (getApplicationContext(), msg, 
	                    (longLength ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
	                   ).show ();
	}

} // end class
