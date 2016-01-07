package com.xbwl.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {
	
	/**
	 * 通过传入正则表达式与要验证的结果进行正则验证
	 */
	public static Boolean chkByRegex(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();

	}
}
