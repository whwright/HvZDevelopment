package edu.gatech.hvz.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Message;
import edu.gatech.hvz.entities.Player;

public class MessageDataSource {
	
	private final String messageUrl = "https://hvz.gatech.edu/api/messages";

	public List<Message> getMessage() {
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(messageUrl);
		return new ArrayList<Message>(Arrays.asList(new Gson().fromJson(json, Message[].class)));
	}
	
}
