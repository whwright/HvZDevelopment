package edu.gatech.hvz.datasource;

import edu.gatech.hvz.entites.FactionType;
import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Mission;
import edu.gatech.hvz.entites.Player;

public class DataSourceManager implements IDataSourceManager 
{
	private PlayerDataSource playerDataSource;
	private MissionDataSource missionDataSource;
	
	public DataSourceManager()
	{
		this.playerDataSource = new PlayerDataSource();
		this.missionDataSource = new MissionDataSource();
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
	public String getQRCode(String data, int w, int h) {
		return playerDataSource.getQRCode(data, w, h);
	}

	@Override
	public Mission[] getMissions(FactionType faction, boolean current) {
		return missionDataSource.getMissions(faction, current);
	}

}
