package com.xbwl.oper.stock.web;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.vo.CarGoVo;

/**
 * author CaoZhili time Jul 2, 2011 2:50:46 PM
 * 
 * 交接单主表控制层操作类
 */
@Controller
@Action("oprOvermemoAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
	 	@Result(name = "carArriveInput", location = "/WEB-INF/xbwl/instock/carArrive.jsp", type = "dispatcher"), 
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_overmemo.jsp", type = "dispatcher"), 
		@Result(name = "outCar", location = "/WEB-INF/xbwl/stock/opr_overmemo_outCar.jsp", type = "dispatcher"), //outCar 发车确认
		@Result(name = "ovemInput", location = "/WEB-INF/xbwl/stock/opr_prewired_overmemo.jsp", type = "dispatcher"), //ovemInput  货物实配
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class OprOvermemoAction extends SimpleActionSupport {

	@Resource(name = "oprOvermemoServiceImpl")
	private IOprOvermemoService oprOvermemoService;
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;

	private OprOvermemo oprOvermemo=new OprOvermemo();
	private String carArriveIds; 
	private String carArriveType;//到车确认类型
	private String orderbyName;//排序类型
	
	private Long routeNumber;
	private String lockNum;//锁号
	private Long checkPrint;//是否车辆签单打印
	private Long checkAlone;//是欧服单独送货
	
	private Long loadingbrigadeId;//装卸组ID
	
	
	private CarGoVo goVo;
	
	@Element(value = FiInterfaceProDto.class)
	private List<FiInterfaceProDto> fiList;
	
	private String overmemoType; //配载类型(取消实配)
	
	@Element(value = OprOvermemoDetail.class)
	private List<OprOvermemoDetail> oprDetails;
	private OprOvermemoDetail oprOvermomoDetail=new OprOvermemoDetail();
	
	@Override
	protected Object createNewInstance() {

		return new OprOvermemo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprOvermemoService;
	}

	@Override
	public Object getModel() {

		return this.oprOvermemo;
	}

	@Override
	public void setModel(Object obj) {

		this.oprOvermemo = (OprOvermemo) obj;
	}
	
	public String getCarArriveIds() {
		return carArriveIds;
	}

	public void setCarArriveIds(String carArriveIds) {
		this.carArriveIds = carArriveIds;
	}
	
	public String getCarArriveType() {
		return carArriveType;
	}

	public void setCarArriveType(String carArriveType) {
		this.carArriveType = carArriveType;
	}
	
	public String getOrderbyName() {
		return orderbyName;
	}

	public void setOrderbyName(String orderbyName) {
		this.orderbyName = orderbyName;
	}
	

	public List<OprOvermemoDetail> getOprDetails() {
		return oprDetails;
	}

	public void setOprDetails(List<OprOvermemoDetail> oprDetails) {
		this.oprDetails = oprDetails;
	}
	
	/**
	 * @return the fiList
	 */
	public List<FiInterfaceProDto> getFiList() {
		return fiList;
	}

	/**
	 * @param fiList the fiList to set
	 */
	public void setFiList(List<FiInterfaceProDto> fiList) {
		this.fiList = fiList;
	}

	/**
	 * @return the routeNumber
	 */
	public Long getRouteNumber() {
		return routeNumber;
	}

	/**
	 * @param routeNumber the routeNumber to set
	 */
	public void setRouteNumber(Long routeNumber) {
		this.routeNumber = routeNumber;
	}
	
	

	public Long getLoadingbrigadeId() {
		return loadingbrigadeId;
	}

	public void setLoadingbrigadeId(Long loadingbrigadeId) {
		this.loadingbrigadeId = loadingbrigadeId;
	}

	/**
	 * 到车确认首页
	 * @author LiuHao
	 * @return
	 */
	public String carInput(){
		return "carArriveInput";
	}
	
	
	/**
	 * 撤销实配
	 * @author shuw
	 * @return
	 */
	public String delOprOvermemo(){
		try {
			List<Long> list =getPksByIds();
    		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
    		Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
			oprOvermemoService.deleteOprOvermemo(list,bussDepartId,overmemoType);
			
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("撤销实配成功！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	
	
	
	/**
	 * 卸车开始
	 * @return
	 */
	public String carUpload(){
		try {
			if(oprOvermemoService.carUpload(this.getRouteNumber())){
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("操作成功!");
			}
			else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("操作失败:只能对车辆状态为已到车确认的进行卸车操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * 卸车结束
	 * @return
	 */
	public String carEndUpload(){
		try {
			oprOvermemoService.carEndUpload(getId(),this.getRouteNumber());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	
	/**
	 * 到车确认撤销
	 * @return
	 */
	public String carUploadReturn(){
		try {
			if(oprOvermemoService.isCarUpload(this.getRouteNumber())){
				oprOvermemoService.carUploadReturn(this.getRouteNumber());
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("操作成功!");
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("操作失败:车辆状态不是已到车确认或者该货物已点到!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	/**
	 * 到车确认
	 * @return
	 */
	public String carArriveConfirm(){
		try {
			if(this.getRouteNumber()==0){
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("操作失败,失败原因:数据错误，车次号不能为零！");
				return RELOAD;
			}else{
				User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
				oprOvermemoService.carArriveConfirm(this.getRouteNumber(), orderbyName,user);
				super.getValidateInfo().setSuccess(true);
				super.getValidateInfo().setMsg("操作成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}
		return "reload";
	}
	/**
	 * 判断是否能做到车确认操作
	 * @return
	 */
	public String isCarArriveConfirm(){
		try {
			if(oprOvermemoService.isCarArriveConfirm(this.getRouteNumber())){
				super.getValidateInfo().setSuccess(true);
			}else{
				super.getValidateInfo().setMsg("操作失败:只能对车辆状态为已发车的做到车确认!");
				super.getValidateInfo().setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return "reload";
		}return "reload";
	}
	/**
	 * 车辆签单打印
	 */
	public String printMsg(){
		return "reload";
	}
	/**
	 * 判断是否能够打印签单
	 */
	public String isPrintMsg(){
		try {
			User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
			oprOvermemoService.printMsg(this.getRouteNumber());
			super.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			super.getValidateInfo().setMsg( e.getMessage());
			super.getValidateInfo().setSuccess(false);
			addError(e.getLocalizedMessage(), e);
		}
		return "reload";
	}
	
	/**
	 * 手动生成交接单
	 */
	public String handAddConfirm(){
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		try {
			/*
			Set<OprOvermemoDetail> set=new HashSet<OprOvermemoDetail>(oprDetails);
			oprOvermemo.setStatus(Long.valueOf(0));
			oprOvermemo.setCreateName(user.get("name").toString());
			oprOvermemo.setCreateTime(new Date());
			oprOvermemo.setEndDepartId(Long.valueOf(user.get("bussDepart")+""));
			Iterator<OprOvermemoDetail> iter=set.iterator();
			while(iter.hasNext()){
				OprOvermemoDetail opr=iter.next();
				if(opr.getDno()==0){
					opr.setDno(oprOvermemoService.getNewDno());
					opr.setStatus(Long.valueOf(0));
				}else{
					OprStatus os = oprStatusService.findBy("dno", opr.getDno()).get(0);
					os.setAirportOutcarStatus(1L);
					os.setAirportOutcarTime(oprOvermemo.getStartTime());
				}
				opr.setCreateName(user.get("name").toString());
				opr.setCreateTime(new Date());
				opr.setOprOvermemo(oprOvermemo);
			}
			oprOvermemo.setRouteNumber(oprOvermemoService.findRouteNumberSeq());
			oprOvermemo.setOprOvermemoDetails(set);
			oprOvermemoService.save(oprOvermemo);*/
			oprOvermemoService.handAddOpr(oprOvermemo, oprDetails, user,fiList,loadingbrigadeId);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作出错");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
			return RELOAD;
		}
		return RELOAD;
	}
	
	/**
	 * 通过车牌号去判断是否有多个交接单（czl）
	 * @return
	 */
	public String findByCar(){
		
		try{
			String carId=ServletActionContext.getRequest().getParameter("carId");
			String overmemoId=ServletActionContext.getRequest().getParameter("overmemoId");
			
			Long lcarId=new Long(0);
			Long lovermemoId=new Long(0);
			if(carId!=null && !carId.equals("")){
				lcarId=Long.parseLong(carId);
			}
			if(overmemoId!=null && !overmemoId.equals("")){
				lovermemoId=Long.parseLong(overmemoId);
			}
			
			if(carId!=null){
				Integer size=this.oprOvermemoService.findMemoBy(lcarId,lovermemoId);
				Struts2Utils.renderJson(size);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 发车确认被车辆签单
	 * @return
	 */
	public String printOutCar(){
	    try {
	    	oprOvermemoService.printMsg(getId());
	    	super.getValidateInfo().setSuccess(true);
    		getValidateInfo().setMsg("数据保存成功！");
    		addMessage("数据保存成功！");
        } catch (Exception e) {
        	addError(e.getLocalizedMessage(), e);
        	getValidateInfo().setMsg(e.getMessage());
        	super.getValidateInfo().setSuccess(false);
        }
		return RELOAD;
	}
	
	/**
	 * 发车确认保存
	 * @return
	 */
	public String saveOutCar(){
	    try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), getPrivilege(), null, getContextMap())){
        		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
        		Long bussDepartId = Long.parseLong(user.get("bussDepart")+"");
        		List<Long> list =getPksByIds();
        		this.goVo.setCheckAlone(checkAlone==null?0l:checkAlone);
        		this.goVo.setCheckPrint(checkPrint);
        		this.goVo.setLockNum(lockNum);
        		this.goVo.setBussDepartId(bussDepartId);
        		Long routeNumber = oprOvermemoService.saveOprSignRouteAndOvem(goVo,list);
        		
        		getValidateInfo().setMsg("数据保存成功！");
        		getValidateInfo().setValue(routeNumber+"");
        		addMessage("数据保存成功！");
        		
        		//向EDI写入数据
        		try{
        			this.oprOvermemoService.insertEdiDataService(goVo, list);
        		}catch (Exception e) {
        			//addError("写入EDI失败！", e);
        		}
        	}else{
        		getValidateInfo().setSuccess(false);
        		getValidateInfo().setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
        } catch (Exception e) {
        	addError(e.getLocalizedMessage(), e);
        }
        
		return RELOAD;
	}
	
	public String ovemInput(){
		return "ovemInput";
	}
	
	public String outCar(){
		return "outCar";
	}

	public String getOvermemoType() {
		return overmemoType;
	}

	public void setOvermemoType(String overmemoType) {
		this.overmemoType = overmemoType;
	}
	public String findStartDepart(){
		this.setPageConfig();
		try {
			oprOvermemoService.findStartDepart(this.getPages());
		} catch (Exception e) {
			e.printStackTrace();
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("数据保存失败！");
            addError("数据保存失败！", e);
		}
		return LIST;
	}

	public String getLockNum() {
		return lockNum;
	}

	public void setLockNum(String lockNum) {
		this.lockNum = lockNum;
	}

	public Long getCheckPrint() {
		return checkPrint;
	}

	public void setCheckPrint(Long checkPrint) {
		this.checkPrint = checkPrint;
	}

	public Long getCheckAlone() {
		return checkAlone;
	}

	public void setCheckAlone(Long checkAlone) {
		this.checkAlone = checkAlone;
	}

	public CarGoVo getGoVo() {
		return goVo;
	}

	public void setGoVo(CarGoVo goVo) {
		this.goVo = goVo;
	}
	
}
