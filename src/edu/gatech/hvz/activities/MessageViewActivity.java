package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.R.layout;
import edu.gatech.hvz.R.menu;
import edu.gatech.hvz.entities.Message;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MessageViewActivity extends Activity 
{
	private Message messageBeingViewed;
	private Button replyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_view);
		
		//get message passed from MessagingActivity
		messageBeingViewed = getIntent().getParcelableExtra("SelectedMessage");
		
		//populate message view
		TextView fromTextView = (TextView) findViewById(R.id.messageview_from_textview);
		fromTextView.setText( messageBeingViewed.getUserFrom(), TextView.BufferType.EDITABLE );
		
		TextView timeTextView = (TextView) findViewById(R.id.messageview_time_textfield);
		timeTextView.setText( messageBeingViewed.getTimeStamp(), TextView.BufferType.EDITABLE );
		
		TextView bodyTextView = (TextView) findViewById(R.id.messagefrom_body_textfield);
		bodyTextView.setText( messageBeingViewed.getMessage(), TextView.BufferType.EDITABLE );
		
		replyButton = (Button) findViewById(R.id.messageview_reply_button);
		replyButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				doReply();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_view, menu);
		return true;
	}
	
	private void doReply() 
	{
		Intent returnIntent = new Intent(MessageViewActivity.this, MessageComposeActivity.class);
		returnIntent.putExtra("MessageToReply", messageBeingViewed);
		startActivity(returnIntent);
	}

}
