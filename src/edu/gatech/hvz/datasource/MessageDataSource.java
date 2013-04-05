package edu.gatech.hvz.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Message;

public class MessageDataSource {
	
	private final String messageUrl = "https://hvz.gatech.edu/api/messages";
	private final String messageComposeUrl = "https://hvz.gatech.edu/api/messages/new?to=%s&message=%s";

	public List<Message> getMessage() {
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(messageUrl);
		return new ArrayList<Message>(Arrays.asList(new Gson().fromJson(json, Message[].class)));
	}

	public void postMessage(Message message) 
	{
		String requestString = String.format(messageComposeUrl, message.getUserTo(), message.getMessage());
		ResourceManager.getResourceManager().getNetworkManager().makeRequest(requestString);	
	}
	
}
