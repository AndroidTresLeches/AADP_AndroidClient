package com.tresleches.aadp.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Contact;

public class ContactArrayAdapter extends ArrayAdapter<Contact> {
    private List<Contact> _contacts;
	private final Activity _context;

	public ContactArrayAdapter(Activity context, List<Contact> contacts) {
		super(context, 0, contacts);
		this._context = context;
		this._contacts = contacts;
	}

	private static class ViewHolder {
		protected ImageView ivProfileImage;
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
			viewHolder.ivProfileImage = (ImageView) view
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
		return view;
	}

}
