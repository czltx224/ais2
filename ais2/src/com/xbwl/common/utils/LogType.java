package com.xbwl.common.utils;

import com.xbwl.common.exception.ServiceException;

/**
 * @author Administrator
 * @createTime 4:39:38 PM
 * @updateName Administrator
 * @updateTime 4:39:38 PM
 * 
 */

public enum LogType {
	buss,bas,fi,rbac;
	public static String getLogType(LogType logType){
		if(logType.toString().equals("buss")){
			return "ҵ��ģ��";
		}else if(logType.toString().equals("bas")){
			return "����ģ��";
		}else if(logType.toString().equals("fi")){
			return "����ģ��";
		}else if(logType.toString().equals("rbac")){
			return "Ȩ��ģ��";
		}else{
			throw new ServiceException("δ֧�ֵ�logtype");
		}
	}

}
