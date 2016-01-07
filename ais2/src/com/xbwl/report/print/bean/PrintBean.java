package com.xbwl.report.print.bean;


/**
 * @author Administrator
 * @createTime 11:51:57 AM
 * @updateName Administrator
 * @updateTime 11:51:57 AM
 * 
 */

public abstract class PrintBean {
	

	public abstract  Long getPrintNum() ;

	public abstract void setPrintNum(Long printNum) ;

	public abstract String getPrintName();

	public abstract void setPrintName(String printName);

	public abstract String getPrintTime();

	public abstract void setPrintTime(String printTime);

	public  abstract String getPrintId();
	
	public abstract void setPrintId(String printId) ;

	public abstract String getSourceNo() ;

	public abstract void setSourceNo(String sourceNo);

}
