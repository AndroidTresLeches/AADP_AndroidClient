package com.tresleches.aadp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.EventDetailActivity;
import com.tresleches.aadp.activity.LoginActivity;
import com.tresleches.aadp.adapter.EventArrayAdapter;
import com.tresleches.aadp.helper.AADPTaskManager;
import com.tresleches.aadp.helper.NetworkUtils;
import com.tresleches.aadp.interfaces.AADPTask;
import com.tresleches.aadp.model.Event;
import com.tresleches.aadp.model.Favorite;

public class FavoriteFragment extends Fragment implements AADPTask {

	private static final int REQUEST_CODE = 30;
	private ArrayList<Event> favEvents;
	private ArrayList<Favorite> favorites;
	private EventArrayAdapter aFavEvent;
	private ListView lvEvents;
	private Event event;
	ParseUser parseUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		favEvents = new ArrayList<Event>();
		favorites = new ArrayList<Favorite>();
		aFavEvent = new EventArrayAdapter(getActivity(),
				R.layout.fragment_favorite, favEvents);
	}

	/**
	 * Called on Fragment after the Activity is setup
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_favorite, container, false);
		lvEvents = (ListView) view.findViewById(R.id.lvFavoritesList);
		lvEvents.setAdapter(aFavEvent);
		new AADPTaskManager(this, getActivity()).execute();		//getFavEvents();
		lvEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity(), EventDetailActivity.class);
				event = favEvents.get(position);
				i.putExtra("location", event.getLocationAddress());
				i.putExtra("eventId", event.getObjectId());
				startActivity(i);
			}
		});
		Toast.makeText(getActivity(), getActivity().toString(), Toast.LENGTH_SHORT).show();
		return view;
	}

	/**
	 * Get the Events from net and fill in our List.
	 */
	public void getFavEvents() {
		if (NetworkUtils.isNetworkAvailable(getActivity())) {
			// Define the class we would like to query

			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser == null) {

				// show the signup or login screen
				Intent i = new Intent(getActivity(), LoginActivity.class);
				// i.putExtra("objectId", event.getObjectId());
				getActivity().startActivityForResult(i, REQUEST_CODE);
				getActivity().overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
				// Set the current user, assuming a user is signed in
			} else {
				ParseQuery<Favorite> query = ParseQuery
						.getQuery(Favorite.class);
				// Define our query conditions
				String user = ParseUser.getCurrentUser().getUsername();
				if (user != null) {
					query.whereEqualTo("user", user);
					query.findInBackground(new FindCallback<Favorite>() {
						public void done(List<Favorite> results,
								ParseException e) {
							if (e == null) {
								// results have all the Story
								favorites.addAll(results);
								getEvents(favorites);
								// aFavEvent.notifyDataSetChanged();
							} else {
								// There was an error
							}
						}
					});
				} else {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.no_network),
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	public void getEvents(ArrayList<Favorite> favorites) {
		// TODO Auto-generated method stub
		if (NetworkUtils.isNetworkAvailable(getActivity())) {
			// Define the class we would like to query

			for (Favorite f : favorites) {
				ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
				// Define our query conditions

				query.whereEqualTo("objectId", f.getEventObjId());
				query.findInBackground(new FindCallback<Event>() {
					public void done(List<Event> results, ParseException e) {
						if (e == null) {
							// results have all the Story
							favEvents.addAll(results);

							aFavEvent.notifyDataSetChanged();
						} else {
							// There was an error
						}
					}
				});
			}
		} else {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.no_network),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("@@@@@@@@");
		getActivity();
		if (resultCode == Activity.RESULT_OK  && requestCode == REQUEST_CODE) {
			ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
			// Define our query conditions
			String user = ParseUser.getCurrentUser().getUsername();
			if (user != null) {
				query.whereEqualTo("user", user);
				query.findInBackground(new FindCallback<Favorite>() {
					public void done(List<Favorite> results, ParseException e) {
						if (e == null) {
							// results have all the Story
							favorites.addAll(results);
							getEvents(favorites);
							// aFavEvent.notifyDataSetChanged();
						} else {
							// There was an error
						}
					}
				});
			}
		}
	}
	
	public void doSomething(String param) {
	      // do something in fragment
		ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
		// Define our query conditions
		String user = ParseUser.getCurrentUser().getUsername();
		if (user != null) {
			query.whereEqualTo("user", user);
			query.findInBackground(new FindCallback<Favorite>() {
				public void done(List<Favorite> results, ParseException e) {
					if (e == null) {
						// results have all the Story
						favorites.addAll(results);
						getEvents(favorites);
						// aFavEvent.notifyDataSetChanged();
					} else {
						// There was an error
					}
				}
			});
		}
	  }

	@Override
	public void performTask() {
		// TODO Auto-generated method stub
		getFavEvents();
	}

	@Override
	public void performOfflineTask() {
		// TODO Auto-generated method stub
		
	}
}
