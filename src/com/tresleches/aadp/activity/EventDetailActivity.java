package com.tresleches.aadp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.helper.AADPTaskManager;
import com.tresleches.aadp.helper.AddressHelper;
import com.tresleches.aadp.helper.DateHelper;
import com.tresleches.aadp.interfaces.AADPTask;
import com.tresleches.aadp.model.Event;

public class EventDetailActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnMapLongClickListener, AADPTask {

	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private TextView tvEventName;
	private TextView tvCoordinatorName;
	private TextView tvNotes;
	private TextView tvEventDate;
	private TextView tvEventAddress;
	private ImageView ivProfileImg;
	private TextView tvOpenInMaps;
	private TextView tvVolunteer;

	private Event event;
	private String eventId;
	private String locationAddress;
	private LatLng srcLatLng;
	private LocationManager mLocationManager;
	private String srcLatitude;
	private String srcLongitude;
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
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3, 1, mLocationListener);
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
		tvOpenInMaps.setOnClickListener(new OnClickListener() {

			LatLng latlng = AddressHelper.getAddress(getApplicationContext(),
					locationAddress);
			String destinationLatitude = Double.toString(latlng.latitude);
			String destinationLongitude = Double.toString(latlng.longitude);

			@Override
			public void onClick(View v) {

				if (srcLatitude != null && srcLongitude != null) {
					String uri = "http://maps.google.com/maps?saddr="
							+ srcLatitude + "," + srcLongitude + "&daddr="
							+ destinationLatitude + "," + destinationLongitude;
					Intent intent = new Intent(
							android.content.Intent.ACTION_VIEW, Uri.parse(uri));
					intent.setClassName("com.google.android.apps.maps",
							"com.google.android.maps.MapsActivity");
					try {
						startActivity(intent);
					} catch (ActivityNotFoundException ex) {
						try {
							Intent unrestrictedIntent = new Intent(
									Intent.ACTION_VIEW, Uri.parse(uri));
							startActivity(unrestrictedIntent);
						} catch (ActivityNotFoundException innerEx) {
							Toast.makeText(getApplicationContext(),
									"Please install a maps application",
									Toast.LENGTH_LONG).show();
						}
					}
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
				}
			}
		});

		tvVolunteer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "some@email.address" });
				intent.putExtra(Intent.EXTRA_SUBJECT,
						"Volunteer for " + event.getEventName());
				intent.putExtra(Intent.EXTRA_TEXT, "mail body");
				startActivity(Intent.createChooser(intent, ""));

			}
		});
	}

	private void loadUI() {
		tvEventName = (TextView) findViewById(R.id.tvEventName);
		tvCoordinatorName = (TextView) findViewById(R.id.tvCoordinatorName);
		tvNotes = (TextView) findViewById(R.id.tvNotes);
		tvEventDate = (TextView) findViewById(R.id.tvEventDate);
		tvEventAddress = (TextView) findViewById(R.id.tvEventAddress);
		ivProfileImg = (ImageView) findViewById(R.id.ivCoordinator);
		tvOpenInMaps = (TextView) findViewById(R.id.tvOpenInMaps);
		tvVolunteer = (TextView) findViewById(R.id.tvBeVolunteer);
		new AADPTaskManager((AADPTask) this, this).execute(); // Loads the Event
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
					tvEventName.setText(event.getEventName());
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

					tvEventDate.setText(DateHelper.getMonthInString(event
							.getEventDate())
							+ " "
							+ DateHelper.getDate(event.getEventDate())
							+ ", "
							+ DateHelper.getYearInString(event.getEventDate())
							+ " @ "
							+ DateHelper.getTime(event.getEventStartTime())

							+ DateHelper.getTime(event.getEventEndTime()));
					tvEventAddress.setText(event.getLocationAddress());
					ParseFile imgFile = event.getProfileImage();
					if (imgFile != null) {
						try {
							byte[] profileImage = imgFile.getData();
							Bitmap bmp = BitmapFactory.decodeByteArray(
									profileImage, 0, profileImage.length);
							ivProfileImg
									.setImageResource(android.R.color.transparent);
							ivProfileImg.setImageBitmap(bmp);
						} catch (ParseException ex) {
							ex.printStackTrace();
						}
					}
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
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	@Override
	public void performTask() {
		getEvent();

	}

	@Override
	public void performOfflineTask() {
		// TODO Auto-generated method stub
		// getOfflineEvent()
	}

	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(final Location srcLocation) {
			srcLatLng = new LatLng(srcLocation.getLatitude(),
					srcLocation.getLongitude());
			srcLatitude = Double.toString(srcLatLng.latitude);
			srcLongitude = Double.toString(srcLatLng.longitude);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}
	};

}