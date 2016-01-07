package com.xbwl.oper.stock.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprStocktake;
import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.oper.stock.service.IOprStockTakeDetailService;

@Controller
@Action("oprStocktakeDetailAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
	 	@Result(name = "carArriveInput", location = "/WEB-INF/xbwl/instock/carArrive.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_stockTakeDetail.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class OprStockTakeDetailAction extends SimpleActionSupport {

	@Resource(name = "oprStockTakeDetailServiceImpl")
	private IOprStockTakeDetailService  iOprStockTakeDetailService;
	
	private OprStocktakeDetail oprStocktakeDetail;
	
	 @Element(value = OprStocktakeDetail.class)
	private List<OprStocktakeDetail> aa;
	 
	 private  Long     filter_EQL_stocktakeId;
	
	@Override
	protected Object createNewInstance() {
		return new OprStocktakeDetail();
	}

	public List<OprStocktakeDetail> getAa() {
		return aa;
	}

	public void setAa(List<OprStocktakeDetail> aa) {
		this.aa = aa;
	}

	@Override
	public Map getContextMap() {
		Map map=new HashMap();
		
		if(filter_EQL_stocktakeId==null){
			map.put("orderFileds", "d_no");
		}else{
			OprStocktake oprStockTake =	(OprStocktake)this.getManager().getAndInitEntity(filter_EQL_stocktakeId);
			if(null!=oprStockTake)
				map.put("orderFileds", oprStockTake.getOrderFields());
			else
				map.put("orderFileds", "d_no");
		}
		return map;
	}

	@Override
	protected IBaseService getManager() {
		return this.iOprStockTakeDetailService;
	}

	@Override
	public Object getModel() {
		return this.oprStocktakeDetail;
	}

	@Override
	public void setModel(Object obj) {
		this.oprStocktakeDetail=(OprStocktakeDetail)obj;
	}

	
	/**
	 * �̵���֣������ʵ�ռ�����list���飬����״̬�ĳ����̵�
	 * @return
	 */
	public String saveList(){
	
		if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(),null,getContextMap())){
				try {
							iOprStockTakeDetailService.saveRealPieceById(aa);
			        		getValidateInfo().setMsg("���ݱ���ɹ���");
			        		addMessage("���ݱ���ɹ���");
				} catch (Exception e) {
							getValidateInfo().setSuccess(false);
							getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
							addError("���ݱ���ʧ�ܣ�", e);
				}
		     }else{
			        		getValidateInfo().setSuccess(false);
			        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			        		
        	}
		     return RELOAD;
	}



}
