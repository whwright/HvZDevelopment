package edu.gatech.hvz.entities;

public class Kill {
	private String killer;
	private String victim;
	private String feed1, feed2;
	private int lat, lng;
	private String time;
	
	@Override
	public String toString() {
		return "Kill [victim=" + victim + ", lat=" + lat + ", lng=" + lng
				+ ", time=" + time + "]";
	}
	

}
