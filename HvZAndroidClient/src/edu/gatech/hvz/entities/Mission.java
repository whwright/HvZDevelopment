package edu.gatech.hvz.entities;

import java.io.Serializable;

import android.text.Html;

/**
 * Represents a mission from the website.  Can have one of three
 * states: active, issued, or closed.
 */
public class Mission implements Serializable{

	private static final long serialVersionUID = -3595817016895659759L;

	public enum Status {
		ACTIVE,
		ISSUED,
		CLOSED
	}
	
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
		//Attempt to decode the description from HTML.
		return (description != null) ? Html.fromHtml(description).toString() : "";
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
}
