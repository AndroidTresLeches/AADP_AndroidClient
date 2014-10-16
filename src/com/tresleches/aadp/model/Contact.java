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
	private static final String LAST_NAME = "lastName";
	private static final String FIRST_NAME = "firstName";
	private static final String MIDDLE_NAME = "middleName";
	private static final String PRIMARY_PHONE = "primaryPhone";
	private static final String SECONDARY_PHONE = "secondaryPhone";
	private static final String EMAIL = "email";
	private static final String PROFILE_IMAGE = "profileImage";

	public Contact() {
		super();
	}

	public Contact(String lastName, String firstName, String middleName,
			String primaryPhone, String secondaryPhone, String email,
			String profileImagePath) {

		setFirstName(firstName);
		setLastName(lastName);
		setMiddleName(middleName);
		setPrimaryPhone(primaryPhone);
		setSecondaryPhone(secondaryPhone);
		setEmail(email);

		if (profileImagePath != null) {

		}
	}

	public String getLastName() {
		return getString(LAST_NAME);
	}

	public void setLastName(String value) {
		put(LAST_NAME, value);
	}

	public String getFirstName() {
		return getString(FIRST_NAME);
	}

	public void setFirstName(String value) {
		put(LAST_NAME, value);
	}

	public String getMiddleName() {
		return getString(MIDDLE_NAME);
	}

	public void setMiddleName(String value) {
		put(MIDDLE_NAME, value);
	}

	public String getPrimaryPhone() {
		return getString(PRIMARY_PHONE);
	}

	public void setPrimaryPhone(String value) {
		put(PRIMARY_PHONE, value);
	}

	public String getSecondaryPhone() {
		return getString(SECONDARY_PHONE);
	}

	public void setSecondaryPhone(String value) {
		put(SECONDARY_PHONE, value);
	}

	public String getEmail() {
		return getString(EMAIL);
	}

	public void setEmail(String value) {
		put(EMAIL, value);
	}

	public ParseFile getProfileImage() {
		return getParseFile(PROFILE_IMAGE);
	}

	public void setProfileImage(ParseFile file) {
		put(PROFILE_IMAGE, file);
	}

}
