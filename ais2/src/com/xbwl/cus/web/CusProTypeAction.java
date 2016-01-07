package com.xbwl.cus.web;

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
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusProductTypeService;
import com.xbwl.entity.CusProducttype;

/**
 * 客户产品类型控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusProTypeAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_productType.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusProTypeAction extends SimpleActionSupport {
	@Resource(name="cusProductTypeServiceImpl")
	private ICusProductTypeService cusProductTypeService;
	private CusProducttype cusProducttype;
	private Date countDate;
	private Long cusId;
	private String startCount;
	private String endCount;
	private String countRange;
	
	@Override
	protected Object createNewInstance() {
		return new CusProducttype();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusProductTypeService;
	}
	@Override
	public Object getModel() {
		return cusProducttype;
	}
	@Override
	public void setModel(Object obj) {
		cusProducttype=(CusProducttype)obj;
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
	public String findProMsg(){
		this.setPageConfig();
		try {
			cusProductTypeService.findCusProductMsg(this.getPages(),startCount,endCount,countRange,cusId);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
