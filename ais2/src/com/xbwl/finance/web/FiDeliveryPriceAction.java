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

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiDeliveryPrice;
import com.xbwl.finance.Service.IFiDeliveryPriceService;

/**
 * author shuw
 * time Oct 10, 2011 4:02:28 PM
 */
/**
 * @author Administrator
 *
 */
@Controller
@Action("fiDeliveryPriceAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Delivery_Price.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class FiDeliveryPriceAction extends SimpleActionSupport {

	private FiDeliveryPrice fiDeliveryPrice;
	
	@Resource(name = "fiDeliveryPriceServiceImpl")
	private IFiDeliveryPriceService fiDeliveryPriceService;
	
    /**
     * ����������ʵ��
     * @return
     * @throws Exception
     */
    @Override
    public String save() throws Exception {
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliveryPriceService.save(fiDeliveryPrice);
        		getValidateInfo().setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }

        return RELOAD;
    }

    
    /**
     * ɾ�����Э���(�ı�״̬)
     * @return
     */
    public String deleteOfStatus(){
    	try {
            List pks = getPksByIds();
            fiDeliveryPriceService.deleteOfStatus(pks);
            addMessage("����ɾ���ɹ���");
            getValidateInfo().setSuccess(true);
            getValidateInfo().setMsg("����ɾ���ɹ���");
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("����ɾ��ʧ�ܣ�");
            addError("����ɾ��ʧ�ܣ�", e);
        }
        return RELOAD;
    }
    
    
	@Override
	protected Object createNewInstance() {
		return new FiDeliveryPrice();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiDeliveryPriceService;
	}

	@Override
	public Object getModel() {
		return fiDeliveryPrice;
	}

	@Override
	public void setModel(Object obj) {
		fiDeliveryPrice = (FiDeliveryPrice)obj; 
	}
		
}
