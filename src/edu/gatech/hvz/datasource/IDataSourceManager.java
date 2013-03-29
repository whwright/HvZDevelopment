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
	 * Gets all of the missions which match the status given.
	 *  Mission.ACTIVE: start time is in the past, and end time is in the future
	 *  Mission.ISSUED: start time is in the future.
	 *  Mission.CLOSED: end time is in the past.
	 * 
	 * @param status the type of missions to receive from the server
	 * @return An array of Missions that currently match the status
	 */
	public Mission[] getMissions(Mission.Status status);

	
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
