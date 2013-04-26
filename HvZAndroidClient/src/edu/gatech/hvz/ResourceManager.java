package edu.gatech.hvz;

import edu.gatech.hvz.datasource.CacheDataSource;
import edu.gatech.hvz.datasource.DataSourceManager;
import edu.gatech.hvz.datasource.IDataSourceManager;
import edu.gatech.hvz.entities.Player;
import edu.gatech.hvz.networking.NetworkManager;

public class ResourceManager {
	
	private static ResourceManager resourceManager;
	private NetworkManager networkManager;
	private IDataSourceManager dataManager;
	private CacheDataSource cache;
	private Player player;
	
	private ResourceManager()
	{
		resetData();
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
	
	public IDataSourceManager getDataManager() {
		return dataManager;
	}
	
	public CacheDataSource getCache() {
		return cache;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public void resetData() {
		this.networkManager = new NetworkManager();
		this.dataManager = new DataSourceManager();
		this.cache = new CacheDataSource();
		this.player = null;
	}
	

}
