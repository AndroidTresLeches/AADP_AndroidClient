package com.tresleches.aadp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.EventDetailActivity;
import com.tresleches.aadp.adapter.EventArrayAdapter;
import com.tresleches.aadp.helper.NetworkUtils;
import com.tresleches.aadp.model.Event;

public class EventFragment extends Fragment {

	private ArrayList<Event> events;
	private EventArrayAdapter aEvent;
	private ListView lvEvents;
	private Event event;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		events = new ArrayList<Event>();
		aEvent = new EventArrayAdapter(getActivity(), R.layout.event_list_item,
				events);
	}

	/**
	 * Called on Fragment after the Activity is setup
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.event_list, container, false);
		lvEvents = (ListView) view.findViewById(R.id.lvEventsList);
		lvEvents.setAdapter(aEvent);
		getEvents();
		lvEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity(), EventDetailActivity.class);
				event = events.get(position);
				i.putExtra("eventId", event.getObjectId());
				startActivity(i);
			}
		});
		return view;
	}

	/**
	 * Get the Events from net and fill in our List.
	 */
	public void getEvents() {
		if (NetworkUtils.isNetworkAvailable(getActivity())) {
			// Define the class we would like to query
			ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
			// Define our query conditions
			query.findInBackground(new FindCallback<Event>() {
				public void done(List<Event> results, ParseException e) {
					if (e == null) {
						// results have all the Story
						events.addAll(results);
						aEvent.notifyDataSetChanged();
					} else {
						// There was an error
					}
				}
			});
		} else {
			Toast.makeText(getActivity(), getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
		}
	}
}