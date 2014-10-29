package com.tresleches.aadp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.adapter.StoryArrayAdapter.ViewHolder;
import com.tresleches.aadp.helper.AADPTaskManager;
import com.tresleches.aadp.helper.Utils;
import com.tresleches.aadp.interfaces.AADPTask;
import com.tresleches.aadp.interfaces.Shareable;
import com.tresleches.aadp.model.Story;

public class StoryDetailActivity extends Activity implements AADPTask{

	private String storyId;
	private Story story;
	private ImageView ivTwitter;
	private ImageView ivFacebook;
	private ImageView ivShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_detail);
		storyId = getIntent().getStringExtra("story_id");
		
		new AADPTaskManager((AADPTask)this, (Context)this).execute();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.overridePendingTransition(R.anim.open_main, R.anim.close_next);
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
		
		
		ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
		ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
		ivShare = (ImageView) findViewById(R.id.ivShare);
		
		ImageLoader imgLoader = ImageLoader.getInstance();
		imgLoader.displayImage(story.getPicUrl(), ivStoryDetailPic);
		tvStoryDetail.setText(story.getDetail());
		
		ivFacebook.setTag(story);
		ivTwitter.setTag(story);
		ivShare.setTag(story);

		setShareListeners();
		
	}
	
	/**
	 * Setting up share Listeners.
	 */
	private void setShareListeners() {
		
		ivShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startShareIntent(StoryDetailActivity.this, (Shareable)v.getTag());

			}
		});
		
		ivTwitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startShareIntentTwitter(StoryDetailActivity.this,(Shareable)v.getTag());

			}
		});

		
		ivFacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startShareIntentFaceBook(StoryDetailActivity.this,(Shareable)v.getTag());

			}
		});

	}

	@Override
	public void performTask() {
		// TODO Auto-generated method stub
		//1 load story based on Story Id.
		loadStory();
		
	}

	@Override
	public void performOfflineTask() {
		// TODO Auto-generated method stub
		
	}	
	
}
