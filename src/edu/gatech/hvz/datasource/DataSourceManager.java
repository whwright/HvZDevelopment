package edu.gatech.hvz.datasource;

import java.util.List;

import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Mission;
import edu.gatech.hvz.entities.Player;

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
	public Player getPlayerByCode(String code) {
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

	@Override
	public List<Player> getZombies() {
		return playerDataSource.getZombies();
	}

}
