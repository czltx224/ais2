package com.xbwl.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {
	
	/**
	 * ͨ������������ʽ��Ҫ��֤�Ľ������������֤
	 */
	public static Boolean chkByRegex(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();

	}
}
