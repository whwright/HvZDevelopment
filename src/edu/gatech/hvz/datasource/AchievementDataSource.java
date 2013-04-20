package edu.gatech.hvz.datasource;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Achievement;

public class AchievementDataSource {
	
	private String chatURL = "https://hvz.gatech.edu/api/achievements";
	
	public Achievement[] getAchievements() {
		String url = chatURL;
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		try {
			Achievement[] arr = new Gson().fromJson(json, Achievement[].class);
			return arr;
		} catch (JsonParseException e) {
			return null;
		}
	}
	
}