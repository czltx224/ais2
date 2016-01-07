package com.xbwl.finance.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiBusinessFeePrice;
import com.xbwl.entity.FiDeliverycost;
import com.xbwl.finance.Service.IFiBusinessFeePriceService;

/**
 * author shuw
 * time Dec 26, 2011 10:20:21 AM
 */
@Controller
@Action("fiBusinessFeePriceAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_business_fee_price.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }) })
public class FiBusinessFeePriceAction  extends SimpleActionSupport{
	
	private FiBusinessFeePrice fiBusinessFeePrice;
	
	@Resource(name="fiBusinessFeePriceServiceImpl")
	private IFiBusinessFeePriceService fiBusinessFeePriceService;

	 @Element(value = FiBusinessFeePrice.class)
	private List<FiBusinessFeePrice> aa;  //ǰ̨�ύ�����ļ���(�п���ֻ��id��ts)

	 /**
		 * ���ҵ���Э��� 
		 * @return
		 */
		public String audit() {
			if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
			    try {
		            fiBusinessFeePriceService.audit(aa);
		            getValidateInfo().setMsg("������˳ɹ���");
		            addMessage("������˳ɹ���");
			    } catch (Exception e) {
			    	addMessage("������˳ɹ���");
			    	addError("�������ʧ�ܣ�", e);
			    }
			}else{
				getValidateInfo().setSuccess(false);
				getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}
			return RELOAD;
		}
	 
	/**
	 * ɾ��ҵ���Э���(״̬ɾ��) 
	 * @return
	 */
	public String deleteByStatus() {
		if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
		    try {
	            List<Long> pks = getPksByIds();
	            fiBusinessFeePriceService.deleteByStatus(pks);
	            getValidateInfo().setMsg("����ɾ���ɹ���");
	            addMessage("����ɾ���ɹ���");
		    } catch (Exception e) {
		    	addMessage("����ɾ���ɹ���");
		    	addError("����ɾ��ʧ�ܣ�", e);
		    }
		}else{
			getValidateInfo().setSuccess(false);
			getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
		}
		return RELOAD;
	}
	
	protected Object createNewInstance() {
		return new FiBusinessFeePrice();
	}

	public Map getContextMap() {
		return null;
	}

	protected IBaseService getManager() {
		return fiBusinessFeePriceService;
	}

	public Object getModel() {
		return fiBusinessFeePrice;
	}

	public void setModel(Object obj) {
		fiBusinessFeePrice=(FiBusinessFeePrice)obj;
	}
	 
	 public List<FiBusinessFeePrice> getAa() {
		return aa;
	}

	public void setAa(List<FiBusinessFeePrice> aa) {
		this.aa = aa;
	}

}
