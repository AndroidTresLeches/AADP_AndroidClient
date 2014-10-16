package com.tresleches.aadp.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}

	public static Boolean isOnline() {
		try {
			Process p1 = java.lang.Runtime.getRuntime().exec(
					"ping -c 1 www.google.com");
			int returnVal = p1.waitFor();
			boolean reachable = (returnVal == 0);
			return reachable;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
