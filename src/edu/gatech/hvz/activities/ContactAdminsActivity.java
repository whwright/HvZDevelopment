package edu.gatech.hvz.activities;

import java.util.Properties;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.R.layout;
import edu.gatech.hvz.R.menu;
import edu.gatech.hvz.entities.Email;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class ContactAdminsActivity extends Activity {
	
	private ProgressDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_admins);
		
		//set email to gt_name@gatech.edu
		EditText emailEditText = (EditText) findViewById(R.id.contactadmins_email_edittext);
		emailEditText.setText( ResourceManager.getResourceManager().getPlayer().getGTName() + "@gatech.edu" );
		//set name to default
		EditText nameEditText = (EditText) findViewById(R.id.contactadmins_name_edittext);
		nameEditText.setText( ResourceManager.getResourceManager().getPlayer().getName() );
		
		Button sendButton = (Button) findViewById(R.id.contactadmins_send_button);
		sendButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				new ContactRequest().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_admins, menu);
		return true;
	}
	
	private class ContactRequest extends AsyncTask<Void, Void, Boolean>
	{
		private String errorMessage;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			loadingDialog = ProgressDialog.show(ContactAdminsActivity.this, "Loading...", "Sending email to admins.", false);
			
			String subject = ((EditText) findViewById(R.id.contactadmins_subjet_edittext)).getText().toString();
			if( subject.equals("") )
			{
				errorMessage = "Please enter a subject.";
				return false;
			}
			
			String body = ((EditText) findViewById(R.id.contactadmins_body_edittext)).getText().toString();
			if( body.equals("") )
			{
				errorMessage = "Please enter a message body.";
				return false;
			}
			
			String name = ((EditText) findViewById(R.id.contactadmins_email_edittext)).getText().toString();
			if( name.equals("") )
			{
				errorMessage = "Please enter your name.";
				return false;
			}
			
			String toEmail = ((EditText) findViewById(R.id.contactadmins_email_edittext)).getText().toString();
			if( toEmail.equals("") )
			{
				errorMessage = "Please enter your email.";
				return false;
			}
			Email emailToSend = new Email(subject, body, name, toEmail);
			ResourceManager.getResourceManager().getDataManager().postEmail(emailToSend);
			
			return true;
		}
		
		protected void onProgressUpdate(Void ... stuff) {

		}
		
		protected void onPostExecute(Boolean success) {
			loadingDialog.dismiss();
			if( success )
			{
				
			}
			else
			{
				Toast.makeText(ContactAdminsActivity.this, 
						errorMessage, 
						Toast.LENGTH_SHORT).show();
			}
			
		}
	}

}
