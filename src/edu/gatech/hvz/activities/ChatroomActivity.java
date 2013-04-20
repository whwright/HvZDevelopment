package edu.gatech.hvz.activities;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;

import edu.gatech.hvz.R;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.ChatMessage;
import edu.gatech.hvz.entities.EntityUtils;
import edu.gatech.hvz.entities.Mission;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ChatroomActivity extends SherlockListActivity {

	private ResourceManager resources;
	private AsyncTask<Void, Void, Boolean> getTask;
	private AsyncTask<Void, Void, Boolean> postTask;
	private List<ChatMessage> messages;
	private ChatMessageAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatroom);
		
		// ActionBar setup
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("Chat");
		
		//Button listeners
		Button button = (Button) findViewById(R.id.chatroomactivity_post_button);
		button.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				postTask = new PostChatTask().execute();
			}
		});
		
		
		
		//Get any old messages
		resources = ResourceManager.getResourceManager();
		if (resources.getCache().get("chatMessages") != null) {
			messages = ((List<ChatMessage>)resources.getCache().get("chatMessages"));
		} else {
			messages = new LinkedList<ChatMessage>();
			resources.getCache().put("chatMessages", messages);
		}
		
		//Set Adapter and scroll to bottom
		adapter = new ChatMessageAdapter(this, android.R.layout.simple_list_item_1, messages);
		setListAdapter(adapter);
		if (adapter.getCount() > 0) {
			setSelection(adapter.getCount() - 1);
		}
		
		
		getTask = new ChatTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	protected void onPause() {
		super.onPause();
		if (getTask != null && getTask.getStatus() == AsyncTask.Status.RUNNING) {
			getTask.cancel(true);
		}
		if (postTask != null && postTask.getStatus() == AsyncTask.Status.RUNNING) {
			postTask.cancel(true);
		}
	}
	
	private class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

		private LayoutInflater inflater;

		public ChatMessageAdapter(Context context, int textViewResourceId,
				List<ChatMessage> objects) {
			super(context, textViewResourceId, objects);
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.activity_chatroom_list_item, null);
			}

			ChatMessage chatMessage = this.getItem(position);
			
			TextView name = (TextView) convertView
					.findViewById(R.id.chatroomactivityitem_name_edittext);
			TextView faction = (TextView) convertView
					.findViewById(R.id.chatroomactivityitem_faction_edittext);
			TextView time = (TextView) convertView
					.findViewById(R.id.chatroomactivityitem_time_edittext);
			TextView message = (TextView) convertView
					.findViewById(R.id.chatroomactivityitem_message_edittext);
			
			name.setText(chatMessage.getUser());
			faction.setText(chatMessage.getAudience());
			message.setText(chatMessage.getComment().trim());
			time.setText(EntityUtils.stringToFormattedDate(chatMessage.getTimestamp()));

			return convertView;
		}
	}
	
	/* Task posts a new message and then refreshes the chat */
	private class PostChatTask extends AsyncTask<Void, Void, Boolean> {
		protected Boolean doInBackground(Void ... voids) {
			try {
				TextView post = (TextView) findViewById(R.id.chatroomactivity_message_edittext);
				String text = post.getText().toString().trim();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}	
		protected void onPostExecute(Boolean success) {
			if (success) {
				getTask = new ChatTask().execute();
			} else {
				Toast.makeText(ChatroomActivity.this, "There was an error fetching the chats", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	/* Task grabs all the new messages on the server */
	private class ChatTask extends AsyncTask<Void, Void, Boolean> {
		private List<ChatMessage> newMessages;

		protected Boolean doInBackground(Void ... voids) {
			try {
				int start = -1;
				if (messages.size() > 0) {
					start = messages.get(messages.size()-1).getId();
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
			if (success && newMessages != null) {
				messages.addAll(newMessages);
				adapter.notifyDataSetChanged();
				if (adapter.getCount() > 0) {
					setSelection(adapter.getCount() - 1);
				}
			} else {
				Toast.makeText(ChatroomActivity.this, "There was an error fetching the chats", Toast.LENGTH_LONG).show();
			}
		}
	}

}
