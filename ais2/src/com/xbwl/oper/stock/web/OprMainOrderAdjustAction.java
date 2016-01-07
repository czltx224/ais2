package com.xbwl.oper.stock.web;

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
import com.xbwl.entity.OprMainOrderAdjust;
import com.xbwl.oper.stock.service.IOprMainOrderAdjustService;
import com.xbwl.oper.stock.vo.OprMainOrderAdjustVo;

/**
 * @author CaoZhili time Aug 8, 2011 10:01:16 AM
 * 
 * 主单调整记录表控制层操作类
 */
@Controller
@Action("oprMainOrderAdjustAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_mainOrderAdjust.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprMainOrderAdjustAction extends SimpleActionSupport {

	private OprMainOrderAdjust oprMainOrderAdjust;

	@Resource(name = "oprMainOrderAdjustServiceImpl")
	private IOprMainOrderAdjustService oprMainOrderAdjustService;

	@Element(value = OprMainOrderAdjustVo.class)
	private List<OprMainOrderAdjustVo> mainIds;

	public List<OprMainOrderAdjustVo> getMainIds() {
		return mainIds;
	}

	public void setMainIds(List<OprMainOrderAdjustVo> mainIds) {
		this.mainIds = mainIds;
	}

	@Override
	protected Object createNewInstance() {

		return new OprMainOrderAdjust();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprMainOrderAdjustService;
	}

	@Override
	public Object getModel() {

		return this.oprMainOrderAdjust;
	}

	@Override
	public void setModel(Object obj) {

		this.oprMainOrderAdjust = (OprMainOrderAdjust) obj;
	}

	
	/**主单及重量调整
	 * @return
	 */
	public String updateMainNoAndWeight() {
		try {
			this.oprMainOrderAdjustService
					.updateMainNoAndWeightService(this.mainIds);
		} catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return "reload";
	}

}
