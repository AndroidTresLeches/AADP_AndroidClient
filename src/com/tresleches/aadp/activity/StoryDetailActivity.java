package com.tresleches.aadp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Story;

public class StoryDetailActivity extends Activity {

	private String storyId;
	private Story story;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_detail);
		storyId = getIntent().getStringExtra("story_id");
		
		//1 load story based on Story Id.
		loadStory();
		

	}

	/**
	 * Method to load Query
	 * First try to load it from Caches otherwise network
	 */
	private void loadStory() {
		ParseQuery<Story> query = ParseQuery.getQuery(Story.class);
		// First try to find from the cache and only then go to network
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
		// Execute the query to find the object with ID
		query.getInBackground(storyId, new GetCallback<Story>() {
		  public void done(Story cachedStory, ParseException e) {
		    if (e == null) {
		    	story = cachedStory;
				//Setup widgets 
				setupWidgets();
		    }
		  }
		});
		
	}

	/**
	 * Now setting up widgets for display.
	 */
	private void setupWidgets() {
		
		getActionBar().setTitle(story.getName());
		ImageView ivStoryDetailPic = (ImageView)findViewById(R.id.ivStoryDetailPic);
		TextView tvStoryDetail = (TextView) findViewById(R.id.tvStoryDetailDesc);
		
		
		ImageLoader imgLoader = ImageLoader.getInstance();
		imgLoader.displayImage(story.getPicUrl(), ivStoryDetailPic);
		tvStoryDetail.setText(story.getDetail());
	}
	
}
