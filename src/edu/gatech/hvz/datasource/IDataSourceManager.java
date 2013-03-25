package edu.gatech.hvz.datasource;

import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Mission;
import edu.gatech.hvz.entities.Player;

public interface IDataSourceManager {

	public Player getPlayerByName(String name);
	public Player getPlayerByCode(String code);
	
	public Kill[] getKillsByPlayer(String player);
	
	public Mission[] getMissions(FactionType faction, boolean current);
	public String getQRCode(String data, int w, int h);
}
