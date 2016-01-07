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

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.cus.service.ICusDemandService;
import com.xbwl.cus.service.ICusLinkManService;
import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusLinkman;
import com.xbwl.entity.CusRecord;

/**
 * �ͻ���ϵ�˿��Ʋ�
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusLinkManAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_linkman.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusLinkManAction extends SimpleActionSupport {
	@Resource(name="cusLinkManServiceImpl")
	private ICusLinkManService cusLinkManService;
	@Resource(name="cusRecordServiceImpl")
	private ICusRecordService cusRecordService;
	private CusLinkman cusLinkman;
	@Resource(name="cusDemandServiceImpl")
	private ICusDemandService cusDemandService;
	
	@Override
	protected Object createNewInstance() {
		return new CusLinkman();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusLinkManService;
	}
	@Override
	public Object getModel() {
		return cusLinkman;
	}
	@Override
	public void setModel(Object obj) {
		this.cusLinkman=(CusLinkman)obj;
	}
	public String delete(){
		try {
			List pks = getPksByIds();
			cusLinkManService.delCusLinkman(pks);
			this.getValidateInfo().setMsg("���ϳɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			getValidateInfo().setMsg("����ʧ��");
			addError("����ʧ��", e);
		}
		return RELOAD;
	}
	public String save(){
		//����ÿͻ�������
		List<CusDemand> list=cusDemandService.findBy("cusRecordId", cusLinkman.getCusRecordId());
		//REVIEW-ACCEPT ��Ҫ���ǿ��ж�
		//FIXED LIUH
		if(list!=null && list.size()>0){
			CusRecord cus=cusRecordService.get(cusLinkman.getCusRecordId());
			cus.setDevelopLevel("Ŀ��ͻ�");
			cusRecordService.save(cus);
		}
		cusLinkman.setStatus(1L);
		cusLinkManService.save(cusLinkman);
		return RELOAD;
	}
}
