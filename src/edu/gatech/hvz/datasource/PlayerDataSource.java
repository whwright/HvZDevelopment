package edu.gatech.hvz.datasource;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Player;

public class PlayerDataSource {
	
	private String playerURL = "https://hvz.gatech.edu/api/player/%s";
	private String killsURL = "https://hvz.gatech.edu/api/player/%s/kills";
	private String factionURL = "https://hvz.gatech.edu/api/faction/%s";
	private String qrCodeURL = "http://api.qrserver.com/v1/create-qr-code/?qzone=1&size=%dx%d&data=%s";
	
	
	public PlayerDataSource()
	{

	}
	
	public Player getPlayerByName(String name)
	{
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(playerURL, name));
		if( json != null )
		{
			return new Gson().fromJson(json, Player.class);
		}
		return null;
	}
	
	public Player getPlayerByCode(String code)
	{
		String[] params = { "player_code", code };
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(playerURL, params);
		return new Gson().fromJson(json, Player.class);
	}
	
	public Kill[] getKillsByPlayer(String player) 
	{
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(killsURL, player));
		Gson gson = new Gson();
		Kill[] kills = gson.fromJson(json, Kill[].class);
		return kills;
	}
	
	public String getQRCode(String data, int w, int h) {
		return String.format(qrCodeURL, w, h, data);
	}

	public List<Player> getZombies(String orderByParam) {
		String[] params = { "order", orderByParam };
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(factionURL, "zombie"), params);		
		return new ArrayList<Player>(Arrays.asList( new Gson().fromJson(json, Player[].class)));
	}

}
