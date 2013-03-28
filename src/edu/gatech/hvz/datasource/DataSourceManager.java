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
	private KillDataSource killDataSource;
	
	public DataSourceManager()
	{
		this.playerDataSource = new PlayerDataSource();
		this.missionDataSource = new MissionDataSource();
		this.killDataSource = new KillDataSource();
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
	public List<Player> getZombies(String orderByParam) {
		return playerDataSource.getZombies(orderByParam);
	}

	@Override
	public void postKill(Kill kill) {
		killDataSource.postKill(kill);
	}

}
