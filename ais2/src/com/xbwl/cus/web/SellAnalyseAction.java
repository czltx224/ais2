package com.xbwl.cus.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 * 销售分析控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("sellAnalyseAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_cusanalyse.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class SellAnalyseAction extends SimpleActionSupport {
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	private OprFaxIn oprFaxIn;
	
	private String countType;//统计类型
	private String countRange;
	private String startCount;
	private String endCount;
	private Long departId;
	private String groups;
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
		return oprFaxInService;
	}
	@Override
	public Object getModel() {
		return oprFaxIn;
	}
	@Override
	public void setModel(Object obj) {
		oprFaxIn=(OprFaxIn)obj;
	}
	
	/**
	 * @return the countType
	 */
	public String getCountType() {
		return countType;
	}
	/**
	 * @param countType the countType to set
	 */
	public void setCountType(String countType) {
		this.countType = countType;
	}
	
	
	public String getCountRange() {
		return countRange;
	}
	public void setCountRange(String countRange) {
		this.countRange = countRange;
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
	
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	/**
	 * 整体销售分析
	 * @return
	 */
	public String findWholeSellMsg(){
		this.setPageConfig();
		try {
			oprFaxInService.findWholeSellMsg(this.getPages(),countRange,startCount,endCount,departId,countType,groups);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
