package edu.gatech.hvz;

import edu.gatech.hvz.networking.NetworkManager;

public class ResourceManager {
	
	private NetworkManager networkManager;
	
	public ResourceManager()
	{
		this.networkManager = new NetworkManager();
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

}
