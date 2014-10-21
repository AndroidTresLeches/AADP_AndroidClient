package com.tresleches.aadp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String getDateInString(Date date){
		SimpleDateFormat ddMMMyyFormat = new SimpleDateFormat("dd-MMM-yy");
		String dateToString = ddMMMyyFormat.format(date);
		return dateToString;
	}
	
	public static String getTime(String time){
		int newTime = 0;
		String exactTime;
		if(Integer.parseInt(time) > 12){
			newTime = Integer.parseInt(time) -12;
			exactTime = Integer.toString(newTime) + " PM";
		}else if(Integer.parseInt(time) == 12){
			exactTime = time + " PM";
		}else{
			exactTime = time + " PM";
		}
		return exactTime;
	}
}
