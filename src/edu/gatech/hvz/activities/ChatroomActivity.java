package edu.gatech.hvz.activities;

import java.util.LinkedList;
import java.util.List;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.ChatMessage;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class ChatroomActivity extends Activity {

	private ResourceManager resources;
	private AsyncTask<Void, Void, Boolean> task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatroom);
		
		resources = ResourceManager.getResourceManager();
		
		task = new ChatTask().execute();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	protected void onPause() {
		super.onPause();
		if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
			task.cancel(true);
		}
	}
	
	private class ChatTask extends AsyncTask<Void, Void, Boolean> {
		private List<ChatMessage> newMessages;
		private List<ChatMessage> oldMessages;
		
		protected Boolean doInBackground(Void ... voids) {
			if (resources.getCache().get("chatMessages") != null) {
				oldMessages = ((List<ChatMessage>)resources.getCache().get("chatMessages"));
			}
			try {
				int start = -1;
				if (oldMessages != null) {
					start = oldMessages.get(0).getId();
				}
				Log.i("ChatroomActivity", "Getting messages starting at " + start);
				newMessages = resources.getDataManager().getChatMessages(start);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		protected void onPostExecute(Boolean success) {
			if (success) {
				if (newMessages != null) {
					if (oldMessages == null) {
						oldMessages = new LinkedList<ChatMessage>();
					}
					Log.i("ChatroomActivity", "New: " + newMessages.toString());
					newMessages.addAll(oldMessages);
					oldMessages = newMessages;
					resources.getCache().put("chatMessages", oldMessages);
				}
				Log.i("ChatroomActivity", "Old: " + oldMessages.toString());
			} else {
				Toast.makeText(ChatroomActivity.this, "There was an error fetching the chats", Toast.LENGTH_LONG).show();
			}
		}
	}

}
