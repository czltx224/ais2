package com.xbwl.cus.web;

import java.util.Date;
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
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.cus.service.ICusOverWeightService;
import com.xbwl.entity.CusOverweightManager;
import com.xbwl.entity.OprOverweight;

/**
 * 主单超重处理控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusOverWeightAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_overweight.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusOverWeightAction extends SimpleActionSupport {
	@Resource(name="cusOverWeightServiceImpl")
	private ICusOverWeightService cusOverWeightService;
	private OprOverweight oprOverweight;
	private Long ooId;
	private String aduitRemark;
	
	private String type;
	@Override
	protected Object createNewInstance() {
		return new OprOverweight();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusOverWeightService;
	}
	@Override
	public Object getModel() {
		return oprOverweight;
	}
	@Override
	public void setModel(Object obj) {
		this.oprOverweight=(OprOverweight)obj;
	}
	
	/**
	 * @return the ooId
	 */
	public Long getOoId() {
		return ooId;
	}
	/**
	 * @param ooId the ooId to set
	 */
	public void setOoId(Long ooId) {
		this.ooId = ooId;
	}
	
	/**
	 * @return the aduitRemark
	 */
	public String getAduitRemark() {
		return aduitRemark;
	}
	/**
	 * @param aduitRemark the aduitRemark to set
	 */
	public void setAduitRemark(String aduitRemark) {
		this.aduitRemark = aduitRemark;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	public String aduit(){
		try {
			//REVIEW-ACCEPT 对象应当放在euals的后面
			//FIXED LIUH
			//如果是审核操作
			if("aduit".equals(type)){
				cusOverWeightService.aduitOverWeight(ooId, aduitRemark);
			}else{
				OprOverweight oprOverweight = cusOverWeightService.get(ooId);
				User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
				//REVIEW-ACCEPT 需要做非空判断oprOverweight
				//FIXED LIUH
				if(oprOverweight==null){
					throw new ServiceException("数据异常，编号"+ooId+"对应的信息不存在");
				}
				if(oprOverweight.getAuditStatus()!=1L){
					getValidateInfo().setMsg("操作失败：只能否决未审核的超重信息！");
					getValidateInfo().setSuccess(false);
		    		addMessage("数据保存失败！");
					throw new ServiceException("只能否决未审核的超重信息！");
				}else{
					oprOverweight.setRejectName(user.get("name").toString());
					oprOverweight.setRejectTime(new Date());
					oprOverweight.setAuditStatus(3L);
					oprOverweight.setAuditRemark(aduitRemark);
					cusOverWeightService.save(oprOverweight);
					getValidateInfo().setMsg("操作成功！");
					getValidateInfo().setSuccess(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			getValidateInfo().setMsg("操作失败,失败原因："+e.getLocalizedMessage());
			getValidateInfo().setSuccess(false);
    		addMessage("数据保存失败！");
		}
		return RELOAD;
	}
}
