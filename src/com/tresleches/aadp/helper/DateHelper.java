package com.tresleches.aadp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String getDateInString(Date date){
		SimpleDateFormat ddMMMyyFormat = new SimpleDateFormat("dd-MMM-yy");
		String dateToString = ddMMMyyFormat.format(date);
		return dateToString;
	}
}
