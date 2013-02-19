package edu.gatech.hvz.datasource;

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

}
