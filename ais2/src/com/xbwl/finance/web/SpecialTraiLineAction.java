package com.xbwl.finance.web;

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
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.entity.BasFlight;
import com.xbwl.entity.BasSpecialTrainLine;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.Service.ISpecialTrainLineService;
import com.xbwl.finance.Service.ISpecialTrainRateService;
import com.xbwl.sys.service.IBasAreaService;
import com.xbwl.sys.service.IBasFlightService;
import com.xbwl.sys.service.IConInfoService;

@Controller
@Action("specialTrainLineAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class SpecialTraiLineAction extends SimpleActionSupport {

	@Resource(name = "specialTrainLineServiceImpl")
	private ISpecialTrainLineService specialTrainLineService;
	private BasSpecialTrainLine basSpecialTrainLine;
	

	@Override
	protected Object createNewInstance() {
		return new BasSpecialTrainLine();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return specialTrainLineService;
	}

	@Override
	public Object getModel() {
		return basSpecialTrainLine;
	}

	@Override
	public void setModel(Object obj) {
		basSpecialTrainLine = (BasSpecialTrainLine) obj;
		
	}

}
