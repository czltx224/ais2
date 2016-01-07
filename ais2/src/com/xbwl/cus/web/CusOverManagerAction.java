package com.xbwl.cus.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.cus.service.ICusOverManagerService;
import com.xbwl.entity.CusOverweightManager;

/**
 * �����������ÿ��Ʋ�
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusOverManagerAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_overmanager.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusOverManagerAction extends SimpleActionSupport {
	@Resource(name="cusOverManagerServiceImpl")
	private ICusOverManagerService cusOverManagerService;
	private CusOverweightManager cusOverweightManager;
	@Override
	protected Object createNewInstance() {
		return new CusOverweightManager();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusOverManagerService;
	}
	@Override
	public Object getModel() {
		return cusOverweightManager;
	}
	@Override
	public void setModel(Object obj) {
		this.cusOverweightManager=(CusOverweightManager)obj;
	}
	public String save(){
		if(cusOverweightManager.getId() == null){
			List<CusOverweightManager> list=cusOverManagerService.findBy("cusId", cusOverweightManager.getCusId());
			//REVIEW ��Ҫ���ǿ��ж�
			//FIXED LIUH 
			if(list == null){
				throw new ServiceException("����ID:"+cusOverweightManager.getCusId()+"��Ӧ�Ĵ�������Ϣ������");
			}
			if(list.size()>0){
				throw new ServiceException("�Ѵ��ڴ˴�������ã�");
			}else{
				cusOverManagerService.save(cusOverweightManager);
			}
		}else{
			cusOverManagerService.save(cusOverweightManager);
		}
		return RELOAD;
	}
}
