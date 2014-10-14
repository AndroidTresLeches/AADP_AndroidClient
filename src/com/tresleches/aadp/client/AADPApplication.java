package com.tresleches.aadp.client;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseObject;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Event;
import com.tresleches.aadp.model.Story;

public class AADPApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(Event.class);
		ParseObject.registerSubclass(Story.class);
		Parse.initialize(this, getString(R.string.parseApplicationId),
                getString(R.string.parseClientId));
		
		
		// Create global configuration and initialize ImageLoader with this configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
				cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.defaultDisplayImageOptions(defaultOptions)
		.build();
		ImageLoader.getInstance().init(config);
		
	}
}

