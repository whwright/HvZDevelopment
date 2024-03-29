package edu.gatech.hvz.datasource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Achievement;

/**
 * A class that manages fetching locked and unlocked
 * achievements for the currently logged in player.
 */
public class AchievementDataSource {
	
	private String achievementURL = "https://hvz.gatech.edu/api/achievements/%s";
	
	public List<Achievement> getAchievements(String type) {
		//Default to UNLOCKED achievements
		if (!type.equals("LOCKED") && !type.equals("UNLOCKED")) {
			type = "UNLOCKED";
		}
		
		//Format URL and get JSON
		String url = String.format(achievementURL, type.toLowerCase(Locale.ENGLISH));
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		
		//Attempt to parse the JSON
		try {
			Achievement[] arr = new Gson().fromJson(json, Achievement[].class);
			if (arr == null) {
				arr = new Achievement[0];
			}
			return new LinkedList<Achievement>(Arrays.asList(arr));
		} catch (JsonParseException e) {
			return null;
		}
	}
	
}