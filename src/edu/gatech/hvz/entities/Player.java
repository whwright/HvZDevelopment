package edu.gatech.hvz.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
	private int id;
	private String gt_name;
	private String player_code;
	private String fname, lname;
	private String faction;
	private String slogan;
	private String starve_time;
	private int kills;
	
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

	public String getPlayerCode() {
		return player_code;
	}

	public String getFName() {
		return fname;
	}
	
	public String getLName() {
		return lname;
	}
	
	/**
	 * Returns Player's full name. (Fname Lname)
	 * @return String 
	 */
	public String getPlayerName() {
		return (fname + " " + lname);
	}
	
	public String getStarveTime() {
		return starve_time;
	}
	
	/**
	 * Returns time until starve, i.e. "Starves in [time]"
	 * @return String
	 */
	public String getStarveTimeFormatted()
	{
		String result = "Starves in ";
		
		int year = Integer.parseInt(starve_time.substring(0, 4));
		int month =  Integer.parseInt(starve_time.substring(5, 7));
		int day =  Integer.parseInt(starve_time.substring(8, 10));
		int hours =  Integer.parseInt(starve_time.substring(11, 13));
		int minutes =  Integer.parseInt(starve_time.substring(14, 16));
		int seconds =  Integer.parseInt(starve_time.substring(17, 19));
		
		if( year!=0 && month!=0 && day!=0 && hours!=0 && minutes!=0 && seconds!=0)
		{
			DateTime starveTime = new DateTime(year, month, day, hours, minutes, seconds);
			DateTime now = new DateTime();
			
			PeriodFormatter formatter = new PeriodFormatterBuilder()
			.appendYears().appendSuffix(" years ")
			.appendMonths().appendSuffix(" months ")
			.appendWeeks().appendSuffix(" weeks ")
			.appendDays().appendSuffix(" days ")
			.appendHours().appendSuffix(" hours ")
			.appendMinutes().appendSuffix(" minutes ")
		    .appendSeconds().appendSuffix(" seconds")
		    .printZeroNever()
		    .toFormatter();
			
			Period p = new Period(now, starveTime);
			result += formatter.print(p);
			result += ".";
			
			return result;
		}
		
		return "empty starve time";
		
	}
	
	public String getFaction() {
		return faction;
	}
	
	public String getGTName() {
		return gt_name;
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
