package com.xbwl.cus.web;

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
import com.xbwl.cus.service.ICusAnalyseService;
import com.xbwl.entity.CusAnalyse;

/**
 * 客服分析控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusAnalyseAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_cusanalyse.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusAnalyseAction extends SimpleActionSupport {
	@Resource(name="cusAnalyseServiceImpl")
	private ICusAnalyseService cusAnalyseService;
	private CusAnalyse cusAnalyse;
	private Date rankDate;//月度统计时间
	
	private Date countDate;//年度统计时间
	private Long departId;//权限部门
	private String countType;//统计类型
	
	private String cusType;//客户类型
	private Date beforeDate;//比较开始时间
	private Date afterDate;//比较结束时间
	
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private String dateType;//日期类型
	
	private String countRange;
	private String startCount;
	private String endCount;
	private Long cusRecordId;
	
	private String customerService;//客服员
	@Override
	protected Object createNewInstance() {
		return new CusAnalyse();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusAnalyseService;
	}
	@Override
	public Object getModel() {
		return cusAnalyse;
	}
	@Override
	public void setModel(Object obj) {
		cusAnalyse=(CusAnalyse)obj;
	}
	
	/**
	 * @return the rankDate
	 */
	public Date getRankDate() {
		return rankDate;
	}
	/**
	 * @param rankDate the rankDate to set
	 */
	public void setRankDate(Date rankDate) {
		this.rankDate = rankDate;
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
	 * @return the departId
	 */
	public Long getDepartId() {
		return departId;
	}
	/**
	 * @param departId the departId to set
	 */
	public void setDepartId(Long departId) {
		this.departId = departId;
	}
	/**
	 * @return the countType
	 */
	public String getCountType() {
		return countType;
	}
	/**
	 * @param countType the countType to set
	 */
	public void setCountType(String countType) {
		this.countType = countType;
	}
	
	/**
	 * @return the cusType
	 */
	public String getCusType() {
		return cusType;
	}
	/**
	 * @param cusType the cusType to set
	 */
	public void setCusType(String cusType) {
		this.cusType = cusType;
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
	 * @return the customerService
	 */
	public String getCustomerService() {
		return customerService;
	}
	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
	public String getCountRange() {
		return countRange;
	}
	public void setCountRange(String countRange) {
		this.countRange = countRange;
	}
	public String getStartCount() {
		return startCount;
	}
	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}
	public String getEndCount() {
		return endCount;
	}
	public void setEndCount(String endCount) {
		this.endCount = endCount;
	}
	
	public Long getCusRecordId() {
		return cusRecordId;
	}
	public void setCusRecordId(Long cusRecordId) {
		this.cusRecordId = cusRecordId;
	}
	/**
	 * 客户等级信息统计
	 * @return
	 */
	public String findCusRankMsg(){
		this.setPageConfig();
		try {
			cusAnalyseService.findCusRankMsg(this.getPages(),startDate,endDate,dateType);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	/**
	 * 年度客户信息统计
	 * @return
	 */
	public String findCusRankMonMsg(){
		this.setPageConfig();
		try {
			cusAnalyseService.findCusMonRankMsg(this.getPages(),countType, startCount,endCount, departId,countRange,cusRecordId);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	/**
	 * 客户等级比较(同比/环比)
	 * @return
	 */
	public String findCusRankThan(){
		this.setPageConfig();
		try {
			cusAnalyseService.findCusRankThan(this.getPages(),countType, beforeDate, afterDate, cusType);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	/**
	 * 客服员客户对比
	 * @author LiuHao
	 * @time Apr 6, 2012 2:59:20 PM 
	 * @return
	 */
	public String findCusVsCus(){
		this.setPageConfig();
		try {
			cusAnalyseService.findCusVsCus(this.getPages(),countType, beforeDate, afterDate, customerService);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
