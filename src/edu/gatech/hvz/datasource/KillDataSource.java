package edu.gatech.hvz.datasource;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Kill;

public class KillDataSource {
	
	private String killURL = "https://hvz.gatech.edu/api/kill";

	/**
	 * Posts the given Kill object to the web server.
	 * @param kill
	 */
	public void postKill(Kill kill) 
	{
		String json = new Gson().toJson(kill);
		String[] params = { "json", json };
		ResourceManager.getResourceManager().getNetworkManager().makeRequest(killURL, params);
	}

}
