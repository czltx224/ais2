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
import com.xbwl.cus.service.ICusSaleChanceService;
import com.xbwl.entity.CusSaleChance;

/**
 * 客户销售机会控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusSaleChanceAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_saleChance.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusSaleChanceAction extends SimpleActionSupport {
	@Resource(name="cusSaleChanceServiceImpl")
	private ICusSaleChanceService cusSaleChanceService;
	private CusSaleChance cusSaleChance;
	private Long scId;
	
	@Override
	protected Object createNewInstance() {
		return new CusSaleChance();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusSaleChanceService;
	}
	@Override
	public Object getModel() {
		return cusSaleChance;
	}
	@Override
	public void setModel(Object obj) {
		this.cusSaleChance=(CusSaleChance)obj;
	}
	
	/**
	 * @return the scId
	 */
	public Long getScId() {
		return scId;
	}
	/**
	 * @param scId the scId to set
	 */
	public void setScId(Long scId) {
		this.scId = scId;
	}
	public String delete(){
		try {
			cusSaleChanceService.delete(scId);
			getValidateInfo().setSuccess(true);
        	getValidateInfo().setMsg("数据删除成功！");
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据删除失败！");
        	addError("数据删除失败！", e);
		}
		return RELOAD;
	}
	public String save(){
		cusSaleChance.setStatus(1L);
		Long endDay = (cusSaleChance.getEndTime().getTime()-cusSaleChance.getStartTime().getTime())/(24*60*60*1000);
		Long startDay=(new Date().getTime()-cusSaleChance.getStartTime().getTime())/(24*60*60*1000);
		cusSaleChance.setTimeUser(Double.valueOf(startDay)/endDay);
		cusSaleChanceService.save(cusSaleChance);
		return RELOAD;
	}
}
