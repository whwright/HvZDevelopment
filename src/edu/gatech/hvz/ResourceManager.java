package edu.gatech.hvz;

import edu.gatech.hvz.datasource.DataSourceManager;
import edu.gatech.hvz.networking.NetworkManager;

public class ResourceManager {
	
	private static ResourceManager resourceManager;
	private NetworkManager networkManager;
	private DataSourceManager dataManager;
	
	private ResourceManager()
	{
		this.networkManager = new NetworkManager();
		this.dataManager = new DataSourceManager();
	}
	
	public static ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new ResourceManager();
		}
		return resourceManager;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	public DataSourceManager getDataManager() {
		return dataManager;
	}

}
