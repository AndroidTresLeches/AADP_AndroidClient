package com.tresleches.aadp.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.tresleches.aadp.model.Contact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public final class Utils {

	//need to check the size and then resize it first
	public static byte[] CompressConvertBitmapTobyteArray(Bitmap bmp, Bitmap.CompressFormat format) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(format, 100, stream);
		
		return (stream.toByteArray());
	}
	
	public static byte[] ConvertBitmapTobyteArray(Bitmap bmp) {
		final int length = bmp.getByteCount();

        ByteBuffer dst= ByteBuffer.allocate(length);
        bmp.copyPixelsToBuffer( dst);

		return (dst.array());
	}

	public static void updateAvatarForContact(String objId, String imagePath) {
		if (imagePath == null)
			return;
		
		File imgFile = new File(imagePath);
		boolean fileExists = imgFile.exists();
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
