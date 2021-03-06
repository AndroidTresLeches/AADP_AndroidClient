package com.tresleches.aadp.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.LoginActivity;
import com.tresleches.aadp.circularImageView.CircularImageView;
import com.tresleches.aadp.helper.DateHelper;
import com.tresleches.aadp.model.Event;
import com.tresleches.aadp.model.Favorite;

public class EventArrayAdapter extends ArrayAdapter<Event> {

	private static class ViewHolder {
		TextView tvEventName;
		TextView tvDate;
		TextView tvDay;
		CircularImageView ivCoordinatorImg;
		TextView tvTime;
		TextView tvAddress;
		TextView tvCoordinatorName;
		ImageView ivCalendarIcon;
		ImageView ivFavoriteIcon;
	}

	private Context context;
	int year;
	int month;
	String fullMonth;
	int day;
	private final int REQUEST_CODE = 20;
	private int lastPosition = -1;
	boolean isFav;

	public EventArrayAdapter(Context context, int resource, List<Event> events) {
		super(context, R.layout.event_list_item, events);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the item
		final ViewHolder viewHolder;
		final Event event = (Event) getItem(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.event_list_item, parent, false);
			viewHolder.tvEventName = (TextView) convertView
					.findViewById(R.id.tvEventName);
			viewHolder.tvDate = (TextView) convertView
					.findViewById(R.id.tvDate);
			viewHolder.tvDay = (TextView) convertView.findViewById(R.id.tvDay);
			viewHolder.ivCoordinatorImg = (CircularImageView) convertView
					.findViewById(R.id.ivCoordinator);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tvTime);
			viewHolder.tvAddress = (TextView) convertView
					.findViewById(R.id.tvAddress);
			viewHolder.tvCoordinatorName = (TextView) convertView
					.findViewById(R.id.tvCoordinatorName);
			viewHolder.ivCalendarIcon = (ImageView) convertView
					.findViewById(R.id.ivCalendarIcon);
			viewHolder.ivFavoriteIcon = (ImageView) convertView
					.findViewById(R.id.ivFavoriteIcon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		getDate(event.getEventDate());
		viewHolder.tvEventName.setText(event.getEventName());
		viewHolder.tvDate.setText(fullMonth);
		viewHolder.tvDay.setText(Integer.toString(day));
		viewHolder.tvTime.setText(DateHelper.getTime(event.getEventStartTime())
				+ " - " + DateHelper.getTime(event.getEventEndTime()));
		viewHolder.tvAddress.setText(event.getLocationAddress());
		viewHolder.tvCoordinatorName.setText(event.getCoordinatorName());

		/*if (isFavorite(event)) {
			viewHolder.ivFavoriteIcon
					.setImageResource(R.drawable.ic_checked_fav);
		}*/

		ParseFile imgFile = event.getProfileImage();
		if (imgFile != null) {
			try {
				byte[] profileImage = imgFile.getData();
				Bitmap bmp = BitmapFactory.decodeByteArray(profileImage, 0,
						profileImage.length);
				viewHolder.ivCoordinatorImg
						.setImageResource(android.R.color.transparent);
				viewHolder.ivCoordinatorImg.setImageBitmap(bmp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		viewHolder.ivCalendarIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent calIntent = new Intent(Intent.ACTION_INSERT);
				calIntent.setData(CalendarContract.Events.CONTENT_URI);
				calIntent.setType("vnd.android.cursor.item/event");
				calIntent.putExtra(Events.TITLE, event.getEventName());
				calIntent.putExtra(Events.EVENT_LOCATION,
						event.getLocationAddress());
				calIntent.putExtra(Events.DESCRIPTION, "");
				// getDate(event.getEventDate());
				GregorianCalendar startTime = new GregorianCalendar(year,
						month, day, Integer.parseInt(event.getEventStartTime()
								.substring(0, 2)), 0);
				GregorianCalendar endTime = new GregorianCalendar(year, month,
						day, Integer.parseInt(event.getEventEndTime()
								.substring(0, 2)), 0);
				// calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,
				// true);
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
						startTime.getTimeInMillis());
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
						endTime.getTimeInMillis());
				calIntent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
				calIntent.putExtra(Events.AVAILABILITY,
						Events.AVAILABILITY_BUSY);

				context.startActivity(calIntent);
			}
		});

		viewHolder.ivFavoriteIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Favorite favItem = new Favorite();
				ParseUser currentUser = ParseUser.getCurrentUser();
				if (currentUser != null) {
					// do stuff with the user
					// Set the current user, assuming a user is signed in
					String user = ParseUser.getCurrentUser().getUsername();
					favItem.setUser(user);
					favItem.setEventObjId(event.getObjectId());
					// Immediately save the data asynchronously
					favItem.saveInBackground();
					Toast.makeText(context, "Favorite Saved",
							Toast.LENGTH_SHORT).show();
					ObjectAnimator fade = ObjectAnimator.ofFloat(v, "alpha", 1,
							0f, 0f);
					fade.setDuration(3000);
					viewHolder.ivFavoriteIcon
							.setImageResource(R.drawable.ic_checked_fav);
				} else {
					// show the signup or login screen
					Intent i = new Intent(getContext(), LoginActivity.class);
					i.putExtra("objectId", event.getObjectId());
					((Activity) context)
							.startActivityForResult(i, REQUEST_CODE);
					// Set the current user, assuming a user is signed in
				}
			}
		});

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;
		return convertView;
	}

	public void getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH);
			fullMonth = new SimpleDateFormat("MMMM").format(date);
			day = cal.get(Calendar.DAY_OF_MONTH);
		}
	}
}