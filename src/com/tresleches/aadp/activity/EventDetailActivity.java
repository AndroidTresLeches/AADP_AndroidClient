package com.tresleches.aadp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Event;

public class EventDetailActivity extends Activity {

	private TextView tvEventName;
	private TextView tvCoordinatorName;
	private TextView tvPublishedDate;
	private ImageView ivMap;
	private TextView tvEventDate;
	private TextView tvEventAddress;
	private TextView tvEventTime;
	private Event event;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		event = new Event();
		populateEventInfo();
		getEvents();
	}
	
	public void populateEventInfo() {
		tvEventName = (TextView) findViewById(R.id.tvEventName);
		tvCoordinatorName = (TextView) findViewById(R.id.tvCoordinatorName);
		tvPublishedDate = (TextView) findViewById(R.id.tvPublishedDate);
		tvEventDate = (TextView) findViewById(R.id.tvEventDate);
		ivMap = (ImageView) findViewById(R.id.ivMap);
		tvEventAddress = (TextView) findViewById(R.id.tvEventAddress);
		tvEventTime = (TextView) findViewById(R.id.tvEventTime);
	}
	
	
	public void getEvents(){
		// Define the class we would like to query
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		// Define our query conditions
		query.getInBackground("f2YDNUeORB", new GetCallback<Event>() {
			 public void done(Event eventObj, ParseException e) {
				    if (e == null) {
				    	event = eventObj;
				    	tvEventName.setText(event.get("eventName").toString());
				    	tvCoordinatorName.setText(event.get("coordinateName").toString());
				    	tvPublishedDate.setText(event.get("publishedDate").toString());
						tvEventDate.setText(event.get("eventDate").toString());
						tvEventAddress.setText(event.get("locationAddress").toString());
						tvEventTime.setText(event.get("eventStartTime").toString());
				    } else {
				      // something went wrong
				    }
				  }
		});
		
	}
}
