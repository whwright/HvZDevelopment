package edu.gatech.hvz.datasource;

import java.util.List;

import edu.gatech.hvz.entities.Achievement;
import edu.gatech.hvz.entities.ChatMessage;
import edu.gatech.hvz.entities.Email;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Mission;
import edu.gatech.hvz.entities.Mission.Status;
import edu.gatech.hvz.entities.Player;

public class DataSourceManager implements IDataSourceManager 
{
	private PlayerDataSource playerDataSource;
	private MissionDataSource missionDataSource;
	private KillDataSource killDataSource;
	private EmailDataSource emailDataSource;
	private ChatDataSource chatDataSource;
	private AchievementDataSource achievementDataSource;
	
	public DataSourceManager()
	{
		this.playerDataSource = new PlayerDataSource();
		this.missionDataSource = new MissionDataSource();
		this.killDataSource = new KillDataSource();
		this.emailDataSource = new EmailDataSource();
		this.chatDataSource = new ChatDataSource();
		this.achievementDataSource = new AchievementDataSource();
	}
	
	@Override
	public Player getPlayerByName(String name) {
		return playerDataSource.getPlayerByName(name);
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
	public List<Player> getZombies(String orderByParam) {
		return playerDataSource.getZombies(orderByParam);
	}
	
	@Override
	public List<Player> getHumans() {
		return playerDataSource.getHumans();
	}
	
	@Override
	public void postKill(Kill kill) {
		killDataSource.postKill(kill);
	}

	@Override
	public Mission[] getMissions(Status status) {
		return missionDataSource.getMissions(status);
	}

	@Override
	public void postEmail(Email email) {
		emailDataSource.postEmail(email);
	}

	@Override
	public String getPlayerAvatar(Player player) {
		return playerDataSource.getPlayerAvatar(player);
	}
	
	@Override
	public List<ChatMessage> getChatMessages(int id) {
		return chatDataSource.getChatMessages(id);
	}
	
	@Override
	public void postChatMessage(String message) {
		chatDataSource.postChatMessage(message);
	}
	
	@Override
	public List<Achievement> getAchievements(String type) {
		return achievementDataSource.getAchievements(type);
	}
}
