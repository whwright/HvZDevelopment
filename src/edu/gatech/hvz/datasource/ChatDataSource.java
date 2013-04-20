package edu.gatech.hvz.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.ChatMessage;

public class ChatDataSource {
	
	private String chatURL = "https://hvz.gatech.edu/api/chat/%d";
	
	public List<ChatMessage> getChatMessages(int id) {
		String url = String.format(chatURL, id);
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		try {
			ChatMessage[] arr = new Gson().fromJson(json, ChatMessage[].class);
			if (arr == null) {
				return null;
			}
			List<ChatMessage> list = new ArrayList<ChatMessage>(Arrays.asList(arr));
			return list;
		} catch (JsonParseException e) {
			Log.e("ChatDataSource", "Error parsing JSON data.");
			return null;
		} catch (Exception e) {
			Log.e("ChatDataSource", "Horrible error getting chat messages.\n" + Log.getStackTraceString(e));
			return null;
		}
	}
	
}