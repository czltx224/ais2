package com.xbwl.oper.fax.web;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.utils.ReflectUntil;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.fax.service.IOprFaxChangeDetailService;
import com.xbwl.oper.fax.service.IOprFaxChangeService;
import com.xbwl.oper.fax.service.IOprFaxInService;

/**
 * @author Administrator
 * @createTime 10:52:24 AM
 * @updateName Administrator
 * @updateTime 10:52:24 AM
 * 
 */
@Action("oprChangeAction")
@Scope("prototype")
@Namespace("/fax")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/oprChange.jsp", type = "dispatcher"),
		@Result(name = "search", location = "/WEB-INF/xbwl/fax/oprChangeMsg.jsp", type = "dispatcher"),
		@Result(name = "flowpath", location = "/WEB-INF/xbwl/fax/flowpath.jsp", type = "dispatcher"),
		@Result(name = "dealChange", location = "/WEB-INF/xbwl/fax/oprDealChange.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprChangeAction  extends SimpleActionSupport{
	
	@Resource(name="oprFaxChangeServiceImpl")
	private IOprFaxChangeService oprFaxChangeService;
	
	@Resource(name="oprFaxChangeDetailServiceImpl")
	private IOprFaxChangeDetailService oprFaxChangeDetailService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	private OprChangeMain oprChangeMain;
	
	private OprFaxIn ofi=new OprFaxIn();
	
	@Element(value = OprChangeDetail.class)
	private List<OprChangeDetail> changeDetailList;
	
	@Element(value = OprValueAddFee.class)
	private List<OprValueAddFee> addFeeList;
	
	private Long dno;
	private String changeType;
	
	
	@Resource(name="dozer")
	private  DozerBeanMapper dozer;
	@Override
	protected Object createNewInstance() {
		return new OprChangeMain();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return oprFaxChangeService;
	}
	@Override
	public Object getModel() {
		return oprChangeMain;
	}
	@Override
	public void setModel(Object obj) {
		oprChangeMain=(OprChangeMain)obj;
	}
	/**
	 * @return the changeDetailList
	 */
	public List<OprChangeDetail> getChangeDetailList() {
		return changeDetailList;
	}

	/**
	 * @param changeDetailList the changeDetailList to set
	 */
	public void setChangeDetailList(List<OprChangeDetail> changeDetailList) {
		this.changeDetailList = changeDetailList;
	}
	
	/**
	 * @return the ofi
	 */
	public OprFaxIn getOfi() {
		return ofi;
	}
	/**
	 * @param ofi the ofi to set
	 */
	public void setOfi(OprFaxIn ofi) {
		this.ofi = ofi;
	}
	
	/**
	 * @return the addFeeList
	 */
	public List<OprValueAddFee> getAddFeeList() {
		return addFeeList;
	}
	/**
	 * @param addFeeList the addFeeList to set
	 */
	public void setAddFeeList(List<OprValueAddFee> addFeeList) {
		this.addFeeList = addFeeList;
	}
	
	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}
	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String dealChange(){
		return "dealChange";
	}
	public String flowpath(){
		return "flowpath";
	}
	public String save(){
		try {
			OprFaxIn fax=oprFaxInService.getAndInitEntity(ofi.getDno());
			Field[] fields=OprFaxIn.class.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				String fieldName=fields[i].getName();
				Method getMethod=ReflectUntil.getInstance().getGetMethod(ofi.getClass(), fieldName);
				Object getValue=getMethod.invoke(ofi, new Object[0]);
				if(getValue!=null){
					Method setMethod=ReflectUntil.getInstance().getSetMethod(fax.getClass(), fieldName);
					setMethod.invoke(fax, getValue);
				}
			}
			for(OprChangeDetail ocd:changeDetailList){
				if(ocd.getChangeField().equals("whoCash")&&ocd.getChangePost().equals("预付")){
					String returnStr  = oprFaxChangeDetailService.decideDebt(fax, changeDetailList);
					if(!"true".equals(returnStr)){
						throw new ServiceException(returnStr);
					}
				}
			}
			oprFaxChangeService.saveOprFaxChange(addFeeList,oprChangeMain, changeDetailList,fax,changeType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return RELOAD;
	}
	
	/**
	 * 综合查询，查询更改记录
	 * @return
	 */
	public String selectQuery(){
		   try {
			   		setPageConfig();
				  	Map map = new HashMap();
					map.put("dno",dno);
					String sql =oprFaxChangeService.getSelectQuerySql(dno);
					oprFaxChangeService.getPageBySqlMap(getPages(), sql, map);
					super.getValidateInfo().setSuccess(true);
					super.getValidateInfo().setMsg("操作成功");
	        } catch (Exception e) {
	            addError("数据查询失败！", e);
	            e.printStackTrace();
				logger.error("操作出错");
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
	        }
		return LIST;
	}
	public Long getDno() {
		return dno;
	}
	public void setDno(Long dno) {
		this.dno = dno;
	}
	public String gotoSearch(){
		return "search";
	}
}
