/**
 * 
 */
package com.api.gracenote.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.StringConstants;

/**
 * @author kunal.ashar
 *
 */
public class DateAndTime {

	private Logger	logger		= Logger.getLogger(DateAndTime.class);

	DateFormat		dateFormat	= null;
	int				addDays		= 0;

	/**
	 * @description to set date format and timezone
	 */
	public DateAndTime() {
		dateFormat = new SimpleDateFormat(StringConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(StringConstants.TIMEZONE));
	}

	public DateAndTime(int numberOfDaysToAdd) {
		dateFormat = new SimpleDateFormat(StringConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(StringConstants.TIMEZONE));
		addDays = numberOfDaysToAdd;
	}

	/**
	 * @description to create calendar instance
	 * @return Calendar
	 */
	private Calendar createCalendarInstance() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(StringConstants.TIMEZONE));

		int mins = calendar.get(Calendar.MINUTE);

		calendar.set(Calendar.MINUTE, mins);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.DATE, addDays);

		return calendar;
	}

	/**
	 * 
	 * @description to get Grid Start Time
	 * @return
	 */
	public String getGridStartTime() {
		Calendar calendar = createCalendarInstance();
		Date gridStart = new Date(calendar.getTimeInMillis());
		String gridStartTime = dateFormat.format(gridStart);

		return gridStartTime;
	}
	
	public String getGridStartTime(long epochTime) {
		
		Date gridStart = new Date();
		gridStart.setTime(epochTime*1000);
		String gridStartTime = dateFormat.format(gridStart);

		return gridStartTime;
	}
	
	
	

	/**
	 * 
	 * @description to get Grid End Time
	 * @param timeSpan
	 * @return
	 */
	public String getGridEndTime(int timeSpan) {

		Calendar calendar = createCalendarInstance();
		calendar.add(Calendar.HOUR, timeSpan);

		Date gridEnd = new Date(calendar.getTimeInMillis());
		String gridEndTime = dateFormat.format(gridEnd);

		return gridEndTime;
	}

	/**
	 * 
	 * @description to get Grid End Time
	 * @param timeSpan , epochTime
	 * @return 
	 */

	public String getGridEndTime(int timeSpanInHours , long epochTimeInMilis) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(epochTimeInMilis*1000));
		calendar.add(Calendar.HOUR, timeSpanInHours);
		
		Date gridEnd = new Date(calendar.getTimeInMillis());
		String gridEndTime = dateFormat.format(gridEnd);

		return gridEndTime;
	}

	/**
	 * 
	 * @description to calculate epoch time
	 * @return
	 */
	public long getEpochTime() {
		Calendar calendar = createCalendarInstance();
		long epoch = System.currentTimeMillis()/1000;
		long epochTime = calendar.getTimeInMillis() / 1000;
		return epochTime;
	}

	/**
	 * 
	 * @description comparing two dates
	 * @param date1
	 * @param date2
	 * @return
	 */
	public int compareDateTime(String date1, String date2) {

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = dateFormat.parse(date1);
			d2 = dateFormat.parse(date2);
		} catch (ParseException e) {
			logger.error("Exception Occurred while parsing date. ", e);
		}

		return d1.compareTo(d2);
	}

	public static void main(String[] args) {
		DateAndTime dt = new DateAndTime();
		System.out.println(dt.getEpochTime());
	}
}
