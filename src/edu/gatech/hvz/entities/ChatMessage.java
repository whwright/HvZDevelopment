package edu.gatech.hvz.entities;

import java.io.Serializable;

import android.text.Html;

public class ChatMessage implements Serializable {

	private static final long serialVersionUID = 2738625505328994550L;
	
	private int id;
	private String audience;
	private String user;
	private String timestamp;
	private String comment;
	
	private ChatMessage() {
		
	}

	public int getId() {
		return id;
	}

	public String getAudience() {
		return audience;
	}

	public String getUser() {
		return user;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public String getComment() {
		return (comment != null) ? Html.fromHtml(comment).toString() : "";
	}
	
	@Override
	public String toString() {
		return "ChatMessage [id=" + id + ", audience=" + audience + ", user="
				+ user + ", timestamp=" + timestamp + ", comment=" + comment
				+ "]";
	}
}
