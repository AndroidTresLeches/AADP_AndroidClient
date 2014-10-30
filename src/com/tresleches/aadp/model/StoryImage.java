package com.tresleches.aadp.model;

public class StoryImage{
	public int imageResource;
	public String imageTitle;
	public String imageTag;
	
	public StoryImage(int searching, String title,String tag){
		imageResource = searching;
		imageTitle = title;
		imageTag = tag;
	}
	
}