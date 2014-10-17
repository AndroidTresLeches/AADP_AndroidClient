package com.tresleches.aadp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String getDateInString(Date date){
		SimpleDateFormat ddMMMyyFormat = new SimpleDateFormat("dd-MMM-yy");
		String date_to_string = ddMMMyyFormat.format(date);
		System.out.println("Today's date into dd-MMM-yy format: " + date_to_string);
		return date_to_string;
	}
}
