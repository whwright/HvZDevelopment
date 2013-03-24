package edu.gatech.hvz.datasource;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entites.FactionType;
import edu.gatech.hvz.entites.Mission;

public class MissionDataSource {
	
	private String missionsURL = "https://hvz.gatech.edu/api/mission";
	
	
	public Mission[] getMissions(FactionType faction, boolean current) {
		String url = missionsURL;
		url += "/" + faction.toString();
		if (current) {
			url += "/current";
		}
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(url);
		Mission[] arr = new Gson().fromJson(json, Mission[].class);
		for (Mission a : arr)
			System.out.println(a.toString());
		return arr;
	}
	
}