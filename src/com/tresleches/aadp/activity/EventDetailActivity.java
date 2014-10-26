package com.tresleches.aadp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.helper.AddressHelper;
import com.tresleches.aadp.helper.DateHelper;
import com.tresleches.aadp.model.Event;

public class EventDetailActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnMapLongClickListener {

	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private TextView tvEventName;
	private TextView tvCoordinatorName;
	private TextView tvNotes;
	private TextView tvEventDate;
	private TextView tvEventAddress;
	private TextView tvEventTime;
	private Event event;
	private String eventId;
	private String locationAddress;
	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		event = new Event();
		eventId = getIntent().getStringExtra("eventId");
		locationAddress = getIntent().getStringExtra("location");
		loadUI();
		mLocationClient = new LocationClient(this, this, this);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map));
		if (mapFragment != null) {
			map = mapFragment.getMap();
			if (map != null) {
				Toast.makeText(this, "Map Fragment was loaded properly!",
						Toast.LENGTH_SHORT).show();
				map.setMyLocationEnabled(true);
			} else {
				Toast.makeText(this, "Error - Map was null!!",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Error - Map Fragment was null!!",
					Toast.LENGTH_SHORT).show();
		}
		// map.setOnMapLongClickListener(this);
	}

	private void loadUI() {
		tvEventName = (TextView) findViewById(R.id.tvEventName);
		tvCoordinatorName = (TextView) findViewById(R.id.tvCoordinatorName);
		tvNotes = (TextView) findViewById(R.id.tvNotes);
		tvEventDate = (TextView) findViewById(R.id.tvEventDate);
		tvEventAddress = (TextView) findViewById(R.id.tvEventAddress);
		tvEventTime = (TextView) findViewById(R.id.tvEventTime);
		getEvent();
	}

	public void getEvent() {
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		// First try to find from the cache and only then go to network
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or
																			// CACHE_ONLY
		// Execute the query to find the object with ID
		query.getInBackground(eventId, new GetCallback<Event>() {
			public void done(Event item, ParseException e) {
				if (e == null) {
					// item was found
					event = item;
					tvEventName.setText(Html.fromHtml("<i>" + event.getEventName()+ "</i>"));
					tvCoordinatorName.setText(event.getCoordinatorName());
					tvCoordinatorName.setText(Html.fromHtml("<i>"
							+ getResources().getString(R.string.by)
							+ "</i>"
							+ " "
							+ event.getCoordinatorName()
							+ " | "
							+ "<i>"
							+ getResources().getString(R.string.published)
							+ "</i>"
							+ DateHelper.getMonthInString(event
									.getPublishedDate())
							+ " "
							+ DateHelper.getDate(event.getPublishedDate())
							+ ", "
							+ DateHelper.getYearInString(event
									.getPublishedDate())));
					tvNotes.setText(event.getNotes());

					tvEventDate.setText(getResources().getString(R.string.event_date) + 
							DateHelper.getMonthInString(event
							.getEventDate())
							+ " "
							+ DateHelper.getDate(event.getEventDate())
							+ ", "
							+ DateHelper.getYearInString(event.getEventDate()));
					tvEventAddress.setText(Html.fromHtml("<i>"+ getResources().getString(R.string.event_address)) +"</i>"+ event.getLocationAddress());
					tvEventTime.setText(getResources().getString(R.string.event_time) + DateHelper.getTime(event
							.getEventStartTime())
							+ " - "
							+ DateHelper.getTime(event.getEventEndTime()));
				}
			}
		});
	}

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Long Press", Toast.LENGTH_LONG).show();
		// showAlertDialogForPoint(point);
	}

	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if (isGooglePlayServicesAvailable()) {
			mLocationClient.connect();
		}
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				mLocationClient.connect();
				break;
			}
		}
	}

	private boolean isGooglePlayServicesAvailable() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				errorFragment.show(getSupportFragmentManager(),
						"Location Updates");
			}
			return false;
		}
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status

		LatLng latLng = AddressHelper.getAddress(this, locationAddress);
		if (latLng != null) {
			Toast.makeText(this, "Location was found!", Toast.LENGTH_SHORT)
					.show();
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					latLng, 15);
			map.animateCamera(cameraUpdate);
			map.addMarker(new MarkerOptions().position(latLng).title(
					"Hello world"));
		} else {
			Toast.makeText(this, "Location was not found!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry. Location services not available to you",
					Toast.LENGTH_LONG).show();
		}
	}

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {

		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}