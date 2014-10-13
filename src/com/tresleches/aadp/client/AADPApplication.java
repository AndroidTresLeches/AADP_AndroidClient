package com.tresleches.aadp.client;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.tresleches.aadp.model.Event;

public class AADPApplication extends Application {
	public static final String YOUR_APPLICATION_ID = "dWxQW27IKwJ0kbeB2AkZK78EfD4OoukA4l0bcq39";
	public static final String YOUR_CLIENT_KEY = "ac9paLL4y4okoSPbQVlOUzkZB7c1ktvWqZEh9JpQ";
	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(Event.class);
		Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
	}
}

