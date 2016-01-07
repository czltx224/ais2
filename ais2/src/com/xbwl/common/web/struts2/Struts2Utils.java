/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Struts2Utils.java,v 1.3 2010/08/18 08:42:47 yyj Exp $
 */
package com.xbwl.common.web.struts2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.xblink.XBlink;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.entity.BasArea;
/**
 * Struts2������.
 * 
 * ʵ�ֻ�ȡRequest/Response/Session���ƹ�jsp/freemakerֱ������ı��ļ򻯺���.
 * 
 * @author calvin
 */
public class Struts2Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Struts2Utils.class);

	//-- header �������� --//
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;

	private static ObjectMapper mapper = new ObjectMapper();
	

	//-- ȡ��Request/Response/Session�ļ򻯺��� --//
	/**
	 * ȡ��HttpSession�ļ򻯺���.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * ȡ��HttpSession�ļ򻯺���.
	 */
	public static HttpSession getSession(boolean isNew) {
		return ServletActionContext.getRequest().getSession(isNew);
	}

	/**
	 * ȡ��HttpSession��Attribute�ļ򻯺���.
	 */
	public static Object getSessionAttribute(String name) {
		HttpSession session = getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * ȡ��HttpRequest�ļ򻯺���.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * ȡ��HttpRequest��Parameter�ļ򻯷���.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * ȡ��HttpResponse�ļ򻯺���.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	//-- �ƹ�jsp/freemakerֱ������ı��ĺ��� --//
	/**
	 * ֱ��������ݵļ�㺯��.

	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers �ɱ��header���飬Ŀǰ���ܵ�ֵΪ"encoding:"��"no-cache:",Ĭ��ֵ�ֱ�ΪUTF-8��true.
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		 // REVIEW-ACCEPT �Դ��ν��зǿ��ж�
		//FIXED
		Assert.hasText(contentType);
		Assert.hasText(content);
		
		HttpServletResponse response = initResponseHeader(contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * ֱ������ı�.
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(ServletUtils.TEXT_TYPE, text, headers);
	}

	/**
	 * ֱ�����HTML.
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(ServletUtils.HTML_TYPE, html, headers);
	}

	/**
	 * ֱ�����XML.
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		System.out.println(xml);
		render(ServletUtils.XML_TYPE, xml, headers);
	}

	/**
	 * ֱ�����JSON.
	 * 
	 * @param jsonString json�ַ���.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, final String... headers) {
		render(ServletUtils.JSON_TYPE, jsonString, headers);
	}

	/**
	 * ֱ�����JSON,ʹ��Jacksonת��Java����.
	 * 
	 * @param data ������List<POJO>, POJO[], POJO, Ҳ����Map��ֵ��.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object data, final String... headers) {
		// REVIEW-ACCEPT �Դ��ν��зǿ��ж�
		//FIXED
		
		Assert.notNull(data);
		
		HttpServletResponse response = initResponseHeader(ServletUtils.JSON_TYPE, headers);
		try {
			//SerializerFactory factory=new SerializerFactory();
			//SerializationConfig config=factory.//SerializationConfig.createUnshared();
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * ֱ�����JSON,ʹ��Xmlת��Java����.
	 * 
	 * @param data ������List<POJO>, POJO[], POJO, Ҳ����Map��ֵ��.
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final Object data, final String... headers) {
			renderXml(XBlink.toXml(data));
	}
	
	public static void renderHTML(final Object data, final String... headers) {
		// REVIEW-ACCEPT �Դ��ν��зǿ��ж�
		//FIXED
		
		Assert.notNull(data);
		
		HttpServletResponse response = initResponseHeader(ServletUtils.HTML_TYPE, headers);
		try {
			//SerializerFactory factory=new SerializerFactory();
			//SerializationConfig config=factory.//SerializationConfig.createUnshared();
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * ֱ�����֧�ֿ���Mashup��JSONP.
	 * 
	 * @param callbackName callback������.
	 * @param object Java����,������List<POJO>, POJO[], POJO ,Ҳ����Map��ֵ��, ����ת��Ϊjson�ַ���.
	 */
	public static void renderJsonp(final String callbackName, final Object object, final String... headers) {
		// REVIEW-ACCEPT �Դ��ν��зǿ��ж�
		//FIXED
		
		Assert.hasText(callbackName);
		Assert.notNull(object);
		
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();

		//��ȾContent-TypeΪjavascript�ķ�������,������Ϊjavascript���, ��callback197("{html:'Hello World!!!'}");
		render(ServletUtils.JS_TYPE, result, headers);
	}

	/**
	 * ����������contentType��headers.
	 */
	private static HttpServletResponse initResponseHeader(final String contentType, final String... headers) {
		// REVIEW-ACCEPT �Դ��ν��зǿ��ж�
		//FIXED
		
		Assert.hasText(contentType);
		
		//����headers����
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "����һ���Ϸ���header����");
			}
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		//����headers����
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			ServletUtils.setNoCacheHeader(response);
		}

		return response;
	}
	
	

}
