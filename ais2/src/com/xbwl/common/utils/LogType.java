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
			return "业务模块";
		}else if(logType.toString().equals("bas")){
			return "基础模块";
		}else if(logType.toString().equals("fi")){
			return "财务模块";
		}else if(logType.toString().equals("rbac")){
			return "权限模块";
		}else{
			throw new ServiceException("未支持的logtype");
		}
	}

}
