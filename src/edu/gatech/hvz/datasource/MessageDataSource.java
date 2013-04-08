package edu.gatech.hvz.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Message;

public class MessageDataSource {
	
	private final String oldMessagesUrl = "https://hvz.gatech.edu/api/messages";
	private final String newMessagesUrl = "https://hvz.gatech.edu/api/messages/new";
	private final String sentMessagesUrl = "https://hvz.gatech.edu/api/messages/sent";
	private final String composeMessageUrl = "https://hvz.gatech.edu/api/messages/create?to=%s&message=%s";

	public List<Message> getOldMessages(int count, int offset) {
		String[] params = {"count", "" + count, "offset", "" + offset};
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(oldMessagesUrl, params);
		return new ArrayList<Message>(Arrays.asList(new Gson().fromJson(json, Message[].class)));
	}
	
	public List<Message> getNewMessages() {
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(newMessagesUrl);
		return new ArrayList<Message>(Arrays.asList(new Gson().fromJson(json, Message[].class)));
	}
	
	public List<Message> getSentMessages() {
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(sentMessagesUrl);
		return new ArrayList<Message>(Arrays.asList(new Gson().fromJson(json, Message[].class)));
	}

	public void postMessage(Message message) 
	{
		String requestString = String.format(composeMessageUrl, message.getUserTo(), message.getMessage());
		ResourceManager.getResourceManager().getNetworkManager().makeRequest(requestString);	
	}
	
}
