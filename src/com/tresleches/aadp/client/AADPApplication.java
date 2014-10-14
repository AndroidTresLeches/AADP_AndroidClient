package com.tresleches.aadp.client;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Event;
import com.tresleches.aadp.model.Contact;

public class AADPApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(Event.class);
		ParseObject.registerSubclass(Contact.class);
		Parse.initialize(this, getString(R.string.parseApplicationId),
                getString(R.string.parseClientId));		
	}
}

