package edu.gatech.hvz.activities;
import com.actionbarsherlock.app.SherlockActivity;

import edu.gatech.hvz.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


/**
 * This activity displays help topics for an individual screen.
 * There is also a button to go to the main help menu
 *
 */

public class TopicActivity extends SherlockActivity 
{
	static public final String ARG_TEXT_ID = "text_id";
int mTextResourceId = 0;

/**
 * onCreate
 *
 * @param savedInstanceState Bundle
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_topic);
    Log.d("no","way");
    // Read the arguments from the Intent object.
    Intent in = getIntent ();
    mTextResourceId = in.getIntExtra (HelpActivity.ARG_TEXT_ID, 0);

    
    TextView textView = (TextView) findViewById (R.id.topic_text);
    textView.setMovementMethod (LinkMovementMethod.getInstance());
    textView.setText (Html.fromHtml (getString (mTextResourceId)));
    
}
public void onClickTopic (View v)
{
	 int id = v.getId ();


	 switch (id) {
	      case R.id.main_help_menu_button :
	           Intent intent = (new Intent(this, HelpActivity.class));
		       startActivity (intent);
	           break;
	      default: 
	    	   break;
	    }
}


}