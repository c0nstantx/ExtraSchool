package models.persistence;

import java.util.Calendar;
import java.util.Date;

/**
 * Various utilities for working with dates and times
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 *
 */
public class CalendarUtilities {	
	private static Calendar cal = Calendar.getInstance();
	
	public static void setDate(int date, int month, int year) {
		cal.set(year, month - 1, date);
	}
	
	public static Date getDate() {
		return cal.getTime();
	}
	
	public static Date getDate(int date, int month, int year) {
		cal.set(year, month - 1, date);
		return cal.getTime();
	}
	
	public static WeekDay getWeekDay() {
		return WeekDay.values()[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	public static WeekDay getWeekDay(int date, int month, int year) {
		cal.set(year, month, date);
		return WeekDay.values()[cal.get(Calendar.DAY_OF_WEEK) - 1];
	}
}
