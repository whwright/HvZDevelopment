package edu.gatech.hvz.datasource;

import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Player;

public class DataSourceManager implements IDataSourceManager 
{
	private PlayerDataSource playerDataSource;
	
	public DataSourceManager()
	{
		this.playerDataSource = new PlayerDataSource();
	}
	

	@Override
	public Player getPlayerById(int idnumber) {
		return playerDataSource.getPlayerById(idnumber);
	}

	@Override
	public Player getPlayerByName(String name) {
		return playerDataSource.getPlayerByName(name);
	}

	@Override
	public Kill[] getKillsByPLayer(String player) {
		return playerDataSource.getKillsByPlayer(player);
	}


	

}
