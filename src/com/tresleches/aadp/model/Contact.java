package com.tresleches.aadp.model;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tresleches.aadp.helper.Utils;

@ParseClassName("Contact")
public class Contact extends ParseObject {

	public Contact() {
		super();
	}

	public Contact(String _lastName, String _firstName, String _middleName,
			String _primaryPhone, String _secondaryPhone, String _email,
			String _profileImagePath) {
		setFirstName(_firstName);
		setLastName(_lastName);
		setMiddleName(_middleName);
		setPrimaryPhone(_primaryPhone);
		setSecondaryPhone(_secondaryPhone);
		setEmail(_email);

		if (_profileImagePath != null) {

		}
	}

	public String getLastName() {
		return getString("lastName");
	}

	public void setLastName(String value) {
		put("lastName", value);
	}

	public String getFirstName() {
		return getString("firstName");
	}

	public void setFirstName(String value) {
		put("firstName", value);
	}

	public String getMiddleName() {
		return getString("middleName");
	}

	public void setMiddleName(String value) {
		put("middleName", value);
	}

	public String getPrimaryPhone() {
		return getString("primaryPhone");
	}

	public void setPrimaryPhone(String value) {
		put("primaryPhone", value);
	}

	public String getSecondaryPhone() {
		return getString("secondaryPhone");
	}

	public void setSecondaryPhone(String value) {
		put("secondayPhone", value);
	}

	public String getEmail() {
		return getString("email");
	}

	public void setEmail(String value) {
		put("email", value);
	}

	public ParseFile getProfileImage() {
		return getParseFile("profileImage");
	}

	public void setProfileImage(ParseFile file) {
		put("profileImage", file);
	}

	public static void updateAvatarForCoordinator(String objId, String imagePath) {
		if (imagePath == null)
			return;
		
		File imgFile = new File(imagePath);
		boolean fileExists = imgFile.exists();
		String pp = imgFile.getAbsolutePath();
		if (fileExists == false)
			return;

		Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		final ParseFile pImgFile = new ParseFile("avatar.jpg",
				Utils.CompressConvertBitmapTobyteArray(bitmap,
						Bitmap.CompressFormat.JPEG));

		ParseQuery<Contact> query = ParseQuery.getQuery(Contact.class);

		query.getInBackground(objId, new GetCallback<Contact>() {
			public void done(Contact item, ParseException e) {
				if (e == null) {
					item.setProfileImage(pImgFile);
					item.saveInBackground();

				} else {
					Log.d("ERROR", e.toString());
				}
			}
		});
	}
}
