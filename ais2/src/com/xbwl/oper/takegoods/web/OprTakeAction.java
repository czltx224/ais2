package com.xbwl.oper.takegoods.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.lob.SerializableClob;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprShuntApply;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.web.OprFaxMainAction;
import com.xbwl.oper.takegoods.service.IOprShuntApplyDetailService;
import com.xbwl.oper.takegoods.service.IOprShuntApplyService;

/**
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("oprTakeAction")
@Scope("prototype")
@Namespace("/takegoods")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/takegoods/oprTakeGoods.jsp", type = "dispatcher"),
		@Result(name = "aduit", location = "/WEB-INF/xbwl/takegoods/opr_apply_aduit.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class OprTakeAction extends SimpleActionSupport {
	private String flightNo;
	private Long cusId;
	private String startTime;
	private String endTime;
	private Long departId;
	private String flightNos;
	private String takeAddr;
	private Long isSonderzug;//是否专车
	
	private Date countTime;//车辆监控 时间
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	@Resource(name="oprShuntApplyServiceImpl")
	private IOprShuntApplyService oprShuntApplyService;
	@Resource(name="oprShuntApplyDetailServiceImpl")
	private IOprShuntApplyDetailService oprShuntApplyDetailService;
	private OprShuntApply oprShuntApply;
	@Element(value = OprShuntApply.class)
	private List<OprShuntApply> shuntApplyList;
	@Override
	protected Object createNewInstance() {
		return new OprShuntApply();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprShuntApplyService;
	}

	@Override
	public Object getModel() {
		return oprShuntApply;
	}

	@Override
	public void setModel(Object obj) {
		this.oprShuntApply=(OprShuntApply)obj;
	}

	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo
	 *            the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @return the cusId
	 */
	public Long getCusId() {
		return cusId;
	}

	/**
	 * @param cusId
	 *            the cusId to set
	 */
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}

	
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	 * @return the shuntApplyList
	 */
	public List<OprShuntApply> getShuntApplyList() {
		return shuntApplyList;
	}

	/**
	 * @param shuntApplyList the shuntApplyList to set
	 */
	public void setShuntApplyList(List<OprShuntApply> shuntApplyList) {
		this.shuntApplyList = shuntApplyList;
	}
	
	/**
	 * @return the flightNos
	 */
	public String getFlightNos() {
		return flightNos;
	}

	/**
	 * @param flightNos the flightNos to set
	 */
	public void setFlightNos(String flightNos) {
		this.flightNos = flightNos;
	}
	
	
	public Date getCountTime() {
		return countTime;
	}

	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}
	
	public Long getIsSonderzug() {
		return isSonderzug;
	}

	public void setIsSonderzug(Long isSonderzug) {
		this.isSonderzug = isSonderzug;
	}

	public String findTakeGoods() {
		this.setPageConfig();
		try {
			oprFaxInService.findTakeGoods(this.getPages(), cusId, flightNo, startTime, endTime,departId,flightNos,isSonderzug);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return LIST;
	}
	
	public String inquerySum(){
		try {
			setPageConfig();
			oprFaxInService.fnSumInfo(this.getPages(), cusId, flightNo, startTime, endTime,departId,isSonderzug);
			Map mapOld=this.getPages().getResultMap().get(0);
			Map map=new HashMap();
			map.put("ticketNum", mapOld.get("TICKETNUM")==null?"":"票:"+mapOld.get("TICKETNUM"));
			map.put("sonderzug",mapOld.get("SONDERZUG")==null?"":"专:"+mapOld.get("SONDERZUG"));
			map.put("piece", mapOld.get("PIECE")==null?"":"件:"+mapOld.get("PIECE"));
			map.put("cqWeight", mapOld.get("CQWEIGHT")==null?"":"重(kg):"+mapOld.get("CQWEIGHT"));
			map.put("cusName", "<span style='color:red'>合计:</span>");
			Struts2Utils.renderJson(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"查询失败,失败原因:" + e.getLocalizedMessage());
			return "reload";
		}
		return null;
	}
	public String goaudit(){
		return "aduit";
	}
	public String shuntApply(){
		try {
			oprShuntApplyService.shuntApply(shuntApplyList);
			getValidateInfo().setMsg("保存成功");
		} catch (Exception e) {
			addError("保存出错！", e);
		}
		return RELOAD;
	}
	public String findShuntApply(){
		this.setPageConfig();
		try {
			oprShuntApplyService.findShuntApply(this.getPages(), flightNo, takeAddr);
			List<Map>list  = getPages().getResultMap();
			for(Map mapist :list ){
				SerializableClob clob=(SerializableClob)mapist.get("DIS_CAR_NO");
				SerializableClob planTime=(SerializableClob)mapist.get("PLAN_CAR_TIME");
				mapist.put("DIS_CAR_NO", OprFaxMainAction.clob2String(clob));
				mapist.put("PLAN_CAR_TIME", OprFaxMainAction.clob2String(planTime));
			}
			getPages().setResultMap(list);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	
	public String findCarGuard(){
		this.setPageConfig();
		try {
			oprShuntApplyDetailService.findCarGuard(this.getPages(), countTime);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}
