package edu.gatech.hvz.entities;

public class Message {
	
	private int id;
	private String user_to;
	private String user_from;
	private String message;
	private String timestamp;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserTo() {
		return user_to;
	}
	public void setUserTo(String to) {
		this.user_to = to;
	}
	public String getUserFrom() {
		return user_from;
	}
	public void setUserFrom(String from) {
		this.user_from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimeStamp() {
		return timestamp;
	}
	public void setTimeStamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString()
	{
		return "Message [id=" + id + ", user_to=" + user_to +
				", user_from=" + user_from + ", message=" + message + ", timestamp=" 
				+ timestamp + "]";
			
	}
	

}
