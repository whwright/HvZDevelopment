package edu.gatech.hvz.entities;

public class Kill {
	private String killer;
	private String victim;
	private String feed1, feed2;
	private int lat, lng;
	private String time;
	
	public Kill(String killer, String victim, String feed1, String feed2, int lat, int lng, String time)
	{
		this.killer = killer;
		this.victim = victim;
		this.feed1 = feed1;
		this.feed2 = feed2;
		this.lat = lat;
		this.lng = lng;
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Kill [killer=" + killer + ", victim=" + victim + ", lat=" + lat + ", lng=" + lng
				+ ", feed1=" + feed1 + ", feed2=" + feed2 + ", time=" + time + "]";
	}
	

}
