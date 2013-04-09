package edu.gatech.hvz.datasource;

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
		String[] params = {"playerCode", kill.getPlayerCode(),
						   "feed1", kill.getFeed1(),
						   "feed2", kill.getFeed2(),
						   "lat", "" + kill.getLat(),
						   "lng", "" + kill.getLng()
						  };
		ResourceManager.getResourceManager().getNetworkManager().makeRequest(killURL, params);
	}

}
