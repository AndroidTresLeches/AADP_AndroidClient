package com.tresleches.aadp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static String getDateInString(Date date){
		SimpleDateFormat ddMMMyyFormat = new SimpleDateFormat("dd-MMM-yy");
		String dateToString = ddMMMyyFormat.format(date);
		return dateToString;
	}
	
	public static String getDate(Date date){
		SimpleDateFormat ddFormat = new SimpleDateFormat("dd");
		String day= ddFormat.format(date);
		return day;
	}
	
	public static String getMonthInString(Date date){
		SimpleDateFormat MMMMFormat = new SimpleDateFormat("MMMM");
		String month = MMMMFormat.format(date);
		return month;
	}
	
	public static String getYearInString(Date date){
		SimpleDateFormat yyFormat = new SimpleDateFormat("yyyy");
		String dateToString = yyFormat.format(date);
		return dateToString;
	}
	
	public static String getTime(String time){
		int newTime = 0;
		String exactTime;
		time = time.substring(0, 2);
		if(Integer.parseInt(time) > 12){
			newTime = Integer.parseInt(time) -12;
			exactTime = Integer.toString(newTime) + " PM";
		}else if(Integer.parseInt(time) == 12){
			exactTime = time + " PM";
		}else{
			exactTime = time + " AM";
		}
		return exactTime;
	}
}
