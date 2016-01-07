package com.xbwl.finance.web;

import java.util.ArrayList;
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
import org.hibernate.dialect.IngresDialect;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.Service.IFiReceivablestatementService;
	
@Controller
@Action("fiReceivabledetailAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiReceivabledetail.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "reload", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiReceivabledetailAction extends SimpleActionSupport {

	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;
	
	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	private FiReceivabledetail fiReceivabledetail;

	//User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
	//Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
	
	private String ids;//往来明细列表(1,2,3)
	private Date stateDate;// 单据开始时间
	private Date endDate;// 单据结束时间
	private String custprop;// 客商类型
	private Long billingCycle;// 结算周期
	private Long customerId;// 客商ID
	private String reconciliationUser;// 对账员
	public Long fiReceivablestatementId;//对账单号
	public Long departId;//所部门ID
	
	@Override
	protected Object createNewInstance() {
		return new FiReceivabledetail();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiReceivabledetailService;
	}

	@Override
	public Object getModel() {
		return fiReceivabledetail;
	}

	@Override
	public void setModel(Object obj) {
		fiReceivabledetail = (FiReceivabledetail) obj;

	}

	/**
	 * 客商对账管理，账单明细导出
	 * @return
	 */
	public String exportExcel(){
		try{
				fiReceivablestatementService.exporterExcel(getId());
		}catch(Exception e){
            addError("导出失败！", e);
		}
		return null;
	}
	
	
	/**
	 * 
	 * @Title: saveReceivableStatement
	 * @Description: TODO(生成对账单)
	 * @throws Exception
	 *             Exception
	 * @return String 返回类型
	 * @throws Exception
	 */
	public String saveReceivableStatement() throws Exception {
		try{
			Map<String, Object> searchMap=new HashMap<String, Object>();
			searchMap.put("ids", ids);
			searchMap.put("stateDate", stateDate);
			searchMap.put("endDate", endDate);
			searchMap.put("custprop", custprop);
			searchMap.put("billingCycle", billingCycle);
			searchMap.put("customerId", customerId);
			searchMap.put("reconciliationUser", reconciliationUser);
			searchMap.put("departId",departId);
			fiReceivabledetailService.saveFiReceivablestatement(searchMap,this.getPages(),this.getValidateInfo());
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
		return "msg";
	}
	
	//从对账单中剔除往来明细
	public String eliminate() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Map<String, Object> searchMap=new HashMap<String, Object>();
				searchMap.put("ids", ids);
				fiReceivabledetailService.eliminate(searchMap);
	    		this.getValidateInfo().setMsg("剔除成功！");
	    		addMessage("剔除成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
		return RELOAD;
	}
	
	//对账单添加对账单明细
	public String receivabledetailAdd() throws Exception{
		try{
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Map<String, Object> searchMap=new HashMap<String, Object>();
				searchMap.put("ids", ids);
				searchMap.put("fiReceivablestatementId", fiReceivablestatementId);
				fiReceivabledetailService.receivabledetailAdd(searchMap);
	    		this.getValidateInfo().setMsg("添加对账明细成功！");
	    		addMessage("添加对账明细成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		}catch(Exception e){
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
		return RELOAD;
	}
	
	public String saveReceivabledetail() throws Exception{
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
    			fiReceivabledetailService.saveReceivabledetail(fiReceivabledetail);
        		this.getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }
		return RELOAD;
	}
	
    /**
     * 保存往来明细前二次绑定
     * @throws Exception
     */
    public void prepareSaveReceivabledetail() throws Exception {
    	prepareModel();
    }
	
	/**
	 * 
	 * @Title: saveProble
	 * @Description: TODO(保存问题账款)
	 * @throws Exception
	 *             Exception
	 * @return String reload
	 * @throws Exception
	 */
	public String saveProble() throws Exception{
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
        		if(this.fiReceivablestatementService.isRevocationReview(String.valueOf(this.fiReceivabledetail.getReconciliationNo()))){
	        		if (this.fiReceivabledetailService.isProbleBytailNo(fiReceivabledetail.getId())){
	        			fiReceivabledetailService.saveProble(fiReceivabledetail);
	            		this.getValidateInfo().setMsg("数据保存成功！");
	            		addMessage("数据保存成功！");
	        		}else{
	        			this.getValidateInfo().setSuccess(false);
	    	    		this.getValidateInfo().setMsg("操作失败:此单号已登记过问题账款，不能重复登记！");
	        		}
        		}else{
        			this.getValidateInfo().setSuccess(false);
    	    		this.getValidateInfo().setMsg("操作失败:对账单必须为审核才能登记问题账款！");
        		}
        		
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	this.getValidateInfo().setSuccess(false);
    		this.getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }
		return RELOAD;
	}
	
    /**
     * 保存问题账款前二次绑定
     * @throws Exception
     */
    public void prepareSaveProble() throws Exception {
    	prepareModel();
       /* try {
        	
            if (getId() != null) {
            	FiReceivabledetail object=(FiReceivabledetail) getManager().getAndInitEntity(getId());
            	if(object instanceof AuditableEntity){
            		AuditableEntity entity=(AuditableEntity)object;
            		entity.setTs(null);
            	}
            	setModel(object);
            } else {
                setModel(createNewInstance());
            }
        } catch (Exception e) {
        	e.printStackTrace();
            addError("记录查询失败！", e);
        }*/
    }
    
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 */
	public String audit() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				String reviewRemark=Struts2Utils.getParameter("reviewRemark")+"";
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("单据不存在!");
				}
				Map map=new HashMap();
				map.put("id", id);
				map.put("reviewRemark", reviewRemark);
				this.fiReceivabledetailService.audit(map, user);//审核
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("审核成功!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"审核失败!失败原因：" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	/**
	 * 撤销审核往来明细
	 * @return
	 * @throws Exception
	 */
	public String revocationAudit() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("单据不存在!");
				}
				Map map=new HashMap();
				map.put("id", id);
				this.fiReceivabledetailService.revocationAudit(map, user);//审核
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("撤销审核成功!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"撤销审核失败!失败原因：" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	public String invalid() throws Exception{
		try {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
				Long id=Long.valueOf(Struts2Utils.getParameter("id"));
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("单据不存在!");
				}
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("id", id);
				this.fiReceivabledetailService.invalid(map, user);//审核
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("作废成功!");
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"作废失败!失败原因：" + e.getLocalizedMessage());
			logger.error(e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
    
	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCustprop() {
		return custprop;
	}

	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}

	public Long getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getReconciliationUser() {
		return reconciliationUser;
	}

	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getFiReceivablestatementId() {
		return fiReceivablestatementId;
	}

	public void setFiReceivablestatementId(Long fiReceivablestatementId) {
		this.fiReceivablestatementId = fiReceivablestatementId;
	}
	
	

}
