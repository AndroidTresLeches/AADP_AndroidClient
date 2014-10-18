package com.tresleches.aadp.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.DonorFragment;
import com.tresleches.aadp.fragment.EventFragment;
import com.tresleches.aadp.fragment.StoryBoardFragment;
import com.tresleches.aadp.fragment.StoryFragment;
import com.tresleches.aadp.listener.FragmentTabListener;

public class HomeActivity extends ActionBarActivity  {
	private final int SEARCH_REQUEST = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setupTabs();

	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Event")
			.setTag("EventFragment")
			.setTabListener(new FragmentTabListener<EventFragment>(R.id.flContainer, this, "EventFragment", EventFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Stories")
			.setTag("StoryBoardFragment")
			.setTabListener(new FragmentTabListener<StoryBoardFragment>(R.id.flContainer, this, "StoryBoardFragment",StoryBoardFragment.class));

		actionBar.addTab(tab2);
		
		Tab tab3 = actionBar
				.newTab()
				.setText("Be a donor")
				.setTag("DonorFragment")
				.setTabListener(new FragmentTabListener<DonorFragment>(R.id.flContainer, this, "DonorFragment", DonorFragment.class));

		actionBar.addTab(tab3);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_search) {
			Intent it = new Intent(this, SearchResultActivity.class);
			startActivityForResult(it, SEARCH_REQUEST);
			return true;
		} 
		return true; 
	}
}