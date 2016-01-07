package com.xbwl.common.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Configure {

	private static Configure instance = null;

	Properties pp;

	private Configure() {
		pp = new Properties();
		try {
			InputStream fps = Configure.class
					.getResourceAsStream("/application.properties");

			pp.load(fps);
			fps.close();
		} catch (Exception e) {
			System.out.print("读取application.properties文件失败!");
			e.printStackTrace();
		}
	}

	public static Configure getSingleton() {
		// 单例模式
		if (instance == null) {
			instance = new Configure();
		}
		return instance;
	}

	public String values(String key) {
		String value = pp.getProperty(key);
		// REVIEW 这个判断没有意义，空和非空返回的结果是一样的。
		if (value != null) {
			return value;
		} else {
			return null;
		}
	}
	public String getFille(String key) {
		String value = pp.getProperty(key);
		File file=new File(value);
		if(!file.exists()){
			file.mkdirs();
		}
		if (value != null) {
			return value.trim();
		} else {
			return null;
		}
	}

}
