package com.tresleches.aadp.model;

/**
 * A Parse model class for Story object .
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Story")
public class Story extends ParseObject {

	public String getName() {
		return getString("name");
	}

	public String getDetail() {
		return getString("detail");
	}

	public String getPicUrl() {
		return getString("pic_url");
	}
	public String getVideoUrl() {
		return getString("video_url");
	}
	
}
