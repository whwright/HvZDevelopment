package edu.gatech.hvz.datasource;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entites.Player;

public class PlayerDataSource {
	
	private String playerURL = "https://hvz.gatech.edu/api/player.php";
	private ResourceManager resourceManager;
	
	public PlayerDataSource()
	{
		
	}

	public Player getPlayerById(int idnumber) 
	{
		String tempUTL = playerURL + "?id=" + idnumber;
		Object response = resourceManager.getNetworkManager().makeRequest(tempUTL);
		return null;
	}

}
