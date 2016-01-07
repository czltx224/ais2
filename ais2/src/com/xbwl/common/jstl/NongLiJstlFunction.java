package com.xbwl.common.jstl;

import java.util.Date;

import com.xbwl.common.utils.NongLiUtils;

/**农历转换的工具类
 * @author Administrator
 *
 */
public class NongLiJstlFunction {
	/**
	 * 取得今天的农历
	 * @return
	 */
	public static String getNongLi(){
		return NongLiUtils.getTodayNongLi();
	}
	
	/**
	 * 取得今年的年份
	 * @return
	 */
	public static String getYear(){
		return NongLiUtils.getYearByDate(new Date());
	}
	
	/**取得今年的月份
	 * @return
	 */
	public static String getMonth(){
		return NongLiUtils.getMonthByDate(new Date());
	}
	
	/**
	 * 取得今天的日期
	 * @return
	 */
	public static String getDay(){
		return NongLiUtils.getDayByDate(new Date());
	}
	
	/**
	 * 取得今天的星期
	 * @return
	 */
	public static String getWeek(){
		return NongLiUtils.getWeekByDate(new Date());
	}
	
	
	/**
	 * 取得明天的日期
	 * @return
	 */
	public static String getTomorrow(){
		return NongLiUtils.getTomorrow(new Date());
	}
}
