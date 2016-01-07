package com.xbwl.oper.stock.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.stock.service.IOprReturnGoodsService;
import com.xbwl.rbac.Service.IDepartService;

/**
 * @author CaoZhili
 * time Jul 30, 2011 10:47:55 AM
 * 
 * 返货管理控制层操作类
 */
@Controller
@Action("oprReturnGoodsAction")
@Scope("prototype")
@Namespace("/instock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/instock/opr_returnGoods.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprReturnGoodsAction extends SimpleActionSupport {

	@Resource(name="oprReturnGoodsServiceImpl")
	private IOprReturnGoodsService oprReturnGoodsService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	private OprReturnGoods oprReturnGoods;
	
	@Override
	protected Object createNewInstance() {

		return new OprReturnGoods();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprReturnGoodsService;
	}

	@Override
	public Object getModel() {

		return this.oprReturnGoods;
	}

	@Override
	public void setModel(Object obj) {

		this.oprReturnGoods=(OprReturnGoods)obj;
	}
	
	/**
	 * 返货入库
	 * @return
	 */
	public String saveEnterStock(){
		Long returnStatus=new Long(2);//异常出库
		
		String ids=ServletActionContext.getRequest().getParameter("ids");
		try{
			this.oprReturnGoodsService.saveEnterStockService(ids,returnStatus);
		}catch(Exception e){
			addError(e.getLocalizedMessage(), e);
			
		}
		return "reload";
	}
	
	/**
	 * 返货登记
	 * @return reload
	 */
	public String saveRegistration(){
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart").toString());
		SysDepart sysdpt= this.departService.getAndInitEntity(bussDepartId);
		
		String strconsigneefee=ServletActionContext.getRequest().getParameter("consigneeFee");
		String strpaymentCollection=ServletActionContext.getRequest().getParameter("paymentCollection");
		String strreturnCost=ServletActionContext.getRequest().getParameter("returnCost");
		String stroutNo=ServletActionContext.getRequest().getParameter("outNo");
		Double consigneefee=new Double(0);
		Double paymentCollection=new Double(0);
		Double returnCost=new Double(0);
		Long outNo=new Long(0);
		try{
			if(null!=strconsigneefee && !"".equals(strconsigneefee)){
				consigneefee=Double.parseDouble(strconsigneefee);
			}
			if(null!=strpaymentCollection && !"".equals(strpaymentCollection)){
				paymentCollection=Double.parseDouble(strpaymentCollection);
			}
			if(null!=strreturnCost && !"".equals(strreturnCost)){
				returnCost=Double.parseDouble(strreturnCost);
			}
			if(null!=stroutNo && !"".equals(stroutNo)){
				outNo=Long.parseLong(stroutNo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.oprReturnGoods=new OprReturnGoods();
		try{
			this.oprReturnGoods.setStatus(new Long(1));
			this.oprReturnGoods.setReturnDepart(sysdpt.getDepartId());
			this.oprReturnGoods.setReturnDepartName(sysdpt.getDepartName());
			this.oprReturnGoods.setOutType(ServletActionContext.getRequest().getParameter("outType"));
			this.oprReturnGoods.setConsigneeFee(consigneefee);
			this.oprReturnGoods.setPaymentCollection(paymentCollection);
			this.oprReturnGoods.setReturnCost(returnCost);
			this.oprReturnGoods.setDno(Long.parseLong(ServletActionContext.getRequest().getParameter("dno")));
			this.oprReturnGoods.setOutNo(outNo);
			this.oprReturnGoods.setDutyParty(ServletActionContext.getRequest().getParameter("dutyParty"));
			this.oprReturnGoods.setReturnType(ServletActionContext.getRequest().getParameter("returnType"));
			this.oprReturnGoods.setReturnNum(Long.parseLong(ServletActionContext.getRequest().getParameter("returnNum")));
			this.oprReturnGoods.setReturnComment(ServletActionContext.getRequest().getParameter("returnComment"));
		}catch(Exception e){
			addError("数据输入有误！", e);
			return "reload";
		}
		try{
			if(null!=this.oprReturnGoodsService.allowRegistration(this.oprReturnGoods.getDno())){
				this.oprReturnGoodsService.saveRegistrationService(this.oprReturnGoods);
				
//				if(Boolean.valueOf(aduitFlag)){
//					this.oprReturnGoodsService.auditReturnGoods(this.oprReturnGoods.getId());
//				}
				super.getValidateInfo().setSuccess(true);
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("您输入的配送单号不存在");
			}
			
		}catch(Exception e){
			addError(e.getLocalizedMessage(), e);
		}
		return "reload";
	}
	
	/**
	 * 判断是否允许登记
	 * @return
	 */
	public String allowRegistration(){
		Long dno=0l;
		dno=Long.valueOf(ServletActionContext.getRequest().getParameter("filter_EQL_dno"));
		OprFaxIn entity = null;
		try{
			entity = this.oprReturnGoodsService.allowRegistration(dno);
			if(entity!=null){
				super.getValidateInfo().setSuccess(true);
				Long overmemoNo = this.oprReturnGoodsService.findMaxOvermemoNoByDno(dno);
				entity.setTown(overmemoNo+"");
				Struts2Utils.renderJson(entity);
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("您输入的配送单号不存在");
			}
		}catch(Exception e){
			addError(e.getLocalizedMessage(), e);
		}
		
		return "reload";
	}
	
	/**返货登记审核
	 * @return
	 */
	public String auditReturnGoods(){
		
		String returnGoodsId =ServletActionContext.getRequest().getParameter("returnGoodsId");//获取返货编号
		if(null==returnGoodsId || "".equals(returnGoodsId)){
			addError("没有返货编号！", null);
			return this.RELOAD;
		}
		try{
			this.oprReturnGoodsService.auditReturnGoods(Long.valueOf(returnGoodsId));
		}catch (Exception e) {
			addError("返货登记审核失败！", e);
		}
		
		return this.RELOAD;
	}
	
	public String findTotalCount(){
		try{
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext
					.getRequest());
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("returnDepart", user.get("bussDepart").toString());
			map.put("checkItems", getCheckItems());
			map.put("itemsValue", getItemsValue());
			String GED_createTime = map.get("GED_createTime");
			String LED_createTime = map.get("LED_createTime");
			if(null!=LED_createTime && null!=GED_createTime){
				map.put("countCheckItems", "createTime");
				map.put("startCount", map.get("GED_createTime"));
				map.put("endCount", map.get("LED_createTime"));
			}else{
				map.put("countCheckItems", "updateTime");
				map.put("startCount", map.get("GED_updateTime"));
				map.put("endCount", map.get("LED_updateTime"));
			}
			String sql = this.oprReturnGoodsService.findTotalCountService(map);
			Page pg = this.oprReturnGoodsService.getPageBySqlMap(getPages(), sql, map);
			List <Map>list =pg.getResultMap();
			for(Map  totalMap : list ){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("dno",(totalMap.get("TOTALNUM")==null?"0":totalMap.get("TOTALNUM").toString())+"票");
				jsonObject.element("returnNum","返(件)"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")+""));
				jsonObject.element("returnCost","成本(元)"+(totalMap.get("TOTALCOST")==null?"0":totalMap.get("TOTALCOST")+""));
				jsonObject.element("consigneeFee","到(元)"+(totalMap.get("TOTALCONSIGNEEFEE")==null?"0":totalMap.get("TOTALCONSIGNEEFEE")+""));
				jsonObject.element("paymentCollection","代(元)"+(totalMap.get("TOTALPAYMENT")==null?"0":totalMap.get("TOTALPAYMENT")+""));
				jsonObject.element("cusValueAddFee","增(元)"+(totalMap.get("CUSVALUEADDFEE")==null?"0":totalMap.get("CUSVALUEADDFEE")+""));
				jsonObject.element("faxPiece","录单(件)"+(totalMap.get("FAXPIECE")==null?"0":totalMap.get("FAXPIECE")+""));
				jsonObject.element("cqWeight","重(KG)"+(totalMap.get("CQWEIGHT")==null?"0":totalMap.get("CQWEIGHT")+""));
				
				Struts2Utils.renderJson(jsonObject);
			}
			//Struts2Utils.renderJson(pg);
		}catch (Exception e) {
			addError("返货统计失败！", e);
		}
		return this.LIST;
	}
	
}
