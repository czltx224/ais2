package com.xbwl.report.print.bean;

import java.util.ArrayList;
import java.util.List;

import org.xblink.annotation.XBlinkAlias;
import org.xblink.annotation.XBlinkOmitField;

/**
 * @author Administrator
 * @createTime 4:29:04 PM
 * @updateName Administrator
 * @updateTime 4:29:04 PM
 * 
 */
@XBlinkAlias("billLadings")
public class BillLadingList  {
	
	private String  billMd5 ;
	
	private String reportPath;
	
	private String reportName;
	
	@XBlinkOmitField
	private String springBean;
	
	private int controlSide=0;
	
	private String imgBaseUrl;
	
	private String msg;
	
	private List<String> noPermission=new ArrayList<String>(); 
	
	private  List<PrintBean> printBeans=new ArrayList<PrintBean>();
	
	private boolean isNew;

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public List<PrintBean> getPrintBeans() {
		return printBeans;
	}

	public void setPrintBeans(List<PrintBean> printBeans) {
		this.printBeans = printBeans;
	}

	public String getBillMd5() {
		return billMd5;
	}

	public void setBillMd5(String billMd5) {
		this.billMd5 = billMd5;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getSpringBean() {
		return springBean;
	}

	public void setSpringBean(String springBean) {
		this.springBean = springBean;
	}

	public List<String> getNoPermission() {
		return noPermission;
	}

	public void setNoPermission(List<String> noPermission) {
		this.noPermission = noPermission;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public int getControlSide() {
		return controlSide;
	}

	public void setControlSide(int controlSide) {
		this.controlSide = controlSide;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getImgBaseUrl() {
		return imgBaseUrl;
	}

	public void setImgBaseUrl(String imgBaseUrl) {
		this.imgBaseUrl = imgBaseUrl;
	}

}
