package edu.gatech.hvz.entities;

import java.io.Serializable;

import android.text.Html;

public class Achievement implements Serializable {
	

	private static final long serialVersionUID = 8642753786296032809L;
	
	private static final String URL = "https://hvz.gatech.edu/images/avatars/";
	
	private String name;
	private String category;
	private String avatar;
	private String description;
	private String time;
	
	private Achievement() {
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return Html.fromHtml(name).toString();
	}

	public String getCategory() {
		return category;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getDescription() {
		return (description != null) ? Html.fromHtml(description).toString() : "";
	}

	public String getTime() {
		return time;
	}
	
	public String getAvatarURL() {
		return URL + avatar;
	}

	@Override
	public String toString() {
		return "Achievement [name=" + name + ", category=" + category
				+ ", avatar=" + avatar + ", description=" + description
				+ ", time=" + time + "]";
	}

}
