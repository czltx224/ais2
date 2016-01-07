package com.xbwl.cus.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.cus.service.ICusServiceService;
import com.xbwl.entity.CusRecord;

/**
 * 客户档案管理控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusRecordAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_record.jsp", type = "dispatcher"),
		@Result(name = "cusWarn", location = "/WEB-INF/xbwl/cus/cus_runoffwarn.jsp", type = "dispatcher"),
		@Result(name = "main", location = "/WEB-INF/xbwl/cus/cusArchivesManage.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusRecordAction extends SimpleActionSupport {
	@Resource(name="cusRecordServiceImpl")
	private ICusRecordService cusRecordService;
	@Resource(name = "cusServiceServiceImpl")
	private ICusServiceService cusServiceService;
	
	private CusRecord cusRecord;
	private Long recordId;
	
	private String consigName;
	private String consigTel;
	private String mainType;
	private String conType;
	
	private String cusName;
	
	private String cusService;
	private Long cusRecordId;
	
	@Override
	protected Object createNewInstance() {
		return new CusRecord();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusRecordService;
	}
	@Override
	public Object getModel() {
		return cusRecord;
	}
	@Override
	public void setModel(Object obj) {
		this.cusRecord=(CusRecord)obj;
	}
	
	/**
	 * @return the recordId
	 */
	public Long getRecordId() {
		return recordId;
	}
	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public String gotoMain(){
		CusRecord c=cusRecordService.get(recordId);
		ServletActionContext.getRequest().setAttribute("cusRecord", c);
		return "main";
	}
	
	/**
	 * @return the cusRecord
	 */
	public CusRecord getCusRecord() {
		return cusRecord;
	}
	/**
	 * @param cusRecord the cusRecord to set
	 */
	public void setCusRecord(CusRecord cusRecord) {
		this.cusRecord = cusRecord;
	}
	/**
	 * @return the consigTel
	 */
	public String getConsigTel() {
		return consigTel;
	}
	/**
	 * @param consigTel the consigTel to set
	 */
	public void setConsigTel(String consigTel) {
		this.consigTel = consigTel;
	}
	
	/**
	 * @return the consigName
	 */
	public String getConsigName() {
		return consigName;
	}
	/**
	 * @param consigName the consigName to set
	 */
	public void setConsigName(String consigName) {
		this.consigName = consigName;
	}
	/**
	 * @return the mainType
	 */
	public String getMainType() {
		return mainType;
	}
	/**
	 * @param mainType the mainType to set
	 */
	public void setMainType(String mainType) {
		this.mainType = mainType;
	}
	
	/**
	 * @return the conType
	 */
	public String getConType() {
		return conType;
	}
	/**
	 * @param conType the conType to set
	 */
	public void setConType(String conType) {
		this.conType = conType;
	}
	
	/**
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}
	/**
	 * @param cusName the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	
	public String getCusService() {
		return cusService;
	}
	public void setCusService(String cusService) {
		this.cusService = cusService;
	}
	public Long getCusRecordId() {
		return cusRecordId;
	}
	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}
	/**查询CUS_RECORD表的备注
	 * @return
	 */
	public String findTableRemark(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("tableName", "CUS_RECORD");
		
			String sql = this.cusRecordService.findTableRemark(map);
			
			this.cusRecordService.getPageBySqlMap(getPages(), sql, map);
			//FIXED 该输出是否必要
		}catch(Exception e){
			addError("查询表备注失败！", e);
		}
		
		return LIST;
	}
	public String save(){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		try{
			cusRecordService.saveCusRecord(cusRecord, user,conType);
			this.getValidateInfo().setMsg("保存成功！");
		}catch(Exception e){
			addError("保存失败！", e);
		}
		return RELOAD;
	}
	
	public String findFaxcus(){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		try {
			List list=cusRecordService.findFaxcus(Long.valueOf(user.get("bussDepart").toString()),cusName);
			Struts2Utils.renderJson(list);
		}catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
	
	public String goFindWarn(){
		return "cusWarn";
	}
	/**
	 * 查询预警客户
	 * @author LiuHao
	 * @time May 10, 2012 4:28:40 PM 
	 * @return
	 */
	public String findWarn(){
		this.setPageConfig();
		try {
			cusRecordService.findWarnCus(this.getPages(),cusRecordId,cusService);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
