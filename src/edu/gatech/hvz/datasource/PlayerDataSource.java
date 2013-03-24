package edu.gatech.hvz.datasource;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Player;

public class PlayerDataSource {
	
	private String playerURL = "https://hvz.gatech.edu/api/player/%s";
	private String killsURL = "https://hvz.gatech.edu/api/player/%s/kills";
	private String qrCodeURL = "http://api.qrserver.com/v1/create-qr-code/?qzone=1&size=%dx%d&data=%s";
	
	
	public PlayerDataSource()
	{

	}
	
	public Player getPlayerByName(String name)
	{
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(playerURL, name));
		return new Gson().fromJson(json, Player.class);
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

}
