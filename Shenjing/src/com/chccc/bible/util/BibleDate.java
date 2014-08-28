package com.chccc.bible.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class BibleDate {
	
	/*
	 * The input parameter should be a date format string as "MM/dd/yyyy"
	 */
	public static int workDaysToTheDay (String d) {
		int thisYear = removeLeadingZero (d.substring(6, 10));
		int thisMonth = removeLeadingZero (d.substring(0, 2));
		int dayOfMonth = removeLeadingZero (d.substring(3, 5));
		
		return workDaysToTheDay(thisYear, thisMonth, dayOfMonth);
	}
	
	/*
	 * Get an integer value for the chosen day is the n'th day of the year, besides all sunday and saturday
	 */
	public static int workDaysToTheDay (int thisYear, int thisMonth, int dayOfMonth) {
		int totalDays = totalDaysToTheDay(thisYear, thisMonth, dayOfMonth);
		
		String dayOfWeekOfLastDayOfLastYear = getDayOfWeekOfLastDayOfLastYear();
		
		String dayOfWeekOfTheDay = nextWeekDay(dayOfWeekOfLastDayOfLastYear );
		
		int result =0;
		for (int i = 0; i<totalDays ; i++) {
			if (!dayOfWeekOfTheDay.equalsIgnoreCase("Saturday") && !dayOfWeekOfTheDay.equalsIgnoreCase("Sunday")) {
				result++;
			}
			
			dayOfWeekOfTheDay = nextWeekDay(dayOfWeekOfTheDay);
		}
		
		return result;
	}
	
	
	public static int totalDaysToTheDay (String d) {
		int thisYear = removeLeadingZero (d.substring(6, 10));
		int thisMonth = removeLeadingZero (d.substring(0, 2));
		int dayOfMonth = removeLeadingZero (d.substring(3, 5));
		
		return totalDaysToTheDay(thisYear, thisMonth, dayOfMonth);
	}
	
	/*
	 * Get an integer value for the chosen day is the n'th day of the year
	 */
	public static int totalDaysToTheDay (int thisYear, int thisMonth, int dayOfMonth) {
		int lastyear = getLastYear();
		
		DateTime startDate = new DateTime(lastyear, 12, 31, 12, 0, 0, 0);
		
//		int thisYear = getCurrentYear();
//		int thisMonth = getCurrentMonth();
//		int dayOfMonth = getCurrentDay();
		
		DateTime endDate = new DateTime(thisYear, thisMonth, dayOfMonth, 12, 0, 0, 0);
		
		Days d = Days.daysBetween(startDate, endDate);
		int days = d.getDays();
		  
		return days;
	}
	
	
	/*
	 * Get the week day in string for the last day of last year 
	 */
	private static String getDayOfWeekOfLastDayOfLastYear() {
		int lastyear = getLastYear ();
		
		String d = "" + "12/31/" + String.valueOf(lastyear);
		
		return (getDayOfWeek("12/31/" + lastyear));
		
	}
	
	/*
	 * Get the current day as integer. 
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	/*
	 * Get the current month as integer. Note: original java month value is "month - 1"
	 */
	public static int getCurrentMonth() {
		int month = Calendar.getInstance().get(Calendar.MONTH);
		
		return (++month);
	}
	
	/*
	 * Get last year as 4 digit integer
	 */
	public static int getLastYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		return (--year);
	}
	
	/*
	 * Get current year as 4 digit integer
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/*
	 * Get next year as 4 digit integer
	 */
	public static int getNextYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		return (++year);
	}
	
	/*
	 * The input parameter should be a date format string as "MM/dd/yyyy"
	 * Output should be "Monday/Tuesday/Wednesday/Thursday/Friday/Saturday/Sunday"
	 */
	public static String getDayOfWeek(String d) {
	
		String dayofweek = "";
		try {
		  SimpleDateFormat format1=new SimpleDateFormat("MM/dd/yyyy");
		  Date dt1=format1.parse(d);
		  DateFormat format2=new SimpleDateFormat("EEEE"); 
		  dayofweek = format2.format(dt1);
		  
//		  System.out.println(dayofweek);
		} catch(Exception e) {
			
		}
		
		return dayofweek;
	}
	
	/*
	 * Get the week day for the next day
	 * both input and output of the function will be value of "Monday/Tuesday/Wednesday/Thursday/Friday/Saturday/Sunday"
	 */
	private static String nextWeekDay(String dayOfWeek) {
		if (dayOfWeek.equalsIgnoreCase("Monday")) {
			return "Tuesday";
		} else if (dayOfWeek.equalsIgnoreCase("Tuesday")) {
				return "Wednesday";
		} else if (dayOfWeek.equalsIgnoreCase("Wednesday")) {
			return "Thursday";
		} else if (dayOfWeek.equalsIgnoreCase("Thursday")) {
			return "Friday";
		} else if (dayOfWeek.equalsIgnoreCase("Friday")) {
			return "Saturday";
		} else if (dayOfWeek.equalsIgnoreCase("Saturday")) {
			return "Sunday";
		} else if (dayOfWeek.equalsIgnoreCase("Sunday")) {
			return "Monday";
		}
		
		return null;
	}
	
	/*
	 * Remove the leading zero
	 */
	private static int removeLeadingZero(String number) {
		if (number.startsWith("0")) {
			number = number.substring(1);
		}
		
		return Integer.parseInt(number);
	}
}
