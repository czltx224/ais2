package com.xbwl.oper.stock.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.lob.SerializableClob;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprInformAppointment;
import com.xbwl.oper.fax.web.OprFaxMainAction;
import com.xbwl.oper.stock.service.IOprInformAppointmentService;

/**
 * author CaoZhili time Jul 18, 2011 2:28:59 PM
 * 
 * 通知预约主表控制层操作类
 */
@Controller
@Action("oprInformAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_inform_appointment.jsp", type = "dispatcher"),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class OprInformAction extends SimpleActionSupport {

	private OprInformAppointment oprInformAppointment;

	@Resource(name = "oprInformAppointmentServiceImpl")
	private IOprInformAppointmentService oprInformAppointmentService;
	
	@Value("${oprInformAction.requestStage}")
	private String requestStage;
	
	private Long informDno;
	
	public Long getInformDno() {
		return informDno;
	}

	public void setInformDno(Long informDno) {
		this.informDno = informDno;
	}

	@Override
	protected Object createNewInstance() {

		return new OprInformAppointment();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprInformAppointmentService;
	}

	@Override
	public Object getModel() {

		return this.oprInformAppointment;
	}

	@Override
	public void setModel(Object obj) {

		this.oprInformAppointment = (OprInformAppointment) obj;
	}
	
	/**通知预约保存方法
	 * @return
	 */
	public String saveInformAppointment() {
		Map map=new HashMap();
		map.put("dno",ServletActionContext.getRequest().getParameter("dno"));
		map.put("informResult",ServletActionContext.getRequest().getParameter("informResult"));
		map.put("informType",ServletActionContext.getRequest().getParameter("informType"));
		map.put("ts", ServletActionContext.getRequest().getParameter("ts"));
	
		try {
			this.oprInformAppointmentService
					.saveInformAppointmentService(map);
		}catch (ServiceException e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(e.getLocalizedMessage());
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("saveInformAppointment faileld");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(e.getLocalizedMessage());
		}

		return "msg";
	}
	
	/**通知预约SQL查询方法
	 * @return
	 */
	public String getResultMapQuery(){
		String sql=null;
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		map.put("checkItems", getCheckItems());
		map.put("itemsValue", getItemsValue());
		
		// map.put("reachStatus", 1+"");
		// map.put("minPiece", "0");
		// map.put("outStatus", "0");
		map.put("requestStage", requestStage);
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		map.put("bussDepart", user.get("bussDepart")+"");
		
		try{
			sql=this.oprInformAppointmentService.getResultMapQueryService(map);
			this.oprInformAppointmentService.getPageBySqlMap(this.getPages(), sql, map);
			List<Map>list  = getPages().getResultMap();
			for(Map mapist :list ){
				SerializableClob clob=(SerializableClob)mapist.get("REQUEST");
				mapist.put("REQUEST", OprFaxMainAction.clob2String(clob));
			}
			getPages().setResultMap(list);
		}catch(Exception e){
			addError("通知预约查询失败！", e);
		}
		return this.LIST;
	}
}
