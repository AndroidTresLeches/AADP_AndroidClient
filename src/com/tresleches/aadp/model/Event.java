package com.tresleches.aadp.model;

import java.util.Date;

import com.parse.ParseClassName;
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

	public Date getEventTime() {
		return getDate("eventTime");
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
}
