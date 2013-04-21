package edu.gatech.hvz.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
	private int id;
	private String gt_name;
	private String player_code;
	private String fname, lname;
	private String faction;
	private String slogan;
	private String avatar;
	private String starve_time;
	private int kills;
	
	public String getGTName() {
		return gt_name;
	}

	public String getPlayerCode() {
		return player_code;
	}

	public String getFName() {
		return fname;
	}

	public String getLName() {
		return lname;
	}

	public String getFaction() {
		return faction;
	}

	public String getSlogan() {
		return slogan;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getStarveTime() {
		return starve_time;
	}

	public int getKills() {
		return kills;
	}
	
	/**
	 * Returns Player's full name. (Fname Lname)
	 * @return String 
	 */
	public String getPlayerName() {
		return (fname + " " + lname);
	}
		
	@Override
	public String toString() {
		return "Player [id=" + id + ", gt_name=" + gt_name + ", player_code="
				+ player_code + ", fname=" + fname + ", lname=" + lname
				+ ", faction=" + faction + ", slogan=" + slogan
				+ ", starve_time=" + starve_time + ", kills=" + kills + "]";
	}

	public boolean searchName(String text) 
	{
		if( text.indexOf(getFName()) > 0 || text.indexOf(getLName()) > 0 )
		{
			return true;
		}
		return false;
	}

	/**
	 * Parcelable constructor used to pass Player objects between activities
	 * @param in
	 */
	public Player(Parcel in) {
		id = in.readInt();
		gt_name = in.readString();
		player_code = in.readString();
		fname = in.readString();
		lname = in.readString();
		faction = in.readString();
		slogan = in.readString();
		starve_time = in.readString();
		kills = in.readInt();
	}

	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(gt_name);
		dest.writeString(player_code);
		dest.writeString(fname);
		dest.writeString(lname);
		dest.writeString(faction);
		dest.writeString(slogan);
		dest.writeString(starve_time);
		dest.writeInt(kills);
	}
	
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>()
	{
		public Player createFromParcel(Parcel in)
		{
			return new Player(in);
		}
		
		public Player[] newArray(int size)
		{
			return new Player[size];
		}
	};
	
}
