package com.xbwl.common.log4j;

import org.apache.log4j.PatternLayout;

/**
 * ����log4j�����ʼ�������������
 * @author Administrator
 *
 */
public class MailPatternLayout extends PatternLayout {
	public String getContentType() { 
		return "text/plain; charset=UTF-8"; 
	} 
}
