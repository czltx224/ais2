package com.xbwl.oper.takegoods.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprShuntApply;
import com.xbwl.entity.OprShuntApplyDetail;
import com.xbwl.oper.takegoods.service.IOprShuntApplyDetailService;

/**
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("oprShuntApplyDetailAction")
@Scope("prototype")
@Namespace("/takegoods")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/takegoods/oprTakeGoods.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class OprShuntApplyDetailAction extends SimpleActionSupport {
	
	@Resource(name="oprShuntApplyDetailServiceImpl")
	private IOprShuntApplyDetailService oprShuntApplyDetailService;
	private OprShuntApplyDetail oprShuntApplyDetail;
	private Long osadId;
	
	private String carNo;

	@Override
	protected Object createNewInstance() {
		return new OprShuntApplyDetail();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprShuntApplyDetailService;
	}

	@Override
	public Object getModel() {
		return oprShuntApplyDetail;
	}

	@Override
	public void setModel(Object obj) {
		this.oprShuntApplyDetail=(OprShuntApplyDetail)obj;
	}
	
	public Long getOsadId() {
		return osadId;
	}

	public void setOsadId(Long osadId) {
		this.osadId = osadId;
	}
	
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String save(){
		try {
			oprShuntApplyDetailService.shuntApplyAduit(oprShuntApplyDetail);
			this.getValidateInfo().setMsg("保存成功");
		} catch (Exception e) {
			addError("保存出错", e);
		}
		return RELOAD;
	}
	
	public String delete(){
		try {
			oprShuntApplyDetailService.repeatAduit(osadId);
			this.getValidateInfo().setMsg("撤销成功");
		} catch (Exception e) {
			addError("作废失败", e);
		}
		return RELOAD;
	}
	
	public String findMaxRouteNumber(){
		Long routeNumber=0L;
		try {
			routeNumber=oprShuntApplyDetailService.findMaxRouteNumber(carNo);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		Struts2Utils.renderJson(routeNumber);
		return null;
	}
}
