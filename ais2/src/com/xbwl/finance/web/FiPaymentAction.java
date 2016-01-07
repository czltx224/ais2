package com.xbwl.finance.web;

import java.util.HashMap;
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

import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiPayment;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.IFiPaymentService;

@Controller
@Action("fiPaymentAcion")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fiPayment.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class FiPaymentAction extends SimpleActionSupport {

	@Resource(name = "fiPaymentServiceImpl")
	private IFiPaymentService fiPaymentService;
	private FiPayment fiPayment;

	private String ids;
	
	private String departId1;

	@Override
	protected Object createNewInstance() {
		return new FiPayment();
	}

	@Override
	public Map getContextMap() {
		Map map=new HashMap();
		map.put("audit", Struts2Utils.getParameter("audit"));//审核
		map.put("departId1",departId1);
		return map;
	}

	@Override
	protected IBaseService getManager() {
		return fiPaymentService;
	}

	@Override
	public Object getModel() {
		return fiPayment;
	}

	@Override
	public void setModel(Object obj) {
		fiPayment = (FiPayment) obj;
	}

	/**
	 * 
	 * @Title: searchReceiving
	 * @Description: TODO(根据选择id，找到ID对应所有配送单应收单，返回Page)
	 * @param ids格式1,2,3,
	 */
	public String searchReceiving() throws Exception {
		try {
			this.setPageConfig();
			this.getPages().setPageSize(1000);
			this.getPages().setLimit(1000);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//单据小类

			Map map1=this.fiPaymentService.searchReceiving(map);
			Struts2Utils.renderJson(map1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return null;
	}

	/**
	 * 
	 * @Title: searchReceiving
	 * @Description: TODO(根据选择id，找到ID对应所有配送单付收单，返回Page)
	 * @param ids格式1,2,3,
	 */
	public String searchPayment() throws Exception {
		try {
			this.setPageConfig();
			this.getPages().setPageSize(1000);
			this.getPages().setLimit(1000);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//单据小类
			this.fiPaymentService.searchPayment(this.getPages(), map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	
	/**
	 * 
	* @Description: TODO(收款确认：根据选择id，保存收款单) 
	 */
	public String saveReceiving() throws Exception{
		try{
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));
			map.put("penyJenis",ServletActionContext.getRequest().getParameter("penyJenis"));//收款方式
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//单据小类
			map.put("selectIds", ServletActionContext.getRequest().getParameter("selectIds"));//收款账号ID
			map.put("settlementAmount", ServletActionContext.getRequest().getParameter("settlementAmount"));//实付金额
			
			//支票信息
			map.put("checkcustomerId", ServletActionContext.getRequest().getParameter("checkcustomerId"));
			map.put("checkNo", ServletActionContext.getRequest().getParameter("checkNo"));
			map.put("checkamount", ServletActionContext.getRequest().getParameter("checkamount"));
			map.put("checkDate", ServletActionContext.getRequest().getParameter("checkDate"));
			map.put("checkUser", ServletActionContext.getRequest().getParameter("checkUser"));
			map.put("checkRemark", ServletActionContext.getRequest().getParameter("checkRemark"));
			
			if (map.get("penyJenis").toString().equals("收付对冲")){
				String[] ids=map.get("ids").toString().split(",");
				if(ids.length>1){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("收款失败，收付对账一次只能选择一个收款单号。");
					return "reload";
				}
			}
			this.fiPaymentService.saveReceiving(map,user);
    		this.getValidateInfo().setMsg("收款成功！");
    		addMessage("收款成功！");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("收款确认失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
			
		}
		return "reload";
	}

	/**
	 * 
	* @Description: TODO(付款确认：根据选择id，保存付款单) 
	 */
	public String savePayment() throws Exception{
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));//选中的付款单ID
			map.put("penyJenis",ServletActionContext.getRequest().getParameter("penyJenis"));//收款方式
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//单据小类
			map.put("selectIds", ServletActionContext.getRequest().getParameter("selectIds"));//收款账号ID
			map.put("settlementAmount", ServletActionContext.getRequest().getParameter("settlementAmount"));//本次实收款金额
			this.fiPaymentService.savePayment(map);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("付款成功!");
			this.addMessage("付款成功!");
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("付款确认失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * 
	* @Description: TODO(委托收付款：根据选择id，保存委托收付款单) 
	 */
	public String saveEntrust() throws Exception{
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));//选中的付款单ID
			map.put("documentsSmalltype",ServletActionContext.getRequest().getParameter("documentsSmalltype"));//单据小类
			this.fiPaymentService.saveEntrust(map,this.fiPayment);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("委托成功!");
			this.addMessage("委托成功!");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("委托失败，失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * 在saveEntrust()前执行二次绑定.
	 */
	public void prepareSaveEntrust() throws Exception {
      super.prepareModel();
	}
	
	//挂账
	public String saveLosses() throws Exception{
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("ids",ServletActionContext.getRequest().getParameter("ids"));//选中的付款单ID
			map.put("customerId",ServletActionContext.getRequest().getParameter("customerId"));//挂账客商id
			map.put("departId",ServletActionContext.getRequest().getParameter("departId"));//挂账客商部门
			map.put("remark",ServletActionContext.getRequest().getParameter("remark"));//备注
			
			this.fiPaymentService.saveLosses(map);
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("挂账成功!");
			this.addMessage("挂账成功!");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("挂账失败，失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
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
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				if("null".equals(id)||"".equals(id)||id==null){
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("单据不存在!");
				}
				this.fiPaymentService.audit(id, user);//审核
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
	 * 审核
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
				this.fiPaymentService.revocationAudit(id, user);//撤销审核
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
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getDepartId1() {
		return departId1;
	}

	public void setDepartId1(String departId1) {
		this.departId1 = departId1;
	}
	

}
