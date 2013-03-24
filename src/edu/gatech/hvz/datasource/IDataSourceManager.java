package edu.gatech.hvz.datasource;

import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Player;

public interface IDataSourceManager {

	public Player getPlayerByName(String name);
	public Player getPlayterByCode(String code);
	
	public Kill[] getKillsByPlayer(String player);
	
	public String getQRCode(String data, int w, int h);
}
