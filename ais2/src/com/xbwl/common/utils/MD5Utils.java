package com.xbwl.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * �ַ���ת��MD5������
 * @project ais2.0
 * @author czl
 * @Time Mar 9, 2012 10:17:03 AM
 */
public class MD5Utils {
	
	/**
	 * ���ַ���ת��ΪMD5
	 * @param str Ҫת�����ַ���
	 * @return ת�����MD5ֵ
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
