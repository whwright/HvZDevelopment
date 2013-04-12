package edu.gatech.hvz.activities;

import java.util.List;
import edu.gatech.hvz.R;
import android.os.Bundle;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Message;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

public class MessageListActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener 
{
	public static final int NUM_TABS = 2;
	public static final int INBOX = 0;
	public static final int SENT = 1;
	
	private int msgOffset;
	
	private MessageListAdapter mAdapter;
	private ViewPager mPager;
	private TabHost mTabHost;
	private Button composeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_list);
		
		msgOffset = 0;
		
		//pages setup
		mAdapter = new MessageListAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.messagelistactivity_pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);
		
		//tab setup
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		makeTab("INBOX", "Inbox");
		makeTab("SENT", "Sent");
		mTabHost.setOnTabChangedListener(this);
		
		composeButton = (Button) findViewById(R.id.messagelist_compose_button);
		composeButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MessageListActivity.this, MessageComposeActivity.class));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_list, menu);
		return true;
	}
	
	private void makeTab(String tag, String text)
	{
		TabSpec tab = mTabHost.newTabSpec(tag);
		tab.setIndicator(text);
		tab.setContent(new TabFactory(this));
		mTabHost.addTab(tab);
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {		
	}

	@Override
	public void onPageSelected(int pos) {
		mTabHost.setCurrentTab(pos);
	}

	@Override
	public void onTabChanged(String tabId) {
		int pos = mTabHost.getCurrentTab();
		mPager.setCurrentItem(pos);
	}
	
	public static class TabFactory implements TabContentFactory
	{
		private Context mContext;
		
		public TabFactory(Context context)
		{
			mContext = context;
		}

		@Override
		public View createTabContent(String tag) 
		{
			View v = new View(mContext);
			v.setMinimumHeight(0);
			v.setMinimumWidth(0);
			return v;
		}
		
	}
	

	public static class MessageListAdapter extends FragmentPagerAdapter
	{
		private Fragment[] messageFragments;
		
		public MessageListAdapter(FragmentManager fm)
		{
			super(fm);
			messageFragments = new Fragment[NUM_TABS];
		}
		
		public int getCount()
		{
			return NUM_TABS;
		}
		
		public Fragment getItem(int pos)
		{
			if( messageFragments[pos] == null )
			{
				messageFragments[pos] = MessageListFragment.newInstance(pos);
			}
			return messageFragments[pos];
		}
	}
	
	public static class MessageListFragment extends ListFragment
	{
		private int num;
		private Message[] messages;
		
		public static MessageListFragment newInstance(int num)
		{
			MessageListFragment f = new MessageListFragment();
			
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);
			f.setRetainInstance(true);
			return f;
		}
		
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			num = getArguments() != null ? getArguments().getInt("num") : 1;
			new MessagesTask().execute(num);
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			return inflater.inflate(R.layout.activity_message_list_fragment, container, false);
		}
		
		public void onActivityCreated(Bundle savedInstanceState)
		{
			super.onActivityCreated(savedInstanceState);
			((TextView) getListView().getEmptyView()).setText("Loading...");
		}
		
		public void onListItemClick(ListView l, View v, int pos, long id)
		{
			Message m = (Message) getListAdapter().getItem(pos);
			Intent i = new Intent(this.getActivity(), MessageDetailActivity.class);
			i.putExtra("SelectedMessage", m);
			startActivity(i);
		}
		
		public void setAdapter(Message[] messages)
		{
			this.messages = messages;
			setListAdapter(new MessageAdapter(getActivity(), android.R.layout.simple_list_item_1, messages));
		}
		
		public class MessageAdapter extends ArrayAdapter<Message>
		{
			public final int PREVIEW_CHARACTER_LENGTH = 40;
			
			public MessageAdapter(Context context, int textViewResourceId, Message[] objects)
			{
				super(context, textViewResourceId, objects);
				
			}
			
			public View getView(int pos, View convertView, ViewGroup parent)
			{
				View v = convertView;
				
				if( v == null )
				{
					LayoutInflater inflator = LayoutInflater.from(getContext());
					v = inflator.inflate(R.layout.activity_message_list_item, null);
				}
				
				Message m = this.getItem(pos);
				
				if( m != null )
				{
					TextView fromTextView = (TextView) v.findViewById(R.id.messagelistitem_from_textview);
					if( fromTextView != null )
					{
						fromTextView.setText( m.getUserFrom() );
					}
					
					TextView bodyTextView = (TextView) v.findViewById(R.id.messagelistitem_body_textview);
					if( bodyTextView != null )
					{
						String preview = m.getMessage();
						if( preview.length() > PREVIEW_CHARACTER_LENGTH )
						{
							preview = preview.substring(0, PREVIEW_CHARACTER_LENGTH);
							preview += " ... ";
						}
						bodyTextView.setText( preview );
					}
					
					TextView timeTextView = (TextView) v.findViewById(R.id.messagelistitem_time_textview);
					if( timeTextView != null )
					{
						timeTextView.setText( m.getTimeStamp() );
					}
					
				}
				
				return v;
			}
		}
		
		private class MessagesTask extends AsyncTask<Integer, Void, Message[]>
		{
			private final int MAX_MSG_FETCH = 15;
			
			@Override
			protected Message[] doInBackground(Integer... params) {
				ResourceManager resources = ResourceManager.getResourceManager();
				if( params[0] == INBOX )
				{/*
					List<Message> newMsgs = resources.getDataManager().getNewMessages();
					List<Message> oldMsgs = new ArrayList<Message>();
					if( newMsgs.size() < MAX_MSG_FETCH )
					{
						oldMsgs = resources.getDataManager().getOldMessages( (MAX_MSG_FETCH -newMsgs.size()) , 0);
					}
					
					List<Message> returnMsgs = new ArrayList<Message>();
					
					for( Message m : newMsgs )
					{
						returnMsgs.add(m);
					}
					for( Message m : oldMsgs )
					{
						returnMsgs.add(m);
					}
					*/
					List<Message> returnMsgs = resources.getDataManager().getOldMessages(15, 0);
					
					return returnMsgs.toArray(new Message[returnMsgs.size()]);
				}
				else if( params[0] == SENT)
				{
					List<Message> msgs = resources.getDataManager().getSentMessages();
					return msgs.toArray(new Message[msgs.size()]);
				}
				else
				{
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(Message[] messages)
			{
				if( messages != null )
				{
					setAdapter(messages);
				}
				else
				{
					TextView empty = (TextView)MessageListFragment.this.getListView().getEmptyView();
					empty.setText("No messages.");
				}
			}
			
		}
	}

	
}
