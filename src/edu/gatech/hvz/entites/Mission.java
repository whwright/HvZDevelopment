package edu.gatech.hvz.entites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Mission {
	private int id;
	private String name;
	private String start_datetime;
	private String end_datetime;
	private String location;
	private String faction;
	private String release_datetime;
	private int hide_by_default;
	private String description;
	
	public String getName() {
		return name;
	}
	public String getStart() {
		return start_datetime;
	}
	public String getEnd() {
		return end_datetime;
	}
	public String getLocation() {
		return location;
	}
	public String getFaction() {
		return faction;
	}
	public String getRelease() {
		return release_datetime;
	}
	public String getDescription() {
		return description;
	}
	
	public Date getStartDate() {
		return stringToDate(start_datetime);
	}
	
	public Date getEndDate() {
		return stringToDate(end_datetime);
	}
	
	public Date getReleaseDate() {
		return stringToDate(release_datetime);
	}
	
	@Override
	public String toString() {
		return "Mission [id=" + id + ", name=" + name + ", start_datetime="
				+ start_datetime + ", end_datetime=" + end_datetime
				+ ", location=" + location + ", faction=" + faction
				+ ", release_datetime=" + release_datetime
				+ ", hide_by_default=" + hide_by_default + ", description="
				+ description + "]";
	}
	
	private Date stringToDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(start_datetime);
		} catch (ParseException pe) {
			return new Date(0);
		}
	}
}
