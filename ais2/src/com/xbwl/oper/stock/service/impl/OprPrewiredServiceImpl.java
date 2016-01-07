package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.Customer;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;
import com.xbwl.oper.stock.dao.IOprPrewiredDao;
import com.xbwl.oper.stock.dao.IOprReturnGoodsDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprPrewiredService;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;
import com.xbwl.oper.stock.vo.OprPrewiredVo;
import com.xbwl.rbac.dao.IDepartDao;
import com.xbwl.sys.service.ICustomerService;

/**
 * author shuw
 * time 2011-7-19 上午11:13:18
 */
 
/**
 * @author Administrator
 *
 */
@Service("oprPrewiredServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprPrewiredServiceImpl   extends BaseServiceImpl<OprPrewired, Long>
	implements IOprPrewiredService{

	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;

	@Resource(name="oprPrewiredHibernateDaoImpl")
	private IOprPrewiredDao oprPrewiredDao;
	
	@Resource(name="oprOvermemoHibernateDaoImpl")
	private IOprOvermemoDao oprOvermemoDao;

	@Resource(name="oprStockDaoImpl")
	private IOprStockDao oprStockDao;
	
	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name = "oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;

	@Resource(name = "oprReturnGoodsHibernateDaoImpl")
	private IOprReturnGoodsDao oprReturnGoodsDao;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	@Value("${oprPrewiredServiceImpl.log_sureCargo}")
	private Long log_sureCargo ;
	
	@Value("${oprPrewiredServiceImpl.log_delBeforeCargo}")
	private Long log_delBeforeCargo ;  //取消预配

	@Value("${oprReceiptServiceImpl.log_receiptOutStore}")
	private Long log_receiptOutStore ;  //回单出库
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	@Override
	public IBaseDAO<OprPrewired, Long> getBaseDao() {
		return oprPrewiredDao;
	}
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface iFiInterface;

	@Resource(name="departHibernateDaoImpl")
    private IDepartDao departDao;
	
	/**
	 *  保存到部门交接明细表，并修改库存，出库状态
	 * @see com.xbwl.oper.stock.service.IOprPrewiredService#saveOvemByIds(java.util.List, java.util.Map)
	 */
	@ModuleName(value="货物实配保存",logType=LogType.buss)
	public Long saveOvemByIds(List<OprPrewiredVo> aa, Map fileMap,String dnos) throws Exception  {
		
				Long totalDno = (Long)fileMap.get("totalDno");
				Long totalNum = (Long)fileMap.get("totalNum");
				Double  totalWeight = (Double)fileMap.get("totalWeight");
				Long departId = (Long)fileMap.get("departId");  //最终去向
				String departName = (String)fileMap.get("departName");  //最终去向名字
				Long bussDepartId = (Long)fileMap.get("bussDepartId");
				String loadingbrigade = (String)fileMap.get("loadingbrigade");
				String loadingbrigadeId = fileMap.get("loadingbrigadeId").toString();
				String dispatchGroup = (String) fileMap.get("dispatchGroup");
				String mode = (String)fileMap.get("mode"); 
				String username=(String)fileMap.get("username");
				
				Set<Long> dnoSet = new HashSet<Long>();  //同一个预配货物，其他配送单状态改为0
				if(!"".equals(dnos)&&null!=dnos){
				        String[] idsValue = dnos.split("\\,");
				        for (String delId : idsValue) {
				        	boolean flag=true;
				        	for(OprPrewiredVo oprPrewiredVo:aa){
				        		Long sLong =oprPrewiredVo.getDno();
				        		if(sLong.equals(Long.valueOf(delId))){
				        				flag=false;
				        		}
				        	}
				        	if(flag){
				        		dnoSet.add(Long.valueOf(delId));
				        	}
				        }
				        for(Long dnoLong:dnoSet){
				        	List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",dnoLong);
				        	OprStatus osu= list2.get(0);
				        	//如果有预配，则写撤销预配的日志 0,未出库，1，为已出库，2，异常出库，3，已预配
				        	if(osu.getOutStatus().equals(3l)){
				        		oprHistoryService.saveLog(dnoLong, "取消预配，未预配走的货物" , log_delBeforeCargo);
				        	}
				        	
				        	if("部门交接".equals(mode)){
							  	osu.setDepartOvermemoStatus(0l);
							  	// osu.setReachStatus(0l);//修改点到状态为未点到
							  	oprStatusDao.save(osu);
				        	}else{
				        		osu.setOutStatus(0l);
				        		osu.setOutTime(new Date());
				        		oprStatusDao.save(osu);
				        	}
				        }
				}
		       
				OprOvermemo  oprOvermemo = new OprOvermemo();
				oprOvermemo.setTotalTicket(totalDno);
				oprOvermemo.setTotalWeight(totalWeight);
				oprOvermemo.setTotalPiece(totalNum);
				oprOvermemo.setStartDepartId(bussDepartId);
				SysDepart sysDepart =departDao.get(bussDepartId);
				oprOvermemo.setStartDepartName(sysDepart.getDepartName());
				oprOvermemo.setEndDepartId(departId);
				oprOvermemo.setEndDepartName(departName);
			    oprOvermemo.setOvermemoType(mode);
			    oprOvermemo.setStatus(7l);
			    
			    Set<OprOvermemoDetail>set = oprOvermemo.getOprOvermemoDetails();
			    if (set==null) {
					set=new HashSet<OprOvermemoDetail>();
				}
			    for(OprPrewiredVo opwdVo:aa ){
			    	if(opwdVo.getPiece()==null){
			    		opwdVo.setPiece(0l);
			    	}
			    	if(opwdVo.getWId()!=null){
			    			OprPrewired oprPrewired = oprPrewiredDao.get(opwdVo.getWId());
			    			if(oprPrewired.getStatus()==0l){
			    					throw new ServiceException("预配单号"+opwdVo.getWId()+"已经作废，请重新预配！");
			    			}
			    		
			    			if(!"专车".equals(mode)){
					    			List<OprStock> list = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",opwdVo.getDno(),bussDepartId);
					    			if(list.size()==0){
					    				throw new ServiceException("配送单号为 "+opwdVo.getDno()+" 货物无库存，请做入库点到处理。");
					    			}
					    			if(list.get(0).getPiece()==0l){
					    				throw new ServiceException("配送单号为 "+opwdVo.getDno()+" 货物库存件数为0，无法出库。");
					    			}
			    			}
			    	}
			    	
			    	 OprOvermemoDetail oprOverDetail = new OprOvermemoDetail();
					 OprFaxIn oIn=oprFaxInDao.get(opwdVo.getDno());
					 
						//判断实配方式和实配的货物是否一致
						if("中转".equals(mode) || ("外发").equals(mode)){
							if(!oIn.getDistributionMode().equals(mode)){
								 throw new ServiceException("预配方式不一样('"+oIn.getDistributionMode()+"')，无法进行实配");
							}
						}else if("专车".equals(mode) || ("市内送货").equals(mode)){
							if((!"新邦".equals(oIn.getDistributionMode()))&&oIn.getSonderzug()!=1l){
								 throw new ServiceException("预配方式不一样('"+oIn.getDistributionMode()+"')，无法进行实配");
							}
						}   // 部门交接的货物，都可以实配
					 
					 oIn.setRealGoWhere(new StringBuffer(mode).append(":").append(departName).append("，").append(opwdVo.getTelPhone()).append("").toString());
					 if("中转".equals(mode)||"外发".equals(mode)){   // 更新传真录入表
						 oIn.setGowhere(departName);
						 oIn.setGoWhereId(departId);
					 }
					 
					 oprOverDetail.setReNum(opwdVo.getReachNum());
					 oprOverDetail.setDno(oIn.getDno());
					 oprOverDetail.setRealPiece(opwdVo.getRealPiece());
					 oprOverDetail.setPiece(oIn.getPiece());
					 oprOverDetail.setCusId(oIn.getCusId());
					 oprOverDetail.setCpName(oIn.getCpName());
			
					 if(opwdVo.getRealPiece()<opwdVo.getPiece()){
						 double  d =Double.parseDouble(opwdVo.getRealPiece().toString());
						 double  cqw =Double.parseDouble(oIn.getCusWeight().toString());
						 double tot =DoubleUtil.mul(DoubleUtil.div(cqw,d, 1),opwdVo.getRealPiece());
						 oprOverDetail.setWeight(tot);
						 List<OprStock> list = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",opwdVo.getDno(),bussDepartId);
						 list.get(0).setWeight(tot);
						 oprStockDao.save(list.get(0));		
			    		
					 }else{
						 oprOverDetail.setWeight(oIn.getCqWeight());
					 }
					 
					 if(opwdVo.getRealPiece()<oIn.getPiece()){
						 oprOverDetail.setIsException(1l);
					 }else {
						 oprOverDetail.setIsException(0l);
					 }
					 oprOverDetail.setSubNo(oIn.getSubNo());
					 oprOverDetail.setFlightNo(oIn.getFlightNo());
					 oprOverDetail.setConsignee(oIn.getConsignee());
					 oprOverDetail.setAddr(oIn.getAddr());
					 oprOverDetail.setDistributionMode(oIn.getDistributionMode());
					 oprOverDetail.setTakeMode(oIn.getTakeMode());
					 oprOverDetail.setGoods(oIn.getGoods());
					 oprOverDetail.setFlightMainNo(oIn.getFlightMainNo());
					 oprOverDetail.setStatus(0l);
					 oprOverDetail.setOprOvermemo(oprOvermemo);
					 set.add(oprOverDetail);
				 }
			    
			    for(OprPrewiredVo opwdVo:aa ){
			    	if(opwdVo.getWId()!=null){
			    		OprPrewired	oprPrewired= oprPrewiredDao.get(opwdVo.getWId());
			    		oprPrewired.setStatus(0l);
			    		oprPrewiredDao.save(oprPrewired);
			    	}
			    }
			    
			     oprOvermemo.setOprOvermemoDetails(set);
			     oprOvermemo.setTelPhone(aa.get(0).getTelPhone());
				 oprOvermemoDao.save(oprOvermemo);
				 
				 Set<OprOvermemoDetail>setDetail=oprOvermemo.getOprOvermemoDetails(); 
				 for(OprOvermemoDetail oprOverDetail:setDetail){
					 oprHistoryService.saveLog(oprOverDetail.getDno(), mode+"实配出库，实配单号："+oprOvermemo.getId()+",实配件数："+oprOverDetail.getRealPiece()+",实配去向："+oprOvermemo.getEndDepartName() , log_sureCargo);
					 if((null!=loadingbrigade && loadingbrigade.trim().length()>0) || (null!=dispatchGroup && dispatchGroup.trim().length()>0)){//装卸组或者分拨组有一个就保存
						 try{
							 saveOprLoadingbrigade(oprOverDetail, oprOvermemo.getId(), loadingbrigadeId, dispatchGroup);  // 保存货量，装卸组和分拨组
						 }catch (Exception e) {
							 throw new ServiceException("保存到装卸组货量表失败！");
						 }
					 }
				 }
				 
				 
				 if(!"部门交接".equals(mode)){
					 saveFiInterface(aa,mode,bussDepartId,oprOvermemo.getId(),departId,departName,username);  
				 }
				 
				 updateOprStockAndStatus(aa,bussDepartId,mode);                  // 修改出库状态

				 //回写回单表的出库
				 List<OprReceipt> receiptList = null;
				 OprReceipt receipt = null;
				 Date dt = new Date();
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				 for(int i=0;i<aa.size();i++){
					 receiptList = this.oprReceiptService.findBy("dno", aa.get(i).getDno());
					 if(null!=receiptList && receiptList.size()>0){
						 receipt = receiptList.get(0);
						 if(aa.get(i).getReachNum()!=null&&aa.get(i).getReachNum()!=0){
							 receipt.setOutStockMan(username);
							 receipt.setOutStockNum(aa.get(i).getReachNum());
							 receipt.setOutStockStatus(1l);
							 receipt.setOutStockTime(new Date());
							 receipt.setCurStatus("已出库");
							 this.oprHistoryService.saveLog(receipt.getDno(), username+"在"+sdf.format(dt)+"出库回单"+aa.get(i).getReachNum()+"份", log_receiptOutStore);
							 this.oprReceiptService.save(receipt);
						 }
					 }
				 }
				 
				 //回写返货表， 修改返货状态
				 List<OprReturnGoods> returnList= null;
				 OprReturnGoods returnGoods =null;
				 for(int i=0;i<aa.size();i++){
					 returnList = this.oprReturnGoodsDao.find("from OprReturnGoods where dno=? and status=?", aa.get(i).getDno(),2l);//查询返货状态为入库的配送单
					 if(null!=returnList && returnList.size()>0){
						 returnGoods = returnList.get(0);
						 returnGoods.setStatus(3l);//修改返货状态为出库
						 this.oprReturnGoodsDao.save(returnGoods);
					 }
				 }
			return oprOvermemo.getId();
	}
	
	/**
	 * 实配出库调 用财务接口
	 * @param aa  实配的货物
	 * @param mode 实配方式 
	 * @param bussDepart 业务部门
	 * @param ovemID  实配表ID
	 * @param departId    实配去向
	 * @param departName
	 * @param username  配载员
	 * @throws Exception
	 */
	public void saveFiInterface(List<OprPrewiredVo> aa,String mode,Long bussDepart,Long ovemID,Long departId,String departName,String username) throws Exception{
		List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
		for(OprPrewiredVo oprPrewiredVo:aa){
			OprFaxIn oIn=oprFaxInDao.get(oprPrewiredVo.getDno());
			
	  		List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",oIn.getDno());
			if(list2.size()==0){
				throw new ServiceException("货物的状态记录不存在");
			}
			OprStatus osu= list2.get(0);
			if(osu.getIsCreateFi()!=null&&osu.getIsCreateFi()==1l){  //如果已挂账
				continue;
			}

			Customer customer=null; 
			if("中转".equals(mode)||"外发".equals(mode)){
				customer=customerService.get(departId);
			}
			
			if(oIn.getSonderzugPrice()!=null&&oIn.getSonderzugPrice()!=0){
				FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
				fiInterfaceProDto.setOutStockMode(mode);
				fiInterfaceProDto.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto.setSettlementType(1l);
				fiInterfaceProDto.setDocumentsType("收入");
				fiInterfaceProDto.setDocumentsSmalltype("配送单");
				fiInterfaceProDto.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto.setAmount(oIn.getSonderzugPrice());
				fiInterfaceProDto.setCostType("到付专车费");
				fiInterfaceProDto.setDepartId(bussDepart);
				fiInterfaceProDto.setIncomeDepartId(oIn.getInDepartId());
				fiInterfaceProDto.setSourceData("实配单");
				fiInterfaceProDto.setSourceNo(ovemID);
				if("市内送货".equals(mode)||"专车".equals(mode)){
					fiInterfaceProDto.setCollectionUser(departName);
					fiInterfaceProDto.setContacts(departName);
				}else{
					fiInterfaceProDto.setGocustomerId(departId);
					fiInterfaceProDto.setCollectionUser(username);
					fiInterfaceProDto.setGocustomerName(departName);
					fiInterfaceProDto.setCustomerId(departId);
					fiInterfaceProDto.setCustomerName(departName);
				}
				fiInterfaceProDto.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(fiInterfaceProDto);
			}
			/*
			 * 如果是月结的供应商并且有到付提送费才写入财务接口
			 * LiuHao 03-20
			 */
			
			if(customer!=null&&"月结".equals(customer.getSettlement())){
				if(oIn.getConsigneeFee()!=null&&oIn.getConsigneeFee()!=0.0){
					FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
					fiInterfaceProDto4.setOutStockMode(mode);
					fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
					fiInterfaceProDto4.setSettlementType(1l);
					fiInterfaceProDto4.setDocumentsType("收入");
					fiInterfaceProDto4.setDocumentsSmalltype("配送单");
					fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
					fiInterfaceProDto4.setAmount(oIn.getConsigneeFee());
					fiInterfaceProDto4.setCostType("到付提送费");
					fiInterfaceProDto4.setDepartId(bussDepart);
					fiInterfaceProDto4.setSourceData("实配单");
					fiInterfaceProDto4.setSourceNo(ovemID);
					fiInterfaceProDto4.setGocustomerId(departId);
					fiInterfaceProDto4.setIncomeDepartId(oIn.getInDepartId());
					fiInterfaceProDto4.setCollectionUser(username);
					fiInterfaceProDto4.setGocustomerName(departName);
					fiInterfaceProDto4.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto4.setDepartName(user.get("rightDepart")+"");
					listfiInterfaceDto.add(fiInterfaceProDto4);
				}
			}else if(customer!=null&&"现结".equals(customer.getSettlement())){
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("收入");
				fiInterfaceProDto4.setDocumentsSmalltype("配送单");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee()==null?0:oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("到付提送费");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("实配单");
				fiInterfaceProDto4.setSourceNo(ovemID);
				fiInterfaceProDto4.setGocustomerId(departId);
				fiInterfaceProDto4.setIncomeDepartId(oIn.getInDepartId());
				fiInterfaceProDto4.setCollectionUser(username);
				fiInterfaceProDto4.setGocustomerName(departName);
				fiInterfaceProDto4.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				fiInterfaceProDto4.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}else{
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("收入");
				fiInterfaceProDto4.setDocumentsSmalltype("配送单");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee()==null?0:oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("到付提送费");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("实配单");
				fiInterfaceProDto4.setIncomeDepartId(oIn.getInDepartId());
				fiInterfaceProDto4.setSourceNo(ovemID);
				fiInterfaceProDto4.setCollectionUser(departName);
				fiInterfaceProDto4.setContacts(departName);
				fiInterfaceProDto4.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				fiInterfaceProDto4.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}
			/*
			if(customer!=null&&"月结".equals(customer.getSettlement())&&(oIn.getConsigneeFee()!=null||oIn.getConsigneeFee()!=0)){
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("收入");
				fiInterfaceProDto4.setDocumentsSmalltype("配送单");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("到付提送费");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("实配单");
				fiInterfaceProDto4.setSourceNo(ovemID);
				if("市内送货".equals(mode)||"专车".equals(mode)){
					fiInterfaceProDto4.setCollectionUser(departName);
					fiInterfaceProDto4.setContacts(departName);
					
				}else{
					fiInterfaceProDto4.setCustomerId(departId);
					fiInterfaceProDto4.setCollectionUser(username);
					fiInterfaceProDto4.setCustomerName(departName);
				}
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}else{
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("收入");
				fiInterfaceProDto4.setDocumentsSmalltype("配送单");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee()==null?0:oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("到付提送费");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("实配单");
				fiInterfaceProDto4.setSourceNo(ovemID);
				if("市内送货".equals(mode)||"专车".equals(mode)){
					fiInterfaceProDto4.setCollectionUser(departName);
					fiInterfaceProDto4.setContacts(departName);
					
				}else{
					fiInterfaceProDto4.setCustomerId(departId);
					fiInterfaceProDto4.setCollectionUser(username);
					fiInterfaceProDto4.setCustomerName(departName);
				}
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}*/
				if(oIn.getCusValueAddFee()!=null&&oIn.getCusValueAddFee()!=0){
					FiInterfaceProDto fiInterfaceProDto2 = new FiInterfaceProDto();
					fiInterfaceProDto2.setOutStockMode(mode);
					fiInterfaceProDto2.setDno(oprPrewiredVo.getDno());
					fiInterfaceProDto2.setSettlementType(1l);
					fiInterfaceProDto2.setDocumentsType("收入");
					fiInterfaceProDto2.setDocumentsSmalltype("配送单");
					fiInterfaceProDto2.setDocumentsNo(oprPrewiredVo.getDno());
					fiInterfaceProDto2.setAmount(oIn.getCusValueAddFee());
					fiInterfaceProDto2.setCostType("到付增值费");
					fiInterfaceProDto2.setDepartId(bussDepart);
					fiInterfaceProDto2.setIncomeDepartId(oIn.getInDepartId());
					fiInterfaceProDto2.setSourceData("实配单");
					fiInterfaceProDto2.setSourceNo(ovemID);
					fiInterfaceProDto2.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto2.setDepartName(user.get("rightDepart")+"");
					if("市内送货".equals(mode)||"专车".equals(mode)){
						fiInterfaceProDto2.setCollectionUser(departName);
						fiInterfaceProDto2.setContacts(departName);
					}else{
						fiInterfaceProDto2.setGocustomerId(departId);
						fiInterfaceProDto2.setCollectionUser(username);
						fiInterfaceProDto2.setGocustomerName(departName);
					}
					listfiInterfaceDto.add(fiInterfaceProDto2);
				}

				if(oIn.getPaymentCollection()!=null&&oIn.getPaymentCollection()!=0){
					FiInterfaceProDto fiInterfaceProDto3 = new FiInterfaceProDto();
					fiInterfaceProDto3.setOutStockMode(mode);
					fiInterfaceProDto3.setDno(oprPrewiredVo.getDno());
					fiInterfaceProDto3.setSettlementType(1l);
					fiInterfaceProDto3.setDocumentsType("代收货款");
					fiInterfaceProDto3.setDocumentsSmalltype("配送单");
					fiInterfaceProDto3.setDocumentsNo(oprPrewiredVo.getDno());
					fiInterfaceProDto3.setAmount(oIn.getPaymentCollection());
					fiInterfaceProDto3.setCostType("代收货款");
					fiInterfaceProDto3.setDepartId(bussDepart);
					fiInterfaceProDto3.setIncomeDepartId(oIn.getInDepartId());
					fiInterfaceProDto3.setSourceData("实配单");
					fiInterfaceProDto3.setSourceNo(ovemID);
					fiInterfaceProDto3.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto3.setDepartName(user.get("rightDepart")+"");
					if("市内送货".equals(mode)||"专车".equals(mode)){
						fiInterfaceProDto3.setCollectionUser(departName);
						fiInterfaceProDto3.setContacts(departName);
					}else{
						fiInterfaceProDto3.setGocustomerId(departId);
						fiInterfaceProDto3.setCollectionUser(username);
						fiInterfaceProDto3.setGocustomerName(departName);
					}
					listfiInterfaceDto.add(fiInterfaceProDto3);
				}
				oIn.setDistributionDepartId(bussDepartId);  //更新配送部门
				osu.setIsCreateFi(1l);
				oprStatusDao.save(osu);
		 }
		/*
		 * 如果到付专车费、到付提送费、到付增值费、代收货款 有一个不为空时，才调用财务接口
		 * LiuHao 03-20
		 */
		iFiInterface.outStockToFi(listfiInterfaceDto);
		 
	}
	
	/**
	 * @param aa
	 * @param id  保存装卸组，分拨组数据
	 * @param loadingbrigadeId
	 * @param dispatchGroup
	 */
	public void saveOprLoadingbrigade(OprOvermemoDetail detail,Long overmemoNo,String loadingbrigadeId,String dispatchGroup)throws Exception{
		List<OprLoadingbrigadeWeight> weightList = new ArrayList<OprLoadingbrigadeWeight>();
		  OprFaxIn oIn=oprFaxInDao.get(detail.getDno());
		  OprLoadingbrigadeWeight olbWeight  = new OprLoadingbrigadeWeight();
		  if(null!=loadingbrigadeId && !"".equals(loadingbrigadeId)){//设置装卸组
			  olbWeight.setLoadingbrigadeId(Long.parseLong(loadingbrigadeId));
		  }
		  if(null!=dispatchGroup && !"".equals(dispatchGroup)){//设置分拨组
			  olbWeight.setDispatchId(Long.parseLong(dispatchGroup));
		  }
		  olbWeight.setDno(detail.getDno());
		  olbWeight.setOvermemoNo(overmemoNo);
		  olbWeight.setGoods(oIn.getGoods());
		  olbWeight.setPiece(detail.getRealPiece());
		  olbWeight.setBulk(oIn.getBulk());
		  olbWeight.setLoadingbrigadeType(1l);
		  olbWeight.setWeight(detail.getWeight());
		  olbWeight.setOvermemoDetailId(detail.getId());
		  
		  weightList.add(olbWeight);
	      oprLoadingbrigadeWeightService.saveLoadingWeight(weightList,LoadingbrigadeTypeEnum.ZHUANGHUO);
	}
	
	/**
	 * @param aa 修改库存，改变出库状态
	 */
	public void updateOprStockAndStatus(List<OprPrewiredVo> aa,Long bussDepartId,String  mode){
		  for(OprPrewiredVo opwdVo:aa ){
			  	OprFaxIn oIn=oprFaxInDao.get(opwdVo.getDno());
			  	OprStock oStock=null;
			  	OprStatus osu=null;
			  	
			  	List<OprStock> list = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",opwdVo.getDno(),bussDepartId);
		  		if(list.size()>0){
		  			oStock=list.get(0);
		  		}else{
		  			if(!"专车".equals(mode)&&oIn.getSonderzug()!=1l){
		  				throw new ServiceException("非专车货物必须要有库存");
		  			}else{
						oStock=new OprStock();
						oStock.setDno(oIn.getDno());
						oStock.setDepartId(bussDepartId);
						oStock.setFlightNo(oIn.getFlightNo());
						oStock.setFlightMainNo(oIn.getFlightMainNo());
						oStock.setSubNo(oIn.getSubNo());
						oStock.setAddr(oIn.getAddr());
						oStock.setConsignee(oIn.getConsignee());
						oStock.setPiece(0l);
						oStock.setWeight(oIn.getCusWeight());
		  			}
		  		}

		  		List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",opwdVo.getDno());
				if(list2.size()==0){
					throw new ServiceException("货物的状态记录不存在");
				}else{
					osu= list2.get(0);
				}
				
				if(osu.getOutStatus()!=null&&osu.getOutStatus()==1l){
					throw new ServiceException("货物已经出库，不能实配");
				}
				if("专车".equals(mode)){   //专车是负库存
					osu.setOutStatus(1l);
					osu.setOutTime(new Date());
					osu.setReturnStatus(0l);
				}else if("部门交接".equals(mode)){
					osu.setDepartOvermemoStatus(1l);
					// osu.setReachStatus(0l);//修改点到状态为未点到
					osu.setReturnStatus(0l);
					if(oStock.getPiece()==opwdVo.getRealPiece()){
						oprStockDao.delete(oStock.getId());
					}else{
						if(oStock.getPiece()==0l){
							throw new ServiceException("库存为零，不能实配");
						}
					}
				}else{
				  	String sql = "select sum(t.return_num) returnNum from opr_return_goods t where t.d_no=? and t.return_depart = ?  ";
				  	Page page = new Page();  //计算返货件数
				  	page.setLimit(500);
				  	page =oprReturnGoodsDao.findPageBySql(page, sql, opwdVo.getDno(),bussDepartId	);
				  	List<Map>  listo = page.getResultMap();
					Long returnNum=0l;   //返货件数
					for(Map totalMap : listo ){
						returnNum = totalMap.get("RETURNNUM")==null?0l:Long.parseLong(String.valueOf(totalMap.get("RETURNNUM")));
					}
					
					StringBuffer sb = new StringBuffer();
					sb.append("select sum(oe.real_piece) sumPiece ")
						 .append(" from opr_overmemo t")
						 .append(" join opr_overmemo_detail oe on t.id = oe.overmemo")
						 .append(" where t.overmemo_type <> '机场接货' ")
						 .append("  and t.overmemo_type <> '外围接货' ")
						 .append("  and t.overmemo_type <> '部门交接' ")
						 .append("  and oe.d_no=? ")
						 .append("  and oe.depart_id = ?   ");
					Page page2 = new Page();  //计算返货件数
				  	page2.setLimit(500);
				  	page2 =oprReturnGoodsDao.findPageBySql(page2, sb.toString(), opwdVo.getDno(),bussDepartId	);
				  	List<Map>  listo2 = page2.getResultMap();
				  	Long sumPiece=0l;   //出库件数
					for(Map totalMap : listo2 ){
						sumPiece = totalMap.get("SUMPIECE")==null?0l:Long.parseLong(String.valueOf(totalMap.get("SUMPIECE")));
					}
					
					if(sumPiece-(oIn.getPiece()+returnNum)>=0){
						osu.setOutStatus(1l);
					}else{
						osu.setOutStatus(2l);
					}
					if(oStock.getPiece()==0l){
						throw new ServiceException("库存为零，不能实配");
					}
					osu.setReturnStatus(0l);
					osu.setOutTime(new Date());
				  	
				}
				oStock.setPiece(oStock.getPiece()-opwdVo.getRealPiece());
				if(null!=oStock.getId() && oStock.getPiece().equals(0l)){
					//如果没有库存则删除
					oprStockDao.delete(oStock);
				}else{
					oprStockDao.save(oStock);
				}
				oprStatusDao.save(osu);
		  }
	}
	
	@ModuleName(value="取消预配",logType=LogType.buss)
	public void deleteStatusByIds(List<Long> list) throws Exception{
		 for(Long id: list){
			 OprPrewired oprPrewired2=this.get(id);
			 if(oprPrewired2.getStatus()!=null&&oprPrewired2.getStatus()==0){
				 continue;
			 }
			 Set<OprPrewiredDetail>listDetail  = oprPrewired2.getOprPrewiredDetails();
			 for(OprPrewiredDetail oprPrewiredDetail : listDetail){
				 	List<OprStatus> listu =oprStatusDao.find(" from OprStatus o where o.dno=?  ",oprPrewiredDetail.getDNo());
				 	OprStatus osu= listu.get(0);
				 	if(osu.getOutStatus()==1l){  //已经出库了的货物，跳过去。
				 		continue;
				 	}
				 	if("部门交接".equals(oprPrewired2.getAutostowMode())){
				 		osu.setDepartOvermemoStatus(0l);
				 	}else{
				 		osu.setOutStatus(0l);
				 	}
				  	oprStatusDao.save(osu);
				  	oprHistoryService.saveLog(oprPrewiredDetail.getDNo(), "取消预配，预配单号："+oprPrewiredDetail.getOprPrewired().getId() , log_delBeforeCargo);
			 }
			 oprPrewired2.setStatus(0l);
			 save(oprPrewired2);
		 }
	}
	

	@ModuleName(value="实配信息汇总SQL拼接",logType=LogType.buss)
	public String getTotalDetail(String ids) {
		StringBuffer sb = new StringBuffer();
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" select count(dno) dno,sum(weight)  weight, ");
			sb.append(" sum(piece)  piece,sum(realPiece) realPiece, ");
			sb.append( " sum(consigneeFee) consigneeFee ,sum(cpFee) cpFee,sum(traFee) traFee,sum(paymentCollection) paymentCollection ");
			sb.append( " from ( ");
					sb.append( " select w.id as wId,w.depart_id as departId,w.autostow_mode as autostowMode,w.to_where as toWhere,  ");
							sb.append( " wd.d_no as dno,wd.consignee as consignee,wd.request as request,wd.weight as weight,wd.piece as piece,wd.addr as addr,wd.gowhere as goWhere, ");
							sb.append( " os.piece as realPiece, ot.OUT_STATUS outstatus, ");
							sb.append( " oi.receipt_type as receiptType,oi.consignee_rate as consigneeRate,oi.consignee_fee as consigneeFee,oi.town as town, ");
							sb.append( " oi.TRA_FEE as cpFee,oi.tra_fee as traFee,oi.payment_collection as paymentCollection, ");
							sb.append( "  ot.fee_audit_status as feeAuditStatus, ");
							sb.append( "   oe.print_num as printNum,oi.TRAFFIC_MODE trafficMode  ");
					sb.append( " from opr_prewired w,opr_prewired_detail wd,opr_fax_in oi,opr_stock os,opr_status ot,opr_receipt oe ");
					sb.append( "  where w.id = wd.opr_prewired_id ");
							sb.append( " and wd.d_no=oi.d_no ");
							sb.append( " and w.STATUS=1 ");  
							sb.append( " and wd.d_no = os.d_no(+) ");
							sb.append(" and os.DEPART_ID = :sdepartId");
							sb.append( " and wd.d_no = ot.d_no ");
							sb.append( " and wd.d_no = oe.d_no(+)) ");
							//REVIEW-ACCEPT 使用or代替in
							//FIXED
			sb.append( "where departId= :departId and (outstatus = 0  or  outstatus = 3 )");
			sb.append("AND   dno in (  "+ids+"  ) 	");
		}
		return sb.toString();
	}

	@ModuleName(value="实配查询SQL拼接",logType=LogType.buss)
	public String getAllDetail(Map filterMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ids = filterMap.get("ids").toString();
					sb.append( " select w.id as wId,w.depart_id as departId,w.autostow_mode as autostowMode,w.to_where as toWhere,  ");
							sb.append( " wd.d_no as dno,wd.consignee as consignee,wd.request as request,wd.weight as weight,wd.piece as piece,wd.addr as addr,wd.gowhere as goWhere, ");
							sb.append( " ot.OUT_STATUS outstatus,oi.remark remark,oi.flight_main_no, ");
							sb.append("  NVL( os.piece, 0 )  as realPiece,  NVL( os.piece, 0 )  as REALPIECE2, ");
							sb.append( " oi.receipt_type as receiptType,oi.consignee_rate as consigneeRate,oi.consignee_fee as consigneeFee,oi.town as town, ");
							sb.append( " oi.CONSIGNEE_FEE as cpFee,oi.tra_fee as traFee,oi.payment_collection as paymentCollection, ");
							sb.append( "  ot.fee_audit_status as feeAuditStatus, oi.cp_name cpName, ");
							sb.append( "   oe.print_num as printNum,oe.REACH_NUM reachNum,oi.TRAFFIC_MODE trafficMode  ");
					sb.append( " from opr_prewired w,opr_prewired_detail wd,opr_fax_in oi,opr_stock os,opr_status ot,opr_receipt oe ");
					sb.append( "  where w.id = wd.opr_prewired_id ");
							sb.append( " and wd.d_no=oi.d_no ");   
							sb.append( " and w.STATUS=1 ");  
							sb.append(" AND   oi.status  =  1 	");
							sb.append( " and wd.d_no = os.d_no(+) ");
							sb.append(" and os.DEPART_ID(+) =:departId2 ");
							sb.append( " and wd.d_no = ot.d_no ");
							sb.append( " and wd.d_no = oe.d_no(+) ");
							sb.append( " and w.depart_id= :departId ");
							sb.append(" and ot.OUT_STATUS in (0,3) ");
		
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND   w.id  in  ("+ids+")  ");
		}
		return sb.toString();
	}

	@ModuleName(value="实配（单票添加）SQL拼接",logType=LogType.buss)
	public String getInfoByDno(String requestStage) {
		StringBuffer sb = new StringBuffer();
		sb.append( " select  os.DEPART_ID departId, oi.DISTRIBUTION_MODE autostowMode,oi.d_no dno,oi.remark remark,oi.flight_main_no,");
				sb.append( "  oi.consignee as consignee ,oi.CUS_WEIGHT as weight,oi.piece as piece,oi.addr as addr,oi.gowhere as goWhere, ");
				sb.append(" os.piece as realPiece2, oi.SONDERZUG as sonderzug, t4.request as  request , oi.cp_name cpName,  ");
				sb.append( " os.piece as realPiece,ot.OUT_STATUS outstatus,oi.TAKE_MODE as TAKEMODE,");
				sb.append( " oi.receipt_type as receiptType,oi.consignee_rate as consigneeRate,oi.consignee_fee as consigneeFee,oi.town as town, ");
				sb.append( " oi.TRA_FEE as cpFee,oi.tra_fee as traFee,oi.payment_collection as paymentCollection, ");
				sb.append( "  ot.fee_audit_status as feeAuditStatus,oi.END_DEPART_ID as  ENDDEPARTID, oi.CUR_DEPART_ID as CURDEPARTID,    ");
				sb.append( "   oe.print_num as printNum,oe.REACH_NUM reachNum,oi.TRAFFIC_MODE trafficMode  ");
		sb.append( " from  opr_fax_in oi,opr_stock os ,  ");
		sb.append( "  opr_status ot,opr_receipt oe,  ");
		sb.append( " ( select   ai.d_no dno ,ai.request request,ai.request_stage  	");
		sb.append(" from   aisuser.OPR_REQUEST_DO ai   	");
		sb.append(" where   ai.request_stage = :requestStage )  t4  ");
		sb.append( "  where  ");
				sb.append( "  oi.d_no = os.d_no(+) ");
				sb.append( " and oi.d_no= ot.d_no ");
				sb.append(" AND   oi.status  =  1 	");
				sb.append(" and t4.dno(+)=oi.d_no ");
				sb.append( " and oi.d_no = oe.d_no(+) ");
				//REVIEW-ACCEPT 使用or代替in
				//FIXED
				
				sb.append( " and  os.DEPART_ID(+)= :departId  and (ot.OUT_STATUS = 0  or  ot.OUT_STATUS = 2 )  ");
				sb.append("  and oi.d_no = :dno ");
		return sb.toString();
	}

	@ModuleName(value="实配查询（部门交接）SQL拼接",logType=LogType.buss)
	public String getAllDetailByDepartId(Map filterMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ids=filterMap.get("ids").toString();
	/*	String toWhere =(String)filterMap.get("toWhere");
		Long feeTypeLong = (Long)filterMap.get("feeType");
		String ids = (String)filterMap.get("ids");
		Long dno = (Long)filterMap.get("dno");*/
		
		sb.append("  select w.id  as wId, ");
		sb.append(" w.depart_id   as departId, ");
		sb.append(" w.autostow_mode  as autostowMode, ");
		sb.append(" w.to_where  as toWhere, ");
		sb.append(" wd.d_no  as dno, ");
		sb.append(" wd.consignee  as consignee, ");
		sb.append(" wd.request  as request, ");
		sb.append(" wd.weight  as weight, ");
		sb.append(" wd.piece  as piece, ");
		sb.append(" wd.addr  as addr, ");
		sb.append(" wd.gowhere as goWhere, ");
		sb.append("  NVL( os.piece, 0 )   as realPiece, ");
		sb.append("  NVL( os.piece, 0 )  as realPiece2, ");
		sb.append(" ot.OUT_STATUS  outstatus, ");
		sb.append(" oi.receipt_type  as receiptType, ");
		sb.append(" oi.consignee_rate  as consigneeRate, ");
		sb.append(" oi.consignee_fee  as consigneeFee, ");
		sb.append(" oi.town  as town, ");
		sb.append(" oi.cp_name cpName,");
		sb.append(" oi.TRA_FEE  as cpFee, ");
		sb.append(" oi.tra_fee  as traFee, ");
		sb.append(" oi.payment_collection as paymentCollection, ");
		sb.append(" ot.fee_audit_status   as feeAuditStatus, ");
		sb.append(" oe.print_num  as printNum, ");
		sb.append(" oe.REACH_NUM reachNum , ");
		sb.append(" ot.DEPART_OVERMEMO_STATUS departstatus,oi.TRAFFIC_MODE trafficMode ,oi.flight_main_no ");
sb.append("  from opr_prewired  w, ");
			sb.append(" opr_prewired_detail wd, ");
			sb.append(" opr_fax_in   oi, ");
			sb.append(" opr_stock    os, ");
			sb.append(" opr_status   ot, ");
			sb.append(" opr_receipt  oe ");
sb.append(" where w.id = wd.opr_prewired_id ");
		sb.append("  and wd.d_no = oi.d_no ");
		sb.append("  and wd.d_no = os.d_no(+) ");
		sb.append("   and wd.d_no = ot.d_no ");
		sb.append("  and wd.d_no = oe.d_no(+)  ");
		sb.append("  and w.STATUS =1  ");
		sb.append(" AND   oi.status  =  1 	");
		// sb.append( " and ot.DEPART_OVERMEMO_STATUS = 3 ");
		sb.append("  and w.depart_id = :departId ");
		sb.append("  and os.DEPART_ID(+)  =:departId2  ");
		
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND    w.id  in  ("+ids+")  ");
		}
		return sb.toString();
	}

	@ModuleName(value="实配查询（专车）SQL拼接",logType=LogType.buss)
	public String getAllDetailByCar(Map filterMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ids = filterMap.get("ids").toString();

		sb.append("  select w.id  as wId, ");
				sb.append(" w.depart_id   as departId, ");
				sb.append(" w.autostow_mode  as autostowMode, ");
				sb.append(" w.to_where  as toWhere, ");
				sb.append(" wd.d_no  as dno, ");
				sb.append(" wd.consignee  as consignee, ");
				sb.append(" wd.request  as request, ");
				sb.append(" wd.weight  as weight, ");
				sb.append(" wd.piece  as piece, ");
				sb.append(" wd.addr  as addr, ");
				sb.append(" wd.gowhere as goWhere, ");
				sb.append(" NVL( os.piece, wd.piece)   as realPiece, ");
				sb.append(" NVL( os.piece, 0 ) as realPiece2, ");
				sb.append(" ot.OUT_STATUS  outstatus, ");
				sb.append(" oi.receipt_type  as receiptType, ");
				sb.append(" oi.consignee_rate  as consigneeRate, ");
				sb.append(" oi.consignee_fee  as consigneeFee, ");
				sb.append(" oi.town  as town, ");
				sb.append(" oi.cp_name cpName, ");
				sb.append(" oi.TRA_FEE  as cpFee, ");
				sb.append(" oi.tra_fee  as traFee, ");
				sb.append(" oi.payment_collection as paymentCollection, ");
				sb.append(" ot.fee_audit_status   as feeAuditStatus, ");
				sb.append(" oe.print_num  as printNum, ");
				sb.append(" oe.REACH_NUM reachNum,oi.TRAFFIC_MODE trafficMode  ");
		sb.append("  from opr_prewired  w, ");
					sb.append(" opr_prewired_detail wd, ");
					sb.append(" opr_fax_in   oi, ");
					sb.append(" opr_stock    os, ");
					sb.append(" opr_status   ot, ");
					sb.append(" opr_receipt  oe ");
		sb.append(" where w.id = wd.opr_prewired_id ");
				sb.append("  and wd.d_no = oi.d_no ");
				sb.append("  and wd.d_no = os.d_no(+) ");
				sb.append("   and wd.d_no = ot.d_no ");
				sb.append("  and wd.d_no = oe.d_no(+)  ");
				sb.append("  and w.STATUS =1  ");
				sb.append(" AND   oi.status  =  1 	");
				sb.append("  and w.depart_id = :departId ");
				sb.append("  and os.DEPART_ID(+) = :departId2 ");
				sb.append("  and ot.OUT_STATUS in (0, 3) ");
	
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND   w.id   in  ("+ids+")  ");
		}
		return sb.toString();
	}

}
