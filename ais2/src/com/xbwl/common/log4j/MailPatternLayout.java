package com.xbwl.common.log4j;

import org.apache.log4j.PatternLayout;

/**
 * 用于log4j发送邮件的中文乱码解决
 * @author Administrator
 *
 */
public class MailPatternLayout extends PatternLayout {
	public String getContentType() { 
		return "text/plain; charset=UTF-8"; 
	} 
}
