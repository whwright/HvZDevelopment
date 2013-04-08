package edu.gatech.hvz.entities;

public class Kill {
	private String playerCode;
	private String feed1, feed2;
	private double lat, lng;
	
	public Kill(String playerCode, String feed1, String feed2, double lat, double lng)
	{
		this.playerCode = playerCode;
		this.feed1 = feed1;
		this.feed2 = feed2;
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "Kill [playerCode=" + playerCode + ", feed1=" + feed1
				+ ", feed2=" + feed2 + ", lat=" + lat + ", lng=" + lng + "]";
	}
	
}
