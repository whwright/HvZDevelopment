package edu.gatech.hvz.datasource;

import java.util.List;

import edu.gatech.hvz.entities.Achievement;
import edu.gatech.hvz.entities.ChatMessage;
import edu.gatech.hvz.entities.Email;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Message;
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
	 * Returns a list of Players that are currently Zombies
	 * @param orderByParam Sort by the strings lname or starve_time
	 * @return Zombie players
	 */
	public List<Player> getZombies(String orderByParam);
	
	/**
	 * Returns a list of Players that are currently Humans
	 * @return Humans players
	 */
	public List<Player> getHumans();
	
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
	
	public void postEmail(Email email);
	
	
	/**
	 * Retrieve old messages that have already been retrieved at least once.
	 * 
	 * @param count The number of messages you want to retrieve
	 * @param offset The offset into the messages to start at (newest is 0)
	 * @return A list of messages, or null if no messages.
	 */
	public List<Message> getOldMessages(int count, int offset);

	/**
	 * Retrieve only new messages that have never been seen before.
	 * 
	 * @return A list of messages, or null if no messages.
	 */
	public List<Message> getNewMessages();
	
	/**
	 * Retrieve all sent messages.
	 * 
	 * @return A list of messages, or null if no messages.
	 */	
	public List<Message> getSentMessages();
	
	
	/**
	 * Send a new message to another player.
	 * 
	 * @param message The message that is to be sent.
	 */
	public void postMessage(Message message) ;

	/**
	 * Returns the given players player emblem as a String URL
	 * 
	 * @param player The player who's avatar we want
	 */
	public String getPlayerAvatar(Player player);
	
	/**
	 * Get a list of chat messages.
	 * @param id The id of the latest message you have received (0 to get all messages).
	 * @return An array of chat message objects
	 */
	public List<ChatMessage> getChatMessages(int id);


	/**
	 * Post a new message to the chat room
	 * @param message The message body to send
	 */
	public void postChatMessage(String message);
	
	/**
	 * Get a list of achievements that the player has earned.
	 * @return A list of the player's current achievements
	 */
	/**
	 * Get a list of achievements that the player has earned.
	 * @param type Either the string "LOCKED" or "UNLOCKED"
	 * @return A list of the player's current achievements
	 */
	public List<Achievement> getAchievements(String type);
}
