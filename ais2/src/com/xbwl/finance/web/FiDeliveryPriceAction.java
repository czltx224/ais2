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
     * 新增、更新实体
     * @return
     * @throws Exception
     */
    @Override
    public String save() throws Exception {
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), getModel(), getContextMap())){
        		fiDeliveryPriceService.save(fiDeliveryPrice);
        		getValidateInfo().setMsg("数据保存成功！");
        		addMessage("数据保存成功！");
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
        }

        return RELOAD;
    }

    
    /**
     * 删除提货协议价(改变状态)
     * @return
     */
    public String deleteOfStatus(){
    	try {
            List pks = getPksByIds();
            fiDeliveryPriceService.deleteOfStatus(pks);
            addMessage("数据删除成功！");
            getValidateInfo().setSuccess(true);
            getValidateInfo().setMsg("数据删除成功！");
        } catch (Exception e) {
        	getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据删除失败！");
            addError("数据删除失败！", e);
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
