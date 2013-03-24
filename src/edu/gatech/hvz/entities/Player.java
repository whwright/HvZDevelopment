package edu.gatech.hvz.entities;

public class Player {
	private int id;
	private String gt_name;
	private String player_code;
	private String fname, lname;
	private String faction;
	private String slogan;
	private String starve_time;
	private int kills;
	
	public String getPlayerCode() {
		return player_code;
	}

	public String getFName() {
		return fname;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", gt_name=" + gt_name + ", player_code="
				+ player_code + ", fname=" + fname + ", lname=" + lname
				+ ", faction=" + faction + ", slogan=" + slogan
				+ ", starve_time=" + starve_time + ", kills=" + kills + "]";
	}
	
}
