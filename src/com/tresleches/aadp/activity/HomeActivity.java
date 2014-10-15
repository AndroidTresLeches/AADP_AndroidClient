package com.tresleches.aadp.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;



import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.DonorFragment;
import com.tresleches.aadp.fragment.EventFragment;
import com.tresleches.aadp.fragment.StoryFragment;
import com.tresleches.aadp.listener.FragmentTabListener;

public class HomeActivity extends ActionBarActivity  {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(80, 165, 230)));
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
			.setTag("StoryFragment")
			.setTabListener(new FragmentTabListener<StoryFragment>(R.id.flContainer, this, "StoryFragment",StoryFragment.class));

		actionBar.addTab(tab2);
		
		Tab tab3 = actionBar
				.newTab()
				.setText("Be a donor")
				.setTag("DonorFragment")
				.setTabListener(new FragmentTabListener<DonorFragment>(R.id.flContainer, this, "DonorFragment", DonorFragment.class));

		actionBar.addTab(tab3);
	}
}
