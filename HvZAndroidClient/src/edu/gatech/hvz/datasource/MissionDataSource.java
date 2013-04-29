package edu.gatech.hvz.datasource;

import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Mission;

/**
 * A data source that grabs all missions of a certain status.
 */
public class MissionDataSource {
	
	private String missionsURL = "https://hvz.gatech.edu/api/mission";
	
	public Mission[] getMissions(Mission.Status status) {
		String url = missionsURL;
		url += "/" + status.toString().toLowerCase(Locale.ENGLISH);
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		try {
			Mission[] arr = new Gson().fromJson(json, Mission[].class);
			return arr;
		} catch (JsonParseException e) {
			return null;
		}
		
	}
	
}