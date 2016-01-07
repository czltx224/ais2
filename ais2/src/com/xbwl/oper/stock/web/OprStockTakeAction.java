package com.xbwl.oper.stock.web;

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

import bsh.This;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprStocktake;
import com.xbwl.oper.stock.service.IOprStockTakeService;

@Controller
@Action("oprStocktakeAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }),
				@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" })
		}
)
public class OprStockTakeAction extends SimpleActionSupport {

	@Resource(name = "oprStockTakeServiceImpl")
	private IOprStockTakeService oprStockTakeService;
	
	private OprStocktake oprStocktake;
	
	@Override
	protected Object createNewInstance() {
		return new OprStocktake();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprStockTakeService;
	}

	@Override
	public Object getModel() {
		return this.oprStocktake;
	}

	@Override
	public void setModel(Object obj) {
		oprStocktake=(OprStocktake)obj;
	}
	
	/**
	 * ��дSave����
	 * */
	
	public String  save(){
		try {
	    	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
					User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
					Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
					String s =oprStockTakeService.saveOprStockTakeQueryOprStock(oprStocktake,bussDepartId);
					if("stop".equals(s)){
			     		getValidateInfo().setMsg("��������Ҫ��ӡ��");
		        		addMessage("û�л�����Ҫ�̵㣡");
		        		return RELOAD;
					}else{
						//��ѯ����ӡ������
						getValidateInfo().setMsg(s);
						addMessage("���ݴ�ӡ�ɹ���");
					}
	    	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
    		getValidateInfo().setMsg("���ݴ�ӡʧ�ܣ�");
            addError("���ݴ�ӡʧ�ܣ�", e);
				e.printStackTrace();
		}
		return RELOAD;
	}
}
