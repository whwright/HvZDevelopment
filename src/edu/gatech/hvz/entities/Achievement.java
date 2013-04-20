package edu.gatech.hvz.entities;

import java.io.Serializable;

public class Achievement implements Serializable {
	

	private static final long serialVersionUID = 8642753786296032809L;
	
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
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getDescription() {
		return description;
	}

	public String getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Achievement [name=" + name + ", category=" + category
				+ ", avatar=" + avatar + ", description=" + description
				+ ", time=" + time + "]";
	}

}
