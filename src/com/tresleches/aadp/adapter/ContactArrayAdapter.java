package com.tresleches.aadp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Contact;

import java.util.List;

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    private List<Contact> _contacts;
	private final Activity _context;
	private int lastPosition = -1;

	public ContactArrayAdapter(Activity context, List<Contact> contacts) {
		super(context, 0, contacts);
		this._context = context;
		this._contacts = contacts;
	}

	private static class ViewHolder {
		protected CircularImageView ivProfileImage;
		protected TextView tvContactName;
		protected TextView tvPrimaryPhone;
		protected TextView tvEmail;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		// Get the data item for this position
		Contact contact = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.contact_item, parent, false);
			viewHolder.ivProfileImage = (CircularImageView) view
					.findViewById(R.id.ivProfileImage);
			viewHolder.tvContactName = (TextView) view
					.findViewById(R.id.tvContactName);
			viewHolder.tvPrimaryPhone = (TextView) view
					.findViewById(R.id.tvPrimaryPhone);
			viewHolder.tvEmail = (TextView) view.findViewById(R.id.tvEmail);
		  
		    view.setTag(viewHolder);
			    	
		} else {
			view = convertView;	  
			viewHolder = (ViewHolder) view.getTag();
		}
		// Populate the data into the template view using the data object
		//viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
		//get parseFile and load it
		
		String firstName = contact.getFirstName();
		String lastName = contact.getLastName();
		String middleName = contact.getMiddleName();
		viewHolder.tvContactName.setText(firstName + " "
				+ ((middleName == null) ? "" : middleName) + " " + lastName);
		viewHolder.tvPrimaryPhone.setText(contact.getPrimaryPhone());
		viewHolder.tvEmail.setText(contact.getEmail());
		byte[] profileImage;
		ParseFile imgFile = contact.getProfileImage();
		if (imgFile != null) {
			try {
				profileImage = imgFile.getData();
				Bitmap bmp = BitmapFactory.decodeByteArray(profileImage, 0,
						profileImage.length);
				viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
				viewHolder.ivProfileImage.setImageBitmap(bmp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		view.startAnimation(animation);
	    lastPosition = position;
		return view;
	}

}
