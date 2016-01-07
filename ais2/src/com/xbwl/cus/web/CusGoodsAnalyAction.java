package com.xbwl.cus.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.xblink.XBlink;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.dto.CompartorCusDto;
import com.xbwl.cus.dto.CusGoodsXmlDto;
import com.xbwl.cus.dto.MainList;
import com.xbwl.cus.service.ICusDemandService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusLinkman;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 *客户 货量分析控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusGoodsAnalyAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_goodsAnaly.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusGoodsAnalyAction extends SimpleActionSupport {
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	private Date countDate;
	private Long cusId;
	private String startCount;//开始时间
	private String endCount;//结束时间
	private String countRange;//统计维度
	@Override
	protected Object createNewInstance() {
		return null;
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return oprFaxInService;
	}
	@Override
	public Object getModel() {
		return null;
	}
	@Override
	public void setModel(Object obj) {
	}
	
	/**
	 * @return the countDate
	 */
	public Date getCountDate() {
		return countDate;
	}
	/**
	 * @param countDate the countDate to set
	 */
	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}
	
	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}
	/**
	 * @param cusId the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	
	public String getStartCount() {
		return startCount;
	}
	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}
	public String getEndCount() {
		return endCount;
	}
	public void setEndCount(String endCount) {
		this.endCount = endCount;
	}
	public String getCountRange() {
		return countRange;
	}
	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}
	public String findGoodsMsg(){
		this.setPageConfig();
		try {
			oprFaxInService.findGoodsAnaly(this.getPages(),startCount,endCount,countRange,cusId);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
