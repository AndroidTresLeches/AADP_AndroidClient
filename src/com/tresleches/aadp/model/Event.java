package com.tresleches.aadp.model;

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

	public String getEventDate() {
		return getString("eventDate");
	}

	public String getEventTime() {
		return getString("eventTime");
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

	public void setEventDate(String eventDate) {
		put("eventDate", eventDate);
	}

	public void setEventTime(String eventTime) {
		put("eventTime", eventTime);
	}
}
