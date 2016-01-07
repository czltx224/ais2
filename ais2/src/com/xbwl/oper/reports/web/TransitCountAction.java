package com.xbwl.oper.reports.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.service.IOperationReportService;

/**
 * ��תͳ�ƿ��Ʋ������
 * @author czl
 * @date 2012-05-18
 */
@Controller
@Action("transitCountAction")
@Scope("prototype")
@Namespace("/reports")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/transit/opr_transitCount.jsp", type = "dispatcher"),
		@Result(name = "toTransitDetail", location = "/WEB-INF/xbwl/reports/transit/opr_transiDetailFind.jsp", type = "dispatcher"),
		@Result(name = "toEdiAgingQuery", location = "/WEB-INF/xbwl/reports/transit/opr_ediAgingQuery.jsp", type = "dispatcher"),
		@Result(name = "toEdiAgingCount", location = "/WEB-INF/xbwl/reports/transit/opr_ediAgingCount.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) 
})
public class TransitCountAction extends SimpleActionSupport {

	private Long gowhereId;
	private String startDate;
	private String endDate;
	private Long curBussDepart;
	private String countCheckItems;
	
	@Resource(name = "operationReportServiceImpl")
	private IOperationReportService operationReportService;

	private OprLoadingbrigadeWeight oprLoadingbrigadeWeight;

	public String getCountCheckItems() {
		return countCheckItems;
	}

	public void setCountCheckItems(String countCheckItems) {
		this.countCheckItems = countCheckItems;
	}

	public Long getGowhereId() {
		return gowhereId;
	}

	public void setGowhereId(Long gowhereId) {
		this.gowhereId = gowhereId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getCurBussDepart() {
		return curBussDepart;
	}

	public void setCurBussDepart(Long curBussDepart) {
		this.curBussDepart = curBussDepart;
	}

	@Override
	protected Object createNewInstance() {
		return new OprLoadingbrigadeWeight();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.operationReportService;
	}

	@Override
	public Object getModel() {
		return this.oprLoadingbrigadeWeight;
	}

	@Override
	public void setModel(Object obj) {
		this.oprLoadingbrigadeWeight = (OprLoadingbrigadeWeight) obj;
	}

	/**
	 * ��ת����ͳ�ƿ��Ʋ㷽��
	 * @return
	 */
	public String findTransitCount(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitCountService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("��ת����ͳ��ʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ��ת����ͳ�ƺϼƿ��Ʋ㷽��
	 * @return
	 */
	public String findTransitCountTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitCountTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("GOWHERE","<b>�ϼ�</b>��");
				jsonObject.element("TOTALTICKET","Ʊ"+(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET")));
				jsonObject.element("TOTALPIECE","��"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("TOTALWEIGHT","��(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("DAOFU","����(Ԫ)"+(totalMap.get("DAOFU")==null?"0":totalMap.get("DAOFU")));
				jsonObject.element("YUFU","Ԥ��(Ԫ)"+(totalMap.get("YUFU")==null?"0":totalMap.get("YUFU")));
				jsonObject.element("PAYMENT_COLLECTION","��(Ԫ)"+(totalMap.get("PAYMENT_COLLECTION")==null?"0":totalMap.get("PAYMENT_COLLECTION")));
				jsonObject.element("TRA_FEE","�ɱ�(Ԫ)"+(totalMap.get("TRA_FEE")==null?"0":totalMap.get("TRA_FEE")));
				jsonObject.element("NOTCONFIRMNUM","����(Ʊ)"+(totalMap.get("NOTCONFIRMNUM")==null?"0":totalMap.get("NOTCONFIRMNUM")));
				jsonObject.element("NOTSETTLEAMOUNT","δ��(Ԫ)"+(totalMap.get("NOTSETTLEAMOUNT")==null?"0":totalMap.get("NOTSETTLEAMOUNT")));
				jsonObject.element("NOTPAYAMOUNT","δ��(Ԫ)"+(totalMap.get("NOTPAYAMOUNT")==null?"0":totalMap.get("NOTPAYAMOUNT")));
				jsonObject.element("PREKGCOST","(Ԫ/KG)"+(totalMap.get("PREKGCOST")==null?"0":totalMap.get("PREKGCOST")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("��ת���ܺϼ�ͳ��ʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ��ת��ϸ��ѯ���Ʋ㷽��
	 * @return
	 */
	public String findTransitDetail(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("overmemoType", "��ת");
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitDetailFindService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("��ת��ϸ��ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ��ת��ϸ��ѯ�ϼƿ��Ʋ㷽��
	 * @return
	 */
	public String findTransitDetailTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("overmemoType", "��ת");
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			map.put("fiStatus", "1");
			
			String sql = this.operationReportService.findTransitDetailTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("D_NO","<b>�ϼ�</b>��");
				jsonObject.element("CP_NAME","Ʊ"+(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET")));
				jsonObject.element("PIECE","��"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("CUS_WEIGHT","��(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("DAOFU","����(Ԫ)"+(totalMap.get("DAOFU")==null?"0":totalMap.get("DAOFU")));
				jsonObject.element("YUFU","Ԥ��(Ԫ)"+(totalMap.get("YUFU")==null?"0":totalMap.get("YUFU")));
				jsonObject.element("PAYMENT_COLLECTION","��(Ԫ)"+(totalMap.get("PAYMENT_COLLECTION")==null?"0":totalMap.get("PAYMENT_COLLECTION")));
				jsonObject.element("TRA_FEE","�ɱ�(Ԫ)"+(totalMap.get("TRA_FEE")==null?"0":totalMap.get("TRA_FEE")));
				jsonObject.element("NOTCONFIRMNUM","����(Ʊ)"+(totalMap.get("NOTCONFIRMNUM")==null?"0":totalMap.get("NOTCONFIRMNUM")));
				jsonObject.element("NOTSETTLEAMOUNT","δ��(Ԫ)"+(totalMap.get("NOTSETTLEAMOUNT")==null?"0":totalMap.get("NOTSETTLEAMOUNT")));
				jsonObject.element("NOTPAYAMOUNT","δ��(Ԫ)"+(totalMap.get("NOTPAYAMOUNT")==null?"0":totalMap.get("NOTPAYAMOUNT")));
				jsonObject.element("PREKGCOST","(Ԫ/KG)"+(totalMap.get("PREKGCOST")==null?"0":totalMap.get("PREKGCOST")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("��ת��ϸ��ѯ�ϼ�ʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ��ת����ת��ϸ��ѯҳ��
	 * @return
	 */
	public String toTransitDetail(){
		
		return "toTransitDetail";
	}
	
	/**
	 * ��ת��EDI����ʱЧ��ѯҳ��
	 * @return
	 */
	public String toEdiAgingQuery(){
		
		return "toEdiAgingQuery";
	}
	
	/**
	 * ��ת��EDI����ʱЧͳ��ҳ��
	 * @return
	 */
	public String toEdiAgingCount(){
		
		return "toEdiAgingCount";
	}
	
	/**
	 * EDI����ʱЧ��ѯ����
	 * @return
	 */
	public String findEdiAgingQuery(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			map.put("okFlag", "1");
			
			String sql = this.operationReportService.findEdiAgingQueryService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("EDI����ʱЧ��ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * EDI����ʱЧ����ͳ�Ʋ�ѯ����
	 * @return
	 */
	public String findEdiAgingCount(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			
			String sql = this.operationReportService.findEdiAgingCountService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
		}catch (Exception e) {
			addError("EDI����ʱЧ����ͳ�Ʋ�ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * EDI����ʱЧ��ѯ�ϼƷ���
	 * @return
	 */
	public String findEdiAgingQueryTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			map.put("okFlag", "1");
			
			String sql = this.operationReportService.findEdiAgingQueryTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("D_NO","<b>�ϼ�</b>��");
				jsonObject.element("CP_NAME",(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET"))+"Ʊ");
				jsonObject.element("PIECE","��"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("CUS_WEIGHT","��(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("TRANSITSTANDARD","�㵽(���)"+(totalMap.get("TRANSITSTANDARDNUM")==null?"0":totalMap.get("TRANSITSTANDARDNUM")));
				jsonObject.element("CAROUTSTANDARD","����(���)"+(totalMap.get("CAROUTSTANDARDNUM")==null?"0":totalMap.get("CAROUTSTANDARDNUM")));
				jsonObject.element("SIGNSTANDARD","ǩ��(���)"+(totalMap.get("SIGNSTANDARDNUM")==null?"0":totalMap.get("SIGNSTANDARDNUM")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("EDI����ʱЧ��ѯ�ϼ�ʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * EDI����ʱЧ����ͳ�ƺϼƷ���
	 * @return
	 */
	public String findEdiAgingCountTotal(){
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			
			map.put("distributionMode", "��ת");
			map.put("faxStatus", "1");
			
			String sql = this.operationReportService.findEdiAgingCountTotalService(map);
			this.operationReportService.getPageBySqlMap(getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("IN_DEPART","<b>�ϼ�</b>��");
				jsonObject.element("TOTALTICKET",(totalMap.get("TOTALTICKET")==null?"0":totalMap.get("TOTALTICKET"))+"Ʊ");
				jsonObject.element("TOTALPIECE","��"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("TOTALWEIGHT","��(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("REACHNUM","�㵽(Ʊ)"+(totalMap.get("REACHNUM")==null?"0":totalMap.get("REACHNUM")));
				jsonObject.element("OUTNUM","����(Ʊ)"+(totalMap.get("OUTNUM")==null?"0":totalMap.get("OUTNUM")));
				jsonObject.element("SIGNNUM","ǩ��(Ʊ)"+(totalMap.get("SIGNNUM")==null?"0":totalMap.get("SIGNNUM")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("EDI����ʱЧ����ͳ�ƺϼƷ���ʧ�ܣ�", e);
		}
		return this.LIST;
	}
}
