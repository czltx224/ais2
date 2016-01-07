package com.xbwl.cus.web;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.MarketingTarget;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusDepartResultAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_cusDepartResult.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusDepartResultAction extends SimpleActionSupport {
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	private MarketingTarget marketingTarget;
	
	private String dateType;//年度统计时间
	private Date startDate;//指标类型  货量、收入
	private Date endDate;//统计区域 业务部门、客服部门
	private Long rightDepart;//业务部门ID

	@Override
	protected Object createNewInstance() {
		return new MarketingTarget();
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
		return marketingTarget;
	}
	@Override
	public void setModel(Object obj) {
		marketingTarget=(MarketingTarget)obj;
	}

	public Long getRightDepart() {
		return rightDepart;
	}
	public void setRightDepart(Long rightDepart) {
		this.rightDepart = rightDepart;
	}
	
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	//查询客服部门业绩
	public String searchCusResult(){
		try {
			this.setPageConfig();
			oprFaxInService.getDepartResults(this.getPages(), startDate, endDate, rightDepart,dateType );
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	
}
