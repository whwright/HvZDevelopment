package edu.gatech.hvz.entities;

public class Kill {
	private String playerCode;
	private String feed1, feed2;
	private int lat, lng;
	
	public Kill(String playerCode, String feed1, String feed2, int lat, int lng)
	{
		this.playerCode = playerCode;
		this.feed1 = feed1;
		this.feed2 = feed2;
		this.lat = lat;
		this.lng = lng;
	}

	public String getPlayerCode() {
		return playerCode;
	}

	public String getFeed1() {
		return feed1;
	}

	public String getFeed2() {
		return feed2;
	}

	public int getLat() {
		return lat;
	}

	public int getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return "Kill [playerCode=" + playerCode + ", feed1=" + feed1
				+ ", feed2=" + feed2 + ", lat=" + lat + ", lng=" + lng + "]";
	}
	
}
