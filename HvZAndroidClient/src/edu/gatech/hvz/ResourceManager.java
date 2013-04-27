package edu.gatech.hvz;

import edu.gatech.hvz.datasource.CacheDataSource;
import edu.gatech.hvz.datasource.DataSourceManager;
import edu.gatech.hvz.datasource.IDataSourceManager;
import edu.gatech.hvz.entities.Player;
import edu.gatech.hvz.networking.NetworkManager;

/**
 * The ResourceManager is a singleton that provides access
 * to various data sources throughout the Android application.
 */
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
	
	/**
	 * Get the singleton instance of the ResourceManager
	 * @return The ResourceManager for the application
	 */
	public static ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new ResourceManager();
		}
		return resourceManager;
	}

	/**
	 * @return An instance of the NetworkManager
	 */
	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	/**
	 * 
	 * @return An instance of the DataManager
	 */
	public IDataSourceManager getDataManager() {
		return dataManager;
	}
	
	/**
	 * A general cache provided to store any type of data
	 * throughout the application.
	 * 
	 * @return An instance of the CacheManager
	 */
	public CacheDataSource getCache() {
		return cache;
	}
	
	/**
	 * The Player object used to display data / gather data to make
	 * calls to the HvZ webserver.
	 * 
	 * @return An instance of the currently logged in player
	 */
	public Player getPlayer() {
		return player;
	}
	
	
	/**
	 * Set a player object to be used throughout the application.
	 * 
	 * @param p The pre-polulated Player object
	 */
	public void setPlayer(Player p) {
		player = p;
	}
	
	/**
	 * Reset all data for the ResourceManager.  Used in logging
	 * out of the application.
	 */
	public void resetData() {
		this.networkManager = new NetworkManager();
		this.dataManager = new DataSourceManager();
		this.cache = new CacheDataSource();
		this.player = null;
	}
	

}
