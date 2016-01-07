package com.xbwl.oper.stock.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

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

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.web.OprFaxMainAction;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.stock.vo.OprFaxInSureVo;

/**
 * author CaoZhili time Jul 2, 2011 2:51:10 PM
 * 
 * ���ӵ���ϸ����Ʋ������
 */
@Controller
@Action("oprOvermemoDetailAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "enterReport", location = "/WEB-INF/xbwl/stock/opr_enterStockReport.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_overmemoDetail.jsp", type = "dispatcher"),
		@Result(name = "toNotReportTracking", location = "/WEB-INF/xbwl/stock/opr_notReportTracking.jsp", type = "dispatcher"),
		@Result(name = "overmemoSearch", location = "/WEB-INF/xbwl/stock/opr_overmemoSearch.jsp", type = "dispatcher"),
		@Result(name = "overmemoDetailSearch", location = "/WEB-INF/xbwl/stock/opr_overmemoDetailSearch.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		)}

)
public class OprOvermemoDetailAction extends SimpleActionSupport {

	@Resource(name = "oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;

	@Resource(name="oprOvermemoServiceImpl")
	private IOprOvermemoService oprOvermemoService;
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
//	@Resource(name="wSCtEstimateRemote")
//	private IWSCtEstimateService ctEstimateService;
	
	private OprOvermemoDetail oprOvermemoDetail;
	private Long dno;

	private String filter_LIKES_overmemoId;
	
	private Long overmemoId;
	
	@Value("${enterStock.requestStage}")
	private String   requestStage;
	
	@Override
	protected Object createNewInstance() {

		return new OprOvermemoDetail();
	}

	@Element(value = OprFaxInSureVo.class)
	private List<OprFaxInSureVo> overIds;

	public List<OprFaxInSureVo> getOverIds() {
		return overIds;
	}

	public void setOverIds(List<OprFaxInSureVo> overIds) {
		this.overIds = overIds;
	}

	public String getFilter_LIKES_overmemoId() {
		return filter_LIKES_overmemoId;
	}

	public void setFilter_LIKES_overmemoId(String filter_LIKES_overmemoId) {
		this.filter_LIKES_overmemoId = filter_LIKES_overmemoId;
	}

	@Override
	public Map getContextMap() {
		Map map=new HashMap();
		map.put("authority",Struts2Utils.getParameter("authority"));
		return map;

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
	 * @return the dno
	 */
	public Long getDno() {
		return dno;
	}

	/**
	 * @param dno the dno to set
	 */
	public void setDno(Long dno) {
		this.dno = dno;
	}

	/**
	 * ��Ϣ���� ������
	 * @return
	 */
	public String getSumInfo(){
		try {
			setPageConfig();
			String ids = getIds();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
			OprOvermemo overmemo = new OprOvermemo();
			overmemo.setOvermemoType("����:");
			if(!"".equals(getIds())&&getIds()!=null){
				String sql  =oprOvermemoDetailService.getSumInfoByIds(ids, bussDepartId);
				oprOvermemoDetailService.getPageBySql(getPages(), sql);
				Page page = getPages();
				List <Map>list =page.getResultMap();
				for(Map totalMap : list ){
					overmemo.setTotalWeight(totalMap.get("TOTALWEIGHT")==null?0.0d:Double.parseDouble(totalMap.get("TOTALWEIGHT")+""));
					overmemo.setTotalTicket(totalMap.get("TOTALTICKET")==null?0:Long.parseLong(totalMap.get("TOTALTICKET")+""));
					overmemo.setTotalPiece(totalMap.get("TOTALPIECE")==null?0:Long.parseLong(totalMap.get("TOTALPIECE")+""));
				}
				System.out.println(overmemo.getTotalPiece());
				Struts2Utils.renderJson(overmemo);
			}else {
				overmemo.setTotalWeight(0.0);
				overmemo.setTotalTicket(0l);
				overmemo.setTotalPiece(0l);
				Struts2Utils.renderJson(overmemo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * ���㵽����
	 * @return
	 */
	public String revokedOvermemo() {
		try {
			User user = WebRalasafe.getCurrentUser(ServletActionContext
					.getRequest());
			this.oprOvermemoDetailService.revokedOvermemoService(overIds,
					user);// update status = 0
		} catch (Exception e) {
			addError("���㵽����ʧ��!", e);
			return "reload";
		}
		return "reload";
	}
	/**
	 * ��ת�����㵽ҳ��
	 * @return
	 */
	public String enterReport() {
		return "enterReport";
	}
	

	
	/**
	 * �������͵��Ų����㵽��Ϣ,��Ʊ��ӷ���
	 * @return
	 */
	public String findByDNO(){
		
		try{
			String strdno=ServletActionContext.getRequest().getParameter("dno");
			//String endDepartId=ServletActionContext.getRequest().getParameter("endDepartId");
			if(strdno!=null){
				if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){
					List<Map> faxlist=this.oprFaxInService.findFaxVoByDno(Long.valueOf(strdno));
					this.getValidateInfo().setSuccess(true);
					Struts2Utils.renderJson(faxlist);
				}else{
	        		this.getValidateInfo().setSuccess(false);
	        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
	        	}
			}
		}catch(Exception e){
			super.addError("�������͵��Ų�ѯ�쳣��", e);
		}
		
		return "reload";
	}

	/**
	 * ���㵽����
	 * @return
	 */
	public String reportConfirm() {
		//showtest();
		try {
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), this.getPrivilege(), getModel(), getContextMap())){

        			this.oprOvermemoDetailService.doEnterReport(overIds, user);
        	}else{
        		this.getValidateInfo().setSuccess(false);
        		this.getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
		} catch (Exception e) {
			addError("���㵽ʧ��!", e);
			return "msg";
		}
		return "reload";
	}

	/**
	 * ���㵽��ѯ���� 
	 * @return
	 */
	public String findEnterReport(){
		String sql=null;
		try{
			//���÷�ҳ����
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.getSqlRalaListService(map);
			map.put("requestStage", requestStage);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("��ѯʧ�ܣ�", e);
			
		}
		return this.LIST;
	}
	
	public String findOverDetail(){
		try {
			List<OprOvermemoDetail> list=oprOvermemoDetailService.findDetailByDno(dno);
			Struts2Utils.renderJson(list);
		} catch (Exception e) {
			addError("���ݲ�ѯʧ�ܣ�", e);
			return RELOAD;
		}
		return null;
	}
	
	/**���ӵ���ѯ����
	 * @return
	 */
	public String overmemoSearch(){
		String sql=null;
		try{
			//���÷�ҳ����
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.overmemoSearchService(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("���ӵ���ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	
	/**ת�����ӵ���ѯҳ��
	 * @return
	 */
	public String toOvermemoSearch(){
		return "overmemoSearch";
	}
	
	/**ת�����ӵ���ϸ��ѯҳ��
	 * @return
	 */
	public String toOvermemoDetailSearch(){
		return "overmemoDetailSearch";
	}
	
	/**���ӵ���ϸ��ѯ����
	 * @return
	 */
	public String overmemoDetailSearch(){
		String sql=null;
		try{
			//���÷�ҳ����
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.overmemoDetailSearchService(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("���ӵ���ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**��������ȷ��
	 * @return
	 */
	public String rollBackCar(){
		try{
			String routeNumber = ServletActionContext.getRequest().getParameter("routeNumber");
			this.oprOvermemoService.cancelOvermemo(Long.valueOf(routeNumber));
		}catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		}
		return this.RELOAD;
	}

	public Long getOvermemoId() {
		return overmemoId;
	}

	public void setOvermemoId(Long overmemoId) {
		this.overmemoId = overmemoId;
	}
	
	/**
	 * ��ת��δ��������ѯҳ��
	 * @return
	 */
	public String toNotReportTracking(){
		return "toNotReportTracking";
	}
	
	/**
	 * ��ѯδ������������Ϣ
	 * @return
	 */
	public String findNotReportTracking(){
		String sql=null;
		try{
			//���÷�ҳ����
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("reachStatus", "1");
			map.put("f_status", "1");
			map.put("bussDepart", user.get("bussDepart")+"");
			sql=this.oprOvermemoDetailService.findNotReportTracking(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
			List<Map>list  = getPages().getResultMap();
			for(Map mapist :list ){
				SerializableClob clob=(SerializableClob)mapist.get("CLOB_STATUS_REALPIECE");
				String statusString  = "";
				if(clob==null){
					mapist.put("CLOB_STATUS_REALPIECE","δ����");
					continue;
				}
				String[] clobStrings = OprFaxMainAction.clob2String(clob).split(",");
				
				Long zhengchang = 0l;
				Long danpiao = 0l;
				Long fachePiece = 0l;
				Long daochePiece = 0l;
				Long xiecheStartPiece = 0l;
				Long xiecheEndPiece = 0l;
				for (int i = 0; i < clobStrings.length; i++) {
					Long vpiece = Long.valueOf(clobStrings[i].substring(clobStrings[i].indexOf(":")+1,clobStrings[i].indexOf("_")));
					Long totalPiece = Long.valueOf(clobStrings[i].substring(clobStrings[i].indexOf("_")+1,clobStrings[i].length()));
					if(clobStrings[i].indexOf(":")==0){
						danpiao+=vpiece;
					}else if(clobStrings[i].indexOf(":")>0){
						Long status = Long.valueOf(clobStrings[i].substring(0,clobStrings[i].indexOf(":")));
						if(status==0l){
							fachePiece=totalPiece;
						}else if(status==1l){
							daochePiece=totalPiece;
						}else if(status==2l){
							xiecheStartPiece=totalPiece;
						}else if(status==3l){
							xiecheEndPiece=totalPiece;
						}
						zhengchang+=vpiece;
					}
				}
				if(fachePiece>0){
					statusString+="�ѷ���"+fachePiece+"����";
				}
				if(daochePiece>0){
					statusString+="�ѵ���"+daochePiece+"����";
				}
				if(xiecheStartPiece>0){
					statusString+="�ѿ�ʼж��"+xiecheStartPiece+"����";
				}
				if(xiecheEndPiece>0){
					statusString+="��ж������"+xiecheEndPiece+"����";
				}
				statusString+="�����㵽 "+zhengchang+" ��,��Ʊ��ӵ㵽 "+danpiao+" ��";
				mapist.put("CLOB_STATUS_REALPIECE",statusString);
			}
			getPages().setResultMap(list);
		}catch(Exception e){
			addError("δ��������Ϣ��ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ͳ��δ������������Ϣ
	 * @return
	 */
	public String totalNotReportTracking(){
		try{
			//���÷�ҳ����
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("reachStatus", "1");
			map.put("f_status", "1");
			map.put("bussDepart", user.get("bussDepart")+"");
			String totalSql=this.oprOvermemoDetailService.totalNotReportTracking(map);
			this.oprOvermemoDetailService.getPageBySqlMap(getPages(), totalSql, map);
			List<Map> list =getPages().getResultMap();
	
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("PIECE","Ӧ��(��)"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("WEIGHT","��(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("FLIGHT_MAIN_NO",(totalMap.get("TOTALNUM")==null?"0":totalMap.get("TOTALNUM"))+"Ʊ");
				jsonObject.element("NOTPIECE","δ��(��)"+(totalMap.get("TOTALNOTPIECE")==null?"0":totalMap.get("TOTALNOTPIECE")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("ͳ��δ��������Ϣʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ͨ�������Ų�ѯ������ϸ
	 * @return
	 */
	public String findNotReportTrackDetail(){
		String sql=null;
		try{
			//���÷�ҳ����
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.findNotReportTrackDetail(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("���������Ų�ѯ��ϸʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ����ۺϴ����ӵ���ѯ���ѵ㵽���
	 * @return
	 */
	public String findOvermemoDetail(){
		String sql=null;
		try{
			//���÷�ҳ����
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("bussDepart", user.get("bussDepart")+"");
			sql=this.oprOvermemoDetailService.findOvermemoDetail(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("���������Ų�ѯ��ϸʧ�ܣ�", e);
		}
		return this.LIST;
	}
}
