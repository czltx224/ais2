package com.xbwl.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.util.StrutsTypeConverter;
import org.springframework.context.annotation.Scope;

import com.xbwl.common.exception.ServiceException;

/**
 * Date 转换器
 * 
 * @author LiuHao
 *@time Nov 8, 2011 1:54:53 PM
 */
public class DateConverter extends StrutsTypeConverter {
	private static final String FORMATDATE = "yyyy-MM-dd";
	private static final String FORMATTIME = "yyyy-MM-dd HH:mm:ss";

	// REVIEW-ACCEPT 增加方法注释
	/**
	 * 将字符串转换为Date
	 * @Override
	 */
	public Object convertFromString(Map arg0, String[] values, Class arg2) {
		if (values == null || values.length == 0) {
			return null;
		}
		//有时分秒的先转换
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);
		Date date = null;
		String dateString = values[0];
		// REVIEW-ACCEPT 根据逻辑是按时间型进行parse，如果不行就通过日期型parse，可以根据长度进行处理
		// if(datestring.trim().lengh==10){parse to date}
		// else if(datestring.trim().lengh==19){parse to datetime}
		// else{return null or throw unsupport message}
		if (dateString != null) {
			/*
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				date = null;
			}
			if (date == null) {
				sdf = new SimpleDateFormat(FORMATDATE);
				try {
					date = sdf.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
			}*/
			//2011.12.30  修改
			try {
				if(dateString.trim().length()==10){
					sdf = new SimpleDateFormat(FORMATDATE);
					date = sdf.parse(dateString);
				}else if(dateString.trim().length()==19){
					date = sdf.parse(dateString);
				}else{
					throw new ServiceException("时间格式不匹配，转换错误!");
				}
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}

	// REVIEW-ACCEPT 增加方法注释
	/**
	 * 将Date转换字符串
	 * @Override
	 */
	//FIXED
	public String convertToString(Map arg0, Object o) {
		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);
			return sdf.format((Date) o);
		}
		return "";
	}

}
