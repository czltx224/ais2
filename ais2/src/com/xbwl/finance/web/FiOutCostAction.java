package com.xbwl.finance.web;

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
import com.xbwl.entity.FiOutcost;
import com.xbwl.finance.Service.IFiOutCostService;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 *@author LiuHao
 *@time Aug 29, 2011 5:53:23 PM
 */
@Controller
@Action("fiOutCostAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fioutcost.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })	
public class FiOutCostAction extends SimpleActionSupport {
	@Resource(name="fiOutCostServiceImpl")
	private IFiOutCostService fiOutCostService;

	private FiOutcost fiOutCost;
	private Long dno;
	private String ts;
	private Long batchNo;  //批次号
	
	@Element(value = FiOutcost.class)
	private List<FiOutcost> aa;
	
	/**
	 * 会计审核
	 * @return
	 */
	public String accountAduit(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
    			String s =fiOutCostService.outCostAduit(aa,batchNo);
        		getValidateInfo().setMsg( s );
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
	 * 取消审核
	 * @return
	 */
	public String qxFiAudit(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
    			Long id = getId();
    			String s =fiOutCostService.qxFiAduit(id,ts);
        		getValidateInfo().setMsg( "保存成功" );
        		getValidateInfo().setSuccess(true);
        		addMessage( "保存成功" );
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
	 * 删除外发成本
	 * @return
	 */
	public String delData(){
		try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
    			Long id = getId();
    			String s =fiOutCostService.delOutcostData(id,ts);
        		getValidateInfo().setMsg( "保存成功" );
        		getValidateInfo().setSuccess(true);
        		addMessage( "保存成功" );
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
	 * 返货提示信息
	 * @return
	 */
	public String returnCheck(){
		try {
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
			String info=fiOutCostService.returnGoodsPrompt(dno, bussDepartId);
			Struts2Utils.renderJson(info);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
	}

	/**
	 * 撤销审核信息提示
	 * @return
	 */
	public String qxAmountCheck() {
		try {
			String info=fiOutCostService.qxAmountCheck(getId());
			Struts2Utils.renderJson(info);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 审核时信息提未 Ajax
	 * @return
	 */
	public String aduitAmountCheck() {
		try {
			Map map=fiOutCostService.aduitAmountCheck(aa);
			Struts2Utils.renderJson(map);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return null;
	}
	@Override
	protected Object createNewInstance() {
		return new FiOutcost();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiOutCostService;
	}

	@Override
	public Object getModel() {
		return fiOutCost;
	}

	@Override
	public void setModel(Object obj) {
		fiOutCost=(FiOutcost)obj;

	}
	
	/**
	 * @return the dno
	 */
	public Long getDno() {
		return dno;
	}

	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public List<FiOutcost> getAa() {
		return aa;
	}

	public void setAa(List<FiOutcost> aa) {
		this.aa = aa;
	}

	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

}
