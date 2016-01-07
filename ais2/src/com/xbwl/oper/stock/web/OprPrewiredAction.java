package com.xbwl.oper.stock.web;
/**
 * author shuw
 * time Jul 26, 2011 5:28:05 PM
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.stock.service.IOprPrewiredService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.vo.OprPrewiredVo;

/**
 * author shuw
 * time 2011-7-19 上午11:26:12
 */



@Controller
@Action("oprPrewiredAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class OprPrewiredAction  extends SimpleActionSupport {

	@Resource(name="oprPrewiredServiceImpl")
	private IOprPrewiredService oprPrewiredService;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	private OprPrewired oprPrewired;
	
	private Long id;
	private String autostowMode;//配载方式
	private String toWhere;//去向
	private Long feeType;//
	private Long dno;//配送单号
	
	private  String totalDno;
	private String  totalNum;
	private  String  totalWeight;
	private  String endDepartName;
	private String  endDepartId;
	private String dnos;
	private String loadingbrigade;
	private String loadingbrigadeId;
	private String dispatchGroup;
	
	@Value("${request_stage}")
	private String   requestStage; 
	
	@Element(value = OprPrewiredVo.class)
	private List<OprPrewiredVo> aa;
	
	
	@Override
	protected Object createNewInstance() {
		return new OprPrewired();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprPrewiredService;
	}

	@Override
	public Object getModel() {
		return oprPrewired;
	}

	@Override
	public void setModel(Object obj) {
		oprPrewired=(OprPrewired)obj;
	}
	
	
	/**
	 * 货物实配
	 * @return
	 */
	public String  saveOvem(){
				try {
					if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
							User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
							String username = user.get("name").toString();
							Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
							Map map = new HashMap();
							map.put("totalDno", Long.parseLong(totalDno));
							map.put("totalNum",Long.parseLong(totalNum));
							map.put("totalWeight", Double.parseDouble(totalWeight));
							try{
								map.put("departId",Long.parseLong(endDepartId) );
							}catch (Exception e) {
							}
							
							map.put("departName",endDepartName);
							map.put("bussDepartId",bussDepartId);
							map.put("username",username);
							map.put("mode",this.autostowMode);
							
							map.put("loadingbrigade", loadingbrigade);
							map.put("loadingbrigadeId",( loadingbrigadeId));
							map.put("dispatchGroup", dispatchGroup);
							Long overmemoId = oprPrewiredService.saveOvemByIds(aa, map,dnos);
							getValidateInfo().setMsg("货物实配成功！");
					        addMessage("货物实配成功！");
					        getValidateInfo().setSuccess(true);
					        getValidateInfo().setValue(overmemoId+"");
					}else{
						getValidateInfo().setSuccess(false);
						getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
						addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
					}
				} catch (Exception e) {
					e.printStackTrace();
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg(e.getLocalizedMessage());
					addMessage("货物实配失败！");
			    	addError("货物实配失败！", e);
				}
			return RELOAD;
	}
	
	public String updateStatus(){
		if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
		    try {
	            List<Long> pks = getPksByIds();
	            oprPrewiredService.deleteStatusByIds(pks);
	            getValidateInfo().setMsg("数据删除成功！");
	            addMessage("数据删除成功！");
		    } catch (Exception e) {
		    	getValidateInfo().setMsg("数据删除失败！");
		    	addMessage("数据删除失败！");
		    	addError("数据删除失败！", e);
		    }
			}else{
				getValidateInfo().setSuccess(false);
				getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
				addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
			}
		return "reload";
	}
	
	/**
	 * 根据配送单号，查本预配明细信息（单票添加）
	 * @return Map
	 */
	public String  getAllByDno(){
		try {
			//首先判断货物状态，是否可以添加
			List<OprStatus> statusList = this.oprStatusService.findBy("dno", this.dno);
			if(null!=statusList && statusList.size()>0){
				OprStatus status = statusList.get(0);
				if(status.getOutStatus().equals(1l)){
					throw new ServiceException("配送单号为"+dno+"的货物已经出库，不能进行实配.");
				}
//				else if(status.getOutStatus().equals(2l)){
//					throw new ServiceException("配送单号为"+dno+"的货物的出库状态为异常出库，不能进行实配.");
//				}
				else if(status.getOutStatus().equals(3l)){
					throw new ServiceException("配送单号为"+dno+"的货物已经预配，不允许进行单票添加.");
				}
			}else{
				throw new ServiceException("没有查询到配送单号为"+dno+"这票货的数据!");
			}
			
			setPageConfig();
			Map filterMap =new HashMap();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
			String eqTakeMode ="市内送货";
			Map map = new HashMap();
			map.put("departId",bussDepartId);
			map.put("dno", dno);
			map.put("requestStage", requestStage);
			String sql  = oprPrewiredService.getInfoByDno(requestStage);  //sonderzug
			oprPrewiredService.getPageBySqlMap(getPages(), sql, map);
			Page page  = getPages();
			List<Map >listMap  =page.getResultMap();
			for(Map map2: listMap ){  //单票添加货物，判断预配类型
				Long  sonderzug =Long.parseLong(map2.get("SONDERZUG").toString());
				if(sonderzug==1){
					map2.put("AUTOSTOWMODE", "专车");
				}
				Long endDepartId = Long.valueOf(map2.get("ENDDEPARTID")+"");
				String  takeMode  = (String)map2.get("TAKEMODE");
				String  autoStowMode  = (String)map2.get("AUTOSTOWMODE");
				String  curDepartId  = map2.get("CURDEPARTID")+"";
				
				boolean departFlag = endDepartId.equals(bussDepartId);
				
				if(departFlag && "新邦".equals(autoStowMode)){
					if(takeMode.equals(eqTakeMode)){
						map2.put("AUTOSTOWMODE", eqTakeMode);
					}else{
						throw new ServiceException("该票货物是 '"+takeMode+"' ，不允许单票添加!");
					}
				}else if(!departFlag){
					map2.put("AUTOSTOWMODE", "部门交接");
				}
			}
			getPages().setResultMap(listMap);
			
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			getPages().setSuccess(false);
			addError("操作失败,失败原因:"+e.getLocalizedMessage(),e);
			e.printStackTrace();
			return "reload";
		}
		
		return LIST;
	}
	
	
	/**
	 * 根据条件，查本预配明细信息
	 * @return Map
	 */
	public String getAllDetail(){
		try {
			setPageConfig();
			Map filterMap =new HashMap();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
		
			filterMap.put("ids", getIds());
			
			Map map = new HashMap();
			map.put("departId",bussDepartId);
			map.put("departId2",bussDepartId);
			
			String sql  = oprPrewiredService.getAllDetail(filterMap);
			oprPrewiredService.getPageBySqlMap(getPages(), sql, map);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			   addError("操作失败,失败原因:"+e.getLocalizedMessage(),e);
			   return "reload";
		}
		return "list";
	}

	/**
	 * 根据条件(部门交接)，查本预配明细信息
	 * @return Map
	 */
	public String getAllDetailDepart(){
		try {
			setPageConfig();
			Map filterMap =new HashMap();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
			filterMap.put("ids", getIds());

			Map map = new HashMap();
			map.put("departId",bussDepartId);
			map.put("departId2",bussDepartId);
			String sql  = oprPrewiredService.getAllDetailByDepartId(filterMap);
			oprPrewiredService.getPageBySqlMap(getPages(), sql, map);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			   e.printStackTrace();
			   logger.error("操作出错");
			   super.getValidateInfo().setSuccess(false);
			   super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			   return "reload";
		}
		return "list";
	}
	
	/**
	 * 根据条件(部门交接)，查本预配明细信息
	 * @return Map
	 */
	public String getAllDetailCar(){
		try {
			setPageConfig();
			Map filterMap =new HashMap();
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
		
			filterMap.put("ids", getIds());
			
			Map map = new HashMap();
			map.put("departId",bussDepartId);
			map.put("departId2",bussDepartId);
			
			String sql  = oprPrewiredService.getAllDetailByCar(filterMap);
			oprPrewiredService.getPageBySqlMap(getPages(), sql, map);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			   e.printStackTrace();
			   logger.error("操作出错");
			   super.getValidateInfo().setSuccess(false);
			   super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			   return "reload";
		}
		return "list";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutostowMode() {
		return autostowMode;
	}

	public void setAutostowMode(String autostowMode) {
		this.autostowMode = autostowMode;
	}

	public String getToWhere() {
		return toWhere;
	}

	public void setToWhere(String toWhere) {
		this.toWhere = toWhere;
	}

	public Long getFeeType() {
		return feeType;
	}

	public void setFeeType(Long feeType) {
		this.feeType = feeType;
	}

	public Long getDno() {
		return dno;
	}

	public void setDno(Long dno) {
		this.dno = dno;
	}

	public String getTotalDno() {
		return totalDno;
	}

	public void setTotalDno(String totalDno) {
		this.totalDno = totalDno;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getEndDepartName() {
		return endDepartName;
	}

	public void setEndDepartName(String endDepartName) {
		this.endDepartName = endDepartName;
	}

	public String getEndDepartId() {
		return endDepartId;
	}

	public void setEndDepartId(String endDepartId) {
		this.endDepartId = endDepartId;
	}

	public String getLoadingbrigade() {
		return loadingbrigade;
	}

	public void setLoadingbrigade(String loadingbrigade) {
		this.loadingbrigade = loadingbrigade;
	}

	public String getLoadingbrigadeId() {
		return loadingbrigadeId;
	}

	public void setLoadingbrigadeId(String loadingbrigadeId) {
		this.loadingbrigadeId = loadingbrigadeId;
	}

	public String getDispatchGroup() {
		return dispatchGroup;
	}

	public void setDispatchGroup(String dispatchGroup) {
		this.dispatchGroup = dispatchGroup;
	}

	public List<OprPrewiredVo> getAa() {
		return aa;
	}

	public void setAa(List<OprPrewiredVo> aa) {
		this.aa = aa;
	}

	public String getDnos() {
		return dnos;
	}

	public void setDnos(String dnos) {
		this.dnos = dnos;
	}

	public String getRequestStage() {
		return requestStage;
	}

	public void setRequestStage(String requestStage) {
		this.requestStage = requestStage;
	}


	
	
}
