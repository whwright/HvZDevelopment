package edu.gatech.hvz.datasource;

import edu.gatech.hvz.entites.FactionType;
import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Mission;
import edu.gatech.hvz.entites.Player;

public interface IDataSourceManager {

	public Player getPlayerByName(String name);
	public Player getPlayterByCode(String code);
	
	public Kill[] getKillsByPlayer(String player);
	
	public Mission[] getMissions(FactionType faction, boolean current);
	public String getQRCode(String data, int w, int h);
}
