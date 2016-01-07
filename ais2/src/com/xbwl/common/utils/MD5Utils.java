package com.xbwl.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串转换MD5帮助类
 * @project ais2.0
 * @author czl
 * @Time Mar 9, 2012 10:17:03 AM
 */
public class MD5Utils {
	
	/**
	 * 将字符串转换为MD5
	 * @param str 要转换的字符串
	 * @return 转化后的MD5值
	 * @throws Exception
	 */
	public static String strToMd5(String str)throws Exception {
        String s=str;
		if(s==null){
			return "";
		}else{
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			}catch (NoSuchAlgorithmException ex) {
				throw ex;
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {
				throw ex;
			}
			return value;
		}
	}    
}
