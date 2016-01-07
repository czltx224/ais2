package com.xbwl.finance.Service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.ReadExcel;
import com.xbwl.common.utils.ReadExcel2007;
import com.xbwl.cus.service.ICusOverManagerService;
import com.xbwl.cus.service.ICusOverWeightService;
import com.xbwl.entity.CusOverweightManager;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiDeliverycost;
import com.xbwl.entity.FiDeliverycostExcel;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.entity.OprOverweight;
import com.xbwl.finance.Service.IFiCostService;
import com.xbwl.finance.Service.IFiDeliverycostExcelService;
import com.xbwl.finance.Service.IFiDeliverycostService;
import com.xbwl.finance.dao.IFiDeliverycostDao;
import com.xbwl.finance.dao.IFiDeliverycostExcelDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.fax.service.IOprFaxMainService;
import com.xbwl.oper.stock.service.IOprHistoryService;

/**
 * author shuw
 * time Oct 8, 2011 5:47:04 PM
 */
@Service("fiDeliverycostServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiDeliverycostServiceImpl extends BaseServiceImpl<FiDeliverycost,Long> implements
																					IFiDeliverycostService{

	@Resource(name = "oprFaxMainServiceImpl")
	private IOprFaxMainService oprFaxMainService;
	
	@Resource(name="fiDeliverycostHibernateDaoImpl")
	private IFiDeliverycostDao fiDeliverycostDao;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name = "fiCostServiceImpl")
	private IFiCostService fiCostService;
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface fiInterfaceImpl;
	
	@Resource(name = "fiDeliverycostExcelHibernateDaoImpl")
	private IFiDeliverycostExcelDao fiDeliverycostExcelDao;
	
	@Resource(name = "fiDeliverycostExcelServiceImpl")
	private IFiDeliverycostExcelService fiDeliverycostExcelService; 
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Value("${fiAuditCost.log_qxAuditCost}")
	private Long log_qxAuditCost ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//主单超重
	@Resource(name="cusOverWeightServiceImpl")
	private ICusOverWeightService cusOverWeightService;
	
	//主单超重设置
	@Resource(name="cusOverManagerServiceImpl")
	private ICusOverManagerService cusOverManagerService;
	
	@Override
	public IBaseDAO<FiDeliverycost, Long> getBaseDao() {
		return fiDeliverycostDao;
	}

	public void save(FiDeliverycost entity) {
		if(entity.getId()==null){
			if(entity.getFlightMainNo()!=null){
				List<FiDeliverycost>list = find("from FiDeliverycost  fi where fi.flightMainNo=? and fi.customerId=? ", entity.getFlightMainNo(),entity.getCustomerId());
				if(list.size()>0){
					throw new ServiceException("此黄单号已录入，不能重复录入");
				}
			}else{
				throw new ServiceException("录入的黄单号不能为空");
			}
		}
		fiDeliverycostDao.save(entity);
	}

    //审核
	@ModuleName(value="提货成本管理会计审核",logType=LogType.fi)
	public void saveFiAudit(List<FiDeliverycost> list,User user) throws Exception {
		List<Long>fiDelivAll = new ArrayList<Long>();    //审核的ID集合
		for (FiDeliverycost fd : list) {
			fiDelivAll.add(fd.getId());
		}
		
		List<FiDeliverycost> listDliv = new ArrayList<FiDeliverycost>();
		for(FiDeliverycost fiDeliverycost:list){
			FiDeliverycost fiDeliverycost2 =get(fiDeliverycost.getId());
			fiDeliverycost2.setTs(fiDeliverycost.getTs());
		//	save(fiDeliverycost2);                               //验证时间戳
			listDliv.add(fiDeliverycost2);
		}
		
		FiDeliverycost fiDecost =get(list.get(0).getId());
		if(fiDecost==null){
			throw new ServiceException("记录已不存在");
		}
		Long cusLong = fiDecost.getCustomerId();
		String cusString =fiDecost.getCustomerName();
		Long batchNo = fiDeliverycostExcelService.getBatchNO(1l);
		Double totalMoney=0.0;
		
		for(FiDeliverycost fiDeliverycost:listDliv){
			if(fiDeliverycost==null){
				throw new ServiceException("记录已不存在");
			}
			if(fiDeliverycost.getMatStatus()==null||fiDeliverycost.getMatStatus()==0){
				throw new ServiceException("存在未匹配的数据，不能审核");
			}
			if(!cusLong.equals(fiDeliverycost.getCustomerId())){
				throw new ServiceException("不同提货公司不能一起审核");
			}
			if(fiDeliverycost.getStatus()!=null&&fiDeliverycost.getStatus()==1l){
				throw new ServiceException("存在记录已审核，请重新查询后再提交记录审核");
			}else{
				totalMoney=DoubleUtil.add(fiDeliverycost.getAmount(),totalMoney);
				fiDeliverycost.setReviewDept(user.get("departName").toString());
				fiDeliverycost.setReviewDate(new Date());
				fiDeliverycost.setReviewUser(user.get("name").toString());
				fiDeliverycost.setBatchNo(batchNo);
				fiDeliverycost.setStatus(1l);
				save(fiDeliverycost);   //更新会计审核信息
				
				List<Long >listFid =getPksByIds(fiDeliverycost.getFaxId());
				if(listFid.size()==1){    //保存黄单多对一的ID集合，用来判断是否一次全部取消匹配
					List<FiDeliverycost>lisd = find("from FiDeliverycost  fd where fd.departId=? and fd.faxId=? and  fd.id<> ? ",fiDeliverycost.getDepartId(),fiDeliverycost.getFaxId(),fiDeliverycost.getId());
					List<Long> idsLongList  = new ArrayList<Long>();  
					for (FiDeliverycost fiDt2 : lisd) {
						idsLongList.add(fiDt2.getId());
					}
					if(!fiDelivAll.containsAll(idsLongList)){
						throw new ServiceException("存在多条黄单记录匹配一条主单号的记录，请一起审核");
					}
				}
				
				//主单处理
				for(Long faxIdLong :listFid){
					OprFaxMain fiMain = oprFaxMainService.get(faxIdLong);
					//标记此主单的成本已分摊
					if(fiMain.getCostAuditStatus()==1l){
						continue;
					}else{
						fiMain.setCostAuditStatus(1l);
					}
					oprFaxMainService.save(fiMain);
					
					List<OprFaxIn> listFax = oprFaxInDao.find(" from OprFaxIn o where o.faxMainId=?  and o.status=1 ",fiMain.getId());
					double doubv=fiDeliverycost.getAmount();
					//保存到成本表中
					for(int i=0;i<listFax.size();i++){
						OprFaxIn oprFaxIn=listFax.get(i);
						FiCost fiCost = new FiCost();
						fiCost.setDno(oprFaxIn.getDno());
						fiCost.setCostType("提货成本");
						fiCost.setCostTypeDetail(oprFaxIn.getTakeMode());
						if((listFax.size()-1)==i){
							fiCost.setCostAmount(doubv);
						
							oprHistoryService.saveLog(oprFaxIn.getDno(), "提货成本审核，审核支付金额："+doubv , log_auditCost);     //操作日志
						}else{
							double dou2 = DoubleUtil.div(fiDeliverycost.getAmount(), listFax.size(),2);
							fiCost.setCostAmount(dou2);
							oprHistoryService.saveLog(oprFaxIn.getDno(), "提货成本审核，审核支付金额："+dou2, log_auditCost);     //操作日志

							doubv=DoubleUtil.sub(doubv, dou2);
							
						}
						fiCost.setSourceSignNo(fiDeliverycost.getId()+"");
						fiCost.setDataSource("提货成本");
						fiCostService.save(fiCost);
					}
				}
			}
		}
		
	/*
		//财务接口
		List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
		FiInterfaceProDto fiIn = new FiInterfaceProDto();
		fiIn.setCustomerId(cusLong);
		fiIn.setCustomerName(cusString);
		fiIn.setDistributionMode("客商");
		fiIn.setSettlementType(2l);
		fiIn.setDocumentsType("成本");
		fiIn.setDocumentsSmalltype("提货对账单");
		fiIn.setDocumentsNo(batchNo);
		fiIn.setAmount(totalMoney);
		fiIn.setCostType("对账");
		fiIn.setDepartId(Long.parseLong(user.get("departId")+""));
		fiIn.setSourceData("提货成本");
		fiIn.setSourceNo(batchNo);
		fiIn.setCreateRemark("支付"+cusString+"提货费"+totalMoney+"元");
		listfiInterfaceDto.add(fiIn);
		fiInterfaceImpl.reconciliationToFiPayment(listfiInterfaceDto);*/
		//fiInterfaceImpl.addFinanceInfo(listPro);
	}

	@ModuleName(value="提货成本管理导入Excel保存",logType=LogType.fi)
	public String saveFiExcel(File excelFile, String fileName) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		List<FiDeliverycostExcel> fiList = new ArrayList<FiDeliverycostExcel>();
		Long batchNo =fiDeliverycostExcelService.getBatchNO(bussDepartId);
		List list=null;
		if(fileName.toLowerCase().endsWith(".xlsx")){
				ReadExcel2007 readExcel2007 = new ReadExcel2007(5);
				FileInputStream fint = new FileInputStream(excelFile);
				try {
					list = readExcel2007.readExcel2007(fint);
				} catch (Exception e) {
						throw new ServiceException("读取数据错误，存在非法空值");
				}finally{
					if (fint!=null) {
						fint.close();
						fint=null;
					}
				}
		}else if(fileName.toLowerCase().endsWith(".xls")){
				ReadExcel readExcel = new ReadExcel(5);
				FileInputStream fint = new FileInputStream(excelFile);
				try {
					list = readExcel.readExcel(fint);
				} catch (Exception e) {
						throw new ServiceException("读取数据错误，存在非法空值");
				}finally{
					if (fint!=null) {
						fint.close();
						fint=null;
					}
				}
		}else{
			throw new ServiceException("请导入Excel文件，后缀为.xlsx或者.xls");
		}
		for(int i=0;i<list.size();i++){
			FiDeliverycostExcel fiDeliveryExcel =  new FiDeliverycostExcel();
			fiDeliveryExcel.setStatus(0l);
			fiDeliveryExcel.setBatchNo(batchNo);
			List row=(List)list.get(i);
			if(i!=0){
				Iterator jt=row.iterator();
				String company=(String) jt.next();
				
				fiDeliveryExcel.setExcelCompany(company);
				jt.hasNext();
				
				String excelNo=(String) jt.next();
				fiDeliveryExcel.setExcelNo(excelNo);
				jt.hasNext();
				String excelWeight=(String)jt.next();
				if(excelWeight == null || "".equals(excelWeight)){
					continue;
				}
				fiDeliveryExcel.setExcelWeight(Double.parseDouble(excelWeight));
				jt.hasNext();
				

				String  excelAmount =(String) jt.next();
				if(excelAmount == null || "".equals(excelAmount)){
					continue;
				}
				fiDeliveryExcel.setExcelAmount((Double.parseDouble(excelAmount)));
				jt.hasNext();
				
				String  banFee =(String ) jt.next();
				if(banFee == null || "".equals(banFee)){
					continue;
				}
				fiDeliveryExcel.setExcelBanFee(Double.parseDouble(banFee));
				fiList.add(fiDeliveryExcel);
			}
		}
		
		for(FiDeliverycostExcel fiDeliverycostExcel : fiList){
			fiDeliverycostExcelDao.save(fiDeliverycostExcel);
		}
		return batchNo+"";
	}

	//取消匹配
	@ModuleName(value="提货成本管理取消匹配",logType=LogType.fi)
	public String qxAudit(List<FiDeliverycost>aa) throws Exception {
		List<Long>fiDelivAll = new ArrayList<Long>();    //取消匹配的ID集合
		for (FiDeliverycost fd : aa) {
			fiDelivAll.add(fd.getId());
		}

		for (FiDeliverycost fiDeliv : aa) {
			FiDeliverycost fiDeliverycost = fiDeliverycostDao.get(fiDeliv.getId());
			fiDeliverycost.setTs(fiDeliv.getTs());
			
			if(fiDeliverycost.getStatus()==1l){
				throw new ServiceException("已审核数据不能进行取消匹配操作");
			}
			
			if(fiDeliverycost.getMatStatus()==null||fiDeliverycost.getMatStatus()==0l){
				throw new ServiceException("未匹配的数据不能进行取消匹配操作");
			}
			List<Long >list =getPksByIds(fiDeliverycost.getFaxId());
			for(Long faxIdLong :list){
				OprFaxMain fiMain = oprFaxMainService.get(faxIdLong);
				fiMain.setMatchStatus(0l);
				oprFaxMainService.save(fiMain);
				
				//超重删除
				List<OprOverweight> list2 = cusOverWeightService.find("from OprOverweight oo where oo.departId=? and oo.flightMainNo=?  and oo.status=1 ",fiDeliverycost.getDepartId(),fiMain.getFlightMainNo());
				for(OprOverweight oprOverweight : list2){
					oprOverweight.setStatus(0l);
					cusOverWeightService.save(oprOverweight);
				}
			}
			
			if(list.size()==1){    //保存黄单多对一的ID集合，用来判断是否一次全部取消匹配
				List<FiDeliverycost>lisd = find("from FiDeliverycost  fd where fd.departId=? and fd.faxId=? and  fd.id<> ? ",fiDeliverycost.getDepartId(),fiDeliverycost.getFaxId(),fiDeliverycost.getId());
				List<Long> idsLongList  = new ArrayList<Long>();  
				for (FiDeliverycost fiDt2 : lisd) {
					idsLongList.add(fiDt2.getId());
				}
				if(!fiDelivAll.containsAll(idsLongList)){
					throw new ServiceException("存在多条黄单记录匹配一条主单号的记录，请全部取消匹配");
				}
			}
			
			if(fiDeliverycost.getStatus()!=null&&fiDeliverycost.getStatus()==1l){
				throw new ServiceException("数据已审核，不能撤销手工匹配");
			}
			
			fiDeliverycost.setMatStatus(0l);
			fiDeliverycost.setFaxMainNo(null);
			fiDeliverycost.setFaxPiece(0l);
			fiDeliverycost.setFaxWeight(0.0);
			fiDeliverycost.setDiffAmount(0.0);
			fiDeliverycost.setDiffWeight(0.0);
			fiDeliverycost.setFaxId(null);
			fiDeliverycostDao.save(fiDeliverycost);   //撤销手工匹配
		}
		return null;
	}

	/*
	 * 取消成本审核
	 */
	@ModuleName(value="提货成本管理取消成本审核",logType=LogType.fi)
	public void saveQxFiAudit(List<FiDeliverycost> aa) throws Exception {
		List<Long>fiDelivAll = new ArrayList<Long>();    //取消审核的ID集合
		for (FiDeliverycost fd : aa) {
			fiDelivAll.add(fd.getId());
		}
		
		List<FiDeliverycost>listAa = new ArrayList<FiDeliverycost>();
		for(FiDeliverycost fiDeliverycost:aa){                             //验证时间戳
			FiDeliverycost fiDeli = fiDeliverycostDao.get(fiDeliverycost.getId());
			List<Long> faxIdList = getPksByIds(fiDeli.getFaxId());
			for(Long faxIdLong :faxIdList){
				OprFaxMain fiMain = oprFaxMainService.get(faxIdLong);
				fiMain.setCostAuditStatus(0l);
				oprFaxMainService.save(fiMain);
				
	//			fiCostService.sumCostBySourceDataId("提货成本", fiDeliverycost.getId());   //总
				List<OprFaxIn> listFax= oprFaxInDao.find("from OprFaxIn oi where oi.faxMainId=? and oi.status=1  ",fiMain.getId());   //找到配送单号
				for(OprFaxIn oin:listFax){
					double totalCost =fiCostService.sumCostBySourceDataId("提货成本", oin.getDno());    //所有配送单号提货成本
					FiCost fiCostNew = new FiCost();
					fiCostNew.setCostType("提货成本");
					fiCostNew.setCostTypeDetail("撤销审核");
					fiCostNew.setCostAmount(-totalCost);
					fiCostNew.setDataSource("提货成本");
					fiCostNew.setDno(oin.getDno());
					fiCostNew.setSourceSignNo(fiDeliverycost.getId()+"");
					fiCostNew.setStatus(1l);
					fiCostService.save(fiCostNew);  //重新
	
					oprHistoryService.saveLog(oin.getDno(), "提货成本审核撤销，撤销金额："+totalCost, log_qxAuditCost);     //操作日志
				}
			}
			if(faxIdList.size()==1){
				List<FiDeliverycost>lisd = find("from FiDeliverycost  fd where fd.departId=? and fd.faxId=? and  fd.id<> ? ",fiDeli.getDepartId(),fiDeli.getFaxId(),fiDeli.getId());
				List<Long> idsLongList  = new ArrayList<Long>();  
				for (FiDeliverycost fiDt2 : lisd) {
					idsLongList.add(fiDt2.getId());
				}
				if(!fiDelivAll.containsAll(idsLongList)){
					throw new ServiceException("存在多条黄单记录匹配一条主单号的记录，请一起取消审核");
				}
			}
			if(fiDeli.getPayStatus()==1l){
				throw new ServiceException("不能撤销会计审核，存在已支付的数据");
			}
			fiDeli.setTs(fiDeliverycost.getTs());
			fiDeliverycostDao.save(fiDeli);
			listAa.add(fiDeli);
			
		}
		
		List<FiDeliverycost>list =fiDeliverycostDao.find("from FiDeliverycost f where f.batchNo=? ",listAa.get(0).getBatchNo());
		if(list.size()==listAa.size()){   //如果是一个批次的数据全部撤销
			for(FiDeliverycost fiDeliverycost:listAa){
				//财务接口
				List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
				FiInterfaceProDto fiIn = new FiInterfaceProDto();
				fiIn.setSourceData("提货成本");
				fiIn.setSourceNo(fiDeliverycost.getBatchNo());
				fiIn.setAmount(fiDeliverycost.getAmount());
				listfiInterfaceDto.add(fiIn);
				fiInterfaceImpl.changePaymentAmount(listfiInterfaceDto);
				
				fiDeliverycost.setReviewDept(null);
				fiDeliverycost.setReviewDate(null);
				fiDeliverycost.setBatchNo(null);
				fiDeliverycost.setReviewUser(null);
				fiDeliverycost.setBatchNo(null);
				fiDeliverycost.setStatus(0l);
				save(fiDeliverycost);   //更新会计审核信息
			}
		}else{   //如果只撤销一部分

			double totalChange=0.0;
			for(int i=0;i<listAa.size();i++){
				totalChange=DoubleUtil.add(listAa.get(i).getAmount(),totalChange);                       //修改的总金额
			}
			
			//财务接口   撤销部分应收应入
			List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
			FiInterfaceProDto fiIn = new FiInterfaceProDto();
			fiIn.setSourceData("提货成本");
			fiIn.setSourceNo(listAa.get(0).getBatchNo());
			fiIn.setAmount(totalChange);
			listfiInterfaceDto.add(fiIn);
			fiInterfaceImpl.revocationFiDeliveryCost(listfiInterfaceDto);

			//		throw new ServiceException("正在开发中...");
			for(FiDeliverycost fiDeliverycost:listAa){

				fiDeliverycost.setReviewDept(null);
				fiDeliverycost.setReviewDate(null);
				fiDeliverycost.setReviewUser(null);
				fiDeliverycost.setBatchNo(null);
				fiDeliverycost.setStatus(0l);
				save(fiDeliverycost);   //更新会计审核信息
				
			}
		}
	}
	
	@ModuleName(value="核销提货成本",logType=LogType.fi)
	public void payConfirmationBybatchNo(Long batchNo) throws Exception{
		List<FiDeliverycost> list=this.fiDeliverycostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("中转成本中根据批次号:"+batchNo+"未找到数据！");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String hql="update FiDeliverycost f set f.payStatus=1L,f.payTime=?,f.payUser=? where f.batchNo=?";
		this.fiDeliverycostDao.batchExecute(hql,new Date(),user.get("name").toString(),batchNo);
	}
	
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception{
		List<FiDeliverycost> list=this.fiDeliverycostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("中转成本中根据批次号:"+batchNo+"未找到数据！");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String hql="update FiDeliverycost f set f.payStatus=0L,f.payTime=?,f.payUser=? where f.batchNo=?";
		this.fiDeliverycostDao.batchExecute(hql,new Date(),user.get("name").toString(),batchNo);
	}

	//匹配保存
	@ModuleName(value="提货成本管理匹配保存",logType=LogType.fi)
	public void saveMat(List<FiDeliverycost>aa ) throws Exception {
		List<FiDeliverycost> arrayFide = new ArrayList<FiDeliverycost>();
		double totalFdWeight=0.0;
		for(FiDeliverycost fiDeliver:aa){
			FiDeliverycost fiDeliverycost=get(fiDeliver.getId());
			fiDeliverycost.setTs(fiDeliver.getTs());
			if(fiDeliverycost==null){
				throw new ServiceException("这黄单号记录可能已经删除，无法手工匹配");
			}else if(fiDeliverycost.getMatStatus()==1l||fiDeliverycost.getMatStatus()==2l||fiDeliverycost.getMatStatus()==3l){
				throw new ServiceException("这黄单号记录已经系统匹配，不能匹配多次");
			}
			
			fiDeliverycost.setFaxId(fiDeliver.getFaxId());
			
			String longFaxId =fiDeliver.getFaxId();
			List<Long>list =getPksByIds(longFaxId);
			if(aa.size()>1||list.size()>1){
				fiDeliverycost.setMatStatus(3l);
			}else{
				fiDeliverycost.setMatStatus(2l);
			}
			if(list.size()==0){
				throw new ServiceException("匹配时出错了");
			}
			double totalWeight=0.0;
			long totalPiece=0l;
			StringBuffer sb= new StringBuffer("");
			for(int i=0;i<list.size();i++){
				 OprFaxMain fiMain = oprFaxMainService.get(list.get(i));
				 fiMain.setMatchStatus(1l);
				 totalWeight+=fiMain.getTotalWeight();
				 totalPiece+=fiMain.getTotalPiece();
				 if(i!=(list.size()-1)){
					 sb.append(fiMain.getFlightMainNo()).append(",");
				 }else{
					 sb.append(fiMain.getFlightMainNo());
				 }
				 oprFaxMainService.save(fiMain);
			}
			
    		fiDeliverycost.setFaxMainNo(sb.toString());
    		fiDeliverycost.setFaxPiece(totalPiece);
    		fiDeliverycost.setFaxWeight(totalWeight);
    		if(aa.size()==1){       //正常匹配写入重量差异 多票黄单匹配在后面处理
    			fiDeliverycost.setDiffWeight(DoubleUtil.sub(fiDeliverycost.getFlightWeight(), fiDeliverycost.getFaxWeight()));
    			fiDeliverycost.setDiffAmount(Math.ceil(DoubleUtil.mul(fiDeliverycost.getDiffWeight(),fiDeliverycost.getPrice())));
    		}
    		save(fiDeliverycost);
    		totalFdWeight=DoubleUtil.add(fiDeliverycost.getFlightWeight(), totalFdWeight);
			arrayFide.add(fiDeliverycost);
		}
		
		if(aa.size()==1){
			oneFlightToManyFax(arrayFide);
		}else{
			manyFlightToOneFax(arrayFide,totalFdWeight);
		}
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED
	/**
	 * 多个黄单号对应-个主单号计算超重方法
	 * @param array
	 */
	public void manyFlightToOneFax(List<FiDeliverycost>array,double totalFdWeight){
		double diffWeight =DoubleUtil.sub(totalFdWeight, array.get(0).getFaxWeight());
		
		FiDeliverycost fiDeliverycost = array.get(0);  //多票匹配放在第一个黄单记录里面
		fiDeliverycost.setDiffWeight(diffWeight);
		fiDeliverycost.setDiffAmount(Math.ceil(DoubleUtil.mul(fiDeliverycost.getDiffWeight(),fiDeliverycost.getPrice())));
		save(fiDeliverycost);
		
		
		if(diffWeight>0.0){
			//主单超重处理  
			List<OprFaxIn> listMain = oprFaxInDao.find("from OprFaxIn oi where oi.faxMainId=?  and oi.status=1   ",Long.parseLong(array.get(0).getFaxId()));
			if(listMain.size()>0){
				OprFaxIn oprFaxIn =listMain.get(0);
				CusOverweightManager cusMan=null;
				List<CusOverweightManager> cuslist=this.cusOverManagerService.findBy("cusId", oprFaxIn.getCusId());
				if(cuslist.size()==0){
					List<CusOverweightManager> listM =cusOverManagerService.find("from CusOverweightManager cs where cs.cusId is null ");
					if(listM.size()==0){
						throw new ServiceException("请在主单超重模块录入主单超重相关信息");
					}else{
						cusMan=listM.get(0);
					}
				}else{
					cusMan=cuslist.get(0);
				}
				
				if(diffWeight>cusMan.getLowWeight()){//写入主单超重表
					OprOverweight oprOverweight=new OprOverweight();
					oprOverweight.setCustomerId(oprFaxIn.getCusId());
					oprOverweight.setCustomerName(oprFaxIn.getCpName());
					oprOverweight.setWeight(diffWeight);
					oprOverweight.setFlightMainNo(array.get(0).getFaxMainNo());
					oprOverweight.setDepartId(array.get(0).getDepartId());
					oprOverweight.setDepartName(array.get(0).getDepartName());
					oprOverweight.setFaxWeight(array.get(0).getFaxWeight());
					oprOverweight.setFlightWeight(totalFdWeight);
					oprOverweight.setAmount(Math.ceil(DoubleUtil.mul(diffWeight, cusMan.getOverweightRate())));
					oprOverweight.setRate(cusMan.getOverweightRate());
					oprOverweight.setStatus(1l);
					cusOverWeightService.save(oprOverweight);
				}
			}

		}
		
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED
	
	/**
	 * 一个黄单号对应多个主单号计算超重方法
	 * @param array
	 */
	public void oneFlightToManyFax(List<FiDeliverycost>array){
		FiDeliverycost fiDeliverycost=array.get(0);
		double diffWeight =DoubleUtil.sub(fiDeliverycost.getFlightWeight(), fiDeliverycost.getFaxWeight());
		if(diffWeight>0.0){
			//主单超重处理   这里计了多次，在超重处理时，必须得判断一下
			List<Long>list =getPksByIds(fiDeliverycost.getFaxId());
			for(int j=0;j<list.size();j++){
				List<OprFaxIn> listMain = oprFaxInDao.find("from OprFaxIn oi where oi.faxMainId=?  and oi.status=1  ",list.get(j));
				OprFaxIn oprFaxIn =listMain.get(0);
				
				CusOverweightManager cusMan=null;
				List<CusOverweightManager> cuslist=this.cusOverManagerService.findBy("cusId", oprFaxIn.getCusId());
				if(cuslist.size()==0){
					List<CusOverweightManager> listM =cusOverManagerService.find("from CusOverweightManager cs where cs.cusId is null ");
					if(listM.size()==0){
						throw new ServiceException("请在主单超重模块录入主单超重相关信息");
					}else{
						cusMan=listM.get(0);
					}
				}else{
					cusMan=cuslist.get(0);
				}
				
				if(diffWeight>cusMan.getLowWeight()){//写入主单超重表
					OprOverweight oprOverweight=new OprOverweight();
					oprOverweight.setCustomerId(oprFaxIn.getCusId());
					oprOverweight.setCustomerName(oprFaxIn.getCpName());
					oprOverweight.setWeight(diffWeight);
					oprOverweight.setFlightMainNo(array.get(0).getFaxMainNo());
					oprOverweight.setDepartId(array.get(0).getDepartId());
					oprOverweight.setDepartName(array.get(0).getDepartName());
					oprOverweight.setFaxWeight(array.get(0).getFaxWeight());
					oprOverweight.setFlightWeight(fiDeliverycost.getFlightWeight());
					oprOverweight.setAmount(Math.ceil(DoubleUtil.mul(diffWeight, cusMan.getOverweightRate())));
					oprOverweight.setRate(cusMan.getOverweightRate());
					oprOverweight.setStatus(1l);
					cusOverWeightService.save(oprOverweight);
				}
			}
		}
	}
	
	//根据字符串取id数组
	 public List<Long> getPksByIds(String ids) {
	        List<Long> pks = new ArrayList<Long>();
	        String[] idsValue = ids.split("\\,");
	        for (String delId : idsValue) {
	            pks.add(Long.valueOf(delId));
	        }
	        return pks;
	    }
}
