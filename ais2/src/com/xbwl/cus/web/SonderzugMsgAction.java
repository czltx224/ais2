package com.xbwl.cus.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.lob.SerializableClob;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.web.OprFaxMainAction;

/**
 * author CaoZhili time Jul 6, 2011 2:40:02 PM
 */
@Controller
@Action("sonderzugMsgAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_sonderzugMsg.jsp", type = "dispatcher"),
		@Result(name = "daySale", location = "/WEB-INF/xbwl/reports/cus/report_daySaleMsg.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class SonderzugMsgAction extends SimpleActionSupport {

	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	private OprFaxIn oprFaxIn;
	
	private Long isException;
	private Date startTime;
	private Date endTime;
	
	private Date saleDate;
	private String countType;
	private Long departId;
	
	private String numberType;//数据类型 客服部门、客服员

	@Override
	protected Object createNewInstance() {
		return new OprFaxIn();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprFaxInService;
	}

	@Override
	public Object getModel() {
		return this.oprFaxIn;
	}
	@Override
	public void setModel(Object obj) {
		this.oprFaxIn = (OprFaxIn) obj;
	}
	
	
	
	/**
	 * @return the isException
	 */
	public Long getIsException() {
		return isException;
	}

	/**
	 * @param isException the isException to set
	 */
	public void setIsException(Long isException) {
		this.isException = isException;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}

	/**
	 * @param saleDate the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}
	
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	public String getNumberType() {
		return numberType;
	}

	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}

	/**
	 * 专车信息查询
	 * @return
	 */
	public String findSonderMsg(){
		this.setPageConfig();
		try {
			oprFaxInService.sonderzugMsgCount(this.getPages(), startTime, endTime, isException);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	public String daySale(){
		return "daySale";
	}
	/**
	 * 日报表
	 * @author LiuHao
	 * @time May 18, 2012 3:18:59 PM 
	 * @return
	 */
	public String findSaleDayMsg(){
		this.setPageConfig();
		List<Map> rList=new ArrayList<Map>();
		try {
			List<Map> list=oprFaxInService.daySaleMsg(saleDate,countType,departId,numberType);
			for(Map mapist :list ){
				SerializableClob clob=(SerializableClob)mapist.get("DUTY_NAME");
				mapist.put("DUTY_NAME", OprFaxMainAction.clob2String(clob));
				rList.add(mapist);
			}
		} catch (Exception e) {
			addError("查询出错", e);
		}
		this.getPages().setResultMap(rList);
		Struts2Utils.renderJson(this.getPages());
		return null;
	}
}
