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
 * 交接单明细表控制层操作类
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
	 * 信息汇总 主单号
	 * @return
	 */
	public String getSumInfo(){
		try {
			setPageConfig();
			String ids = getIds();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
			OprOvermemo overmemo = new OprOvermemo();
			overmemo.setOvermemoType("汇总:");
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
	 * 入库点到撤销
	 * @return
	 */
	public String revokedOvermemo() {
		try {
			User user = WebRalasafe.getCurrentUser(ServletActionContext
					.getRequest());
			this.oprOvermemoDetailService.revokedOvermemoService(overIds,
					user);// update status = 0
		} catch (Exception e) {
			addError("入库点到撤销失败!", e);
			return "reload";
		}
		return "reload";
	}
	/**
	 * 跳转到入库点到页面
	 * @return
	 */
	public String enterReport() {
		return "enterReport";
	}
	

	
	/**
	 * 根据配送单号查入库点到信息,单票添加方法
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
			super.addError("按照配送单号查询异常！", e);
		}
		
		return "reload";
	}

	/**
	 * 入库点到方法
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
			addError("入库点到失败!", e);
			return "msg";
		}
		return "reload";
	}

	/**
	 * 入库点到查询方法 
	 * @return
	 */
	public String findEnterReport(){
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.getSqlRalaListService(map);
			map.put("requestStage", requestStage);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("查询失败！", e);
			
		}
		return this.LIST;
	}
	
	public String findOverDetail(){
		try {
			List<OprOvermemoDetail> list=oprOvermemoDetailService.findDetailByDno(dno);
			Struts2Utils.renderJson(list);
		} catch (Exception e) {
			addError("数据查询失败！", e);
			return RELOAD;
		}
		return null;
	}
	
	/**交接单查询方法
	 * @return
	 */
	public String overmemoSearch(){
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.overmemoSearchService(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("交接单查询失败！", e);
		}
		return this.LIST;
	}
	
	
	/**转到交接单查询页面
	 * @return
	 */
	public String toOvermemoSearch(){
		return "overmemoSearch";
	}
	
	/**转到交接单明细查询页面
	 * @return
	 */
	public String toOvermemoDetailSearch(){
		return "overmemoDetailSearch";
	}
	
	/**交接单明细查询方法
	 * @return
	 */
	public String overmemoDetailSearch(){
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.overmemoDetailSearchService(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("交接单查询失败！", e);
		}
		return this.LIST;
	}
	
	/**撤销发车确认
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
	 * 跳转到未到主单查询页面
	 * @return
	 */
	public String toNotReportTracking(){
		return "toNotReportTracking";
	}
	
	/**
	 * 查询未到主单跟踪信息
	 * @return
	 */
	public String findNotReportTracking(){
		String sql=null;
		try{
			//设置分页参数
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
					mapist.put("CLOB_STATUS_REALPIECE","未发车");
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
					statusString+="已发车"+fachePiece+"件，";
				}
				if(daochePiece>0){
					statusString+="已到车"+daochePiece+"件，";
				}
				if(xiecheStartPiece>0){
					statusString+="已开始卸车"+xiecheStartPiece+"件，";
				}
				if(xiecheEndPiece>0){
					statusString+="已卸车结束"+xiecheEndPiece+"件，";
				}
				statusString+="正常点到 "+zhengchang+" 件,单票添加点到 "+danpiao+" 件";
				mapist.put("CLOB_STATUS_REALPIECE",statusString);
			}
			getPages().setResultMap(list);
		}catch(Exception e){
			addError("未到主单信息查询失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 统计未到主单跟踪信息
	 * @return
	 */
	public String totalNotReportTracking(){
		try{
			//设置分页参数
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
				jsonObject.element("PIECE","应到(件)"+(totalMap.get("TOTALPIECE")==null?"0":totalMap.get("TOTALPIECE")));
				jsonObject.element("WEIGHT","重(KG)"+(totalMap.get("TOTALWEIGHT")==null?"0":totalMap.get("TOTALWEIGHT")));
				jsonObject.element("FLIGHT_MAIN_NO",(totalMap.get("TOTALNUM")==null?"0":totalMap.get("TOTALNUM"))+"票");
				jsonObject.element("NOTPIECE","未到(件)"+(totalMap.get("TOTALNOTPIECE")==null?"0":totalMap.get("TOTALNOTPIECE")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch (Exception e) {
			addError("统计未到主单信息失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 通过主单号查询货物明细
	 * @return
	 */
	public String findNotReportTrackDetail(){
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.oprOvermemoDetailService.findNotReportTrackDetail(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("按照主单号查询明细失败！", e);
		}
		return this.LIST;
	}
	
	/**
	 * 入库综合处理交接单查询（已点到货物）
	 * @return
	 */
	public String findOvermemoDetail(){
		String sql=null;
		try{
			//设置分页参数
			setPageConfig();
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest()); 
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			map.put("bussDepart", user.get("bussDepart")+"");
			sql=this.oprOvermemoDetailService.findOvermemoDetail(map);
			this.oprOvermemoDetailService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("按照主单号查询明细失败！", e);
		}
		return this.LIST;
	}
}
