package edu.gatech.hvz.datasource;

import edu.gatech.hvz.entites.Kill;
import edu.gatech.hvz.entites.Player;

public interface IDataSourceManager {

	public Player getPlayerById(int idnumber);
	public Player getPlayerByName(String name);
	public Kill[] getKillsByPLayer(String player);
	
}
