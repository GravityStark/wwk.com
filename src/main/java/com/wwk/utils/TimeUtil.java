package com.wwk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
	private final static String LONG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static String TIME_FORMAT = "HH:mm:ss";
	public final static int ONE_DAY = 24 * 3600;
	public final static int ONE_HOUR = 3600;
	public final static int ONE_MINUTE = 60;

	/** 取当前时间豪秒 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/** 取当前时间秒 */
	public static int getCurrentSecond() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static String dateFormat(Date time) {
		return new SimpleDateFormat(DATE_FORMAT).format(time);
	}
	/**
	 * 传入时刻获取时间戳
	 * @param hour
	 * @return
	 */
	public static long hourToTimestamp(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static String dateFormat(long time) {
		return new SimpleDateFormat(DATE_FORMAT).format(new Date(time));
	}

	public static String timeFormat(long time) {
		return new SimpleDateFormat(LONG_TIME_FORMAT).format(new Date(time));
	}

	public static Date detailDateFormat(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat(LONG_TIME_FORMAT);
		try {
			return formatter.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date dateFormat(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			return formatter.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(LONG_TIME_FORMAT);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 根据传入的时间 获取当前 Calendar 类
	 * 
	 * @param time
	 *            时间 String 类型 如："20:00:00"
	 * @return 当前日期的 Calendar 类 时间为传入的时间
	 */
	public static Calendar getNowCalendar(String time) {
		Calendar nowCalendar = Calendar.getInstance();
		Calendar timeCalendar = Calendar.getInstance();
		try {
			timeCalendar.setTime(new SimpleDateFormat(TIME_FORMAT).parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		timeCalendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
		timeCalendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
		timeCalendar.set(Calendar.DAY_OF_YEAR, nowCalendar.get(Calendar.DAY_OF_YEAR));
		return timeCalendar;
	}

	/**
	 * 当前时间(单位:毫秒) 0时区
	 * 
	 * @return
	 */
	public static long now() {
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT0"));
		return System.currentTimeMillis();
	}

	/**
	 * 原时间与现在时间超过两天
	 */
	public static boolean isOverTwoDay(long oldTime, long nowTime) {
		Calendar cPast = Calendar.getInstance();
		cPast.setTimeInMillis(oldTime);

		Calendar cNow = Calendar.getInstance();
		cNow.setTimeInMillis(nowTime);

		int pastDay = cPast.get(Calendar.DAY_OF_YEAR);
		int nowDay = cNow.get(Calendar.DAY_OF_YEAR);
		int delayDay = nowDay - pastDay;
		if (delayDay == 1) {// 相邻两天
			if (oldTime < cPast.getTimeInMillis() && nowTime >= cNow.getTimeInMillis()) {
				return true;
			}
		} else if (delayDay > 1) {// 相隔一天
			return true;
		}
		return false;
	}

	/**
	 * 第二天
	 * 
	 * @param oldTime
	 * @param nowTime
	 * @return
	 */
	public static boolean isNextDay(long oldTime, long nowTime) {
		Calendar cPast = Calendar.getInstance();
		cPast.setTimeInMillis(oldTime);

		Calendar cNow = Calendar.getInstance();
		cNow.setTimeInMillis(nowTime);

		int pastDay = cPast.get(Calendar.DAY_OF_YEAR);
		int nowDay = cNow.get(Calendar.DAY_OF_YEAR);
		int delayDay = nowDay - pastDay;
		if (delayDay == 1) {// 同一天
			return true;
		}
		return false;
	}

	public static boolean isToday(long oldTime, long nowTime) {
		Calendar cPast = Calendar.getInstance();
		cPast.setTimeInMillis(oldTime);

		Calendar cNow = Calendar.getInstance();
		cNow.setTimeInMillis(nowTime);

		int pastDay = cPast.get(Calendar.DAY_OF_YEAR);
		int nowDay = cNow.get(Calendar.DAY_OF_YEAR);
		int delayDay = nowDay - pastDay;
		if (delayDay == 0) {// 同一天
			if (oldTime < cPast.getTimeInMillis() && nowTime >= cNow.getTimeInMillis()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	/**
	 * 距离未来零点毫秒数
	 * @return
	 */
	public static long awayZero() {
		Calendar zeroCalendar = Calendar.getInstance();
		zeroCalendar.add(Calendar.DAY_OF_WEEK, 1);
		
		zeroCalendar.set(Calendar.HOUR_OF_DAY, 0);
		zeroCalendar.set(Calendar.MINUTE, 0);
		zeroCalendar.set(Calendar.MILLISECOND, 0);
		
		
		return zeroCalendar.getTimeInMillis() - now();

	}
	
	// 获得下周星期的日期
	public static long getNextWeekDayRefreshTime(long now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		if (week > 2) {
			calendar.add(Calendar.DAY_OF_MONTH, -(week - 2) + 7);
		} else {
			calendar.add(Calendar.DAY_OF_MONTH, 2 - week + 7);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	// 获得下次每日刷新时间
	public static long getNextDayRefreshTime(long now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		calendar.add(GregorianCalendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
}
