package models.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * DateLib Class
 * Various static methods for working with dates and times
 * @author Konstantinos Christofilos <kostasxx@gmail.com>
 * @author Pavlos Gerardos <pavlos.g@gmail.com >
 * @author Sokratis Pantazaras <spantazaras@gmail.com>
 */
public class DateLib {	
	private static Calendar cal = Calendar.getInstance();
	
	/**
	 * Creates and returns a new Date object which refers to the system time at the time of creation
	 * Eliminates the extra milliseconds after the last second
	 * @return a new Date object with the current system time
	 */
	public static Date getDateObject() {
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * Creates and returns a new Date object with the specified values for day, month & year
	 * Eliminates the extra milliseconds after the last second
	 * Hour defaults to 12 (24-hour based day)
	 * Minute defaults to 0
	 * Second defaults to 0
	 * @param day day of month
	 * @param month month of year (1 for January, 2 for February, etc.)
	 * @param year year
	 * @return a new Date object with the specified values for day, month & year
	 */
	public static Date getDateObject(int day, int month, int year) {
		return getDateObject(day, month, year, 12, 0, 0);
	}
	
	/**
	 * Creates and returns a new Date object with the specified values for day, month, year, hour, minute & second
	 * Eliminates the extra milliseconds after the last second
	 * @param day day of month
	 * @param month month of year (1 for January, 2 for February, etc.)
	 * @param year year
	 * @param hour hour of day (24-hour based day)
	 * @param minute minute of hour
	 * @param second second of minute
	 * @return a new Date object with the specified values for day, month, year, hour, minute & second
	 */
	public static Date getDateObject(int day, int month, int year, int hour, int minute, int second) {
		cal.set(year, month - 1, day, hour, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * Creates and returns a new Date object, which is an identical copy of the parameter Date object
	 * Eliminates the extra milliseconds after the last second
	 * @param date Date object to be copied
	 * @return copy of parameter Date object
	 */
	public static Date copyDateObject(Date date) {
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * Advances the given Date object by a number of days
	 * @param date Date object to modify
	 * @param days days to advance (or recur if negative)
	 */
	public static void addDays(Date date, int days) {
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, days);
		date.setTime(cal.getTimeInMillis());
	}
	
	/**
	 * Advances the given Date object by a number of months
	 * @param date Date object to modify
	 * @param months months to advance (or recur if negative)
	 */
	public static void addMonths(Date date, int months) {
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MONTH, months);
		date.setTime(cal.getTimeInMillis());
	}
	
	/**
	 * Advances the given Date object by a number of years
	 * @param date Date object to modify
	 * @param years years to advance (or recur if negative)
	 */
	public static void addYears(Date date, int years) {
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.YEAR, years);
		date.setTime(cal.getTimeInMillis());
	}
	
	/**
	 * Advances the given Date object by a number of hours
	 * @param date Date object to modify
	 * @param hours hours to advance (or recur if negative)
	 */
	public static void addHours(Date date, int hours) {
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.HOUR_OF_DAY, hours);
		date.setTime(cal.getTimeInMillis());
	}
	
	/**
	 * Advances the given Date object by a number of minutes
	 * @param date Date object to modify
	 * @param minutes minutes to advance (or recur if negative)
	 */
	public static void addMinutes(Date date, int minutes) {
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MINUTE, minutes);
		date.setTime(cal.getTimeInMillis());
	}
	
	/**
	 * Advances the given Date object by a number of seconds
	 * @param date Date object to modify
	 * @param seconds seconds to advance (or recur if negative)
	 */
	public static void addSeconds(Date date, int seconds) {
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.SECOND, seconds);
		date.setTime(cal.getTimeInMillis());
	}
	
	/**
	 * Returns an integer representing the day of the week for the specified date
	 * Sunday = 1, Monday = 2, Tuesday = 3, Wednesday = 4, Thursday = 5, Friday = 6, Saturday = 7
	 * @param date Date object to extract day of the week from
	 * @return an integer representing the day of the week
	 */
	public static int getWeekday(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Returns a string representing the day of the week for the specified date
	 * @param date Date object to extract day of the week from
	 * @return a String representing the day of the week
	 */
	public static String getWeekdayAbbr(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("E", DateFormatSymbols.getInstance(Locale.ENGLISH));
			return sdf.format(date);
		}
		return "";
	}
	
	/**
	 * Returns the difference in seconds between two date objects
	 * @param fromDate the first Date object
	 * @param toDate the second Date object
	 * @return the difference in seconds between toDate and fromDate
	 */
	public static long getDateDifferenceInSeconds(Date fromDate, Date toDate) {
		return (toDate.getTime() - fromDate.getTime() / 1000);
	}
	
	/**
	 * Compares two Date Objects for equality, down to the second
	 * @param date1 the first Date object
	 * @param date2 the second Date object
	 * @return true if the objects are equal, false otherwise
	 */
	public static boolean equals(Date date1, Date date2) {
		return (compare(date1, date2) == 0);
	}
	
	/**
	 * Checks if the first Date object represents a later point in time than the second
	 * @param date1 the first Date object
	 * @param date2 the second Date object
	 * @return true the first Date object represents a later point in time, false otherwise
	 */
	public static boolean succeeds(Date date1, Date date2) {
		return (compare(date1, date2) > 0 ? true : false);
	}
	
	/**
	 * Checks if the first Date object represents an earlier point in time than the second
	 * @param date1 the first Date object
	 * @param date2 the second Date object
	 * @return true the first Date object represents an earlier point in time, false otherwise
	 */
	public static boolean preceeds(Date date1, Date date2) {
		return (compare(date1, date2) < 0 ? true : false);
	}
	
	/**
	 * Compares two Date Objects, down to the second
	 * @param date1 the first Date object
	 * @param date2 the second Date object
	 * @return 0 for equality, 1 if date1 refers to a later date & time, -1 otherwise
	 */
	public static int compare(Date date1, Date date2) {
		long secs1 = date1.getTime() / 1000;
		long secs2 = date2.getTime() / 1000;
		return (secs1 - secs2 == 0 ? 0 : (int)((secs1 - secs2) / Math.abs(secs1 - secs2)));
	}
	
	/**
	 * Returns a String representation for a given Date object
	 * @param date the Date object to be represented as String
	 * @return a String representation for the Date object
	 */
	public static String dateAsString(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("E dd/MM/yyyy HH:mm", DateFormatSymbols.getInstance(Locale.ENGLISH));
			return sdf.format(date);
		}
		return "";
	}
}
