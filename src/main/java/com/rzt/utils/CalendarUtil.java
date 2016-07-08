package com.rzt.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CalendarUtil {

	public static final String DD_MMM_YYYY_HH_MM_SS_A = "dd-MMM-yyyy HH:mm:ss a";
	public static final String MM_DD_YYYY_HH_MM_SS_A_Z = "MM/dd/yyyy HH:mm:ss a Z";

	private CalendarUtil()
	{
	}

	public static Calendar getCurrentGMTTime()
	{
		return Calendar.getInstance();
	}

	public static String convertCalendarToString( Calendar datetime, String timeZone )
	{
		DateFormat formatter = new SimpleDateFormat(DD_MMM_YYYY_HH_MM_SS_A);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		formatter.setCalendar(Calendar.getInstance());
		return formatter.format(datetime.getTime());
	}

	public static String convertCalendarToString( Calendar datetime )
	{
		DateFormat formatter = new SimpleDateFormat(MM_DD_YYYY_HH_MM_SS_A_Z);
		formatter.setCalendar(Calendar.getInstance());
		return formatter.format(datetime.getTime());
	}

	public static List<Date> datesBetween( Date startDate, Date endDate )
	{
		List<Date> dates = new ArrayList<Date>(25);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		dates.add(startDate);
		while( cal.getTime().before(endDate) )
		{
			cal.add(Calendar.DATE, 1);
			dates.add(cal.getTime());
		}
		return dates;
	}

	public static synchronized Date getDateInTimeZone( Date date, String timeZone )
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getTimeZone(timeZone));
		return cal.getTime();
	}

	public static synchronized Calendar getCalenderInTimeZone( Date date, String timeZone )
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getTimeZone(timeZone));
		return cal;
	}
}
