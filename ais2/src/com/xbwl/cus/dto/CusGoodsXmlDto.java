package com.xbwl.cus.dto;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * 此类为货量分析图表数据源的DTO类
 *@author LiuHao
 *@time Oct 17, 2011 3:09:41 PM
 */
@Root(name="set")
public class CusGoodsXmlDto {
	@Attribute(name="name")
	private String arrName;
	@Attribute(name="value")
	private String arrValue;
	
	/**
	 * @return the arrName
	 */
	public String getArrName() {
		return arrName;
	}
	/**
	 * @param arrName the arrName to set
	 */
	public void setArrName(String arrName) {
		this.arrName = arrName;
	}
	/**
	 * @return the arrValue
	 */
	public String getArrValue() {
		return arrValue;
	}
	/**
	 * @param arrValue the arrValue to set
	 */
	public void setArrValue(String arrValue) {
		this.arrValue = arrValue;
	}
	
}
