package edu.gatech.hvz.datasource;

import java.util.List;

import edu.gatech.hvz.entities.FactionType;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Mission;
import edu.gatech.hvz.entities.Player;

public interface IDataSourceManager {

	/**
	 * Get a player by their GT name
	 * @param name The GT name (eg gburdell3) of the player
	 * @return The player object associated with the GT Name, or null if no player
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
	 * Fetch all of the players that have been killed by
	 * a certain player.
	 * @param player The player who we are interested in
	 * @return An array of all of the kills by player
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
	 * Used to get a URL to an image that contains a QR code.
	 * @param data The data you want to encode
	 * @param w The width in pixels of the QR code
	 * @param h The height in pixels of the QR code
	 * @return The URL of the QR code image
	 */
	public String getQRCode(String data, int w, int h);
	
	
	/**
	 * 
	 * @return
	 */
	public void postKill(Kill kill);
}
