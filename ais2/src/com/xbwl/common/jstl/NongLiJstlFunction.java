package com.xbwl.common.jstl;

import java.util.Date;

import com.xbwl.common.utils.NongLiUtils;

/**ũ��ת���Ĺ�����
 * @author Administrator
 *
 */
public class NongLiJstlFunction {
	/**
	 * ȡ�ý����ũ��
	 * @return
	 */
	public static String getNongLi(){
		return NongLiUtils.getTodayNongLi();
	}
	
	/**
	 * ȡ�ý�������
	 * @return
	 */
	public static String getYear(){
		return NongLiUtils.getYearByDate(new Date());
	}
	
	/**ȡ�ý�����·�
	 * @return
	 */
	public static String getMonth(){
		return NongLiUtils.getMonthByDate(new Date());
	}
	
	/**
	 * ȡ�ý��������
	 * @return
	 */
	public static String getDay(){
		return NongLiUtils.getDayByDate(new Date());
	}
	
	/**
	 * ȡ�ý��������
	 * @return
	 */
	public static String getWeek(){
		return NongLiUtils.getWeekByDate(new Date());
	}
	
	
	/**
	 * ȡ�����������
	 * @return
	 */
	public static String getTomorrow(){
		return NongLiUtils.getTomorrow(new Date());
	}
}
