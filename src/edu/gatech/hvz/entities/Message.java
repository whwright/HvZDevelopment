package edu.gatech.hvz.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
	
	private int id;
	private String user_to;
	private String user_from;
	private String message;
	private String timestamp;
	
	public Message(Parcel in)
	{
		id = in.readInt();
		user_to = in.readString();
		user_from = in.readString();
		message = in.readString();
		timestamp = in.readString();
	}
	
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(user_to);
		dest.writeString(user_from);
		dest.writeString(message);
		dest.writeString(timestamp);
	}
	
	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>()
	{

		public Message createFromParcel(Parcel in)
		{
			return new Message(in);
		}
		
		public Message[] newArray(int size)
		{
			return new Message[size];
		}
	};
	

}