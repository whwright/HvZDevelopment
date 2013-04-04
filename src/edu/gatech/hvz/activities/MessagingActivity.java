package edu.gatech.hvz.activities;

import java.util.List;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Message;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessagingActivity extends Activity {

	private List<Message> messages;
	private ListView messageList;
	
	private Button composeButton;
	
	private ProgressDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messaging);
		
		new FetchMessages().execute();
		loadingDialog = ProgressDialog.show(this,  "Loading...", "Fetching messages", false);
		
		messageList = (ListView) findViewById(R.id.messaging_message_listview);
		
		composeButton = (Button) findViewById(R.id.messaging_compose_button);
		composeButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MessagingActivity.this, ComposeMessageActivity.class));
			}
		});
		
		
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messaging, menu);
		return true;
	}
	
	private class FetchMessages extends AsyncTask<Void, Void, Boolean>
	{
		private String errorMessage;

		@Override
		protected Boolean doInBackground(Void... params) {
			messages = ResourceManager.getResourceManager().getDataManager().getMessages();
			if( messages != null )
			{
				return true;
			}
			errorMessage = "Error fetching messages";
			return false;
		}
		
		
		protected void onProgressUpdate(Void ... voids) {
	    }

	    protected void onPostExecute(Boolean success) { 
	    	loadingDialog.dismiss();
	    	if( success )
	    	{
	    		//messageList.setAdapter(new ArrayAdapter<Message>(MessagingActivity.this, android.R.layout.simple_list_item_1, messages));
	    		messageList.setAdapter( new MessagesAdapter(MessagingActivity.this, R.layout.message_list_layout, messages));
	    	}
	    	else
	    	{
	    		Toast.makeText(MessagingActivity.this, 
	    				errorMessage, 
	    				Toast.LENGTH_SHORT).show();
	    	}
	    }
		
	}

	private class MessagesAdapter extends ArrayAdapter<Message>
	{

		public MessagesAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			// TODO Auto-generated constructor stub
		}
		
		private List<Message> messages;
		
		public MessagesAdapter(Context context, int resource, List<Message> messages)
		{
			super(context, resource, messages);
			this.messages = messages;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			
			if( v == null )
			{
				LayoutInflater inflator;
				inflator = LayoutInflater.from(getContext());
				v = inflator.inflate(R.layout.message_list_layout, null);
			}
			
			Message m = messages.get(position);
			
			if( m != null )
			{
				TextView fromTextView = (TextView) v.findViewById(R.id.messagelayout_from_textview);
				if( fromTextView != null )
				{
					fromTextView.setText( m.getUserFrom() );
				}
				
				TextView timeTextView = (TextView) v.findViewById(R.id.messagelayout_time_textview);
				if( timeTextView != null )
				{
					timeTextView.setText( m.getTimeStamp() );
				}
				
			}
			
			return v;
		}
		
	}
}
