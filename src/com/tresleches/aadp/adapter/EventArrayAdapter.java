package com.tresleches.aadp.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tresleches.aadp.R;
import com.tresleches.aadp.helper.DateHelper;
import com.tresleches.aadp.model.Event;

public class EventArrayAdapter extends ArrayAdapter<Event> {
	private static class ViewHolder {
		TextView tvEventName;
		TextView tvDate;
		// ImageView ivCoordinator;
		TextView tvTime;
		TextView tvAddress;
		TextView tvCoordinatorName;
		ImageView ivCalendarIcon;
	}

	private Context context;
	int year;
	int month;
	int day;

	public EventArrayAdapter(Context context, int resource, List<Event> events) {
		super(context, R.layout.event_list_item, events);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the item
		final Event event = getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.event_list_item, parent, false);

			viewHolder.tvEventName = (TextView) convertView
					.findViewById(R.id.tvEventName);
			viewHolder.tvDate = (TextView) convertView
					.findViewById(R.id.tvDate);
			// viewHolder.ivCoordinator =
			// (ImageView)convertView.findViewById(R.id.ivCoordinator);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tvTime);
			viewHolder.tvAddress = (TextView) convertView
					.findViewById(R.id.tvAddress);
			viewHolder.tvCoordinatorName = (TextView) convertView
					.findViewById(R.id.tvCoordinatorName);
			viewHolder.ivCalendarIcon = (ImageView) convertView
					.findViewById(R.id.ivCalendarIcon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvEventName.setText(event.getEventName());
		viewHolder.tvDate.setText(DateHelper.getDateInString(event
				.getEventDate()));
		// viewHolder.tvTime.setText(event.getEventTime());
		viewHolder.tvAddress.setText(event.getLocationAddress());
		viewHolder.tvCoordinatorName.setText(event.getCoordinatorName());

		viewHolder.ivCalendarIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent calIntent = new Intent(Intent.ACTION_INSERT);
				calIntent.setData(CalendarContract.Events.CONTENT_URI);
				calIntent.setType("vnd.android.cursor.item/event");
				calIntent.putExtra(Events.TITLE, event.getEventName());
				calIntent.putExtra(Events.EVENT_LOCATION,
						event.getLocationAddress());
				calIntent.putExtra(Events.DESCRIPTION, "");
				getDate(event.getEventDate());
				GregorianCalendar calDate = new GregorianCalendar(year, month, day);
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
						calDate.getTimeInMillis());
				calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
						calDate.getTimeInMillis());
				calIntent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
				calIntent.putExtra(Events.AVAILABILITY,
						Events.AVAILABILITY_BUSY);

				context.startActivity(calIntent);
			}
		});
		return convertView;
	}

	public void getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
	}
}
