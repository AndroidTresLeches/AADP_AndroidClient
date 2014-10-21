package com.tresleches.aadp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Favorite")
public class Favorite extends ParseObject {

	public String getUser() {
		return getString("user");
	}

	public String getEventObjId() {
		return getString("eventObjId");
	}
	
	public void setUser(String user) {
		put("user", user);
	}

	public void setEventObjId(String eventId) {
		put("eventObjId", eventId);
	}

}
