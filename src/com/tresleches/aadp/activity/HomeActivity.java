package com.tresleches.aadp.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.AboutFragment;
import com.tresleches.aadp.fragment.DonateFragment;
import com.tresleches.aadp.fragment.DonorFragment;
import com.tresleches.aadp.fragment.EventFragment;
import com.tresleches.aadp.fragment.FavoriteFragment;
import com.tresleches.aadp.fragment.LoginFragment;
import com.tresleches.aadp.fragment.StoryBoardFragment;
import com.tresleches.aadp.fragment.TwitterFragment;
import com.tresleches.aadp.listener.FragmentTabListener;
import com.tresleches.aadp.navigation.FragmentNavigationDrawer;

public class HomeActivity extends BaseActionBarActivity {
	private final int SEARCH_REQUEST = 100;
	private FragmentNavigationDrawer dlDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// navigation drawer - find drawer view
		dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
		// Setup drawer view
		dlDrawer.setupDrawerConfiguration(
				(ListView) findViewById(R.id.lvDrawer),
				R.layout.drawer_nav_item, R.id.flContainer);
		// Add navigation items
		dlDrawer.addNavItem("Login", R.drawable.ic_nav_login, "Profile and Login", LoginFragment.class);
		dlDrawer.addNavItem("Events", R.drawable.ic_nav_events, "Events", EventFragment.class);
		dlDrawer.addNavItem("Stories", R.drawable.ic_nav_stories, "Stories", StoryBoardFragment.class);
		dlDrawer.addNavItem("Be a donor", R.drawable.ic_nav_donor, "Be a donor", DonorFragment.class);	
		dlDrawer.addNavItem("Favorite", R.drawable.ic_nav_favorite, "Favorite Events", FavoriteFragment.class);
		dlDrawer.addNavItem("Twitter", R.drawable.ic_nav_twitter, "AADP Tweets", TwitterFragment.class);
		dlDrawer.addNavItem("Donate", R.drawable.ic_nav_donate, "Donate", DonateFragment.class);
		dlDrawer.addNavItem("About", R.drawable.ic_nav_about, "About AADP", AboutFragment.class);
		// Select default
		if (savedInstanceState == null) {
			dlDrawer.selectDrawerItem(1);
		}
		
		//setupTabs();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Event")
				.setTag("EventFragment")
				.setTabListener(
						new FragmentTabListener<EventFragment>(
								R.id.flContainer, this, "EventFragment",
								EventFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Stories")
				.setTag("StoryBoardFragment")
				.setTabListener(
						new FragmentTabListener<StoryBoardFragment>(
								R.id.flContainer, this, "StoryBoardFragment",
								StoryBoardFragment.class));

		actionBar.addTab(tab2);

		Tab tab3 = actionBar
				.newTab()
				.setText("Be a donor")
				.setTag("DonorFragment")
				.setTabListener(
						new FragmentTabListener<DonorFragment>(
								R.id.flContainer, this, "DonorFragment",
								DonorFragment.class));

		actionBar.addTab(tab3);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		if (dlDrawer.isDrawerOpen()) {
			// hide menu items
			menu.findItem(R.id.action_search).setVisible(false);
		}
		else{menu.findItem(R.id.action_search).setVisible(true);
			
		}
		return super.onPrepareOptionsMenu(menu);
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
		} else {
			// The action bar home/up action should open or close the drawer.
			// ActionBarDrawerToggle will take care of this.
			if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
				return true;
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		dlDrawer.getDrawerToggle().syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
	}

}