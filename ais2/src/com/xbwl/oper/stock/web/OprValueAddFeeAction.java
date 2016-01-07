package com.xbwl.oper.stock.web;

import java.util.Date;
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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.stock.service.IOprValueAddFeeService;

/**
 * author LiuHao
 * 
 */
@Controller
@Action("oprValueAddFeeAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "analyse", location = "/WEB-INF/xbwl/reports/cus/report_addfeeanalyse.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) })
public class OprValueAddFeeAction extends SimpleActionSupport{
	@Resource(name = "oprValueAddFeeServiceImpl")
	private IOprValueAddFeeService oprValueAddFeeService;
	private OprValueAddFee oprValueAddFee;
	private Date msgDate;
	private String dateType;
	private String departCode;
	private Date afterDate;
	private Date beforeDate;
	@Override
	protected Object createNewInstance() {
		return new OprValueAddFee() ;
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return oprValueAddFeeService;
	}

	@Override
	public Object getModel() {
		return oprValueAddFee;
	}
	@Override
	public void setModel(Object obj) {
		oprValueAddFee=(OprValueAddFee)obj;
	}
	public String analyse(){
		return "analyse";
	}
	
	/**
	 * @return the msgDate
	 */
	public Date getMsgDate() {
		return msgDate;
	}
	/**
	 * @param msgDate the msgDate to set
	 */
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	
	/**
	 * @return the dateType
	 */
	public String getDateType() {
		return dateType;
	}
	/**
	 * @param dateType the dateType to set
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	/**
	 * @return the departCode
	 */
	public String getDepartCode() {
		return departCode;
	}
	/**
	 * @param departCode the departCode to set
	 */
	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	
	/**
	 * @return the afterDate
	 */
	public Date getAfterDate() {
		return afterDate;
	}
	/**
	 * @param afterDate the afterDate to set
	 */
	public void setAfterDate(Date afterDate) {
		this.afterDate = afterDate;
	}
	/**
	 * @return the beforeDate
	 */
	public Date getBeforeDate() {
		return beforeDate;
	}
	/**
	 * @param beforeDate the beforeDate to set
	 */
	public void setBeforeDate(Date beforeDate) {
		this.beforeDate = beforeDate;
	}
	/**
	 * 增值服务费月、日 统计
	 * @return
	 */
	public String findAddFeeMsg(){
		try {
			Struts2Utils.renderJson(oprValueAddFeeService.findAddFeeMsg(msgDate,dateType,departCode));
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
	/**
	 * 增值服务费按部门统计
	 * @return
	 */
	public String findDepartFeeMsg(){
		try {
			Struts2Utils.renderJson(oprValueAddFeeService.findDepartFeeMsg(msgDate,dateType));
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
	/**
	 * 增值服务费同比、环比
	 * @return
	 */
	public String findFeeThen(){
		try {
			Struts2Utils.renderJson(oprValueAddFeeService.findFeeThan(beforeDate, afterDate));
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return null;
	}
	
}
