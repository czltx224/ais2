package com.xbwl.report.print.util;

/**
 * @author czl
 * 
 * �������㹤����
 */
public  class NumberUtil {

	/**
	 * ������Double���ֵĺ�
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double numberAdd(Double num1,Double num2){
		if(num1==null){
			num1=0d;
		}
		if(num2==null){
			num2=0d;
		}
		return num1+num2;
	}
	
	/**
	 * ������Double���ֵĺ�
	 * @param num1
	 * @param num2
	 * @param num3
	 * @return
	 */
	public static Double numberAdd(Double num1,Double num2,Double num3){
		if(num1==null){
			num1=0d;
		}
		if(num2==null){
			num2=0d;
		}
		if(num3==null){
			num3=0d;
		}
		return num1+num2+num3;
	}
	
	/**
	 * ������Long���ֵĺ�
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Long numberAdd(Long num1,Long num2){
		if(num1==null){
			num1=0l;
		}
		if(num2==null){
			num2=0l;
		}
		return num1+num2;
	}
}
