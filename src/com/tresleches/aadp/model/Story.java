package com.tresleches.aadp.model;

/**
 * A Parse model class for Story object .
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.tresleches.aadp.interfaces.Shareable;

@ParseClassName("Story")
public class Story extends ParseObject implements Shareable{

	//Enumeration for Column definition 
	public enum Col{
		name, detail, type, pic_url,video_url,sharable_url
	}
	
	//Enumeration for Story types. 
	public enum Type {
		SEARCHING, SURVIVOR, DONOR, IN_LOVING_MEMORY
	}
	
	public String getName() {
		return getString(Col.name.toString());
	}

	public String getDetail() {
		return getString(Col.detail.toString());
	}

	public String getPicUrl() {
		return getString(Col.pic_url.toString());
	}
	public String getVideoUrl() {
		return getString(Col.video_url.toString());
	}

	public String getType() {
		return getString(Col.type.toString());
	}
	
	public String getShareableUrl() {
		return getString(Col.sharable_url.toString());
	}


}
