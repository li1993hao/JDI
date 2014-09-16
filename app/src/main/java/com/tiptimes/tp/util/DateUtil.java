package com.tiptimes.tp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

	public String getCurrentTime() {
		Date date = new Date();
		String time = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = df.format(date);
		return time;
	}
	
	public String getCurentTime2(){
		Date date = new Date();
		String time = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		time = df.format(date);
		return time;
	}


	/**
	 * 获取date的字符串
	 * 
	 * @author ttt
	 * @time:2012-11-14 下午5:21:24
	 * @param date
	 * @return
	 */
	public String getDateStr(Date date) {
		String time = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = df.format(date);
		return time;
	}

	/**
	 * 获取距离某一天date的value天的date
	 * 
	 * @author ttt
	 * @time:2012-11-14 下午4:58:06
	 * @param date
	 * @param value
	 *            前一天为-1，后一天为+1，以此类推
	 * @return
	 */
	public Date getSomeDate(Date date, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, value);
		date = calendar.getTime();
		return date;
	}

	public Date getADayOfWeek(int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, value);
		Date date = calendar.getTime();

		return date;
	}

	/**
	 * 获取某个日期的星期几
	 * 
	 * @author ttt
	 * @time:2012-11-14 下午5:21:39
	 * @param dt
	 * @return
	 */
	public String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取一天在某一星期的索引 ，星期一的索引为1，星期日的索引为7
	 * 
	 * @author ttt
	 * @time:2012-12-10 下午5:30:26
	 * @param dt
	 * @return
	 */
	public int getWeekIndexOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w <= 0)
			w = 7;
		return w;
	}

	public int getWeekIndexOfDate() {
		Calendar cal = Calendar.getInstance();
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w == 0 || w < 0)
			w = 7;
		return w;
	}

	/**
	 * 计算两个时间间隔
	 */
	public String computeInterval(String startTime, String endTime) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long diff;

		long min = 0;// 计算差多少分钟
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			min = diff % nd % nh / nm;// 计算差多少分钟
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return min + "";
	}

	/**
	 * 判断传入日期和今天哪个早
	 * 
	 * @author ttt
	 * @time:2013-3-20 下午2:37:40
	 * @param theDay
	 * @return =0：相等 >0:比今天早 <0:比今天晚
	 * @throws java.text.ParseException
	 */
	public int compareToday(String theDay) throws ParseException {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date theDate = dateFormat.parse(theDay);
		int i = date.compareTo(theDate);
		return i;
	}
}
