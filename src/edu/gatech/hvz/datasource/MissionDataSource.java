package edu.gatech.hvz.datasource;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Mission;

public class MissionDataSource {
	
	private String missionsURL = "https://hvz.gatech.edu/api/mission";
	
	public Mission[] getMissions(Mission.Status status) {
		String url = missionsURL;
		url += "/" + status.toString().toLowerCase();
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		try {
			Mission[] arr = new Gson().fromJson(json, Mission[].class);
			return arr;
		} catch (JsonParseException e) {
			return null;
		}
		
	}
	
}