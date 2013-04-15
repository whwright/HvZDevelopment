package edu.gatech.hvz.activities;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Message;
import edu.gatech.hvz.entities.Player;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MessageComposeActivity extends Activity {
	
	private Button sendButton;
	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_compose);
		
		//populate to field if reply
		Message replyMessage = getIntent().getParcelableExtra("MessageToReply");
		if( replyMessage != null )
		{
			EditText toEditText = (EditText) findViewById(R.id.messagecompose_to_edittext);
			toEditText.setText( replyMessage.getUserFrom(), TextView.BufferType.EDITABLE );
		}
		
		sendButton = (Button) findViewById(R.id.messagecompose_send_button);
		sendButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				doSend();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_compose, menu);
		menu.removeItem(menu.getItem(0).getItemId());
		menu.add(Menu.NONE, 0, 0,"Contact Admin");
		menu.add(Menu.NONE, 1, 1, "Messages");
		menu.add(Menu.NONE, 2, 2, "Logout");
		return true;
	}
	
	private void doSend() 
	{
		sendButton.setEnabled(false);
		loadingDialog = ProgressDialog.show(MessageComposeActivity.this, "Sending...", "Sending your message", false);
		String userTo = ((TextView) findViewById(R.id.messagecompose_to_edittext)).getText().toString();
		String messageBody = ((TextView) findViewById(R.id.messagecompose_body_edittext)).getText().toString();
		new SendRequest().execute(userTo, messageBody);
	}
	
	
	private class SendRequest extends AsyncTask<String, Void, Boolean>
	{
		
		private String errorMessage;

		@Override
		protected Boolean doInBackground(String... params) {
			String userTo = params[0];
			String messageBody = params[1];
			
			if( userTo.equals("") )
			{
				errorMessage = "Please provide at least one recipient.";
				return false;
			}
			else if( messageBody.equals("") )
			{
				errorMessage = "Please provide a message body";
				return false;
			}
			
			//parse userTo
			String[] usersTo = userTo.split(",");
			boolean userToValidated = true;
			for(int i=0; i<usersTo.length && userToValidated; i++)
			{
				usersTo[i] = usersTo[i].trim();
				Player userToPlayer = ResourceManager.getResourceManager().getDataManager().getPlayerByName(usersTo[i]);
				if( userToPlayer == null )
				{
					errorMessage = "Could not find player " + usersTo[i] + ".";
					userToValidated =  false;
				}
			}
			
			if( userToValidated ) 
			{
				for( String user : usersTo )
				{
					user = user.trim();
					Message messageToSend = new Message(user, ResourceManager.getResourceManager().getPlayer().getGTName(), messageBody);
					ResourceManager.getResourceManager().getDataManager().postMessage(messageToSend);
				}
				return true;
			}
			
			return false;
		}
		
		protected void onProgressUpdate(Void ... params) {
		}
		
		protected void onPostExecute(Boolean success)
		{
			sendButton.setEnabled(true);
			loadingDialog.dismiss();
			if( success )
			{
				Toast.makeText(MessageComposeActivity.this, 
						"Message sent.", 
						Toast.LENGTH_SHORT).show();
						finish();
			}
			else
			{
				Toast.makeText(MessageComposeActivity.this, 
						errorMessage, 
						Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	

}
