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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.kylewbanks.animlv.AnimatedListView;
import com.kylewbanks.animlv.AnimatedListViewObjectMapper;
import com.kylewbanks.animlv.AnimatedListViewAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.EventDetailActivity;

import com.tresleches.aadp.adapter.EventArrayAdapter;
import com.tresleches.aadp.helper.AADPTaskManager;
import com.tresleches.aadp.interfaces.AADPTask;
import com.tresleches.aadp.model.Event;

public class EventFragment extends Fragment implements AADPTask{

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
		
		/*AnimatedListViewObjectMapper objectMapper = new AnimatedListViewObjectMapper() {
		    @Override
		    public void bindObjectToView(Object object, View view) {
		    	event = (Event) object;
		        //Populate and stylize the view however you want...
		    }
		};
		AnimatedListViewAdapter eventListAdapter = new AnimatedListViewAdapter(getActivity(), R.layout.event_list, events, objectMapper);
		*/lvEvents.setAdapter(aEvent);
		
		new AADPTaskManager(this,getActivity()).execute(); //Getting Event in Background
		lvEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity(), EventDetailActivity.class);
				event = events.get(position);
				i.putExtra("location", event.getLocationAddress());
				i.putExtra("eventId", event.getObjectId());
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		return view;
	}

	/**
	 * Get the Events from net and fill in our List.
	 */
	public void getEvents() {
		
			// Define the class we would like to query
			ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
			// Define our query conditions
			query.orderByAscending("eventDate");
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
	}

	@Override
	public void performTask() {
		getEvents();
	}

	@Override
	public void performOfflineTask() {
		Toast.makeText(getActivity(),
				getResources().getString(R.string.no_network),
				Toast.LENGTH_SHORT).show();
	}
}