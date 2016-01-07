/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: PropertyFilter.java,v 1.7 2011/03/23 01:36:44 yyj Exp $
 */
package com.xbwl.common.orm;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.xbwl.common.utils.ReflectionUtils;

/**
 * �����ORMʵ���޹ص����Թ���������װ��.
 * 
 * PropertyFilter��Ҫ��¼ҳ���м򵥵�������������,��Hibernate��CriterionҪ��.
 * 
 * @author calvin
 */
public class PropertyFilter {

	/** ������Լ�OR��ϵ�ķָ���. */
	public static final String OR_SEPARATOR = "_OR_";
	
	public static final String LEFT_JION=".";

	/** ���ԱȽ�����. EQ:����,LT:С��,GT:����,LE:С�ڵ���,GE:���ڵ���,SQ:��SQL,NE:������,EMPTY:������,NULL��isNull,NOTNULL*/
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, SQ, NE,EMPTY,NULL,NOTNULL;
	}

	/** ������������. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String[] propertyNames = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = null;
	private boolean isLeftJion=false;
	private String tempJion=null;
	private String filterName=null;
	private String value=null;
	
	public PropertyFilter() {
	}

	/**
	 * @param filterName �Ƚ������ַ���,�����ȽϵıȽ����͡�����ֵ���ͼ������б�. 
	 *                   eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value ���Ƚϵ�ֵ.
	 */
	public PropertyFilter(final String filterName, final String value) throws IllegalArgumentException {
		// REVIEW-ACCEPT ����������׳��쳣������Ӧ�ö����׳��쳣
		//FIXED
		this.filterName=filterName;
		this.value=value;
		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter����" + filterName + "û�а������д,�޷��õ����ԱȽ�����.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter����" + filterName + "û�а������д,�޷��õ�����ֵ����.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		//propertyNames = StringUtils.split(propertyNameStr, PropertyFilter.OR_SEPARATOR);
		propertyNames=propertyNameStr.split(PropertyFilter.OR_SEPARATOR);

		Assert.isTrue(propertyNames.length > 0, "filter����" + filterName + "û�а������д,�޷��õ���������.");
		//��entity property�е����ͽ��ַ���ת��Ϊʵ������.
		if(propertyNameStr.indexOf(LEFT_JION)!=-1){
			isLeftJion=true;
		}
		
		if(null==value || value.equals("")){
			
			this.propertyValue=null;
		
		}else{
			
			this.propertyValue = ReflectionUtils.convertStringToObject(value, propertyType);
		
		}
	}

	/**
	 * �Ƿ�Ƚ϶������.
	 */
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}

	/**
	 * �Ƿ������Ӳ�ѯ.
	 */
	public boolean isLeftJion() {
		return this.isLeftJion;
	}
	
	/**
	 * ��ȡ�Ƚ����������б�.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * ��ȡΨһ�ıȽ���������.
	 */
	public String getPropertyName() {
		if (propertyNames.length > 1) {
			throw new IllegalArgumentException("There are not only one property");
		}
		return propertyNames[0];
	}

	/**
	 * ��ȡ�Ƚ�ֵ.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * ��ȡ�Ƚ�ֵ������.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * ��ȡ�ȽϷ�ʽ����.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	@Override
	public String toString() {
		return filterName+"="+this.value+" ";
	}
}
