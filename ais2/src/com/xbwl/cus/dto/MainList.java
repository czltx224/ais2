package com.xbwl.cus.dto;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *@author LiuHao
 *此类为生成图表格式的DTO类
 *@time Oct 17, 2011 4:29:21 PM
 */
@Root(name="chart")
public class MainList {
	//属性名称都为图表格式的属性名 名称不可变
	@ElementList(name = "WifeList", entry = "set", inline = true)
	private List<CusGoodsXmlDto> list=new ArrayList();
	@Attribute(name="caption")
	private String caption;
	@Attribute(name="subCaption")
	private String subCaption;
	@Attribute(name="xAxisName")
	private String xAxisName;
	@Attribute(name="yAxisName")
	private String yAxisName;
	@Attribute(name="rotateYAxisName")
	private String rotateYAxisName;
	@Attribute(name="showValues")
	private String showValues;
	@Attribute(name="showNames")
	private String showNames;
	@Attribute(name="baseFontSize")
	private String baseFontSize;
	@Attribute(name="numberSuffix")
	private String numberSuffix;
	@Attribute(name="outCnvBaseFontSiz")
	private String outCnvBaseFontSiz;
	@Attribute(name="pieSliceDepth")
	private String pieSliceDepth;
	@Attribute(name="formatNumberScale")
	private String formatNumberScale;
	
	/**
	 * @return the list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the subCaption
	 */
	public String getSubCaption() {
		return subCaption;
	}

	/**
	 * @param subCaption the subCaption to set
	 */
	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}

	/**
	 * @return the xAxisName
	 */
	public String getXAxisName() {
		return xAxisName;
	}

	/**
	 * @param axisName the xAxisName to set
	 */
	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}

	/**
	 * @return the yAxisName
	 */
	public String getYAxisName() {
		return yAxisName;
	}

	/**
	 * @param axisName the yAxisName to set
	 */
	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}

	/**
	 * @return the rotateYAxisName
	 */
	public String getRotateYAxisName() {
		return rotateYAxisName;
	}

	/**
	 * @param rotateYAxisName the rotateYAxisName to set
	 */
	public void setRotateYAxisName(String rotateYAxisName) {
		this.rotateYAxisName = rotateYAxisName;
	}

	/**
	 * @return the showValues
	 */
	public String getShowValues() {
		return showValues;
	}

	/**
	 * @param showValues the showValues to set
	 */
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}

	/**
	 * @return the showNames
	 */
	public String getShowNames() {
		return showNames;
	}

	/**
	 * @param showNames the showNames to set
	 */
	public void setShowNames(String showNames) {
		this.showNames = showNames;
	}

	/**
	 * @return the baseFontSize
	 */
	public String getBaseFontSize() {
		return baseFontSize;
	}

	/**
	 * @param baseFontSize the baseFontSize to set
	 */
	public void setBaseFontSize(String baseFontSize) {
		this.baseFontSize = baseFontSize;
	}

	/**
	 * @return the numberSuffix
	 */
	public String getNumberSuffix() {
		return numberSuffix;
	}

	/**
	 * @param numberSuffix the numberSuffix to set
	 */
	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	/**
	 * @return the outCnvBaseFontSiz
	 */
	public String getOutCnvBaseFontSiz() {
		return outCnvBaseFontSiz;
	}

	/**
	 * @param outCnvBaseFontSiz the outCnvBaseFontSiz to set
	 */
	public void setOutCnvBaseFontSiz(String outCnvBaseFontSiz) {
		this.outCnvBaseFontSiz = outCnvBaseFontSiz;
	}

	/**
	 * @return the pieSliceDepth
	 */
	public String getPieSliceDepth() {
		return pieSliceDepth;
	}

	/**
	 * @param pieSliceDepth the pieSliceDepth to set
	 */
	public void setPieSliceDepth(String pieSliceDepth) {
		this.pieSliceDepth = pieSliceDepth;
	}

	/**
	 * @return the formatNumberScale
	 */
	public String getFormatNumberScale() {
		return formatNumberScale;
	}

	/**
	 * @param formatNumberScale the formatNumberScale to set
	 */
	public void setFormatNumberScale(String formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}

	
}
