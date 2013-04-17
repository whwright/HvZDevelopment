package edu.gatech.hvz.datasource;

import java.util.List;

import android.R;
import edu.gatech.hvz.entities.Email;
import edu.gatech.hvz.entities.Kill;
import edu.gatech.hvz.entities.Message;
import edu.gatech.hvz.entities.Mission;
import edu.gatech.hvz.entities.Mission.Status;
import edu.gatech.hvz.entities.Player;

public class DataSourceManager implements IDataSourceManager 
{
	private PlayerDataSource playerDataSource;
	private MissionDataSource missionDataSource;
	private KillDataSource killDataSource;
	private EmailDataSource emailDataSource;
	private MessageDataSource messageDataSource;
	
	public DataSourceManager()
	{
		this.playerDataSource = new PlayerDataSource();
		this.missionDataSource = new MissionDataSource();
		this.killDataSource = new KillDataSource();
		this.emailDataSource = new EmailDataSource();
		this.messageDataSource = new MessageDataSource();
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

	public List<Message> getOldMessages(int count, int offset) {
		return messageDataSource.getOldMessages(count, offset);
	}
	
	public List<Message> getNewMessages() {
		return messageDataSource.getNewMessages();
	}
	
	public List<Message> getSentMessages() {
		return messageDataSource.getSentMessages();
	}

	public void postMessage(Message message) 
	{
		messageDataSource.postMessage(message);
	}

	@Override
	public int getPlayerIcon(Player player) {
		// TODO Auto-generated method stub
		return R.drawable.ic_menu_gallery;
	}
}
