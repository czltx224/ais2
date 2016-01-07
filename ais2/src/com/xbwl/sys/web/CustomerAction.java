package com.xbwl.sys.web;

import java.util.List;
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
import com.xbwl.entity.Customer;
import com.xbwl.sys.service.ICustomerService;

@Controller
@Action("customerAction")
@Scope("prototype")
@Namespace("/sys")
@Results( {
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/sys/customer.jsp", type = "dispatcher"),
		@Result(name = "tree", type = "json", params = { "root", "areaList",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"includeProperties", "*" }) })
public class CustomerAction extends SimpleActionSupport {

	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	private Customer customer;

	@Override
	protected Object createNewInstance() {
		return new Customer();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return customerService;
	}

	@Override
	public Object getModel() {
		return customer;
	}

	@Override
	public void setModel(Object obj) {
		customer = (Customer) obj;

	}
	public String getTraCustomer(){
		this.setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		String cusName = map.get("LIKES_cusName");
		try {
			customerService.gettraCustomer(this.getPages(),cusName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("��������");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("����ʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}

	/**
	 * ����ͣ��
	 */
	public String deleteStatus(){
	    try {
            List<Long> pks = getPksByIds();
            for(Long id :pks){
            	customer=customerService.get(id);
            	this.customerService.stopCustomer(customer);
            }
            getValidateInfo().setMsg("����ͣ�óɹ���");
            addMessage("����ͣ�óɹ���");
	    } catch (Exception e) {
	    	addMessage("����ɾ��ʧ�ܣ�");
	    	addError("����ɾ��ʧ�ܣ�", e);
	    }
		return RELOAD;
	}
}
