package edu.gatech.hvz.datasource;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Player;

public class PlayerDataSource {
	
	private String playerURL = "https://hvz.gatech.edu/api/player.php";
	private String killsURL = "https://hvz.gatech.edu/api/kills.php";
	
	
	public PlayerDataSource()
	{

	}
	
	public Player getPlayerByName(String name)
	{
		String[] params = {"gt_name", name};
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(playerURL, params);
		return new Gson().fromJson(json, Player.class);
	}
	
	public Kill[] getKillsByPlayer(String player) 
	{
		String[] data = {"killer", player};
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(killsURL, data);
		Gson gson = new Gson();
		Kill[] kills = gson.fromJson(json, Kill[].class);
		return kills;
	}

}
