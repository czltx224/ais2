package com.xbwl.oper.stock.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.lob.SerializableClob;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.fax.web.OprFaxMainAction;
import com.xbwl.oper.stock.service.IOprPrewiredDetailService;
import com.xbwl.oper.stock.vo.OprPrewiredDetailVo;
import com.xbwl.rbac.Service.IDepartService;

/**
 * author shuw
 * time 2011-7-19 上午11:26:12
 */



@Controller
@Action("oprPrewiredDetailAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_prewiredDetail.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class OprPrewiredDetailAction extends SimpleActionSupport {
	
	@Resource(name="oprPrewiredDetailServiceImpl")
	private IOprPrewiredDetailService oprPrewiredDetailService;

	@Value("${request_stage}")
	private String   requestStage; 
	

		private Date  createTime_start ;
		private Date createTime_end ;
		private String distributionMode;
		private String goWhere;
		private String area;
		private String peopleName;
		private Long totalPiece;
	    private Long totalDno;
	    private Double totalWeight;
	    private Long bussDepart;
	    private String orderFields;
	    
	private OprPrewiredDetail oprPrewiredDetail;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Override
	protected Object createNewInstance() {
		return new OprPrewiredDetail();
	}

	@Override
	public Map getContextMap(){
		
		Map map=new HashMap();
		String s="";
		try {
			 s = requestStage;
			if("".equals(requestStage)||requestStage==null){
				return null;
			}else{
				map.put("requetStage",s);
			}
			} catch (Exception e) {
				   e.printStackTrace();
			}
		return map;
	}

	@Override
	protected IBaseService getManager() {
		return oprPrewiredDetailService;
	}

	@Override
	public Object getModel() {
		return oprPrewiredDetail;
	}

	@Override
	public void setModel(Object obj) {
		oprPrewiredDetail = (OprPrewiredDetail)obj;
	}

	/**
	 * 预配保存方法
	 * @return
	 */
	public String saveOprPrewired(){
		try {
			List<Long> list = getPksByIds();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long departId = Long.parseLong(user.get("bussDepart")+"");
			SysDepart depart= departService.getAndInitEntity(departId);
		
			String s = requestStage;
		    OprPrewired  oprPrewired = new OprPrewired();
		    oprPrewired.setAutostowMode(distributionMode);
		    oprPrewired.setPiece(totalPiece);
		    oprPrewired.setWeight(totalWeight);
		    oprPrewired.setVotes(totalDno);
		    oprPrewired.setDepartId(departId);
		    oprPrewired.setDepartName(depart.getDepartName());
		    oprPrewired.setToWhere(peopleName);
		    oprPrewired.setStatus(1l);
			Long oprId  = oprPrewiredDetailService.saveOprPrewiredByids(oprPrewired, list,s,orderFields);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setValue(oprId+"");
			super.getValidateInfo().setMsg("保存成功");
		} catch (Exception e) {
			   e.printStackTrace();
			   logger.error("操作出错");
			   super.getValidateInfo().setSuccess(false);
			   super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			   addError("保存出错", e);
		}
		return "reload";
	}
	
	
	
	/**
	 * 预配汇总统计方法
	 * @return
	 */
	public String ajaxTotalSum(){
		try {
			setPageConfig();
			String ids = getIds().trim();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long departId = Long.parseLong(user.get("bussDepart")+"");
			String sql =  oprPrewiredDetailService.getAjaxTotalSum(ids);
			Map map=new HashMap();
			map.put("departId", departId);
			oprPrewiredDetailService.getPageBySqlMap(getPages(),sql,map);
			Page page = getPages();
			OprPrewiredDetailVo oprVo = new OprPrewiredDetailVo();
			List <Map>list =page.getResultMap();
			if(list.size()!=0){
				for(Map totalMap : list ){
					oprVo.setGoodsStatus("汇总：");
					oprVo.setDno(totalMap.get("T0_D_NO")==null?0:Long.parseLong(totalMap.get("T0_D_NO")+""));
					oprVo.setPiece(totalMap.get("T0_PIECE") ==null ?0l:Long.parseLong(totalMap.get("T0_PIECE")+""));
					oprVo.setRealPiece(totalMap.get("T1_PIECE") ==null ?0l:Long.parseLong(totalMap.get("T1_PIECE")+""));
					oprVo.setCusWeight(totalMap.get("T0_CUS_WEIGHT")==null?0.0:Double.parseDouble(totalMap.get("T0_CUS_WEIGHT")+""));
				}
			}else{
					oprVo.setGoodsStatus("汇总：");
					oprVo.setDno(0l);
					oprVo.setPiece(0l);
					oprVo.setRealPiece(0l);
					oprVo.setCusWeight(0.0);
			}
			
			Struts2Utils.renderJson(oprVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * 货物预配查询
	 * @return
	 */
	public String selecList() {
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
			Map<String, String> filterMap = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			filterMap.put("f_sonderzug", "0");
			filterMap.put("f_endDepartId", bussDepartId+"");
			filterMap.put("bussDepart", bussDepartId+"");
			filterMap.put("requestStage", this.requestStage);
			if("专车".equals(distributionMode)){
				filterMap.put("f_sonderzug", "1");
				filterMap.put("f_inDepartId", bussDepartId+"");
			}else if("市内送货".equals(distributionMode)){
				filterMap.put("f_takeMode", distributionMode);
				filterMap.put("f_distributionMode", "新邦");
			}else if("部门交接".equals(this.distributionMode)){
				filterMap.put("f_takeMode","");
				filterMap.put("f_sonderzug", "");
				filterMap.put("f_distributionMode","");
				filterMap.put("f_endDepartId","");
				filterMap.put("distributionMode",distributionMode);
			}else{
				filterMap.put("f_distributionMode", distributionMode);
			}
			sql =oprPrewiredDetailService.getListSqlAll(filterMap);
			this.oprPrewiredDetailService.getPageBySqlMap(this.getPages(), sql, filterMap);
			List<Map >list  =getPages().getResultMap();
			for(Map mapist: list ){
				mapist.put("T0_DISTRIBUTION_MODE",this.distributionMode);
				SerializableClob clob=(SerializableClob)mapist.get("T4_REQUEST");
				if(clob!=null){
					mapist.put("T4_REQUEST", OprFaxMainAction.clob2String(clob));
				}else{
					mapist.put("T4_REQUEST", "");
				}
			}
			 getPages().setResultMap(list);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		}catch(Exception e){
			addError("查询失败！", e);
			
		}
		return this.LIST;
	}
	public Date getCreateTime_start() {
		return createTime_start;
	}

	public void setCreateTime_start(Date createTime_start) {
		this.createTime_start = createTime_start;
	}

	public Date getCreateTime_end() {
		return createTime_end;
	}

	public void setCreateTime_end(Date createTime_end) {
		this.createTime_end = createTime_end;
	}

	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getGoWhere() {
		return goWhere;
	}

	public void setGoWhere(String goWhere) {
		this.goWhere = goWhere;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public Long getTotalPiece() {
		return totalPiece;
	}

	public void setTotalPiece(Long totalPiece) {
		this.totalPiece = totalPiece;
	}

	public Long getTotalDno() {
		return totalDno;
	}

	public void setTotalDno(Long totalDno) {
		this.totalDno = totalDno;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Long getBussDepart() {
		return bussDepart;
	}

	public void setBussDepart(Long bussDepart) {
		this.bussDepart = bussDepart;
	}

	public String getOrderFields() {
		return orderFields;
	}

	public void setOrderFields(String orderFields) {
		this.orderFields = orderFields;
	}
	
	/**
	 * 发车确认交接单查询
	 * @return
	 */
	public String findOutCarList(){
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("checkItems", getCheckItems());
			map.put("itemsValue", getItemsValue());
			sql=this.oprPrewiredDetailService.findOutCarList(map);
			this.oprPrewiredDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("发车确认交接单查询失败！", e);
		}
		return this.LIST;
	}

}
