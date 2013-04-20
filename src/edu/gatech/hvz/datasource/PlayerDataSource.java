package edu.gatech.hvz.datasource;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.gatech.hvz.ResourceManager;
import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Player;

public class PlayerDataSource {
	
	private final String playerURL = "https://hvz.gatech.edu/api/player/%s";
	private final String killsURL = "https://hvz.gatech.edu/api/player/%s/kills";
	private final String factionURL = "https://hvz.gatech.edu/api/faction/%s";
	private final String qrCodeURL = "http://api.qrserver.com/v1/create-qr-code/?qzone=1&size=%dx%d&data=%s";
	private final String avatarURL = "https://hvz.gatech.edu/images/avatars/";
	private final String humanAvatarURL = "https://hvz.gatech.edu/images/avatars/human.png";
	private final String zombieAvatarURL = "https://hvz.gatech.edu/images/avatars/zombie.png";
	private final String adminAvatarURL = "https://hvz.gatech.edu/images/avatars/admin.png";
	
	public PlayerDataSource()
	{

	}
	
	public Player getPlayerByName(String name)
	{
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(playerURL, name));
		if( json != null && !json.equals("No player by that id") )
		{
			return new Gson().fromJson(json, Player.class);
		}
		return null;
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

	public String getPlayerAvatar(Player player) {
		//Assume the avatar is valid if it is set to something
		if (player.getAvatar() != null && player.getAvatar().length() > 0) {
			return avatarURL + player.getAvatar();
		} else if (player.getFaction().equals(FactionType.ADMIN.name())) {
			return adminAvatarURL;
		} else if (player.getFaction().equals(FactionType.HUMAN.name())) {
			return humanAvatarURL;
		} else {
			return zombieAvatarURL;
		}
	}
	
	public List<Player> getZombies(String orderByParam) {
		String[] params = { "order", orderByParam };
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(factionURL, "zombie"), params);
		return new ArrayList<Player>(Arrays.asList( new Gson().fromJson(json, Player[].class)));
	}
	
	public List<Player> getHumans() {
		String json = ResourceManager.getResourceManager().getNetworkManager().makeRequest(String.format(factionURL, "human"));
		return new ArrayList<Player>(Arrays.asList( new Gson().fromJson(json, Player[].class)));
	}

}
