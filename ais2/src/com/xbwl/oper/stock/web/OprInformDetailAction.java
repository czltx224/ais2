package com.xbwl.oper.stock.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprInformAppointmentDetail;
import com.xbwl.oper.stock.service.IOprInformAppointmentDetailService;

/**
 * author CaoZhili time Jul 20, 2011 4:59:57 PM
 * 
 * 通知预约明细表控制层操作类
 */
@Controller
@Action("oprInformDetailAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {

		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) })
public class OprInformDetailAction extends SimpleActionSupport {

	@Resource(name = "oprInformAppointmentDetailServiceImpl")
	private IOprInformAppointmentDetailService oprInformAppointmentDetailService;

	private OprInformAppointmentDetail oprInformAppointmentDetail;

	@Override
	protected Object createNewInstance() {

		return new OprInformAppointmentDetail();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprInformAppointmentDetailService;
	}

	@Override
	public Object getModel() {

		return this.oprInformAppointmentDetail;
	}

	@Override
	public void setModel(Object obj) {

		this.oprInformAppointmentDetail = (OprInformAppointmentDetail) obj;
	}

	public String findDetailByDno(){
		String sql=null;
		//设置分页参数
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			sql=this.oprInformAppointmentDetailService.findDetailByDno(map);
			this.oprInformAppointmentDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("交接单查询失败！", e);
		}
		return this.LIST;
	}
}
