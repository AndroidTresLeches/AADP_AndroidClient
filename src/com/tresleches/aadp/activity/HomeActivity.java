package com.tresleches.aadp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.AboutFragment;
import com.tresleches.aadp.fragment.DonateFragment;
import com.tresleches.aadp.fragment.DonorFragment;
import com.tresleches.aadp.fragment.EventFragment;
import com.tresleches.aadp.fragment.FavoriteFragment;
import com.tresleches.aadp.fragment.LoginFragment;
import com.tresleches.aadp.fragment.StoryBoardFragment;
import com.tresleches.aadp.helper.Utils;
import com.tresleches.aadp.model.Contact;
import com.tresleches.aadp.model.Favorite;
import com.tresleches.aadp.navigation.FragmentNavigationDrawer;

public class HomeActivity extends BaseActionBarActivity {
	private static final int REQUEST_CODE = 20;
	private final int SEARCH_REQUEST = 100;
	private FragmentNavigationDrawer dlDrawer;
	LoginFragment loginFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//checkUserSignin();
		// navigation drawer - find drawer view
		dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
		// Setup drawer view
		dlDrawer.setupDrawerConfiguration(
				(ListView) findViewById(R.id.lvDrawer),
				R.layout.drawer_nav_item, R.id.flContainer);
		// Add navigation items
		//dlDrawer.addNavItem("Login", R.drawable.ic_nav_login, "Profile and Login", LoginFragment.class);
		dlDrawer.addNavItem("Events", R.drawable.ic_nav_events, "Events", EventFragment.class);
		dlDrawer.addNavItem("Stories", R.drawable.ic_nav_stories, "Stories", StoryBoardFragment.class);
		dlDrawer.addNavItem("Be a donor", R.drawable.ic_nav_donor, "Be a donor", DonorFragment.class);	
		dlDrawer.addNavItem("Favorites", R.drawable.ic_nav_favorite, "Favorite Events", FavoriteFragment.class);
		//dlDrawer.addNavItem("Twitter Feed", R.drawable.ic_nav_twitter, "AADP Tweets", TwitterFragment.class);
		dlDrawer.addNavItem("Donate", R.drawable.ic_nav_donate, "Donate", DonateFragment.class);
		dlDrawer.addNavItem("About", R.drawable.ic_nav_about, "About AADP", AboutFragment.class);
		// Select default
		if (savedInstanceState == null) {
			dlDrawer.selectDrawerItem(0);
		}
	}

        public void checkUserSignin() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
		  // do stuff with the user
		} else {
		  // show the signup or login screen
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
		}
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

        public void updateAvatarForContact(String objId, int drawableId) {

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
		final ParseFile pImgFile = new ParseFile("avatar.jpg",
				Utils.CompressConvertBitmapTobyteArray(bitmap,
						Bitmap.CompressFormat.JPEG));

		ParseQuery<Contact> query = ParseQuery.getQuery(Contact.class);

		query.getInBackground(objId, new GetCallback<Contact>() {
			public void done(Contact item, ParseException e) {
				if (e == null) {
					item.setProfileImage(pImgFile);
					item.saveInBackground();

				} else {
					Log.d("ERROR", e.toString());
				}
			}
		});
	}
        
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          // REQUEST_CODE is defined above
          if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
             // Extract name value from result extras
             String user = data.getExtras().getString("username");
             String objectId = data.getExtras().getString("objectId");
             // Toast the name to display temporarily on screen
             Favorite favItem = new Favorite();
             favItem.setUser(user);
			 favItem.setEventObjId(objectId);
			 // Immediately save the data asynchronously
			 favItem.saveInBackground();
				Toast.makeText(this, "Favorite Saved", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
          }
        } 
}
