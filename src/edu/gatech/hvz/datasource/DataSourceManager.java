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
	public Player getPlayterByCode(String code) {
		return playerDataSource.getPlayerByCode(code);
	}

	@Override
	public Kill[] getKillsByPlayer(String player) {
		return playerDataSource.getKillsByPlayer(player);
	}

	@Override
	public String getQRCode(int w, int h) {
		return playerDataSource.getQRCode(w, h);
	}
	

}
