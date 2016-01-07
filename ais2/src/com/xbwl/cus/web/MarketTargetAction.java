package com.xbwl.cus.web;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.util.StrutsUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusAnalyseService;
import com.xbwl.cus.service.IMarketTargetService;
import com.xbwl.entity.CusAnalyse;
import com.xbwl.entity.MarketingTarget;

/**
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("marketTargetAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_target.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class MarketTargetAction extends SimpleActionSupport {
	@Resource(name="marketTargetServiceImpl")
	private IMarketTargetService marketTargetService;
	private MarketingTarget marketingTarget;
	
	private Date countDate;//���ͳ��ʱ��
	private String targetType;//ָ������  ����������
	private String countArea;//ͳ������ ҵ���š��ͷ�����
	private Long isTarget;//�Ƿ���ͳ��ָ��
	private Long departId;//ҵ����ID

	@Override
	protected Object createNewInstance() {
		return new MarketingTarget();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return marketTargetService;
	}
	@Override
	public Object getModel() {
		return marketingTarget;
	}
	@Override
	public void setModel(Object obj) {
		marketingTarget=(MarketingTarget)obj;
	}
	
	/**
	 * @return the countDate
	 */
	public Date getCountDate() {
		return countDate;
	}
	/**
	 * @param countDate the countDate to set
	 */
	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}
	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}
	/**
	 * @param targetType the targetType to set
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
	/**
	 * @return the countArea
	 */
	public String getCountArea() {
		return countArea;
	}
	/**
	 * @param countArea the countArea to set
	 */
	public void setCountArea(String countArea) {
		this.countArea = countArea;
	}
	
	/**
	 * @return the isTarget
	 */
	public Long getIsTarget() {
		return isTarget;
	}
	/**
	 * @param isTarget the isTarget to set
	 */
	public void setIsTarget(Long isTarget) {
		this.isTarget = isTarget;
	}
	
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	public String findMargetTargetMsg(){
		try {
			Struts2Utils.renderJson(marketTargetService.findMargetTargetMsg(countArea, targetType, countDate,isTarget));
		} catch (Exception e) {
			addError("��ѯ����", e);
		}
		return null;
	}
	public String searchCusDepart(){
		try {
			Struts2Utils.renderJson(marketTargetService.findTargetDepartCode(departId));
		} catch (Exception e) {
			addError("��ѯ����", e);
		}
		return null;
	}
}
