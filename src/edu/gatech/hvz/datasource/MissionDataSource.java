package edu.gatech.hvz.datasource;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Mission;

public class MissionDataSource {
	
	private String missionsURL = "https://hvz.gatech.edu/api/mission/";
	
	
	public Mission[] getMissions(Mission.Status status) {
		String url = missionsURL;
		url += "/" + status.toString().toLowerCase();
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		Mission[] arr = new Gson().fromJson(json, Mission[].class);
		for (Mission a : arr)
			System.out.println(a.toString());
		return arr;
	}
	
}