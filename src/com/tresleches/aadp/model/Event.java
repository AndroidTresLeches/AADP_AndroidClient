package com.tresleches.aadp.model;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;


@ParseClassName("Event")
public class Event extends ParseObject {

	public String getEventId() {
		return getString("eventId");
	}

	public String getEventName() {
		return getString("eventName");
	}

	public Date getEventDate() {
		return getDate("eventDate");
	}

	public String getEventStartTime() {
		return getString("eventStartTime");
	}
	
	public String getEventEndTime() {
		return getString("eventEndTime");
	}
	
	public Date getPublishedDate() {
		return getDate("publishedDate");
	}
	
	public String getCoordinatorName() {
		return getString("coordinateName");
	}

	public String getLocationAddress() {
		return getString("locationAddress");
	}

	public String getNotes() {
		return getString("notes");
	}
	
	public String getFirstName() {
		return getString("firstName");
	}

	public ParseFile getProfileImage() {
		return getParseFile("profileImage");
	}
	
	public void setEventId(String eventId) {
		put("eventId", eventId);
	}

	public void setLocationAddress(String locationAddress) {
		put("locationAddress", locationAddress);
	}

	public void setEventName(String eventName) {
		put("eventName", eventName);
	}

	public void setEventDate(Date eventDate) {
		put("eventDate", eventDate);
	}

	public void setEventTime(Date eventTime) {
		put("eventTime", eventTime);
	}
	
	public void setPublishedDate(Date publishedDate) {
		put("publishedDate", publishedDate);
	}
	
	public void setCoordinatorName(String coordinateName) {
		put("coordinateName", coordinateName);
	}
	
	public void setEventStartTime(String eventStartTime) {
		put("eventStartTime", eventStartTime);
	}
	
	public void setEventEndTime(String eventEndTime) {
		put("eventEndTime", eventEndTime);
	}
	
	public void setNotes(String notes) {
		put("notes", notes);
	}
	
	public void setFirstName(String firstName) {
		put("firstName", firstName);
	}
	
	public void setProfileImage(ParseFile img) {
		put("profileImg", img);
	}
}