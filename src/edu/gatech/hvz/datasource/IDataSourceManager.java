package edu.gatech.hvz.datasource;

import java.util.List;

import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Mission;
import edu.gatech.hvz.entities.Player;

public interface IDataSourceManager {

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Player getPlayerByName(String name);
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public Player getPlayerByCode(String code);
	
	/**
	 * 
	 * @return
	 */
	public List<Player> getZombies(String orderByParam);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public Kill[] getKillsByPlayer(String player);
	
	/**
	 * 
	 * @param faction
	 * @param current
	 * @return
	 */
	public Mission[] getMissions(FactionType faction, boolean current);
	
	/**
	 * 
	 * @param data
	 * @param w
	 * @param h
	 * @return
	 */
	public String getQRCode(String data, int w, int h);
	
	
	/**
	 * 
	 * @return
	 */
	public void postKill(Kill kill);
}
