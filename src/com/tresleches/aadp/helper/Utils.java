package com.tresleches.aadp.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.tresleches.aadp.interfaces.Shareable;
import com.tresleches.aadp.model.Contact;

public final class Utils {
	
	private static final Set<String> FACEBOOK = new HashSet<String>(
			Arrays.asList(
					"com.facebook.composer.shareintent.ImplicitShareIntentHandler",
					"com.facebook.katana.ShareLinkActivity"));

	private static final Set<String> TWITTER = new HashSet<String>(Arrays.asList("com.twitter.android.composer.ComposerActivity"));

	
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
	

	
	/**
	 * Method for creating Social Share 
	 * Face book
	 * Twitter
	 * @param ctx
	 * @param story
	 * @param socialActivitiesName
	 * @param displayName
	 */
	private static void startShareIntentWithExplicitSocialActivity(
			Activity ctx, Shareable story, Set<String> socialActivitiesName,
			String displayName) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, story.getShareableUrl());

		try {
			final PackageManager pm = ctx.getPackageManager();
			@SuppressWarnings("rawtypes")
			final List activityList = pm.queryIntentActivities(shareIntent, 0);
			int len = activityList.size();
			for (int i = 0; i < len; i++) {
				final ResolveInfo app = (ResolveInfo) activityList.get(i);
				if (socialActivitiesName.contains(app.activityInfo.name)) {
					final ActivityInfo activity = app.activityInfo;
					final ComponentName name = new ComponentName(
							activity.applicationInfo.packageName, activity.name);
					shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					shareIntent
							.putExtra(Intent.EXTRA_SUBJECT, "AADP Story");
					shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
					shareIntent.setComponent(name);
					ctx.startActivity(shareIntent);
					break;
				}
			}
		} catch (final ActivityNotFoundException e) {
			Toast.makeText(ctx, "Could not find " + displayName + " app",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * Public method for Creating Twitter share
	 * @param ctx
	 * @param story
	 */
	public static void startShareIntentTwitter(Activity ctx,Shareable story) {
		startShareIntentWithExplicitSocialActivity(ctx, story,TWITTER, "Twitter");
	}

	/**
	 * Public method for Creating Face book share
	 * @param ctx
	 * @param story
	 */
	public static void startShareIntentFaceBook(Activity ctx,Shareable story) {
		startShareIntentWithExplicitSocialActivity(ctx, story, FACEBOOK,"Facebook");
	}
	
	
	/**
	 * 	Public method for Creating other share
 	* @param activity
 	* @param story
 	*/
	public static void startShareIntent(Activity activity, Shareable story) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, story.getShareableUrl());
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "AADP Story");
		activity.startActivity(Intent.createChooser(shareIntent, "Share AADP Story"));
	}	
}
