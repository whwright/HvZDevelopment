package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.R.layout;
import edu.gatech.hvz.R.menu;
import edu.gatech.hvz.entities.Message;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class MessageComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_compose);
		
		Message replyMessage = getIntent().getParcelableExtra("MessageToReply");
		if( replyMessage != null )
		{
			EditText toEditText = (EditText) findViewById(R.id.messagecompose_to_edittext);
			toEditText.setText( replyMessage.getUserFrom(), TextView.BufferType.EDITABLE );
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_compose, menu);
		return true;
	}

}
