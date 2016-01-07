package com.xbwl.finance.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiDeliverycost;
import com.xbwl.entity.FiTransitcost;
import com.xbwl.finance.Service.IFiPaymentService;
import com.xbwl.finance.Service.IFiTransitcostService;

/**
 * author shuw
 * time Oct 7, 2011 11:50:01 AM
 */

@Controller
@Action("fiTransitcostAcion")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiTransitCostManage.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiTransitcostAction  extends SimpleActionSupport {

	private FiTransitcost fiTransitcost;
	
	@Resource(name = "fiTransitcostServiceImpl")
	private IFiTransitcostService fiTransitService;

	private String ts;
	private Date startDate;
	private Date endDate;
	private Long confirmStatus;  //回单确收状态
	private Long departId;
	private Long fiAuditStatus;
	private String dateType;
	private String sourceData;
	private String serviceDepartCode;
	
	@Element(value = FiTransitcost.class)
	private List<FiTransitcost> aa;
	
	public String qxFiAudit(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	    			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	    			fiTransitService.qxFiAudit(getId(),ts,sourceData);
	        		getValidateInfo().setMsg("操作成功");
	        		getValidateInfo().setSuccess(true);
	        		addMessage("操作成功");
	        	}else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg(e.getLocalizedMessage());
	            addError("数据保存失败！", e);
	        }
	        return RELOAD;
	}

	public String noAuditList(){
		 try {
				setPageConfig();
				Map filterMap =new HashMap();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String endTimeString = null;
				String startTimeString =null;
				if(endDate!=null){
					endTimeString = formatter.format(endDate)+" 23:59:59";
				}
				if(startDate!=null){
					startTimeString = formatter.format(startDate)+" 00:00:00";
				}
				filterMap.put("dateType", dateType);
				filterMap.put("startDate", startDate);
				filterMap.put("endDate", endDate);
				filterMap.put("confirmStatus", confirmStatus);
				filterMap.put("itemsValue", getItemsValue());
				filterMap.put("checkItems", getCheckItems());
				filterMap.put("serviceDepartCode", serviceDepartCode);
				
				Map map = new HashMap();
				map.put("confirmStatus", confirmStatus);
				map.put("startDate", startTimeString);
				map.put("startDate2", startTimeString);
				map.put("endDate",endTimeString );
				map.put("endDate2",endTimeString );
				map.put("departId", departId);
				map.put("depart2", departId);
				map.put("serviceDepartCode", "%"+serviceDepartCode+"%");
				map.put("itemsValue", getItemsValue());
				map.put("checkItems", getCheckItems());
				String sql  = fiTransitService.getSelectSql(filterMap,fiAuditStatus);

				fiTransitService.getPageBySqlMap(getPages(), sql, map);
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg(e.getLocalizedMessage());
	            addError("数据查询失败！", e);
	        }
	        return LIST;
	}
	
	/**
	 * 保存中转成本，多条一起审核，只能是传真录入的数据
	 * @return
	 */
	public String saveOfFax(){
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	    			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	    			String s = fiTransitService.saveFiTransitcostAndFicost(user, aa );
	        		getValidateInfo().setMsg("数据保存成功" );
	        		getValidateInfo().setValue(s);
	        		getValidateInfo().setSuccess(true);
	        		addMessage(s);
	        	}else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg(e.getLocalizedMessage());
	            addError("数据保存失败！", e);
	        }
	        return RELOAD;
	}
	
	/**
	 * 返货成本审核 只能审核一条
	 * @return
	 */
	public String saveReturn() {
		 try {
	        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
	    			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	    			String s = fiTransitService.saveFiTransitcostAndFicost(user,ts, getId());
	        		getValidateInfo().setMsg("保存成功");
	        		getValidateInfo().setValue(s);
	        		getValidateInfo().setSuccess(true);
	        		addMessage(s);
	        	}else{
	        		getValidateInfo().setSuccess(false);
	        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
	            
	        } catch (Exception e) {
	        	getValidateInfo().setSuccess(false);
	        	getValidateInfo().setMsg(e.getLocalizedMessage());
	            addError("数据保存失败！", e);
	        }
	        return RELOAD;
	}

	/**
	 * 撤销审核信息提示
	 * @return
	 */
	public String qxAmountCheck() {
		try {
			String info=fiTransitService.qxAmountCheck(getId());
			Struts2Utils.renderJson(info);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
	}
	
	@Override
	protected Object createNewInstance() {
		return new FiTransitcost();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiTransitService;
	}

	@Override
	public Object getModel() {
		return fiTransitcost;
	}

	@Override
	public void setModel(Object obj) {
		fiTransitcost=(FiTransitcost)obj;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
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

	public Long getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(Long confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Long getFiAuditStatus() {
		return fiAuditStatus;
	}

	public void setFiAuditStatus(Long fiAuditStatus) {
		this.fiAuditStatus = fiAuditStatus;
	}

	public String getSourceData() {
		return sourceData;
	}

	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}

	public List<FiTransitcost> getAa() {
		return aa;
	}

	public void setAa(List<FiTransitcost> aa) {
		this.aa = aa;
	}

	public String getServiceDepartCode() {
		return serviceDepartCode;
	}

	public void setServiceDepartCode(String serviceDepartCode) {
		this.serviceDepartCode = serviceDepartCode;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
}
