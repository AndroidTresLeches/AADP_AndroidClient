package com.tresleches.aadp.model;

import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("KnowledgeBase")
public class KnowledgeBase extends ParseObject {
	public static final String CATEGORY = "category";
	public static final String SUB_CATEGORY = "subCategory";
	public static final String KEYWORD1 = "keyWord1";
	public static final String KEYWORD2 = "keyWord2";
	public static final String KEYWORD3 = "keyWord3";
	public static final String CONTENT_TEXT = "contentText";
	public static final String WEB_URL = "webUrl";
	public static final String VIDEO_URL = "videoUrl";
	public static final String IMAGE_URL = "imageUrl";
	public static final String DOCUMENT = "document";
	
	public KnowledgeBase(){
		super();
	}

	public String getCategory() {
		return getString(CATEGORY);
	}
	
	public void setCategory(String value) {
		put(CATEGORY, value);
	}

	public String getSubCategory() {
		return getString(SUB_CATEGORY);
	}
	
	public void setSubCategory(String value) {
		put(SUB_CATEGORY, value);
	}

	public String getKeyword1() {
		return getString(KEYWORD1);
	}
	
	public void setKeyword1(String value) {
		put(KEYWORD1, value);
	}

	public String getKeyword2() {
		return getString(KEYWORD2);
	}

	public void setKeyword2(String value) {
		put(KEYWORD2, value);
	}
	
	public String getKeyword3() {
		return getString(KEYWORD3);
	}

	public void setKeyword3(String value) {
		put(KEYWORD3, value);
	}
	
	public String getContentText() {
		return getString(CONTENT_TEXT);
	}
	
	public void setContentText(String value) {
		put(CONTENT_TEXT, value);
	}

	public String getWebUrl() {
		return getString(WEB_URL);
	}
	
	public void setWebUrl(String value) {
		put(WEB_URL, value);
	}

	public String getVideoUrl() {
		return getString(VIDEO_URL);
	}
	
	public void setVideoUrl(String value) {
		put(VIDEO_URL, value);
	}

	public String getImageUrl() {
		return getString(IMAGE_URL);
	}
	
	public void setImageUrl(String value) {
		put(IMAGE_URL, value);
	}

	public String getDocument() {
		return getString(DOCUMENT);
	}
	
	public void setDocument(String value) {
		put(DOCUMENT, value);
	}
	
}
