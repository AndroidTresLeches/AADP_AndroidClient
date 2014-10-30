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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.EventDetailActivity;
import com.tresleches.aadp.adapter.EventArrayAdapter;
import com.tresleches.aadp.helper.NetworkUtils;
import com.tresleches.aadp.model.Event;

public class VounteerFragment extends Fragment {
	private ArrayList<Event> volEvents;
	private EventArrayAdapter aVolEvent;
	private ListView lvEvents;
	private Event event;
	private static String contact;
	ParseUser parseUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		volEvents = new ArrayList<Event>();
		aVolEvent = new EventArrayAdapter(getActivity(),
				R.layout.activity_volunteer_events, volEvents);

	}

	public static VounteerFragment newInstance(String contactName) {

		VounteerFragment volFrag = new VounteerFragment();
		contact = contactName;
		Bundle args = new Bundle();
		args.putString("contact_name", contact);
		volFrag.setArguments(args);
		return volFrag;
	}
	/**
	 * Called on Fragment after the Activity is setup
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.event_list, container, false);
		lvEvents = (ListView) view.findViewById(R.id.lvEventsList);
		lvEvents.setAdapter(aVolEvent);
		getVolEvents();
		getActivity().overridePendingTransition(R.anim.right_in,
				R.anim.left_out);
		lvEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity(), EventDetailActivity.class);
				event = volEvents.get(position);
				i.putExtra("location", event.getLocationAddress());
				i.putExtra("eventId", event.getObjectId());
				startActivity(i);
			}
		});
		return view;
	}

	private void getVolEvents() {
		if (NetworkUtils.isNetworkAvailable(getActivity())) {
			// Define the class we would like to query

			ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
			// Define our query conditions

			query.whereContains("coordinateName", contact);
			query.orderByAscending("eventDate");
			query.findInBackground(new FindCallback<Event>() {
				public void done(List<Event> results, ParseException e) {
					if (e == null) {
						// results have all the Story
						volEvents.addAll(results);

						aVolEvent.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), "Error",Toast.LENGTH_SHORT).show();
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
