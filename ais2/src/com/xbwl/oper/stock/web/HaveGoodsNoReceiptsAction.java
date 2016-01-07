package com.xbwl.oper.stock.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.vo.OprMathingGoods;

/**
 * @author CaoZhili time Aug 15, 2011 10:04:24 AM
 * 
 * �л��޵�����ƥ����Ʋ������
 */
@Controller
@Action("haveGoodsNoReceiptsAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_haveGoodsNoReceipts.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class HaveGoodsNoReceiptsAction extends SimpleActionSupport {

	@Resource(name = "oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	private OprOvermemoDetail oprOvermemoDetail;

	@Element(value = OprMathingGoods.class)
	private List<OprMathingGoods> mathList;
	
	@Override
	protected Object createNewInstance() {
		return new OprOvermemoDetail();
	}

	public List<OprMathingGoods> getMathList() {
		return mathList;
	}


	public void setMathList(List<OprMathingGoods> mathList) {
		this.mathList = mathList;
	}


	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprOvermemoDetailService;
	}

	@Override
	public Object getModel() {

		return this.oprOvermemoDetail;
	}

	@Override
	public void setModel(Object obj) {

		this.oprOvermemoDetail = (OprOvermemoDetail) obj;
	}

	/**
	 * ���������Ż��ߺ���Ų�ѯ���������ݣ������㵽״̬
	 * @return
	 */
	public String findHaveGoodsNoReceipt(){
		
		setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
	
		try{
			String sql = this.oprFaxInService.getSqlHaveGoodsNoReceipt(map);
			map.put("EQL_reachStatus", "0");
			map.put("EQL_faxStatus", "1");
			Page pg = this.oprFaxInService.getPageBySqlMap(this.getPages(), sql,map);
			super.getValidateInfo().setSuccess(true);
			Struts2Utils.renderJson(pg);
		}catch(Exception e){
			addError("���������Ż��ߺ���Ų�ѯ����������ʧ�ܣ�",e);
		}
		
		return this.LIST;
	}

	/**
	 * �л��޵�ƥ�䷽��
	 * @return
	 */
	public String saveMatching(){
	
		try{
			this.oprOvermemoDetailService.saveMatchingService(this.mathList);
		}catch(Exception e){
			e.printStackTrace();
			addError("�ֶ�ƥ��ʧ�ܣ�", e);
		}
		return this.RELOAD;
	}
	
	/**
	 * �л��޵���ѯ����
	 * @return
	 */
	public String findNoFaxList(){
		try{
			String sql=null;
			//���÷�ҳ����
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			sql=this.oprOvermemoDetailService.findNoFaxListService(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("�л��޵���ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
}
