package com.xbwl.cus.web;

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
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusServiceService;
import com.xbwl.cus.vo.CusServiceIdVo;
import com.xbwl.entity.CusService;

/**
 * �ͻ�������Ʋ������
 * author CaoZhili time Oct 10, 2011 4:37:54 PM
 */
@Controller
@Action("cusServiceAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_distributionCustomer.jsp", type = "dispatcher"),
		@Result(name = "toServiceDesignate", location = "/WEB-INF/xbwl/cus/cus_serviceDesignateCustomer.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class CusServiceAction extends SimpleActionSupport {

	@Resource(name = "cusServiceServiceImpl")
	private ICusServiceService cusServiceService;

	private CusService cusService;

	private String cusRecordIds;
	private Long userId;
	private Boolean flag;
	private String userCode;
	
	@Element(value = CusServiceIdVo.class)
	private List<CusServiceIdVo> cusServiceList;
	
	@Override
	protected Object createNewInstance() {
		return new CusService();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.cusServiceService;
	}

	@Override
	public Object getModel() {
		return this.cusService;
	}

	@Override
	public void setModel(Object obj) {
		this.cusService = (CusService) obj;
	}

	public String getCusRecordIds() {
		return cusRecordIds;
	}

	public void setCusRecordIds(String cusRecordIds) {
		this.cusRecordIds = cusRecordIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<CusServiceIdVo> getCusServiceList() {
		return cusServiceList;
	}

	public void setCusServiceList(List<CusServiceIdVo> cusServiceList) {
		this.cusServiceList = cusServiceList;
	}

	/**
	 * ����ͻ����淽��
	 * 
	 * @return
	 */
	public String saveCusService() {

		try {
			this.cusServiceService.saveCusService(this.cusRecordIds.split(","),
					this.userId,this.flag);
		} catch (Exception e) {
			addError("����ͻ�����ʧ�ܣ�", e);
		}

		return this.RELOAD;
	}

	/**
	 * ����ͻ��Ƴ�����
	 * 
	 * @return
	 */
	public String moveCusService() {

		try {
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
			Long bussDepart = Long.valueOf(user.get("bussDepart")+"");
			this.cusServiceService.moveCusService(this.cusRecordIds.split(","),bussDepart);
		} catch (Exception e) {
			addError("����ͻ��Ƴ�ʧ�ܣ�", e);
		}

		return this.RELOAD;
	}
	
	/**
	 * ��ת���ͷ�Աָ�ɿͻ�ҳ��
	 * @return
	 */
	public String toServiceDesignate(){
		
		return "toServiceDesignate";
	}
	
	/**
	 * �ͷ�Աָ�ɿͻ���ѯ����
	 * @return
	 */
	public String findServiceDesignate(){
		String sql=null;
		try{
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
			//���÷�ҳ����
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("t_userCode", user.get("userCode")+"");
			sql=this.cusServiceService.findServiceDesignate(map);
			this.cusServiceService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("�ͷ�Աָ�ɿͻ���ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ����ָ�ɿͷ�Ա
	 * @return
	 */
	public String saveServiceDesignate(){
		try{
			Long cusRecordId = Long.valueOf(ServletActionContext.getRequest().getParameter("cusRecordId"));
			this.cusServiceService.saveServiceDesignate(cusRecordId,this.userCode);
			this.getValidateInfo().setMsg("���ݱ���ɹ���");
    		addMessage("���ݱ���ɹ���");
		}catch(Exception e){
			addError("����ָ�ɿͷ�Աʧ�ܣ�", e);
		}
		return this.RELOAD;
	}
	
	/**
	 * ɾ��ָ�ɿͷ�Ա
	 * @return
	 */
	public String deleteServiceDesignate(){
		try{
			for (int i = 0; i < this.cusServiceList.size(); i++) {
				Long cusRecordId = this.cusServiceList.get(i).getCusRecordId();
				String vuserCode = this.cusServiceList.get(i).getUserCode();
				this.cusServiceService.deleteServiceDesignate(cusRecordId,vuserCode);
			}
			addMessage("����ɾ���ɹ���");
		}catch(Exception e){
			addError("ɾ��ָ�ɿͷ�Աʧ�ܣ�", e);
		}
		return this.RELOAD;
	}
}
