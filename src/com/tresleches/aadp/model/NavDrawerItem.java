package com.tresleches.aadp.model;

public class NavDrawerItem {
	 private String mTitle;
	    private int mIcon;
	     
	    public NavDrawerItem(){}
	 
	    public NavDrawerItem(String title, int icon){
	        this.mTitle = title;
	        this.mIcon = icon;
	    }
	     
	    public String getTitle(){
	        return this.mTitle;
	    }
	     
	    public void setTitle(String title){
	        this.mTitle = title;
	    }
	     
	    public void setIcon(int icon){
	        this.mIcon = icon;
	    }

		public int getIcon() {
			return this.mIcon;
		}     
}
