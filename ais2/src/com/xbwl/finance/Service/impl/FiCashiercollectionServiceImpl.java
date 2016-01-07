package com.xbwl.finance.Service.impl;

import java.rmi.ServerException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.entity.FiCashiercollectionExcel;
import com.xbwl.entity.FiExcelPos;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.entity.FiPaid;
import com.xbwl.finance.Service.IFiCashiercollectionService;
import com.xbwl.finance.Service.IFiExcelPosService;
import com.xbwl.finance.Service.IFiFundstransferService;
import com.xbwl.finance.Service.IFiPaidService;
import com.xbwl.finance.dao.IFiCapitaaccountDao;
import com.xbwl.finance.dao.IFiCapitaaccountsetDao;
import com.xbwl.finance.dao.IFiCashiercollectionDao;
import com.xbwl.finance.dao.IFiCashiercollectionExcelDao;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;

@Service("fiCashiercollectionServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCashiercollectionServiceImpl extends
		BaseServiceImpl<FiCashiercollection, Long> implements
		IFiCashiercollectionService {

	@Resource(name = "fiCashiercollectionHibernateDaoImpl")
	private IFiCashiercollectionDao fiCashiercollectionDao;

	//Excel Dao
	@Resource(name = "fiCashiercollectionExcelHibernateDaoImpl")
	private IFiCashiercollectionExcelDao fiCashiercollectionExcelDao;
	
	// 资金账号Dao
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// 资金账号流水Dao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;

	//实收实付Service
	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	
	//资金交接单Service
	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	//POS导入保存
	@Resource(name = "fiExcelPosServiceImpl")
	private IFiExcelPosService fiExcelPosService;
	
	@Resource
	private IBasDictionaryDetailDao idictionaryDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiCashiercollectionDao;
	}

	@ModuleName(value="出纳收款单保存",logType=LogType.fi)
	public void saveCashiercollection(FiCashiercollection fiCashiercollection)
			throws Exception {
		
		Double collectionAmount = null;// 前台转的金额
		Double amount = null;// 数据库已保存金额
		Long capitaaccountsetId = null;// 数据库已保存银行账号id
		Double openingBalance = null;// 账号余额
		Double openingBalance1 =null;

		if (fiCashiercollection == null) {
			throw new ServiceException("保存失败，出纳收款单实体为空！");
		}
		collectionAmount = fiCashiercollection.getCollectionAmount();// 本次结算金额
		if (fiCashiercollection.getId()!=null) {// 修改收款单，获取原保存金额
			FiCashiercollection fc = this.fiCashiercollectionDao.get(fiCashiercollection.getId());
			amount = fc.getCollectionAmount();//100
			capitaaccountsetId = fc.getFiCapitaaccountsetId();//数据库保存的账号ID
			FiCapitaaccountset fiCapitaaccountset1 = this.fiCapitaaccountsetDao.get(fc.getFiCapitaaccountsetId());//原
			
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCashiercollection.getFiCapitaaccountsetId());//新
			openingBalance = fiCapitaaccountset.getOpeningBalance();// 前台录入时对应账号余额200	
			
			if (capitaaccountsetId.equals(fiCashiercollection.getFiCapitaaccountsetId())) {// 未修改银行账号
				// 新增账号流水余额
				FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
				if (collectionAmount > amount) {
					collectionAmount = collectionAmount - amount;// 本次结算金额-已保存金额
					fiCapitaaccount.setLoan(collectionAmount);// 借方金额
					openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// 账号余额+本次收款金额
				} else if (collectionAmount < amount) {
					collectionAmount = DoubleUtil.sub(amount , collectionAmount);// 数据库已保存金额-本次结算金额DoubleUtil.add(v1,
																	// v2)
					fiCapitaaccount.setLoan(collectionAmount);// 贷方金额
					openingBalance = DoubleUtil.sub(openingBalance , collectionAmount);
					
				}
				fiCapitaaccount.setSourceData("出纳收款单");
				fiCapitaaccount.setFiCapitaaccountsetId(fiCashiercollection.getFiCapitaaccountsetId());
				fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
				fiCapitaaccount.setBalance(openingBalance);
				fiCapitaaccount.setRemark("修改出纳收款单金额");
				if(openingBalance<0.0){
					throw new ServiceException("修改失败，账号余额不足！");
				}
				this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
				fiCapitaaccountset.setOpeningBalance(openingBalance);// 计算后余额
				this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
			} else {
				// 原账号处理
				openingBalance1=fiCapitaaccountset1.getOpeningBalance();
				fiCapitaaccountset1.setOpeningBalance(DoubleUtil.sub(openingBalance1 ,fc.getCollectionAmount()));
				this.fiCapitaaccountsetDao.save(fiCapitaaccountset1);

				FiCapitaaccount fct = new FiCapitaaccount();
				fct.setBorrow(fc.getCollectionAmount());
				fct.setSourceData("出纳收款单");
				fct.setFiCapitaaccountsetId(fc.getFiCapitaaccountsetId());
				fct.setSourceNo(fiCashiercollection.getId());
				fct.setBalance(DoubleUtil.sub(openingBalance1 , fc.getCollectionAmount()));// 账号余额-本次收款金额
				fct.setRemark("更换出纳收款账号");
				if(fct.getBalance()<0.0){
					throw new ServiceException("修改失败，账号余额不足！");
				}
				this.fiCapitaaccountDao.save(fct);

				// 新账号处理
				FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
				fiCapitaaccount.setLoan(collectionAmount);// 借方金额
				fiCapitaaccount.setFiCapitaaccountsetId(fiCashiercollection.getFiCapitaaccountsetId());
				fiCapitaaccount.setBalance(DoubleUtil.add(openingBalance, fiCashiercollection.getCollectionAmount()));// 账号余额+本次收款金额
				fiCapitaaccount.setSourceData("出纳收款单");
				fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
				fiCapitaaccount.setRemark("更换出纳收款账号");
				
				this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账

				fiCapitaaccountset.setOpeningBalance(DoubleUtil.add(openingBalance, fiCashiercollection.getCollectionAmount()));// 计算后余额
				this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
			}
			Session session=this.fiCashiercollectionDao.getSession().getSessionFactory().getCurrentSession();
			session.merge(fiCashiercollection);// 保存出纳收款单
		} else {// 新增收款单
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCashiercollection.getFiCapitaaccountsetId());
			openingBalance = fiCapitaaccountset.getOpeningBalance();// 账号余额	
			openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// 账号余额+本次收款金额
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setLoan(collectionAmount);// 借方金额
			fiCapitaaccount.setFiCapitaaccountsetId(fiCashiercollection.getFiCapitaaccountsetId());
			fiCapitaaccount.setSourceData("出纳收款单");
			fiCapitaaccount.setRemark("新增出纳收款单");
			fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
			fiCapitaaccount.setBalance(openingBalance);// 账号余额+本次收款金额
			this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
			
			fiCapitaaccountset.setOpeningBalance(openingBalance);// 计算后余额
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
			this.fiCashiercollectionDao.save(fiCashiercollection);
		}

	}

	@ModuleName(value="出纳收款单核销",logType=LogType.fi)
	public void saveVerification(Map map,User user) throws Exception{
		String verificationType=String.valueOf(map.get("verificationType"));//核销类型
		if("委托收款单".equals(verificationType)){
			cashiercollectionVerification(map,user);
		}else if("资金上交单".equals(verificationType)){
			fundstransferVerification(map,user);
		}else if("支票".equals(verificationType)){
			this.checkVerification(map, user);
		}else if("POS".equals(verificationType)){
			this.cashiercollectionVerification(map, user);
		}else{
			throw new ServiceException("核销类型不存在");
		}

		
	}
	
	//支票核销
	private void checkVerification(Map map,User user) throws Exception{
		Long fiCashiercollectionId=Long.valueOf(String.valueOf(map.get("fiCashiercollectionId")));//出纳收款单ID
		Long ids=Long.valueOf(String.valueOf(map.get("ids")));//资金交接单ID
		FiCashiercollection fct=this.fiCashiercollectionDao.get(fiCashiercollectionId);//出纳收款单
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		Double thisVerificationAmount=0.0;//本次核销金额
		Double cashVerificationAmount=0.0;//出纳收款单未核销金额
		Double paidVerificationAmount=0.0;//实收实付单未核销金额
		
		if(fct==null){
			throw new ServiceException("核销失败，出纳收款单ID不存在！");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("出纳收款单已审核");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("出纳收款单已作废");
		}
		cashVerificationAmount=DoubleUtil.sub(fct.getCollectionAmount(),fct.getVerificationAmount());//收款金额-已核销金额
		if(cashVerificationAmount<=0){
			throw new ServiceException("出纳收款单未核金额金额必须大于零");
		}

		//this.fiPaidService.verificationById(ids,user);//核销实收实付单据
		FiPaid fiPaid=this.fiPaidService.get(ids);
		if(fiPaid==null){
			throw new ServiceException("核销失败，实收实付单不存在！");
		}
		if(fiPaid.getVerificationStatus()==1L)throw new ServiceException("操作失败，实收单已核销");
		paidVerificationAmount=DoubleUtil.sub(fiPaid.getSettlementAmount(), fiPaid.getVerificationAmount());//实收付金额-已核销金额
		if(paidVerificationAmount<=0){
			throw new ServiceException("实收实付单未核金额金额必须大于零");
		}
		
		if(cashVerificationAmount>=paidVerificationAmount){
			thisVerificationAmount=cashVerificationAmount;
		}else{
			thisVerificationAmount=paidVerificationAmount;
		}
		
		//核销实收实付单据
		fiPaid.setVerificationAmount(DoubleUtil.add(fiPaid.getSettlementAmount(),thisVerificationAmount));
		fiPaid.setVerificationUser(String.valueOf(user.get("name")));
		fiPaid.setVerificationDept(String.valueOf(user.get("departName")));
		fiPaid.setVerificationTime(new Date());
		fiPaid.setVerificationStatus(1L);
		
		//保存出纳收款单核销
		fct.setVerificationAmount(DoubleUtil.add(fct.getVerificationAmount(), thisVerificationAmount));
		fct.setEntrustRemark("核销自动委托");
		fct.setEntrustTime(new Date());
		fct.setEntrustUser(verificationUser);
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		fct.setVerificationAmount(fct.getCollectionAmount());
		fct.setVerificationStatus(1L);
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		this.fiCashiercollectionDao.save(fct);//保存出纳收款单核销
		
	}
	
	
	//委托收款单核销
	private void cashiercollectionVerification(Map map,User user) throws Exception{
		Long fiCashiercollectionId=Long.valueOf(String.valueOf(map.get("fiCashiercollectionId")));//出纳收款单ID
		Long ids=Long.valueOf(String.valueOf(map.get("ids")));//资金交接单ID
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		Double thisVerificationAmount=0.0;//本次核销金额
		Double cashVerificationAmount=0.0;//出纳收款单未核销金额
		Double paidVerificationAmount=0.0;//实收实付单未核销金额
		//Double verificationAmount=0.0; //已核销金额+本次需要核销金额
		Double openingBalance=0.0;
		
		FiCashiercollection fct=this.fiCashiercollectionDao.get(fiCashiercollectionId);//出纳收款单
		
		if(fct==null){
			throw new ServiceException("核销失败，出纳收款单ID不存在！");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("出纳收款单已核销");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("出纳收款单已作废");
		}
		cashVerificationAmount=DoubleUtil.sub(fct.getCollectionAmount(),fct.getVerificationAmount());//收款金额-已核销金额
		if(cashVerificationAmount<=0){
			throw new ServiceException("出纳收款单未核金额金额必须大于零");
		}
		
		//实收实付单
		FiPaid fiPaid=this.fiPaidService.get(ids);
		if(fiPaid==null) throw new ServiceException("实收单号不存在");
		if(fiPaid.getVerificationStatus()==1L)throw new ServiceException("操作失败，实收单已核销");
		paidVerificationAmount=DoubleUtil.sub(fiPaid.getSettlementAmount(), fiPaid.getVerificationAmount());//实收付金额-已核销金额
		if(paidVerificationAmount<=0){
			throw new ServiceException("实收实付单未核金额金额必须大于零");
		}
		
		if(cashVerificationAmount>=paidVerificationAmount){
			thisVerificationAmount=paidVerificationAmount;
		}else{
			thisVerificationAmount=cashVerificationAmount;
		}
		
		//保存出纳收款单核销
		fct.setVerificationAmount(DoubleUtil.add(fct.getVerificationAmount(), thisVerificationAmount));
		if(fct.getCollectionAmount().equals(fct.getVerificationAmount())){
			fct.setVerificationStatus(1L);
		}
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		this.fiCashiercollectionDao.save(fct);
		
		//核销实收实付单据
		fiPaid.setVerificationAmount(DoubleUtil.add(fiPaid.getVerificationAmount(),thisVerificationAmount));
		fiPaid.setVerificationUser(verificationUser);
		fiPaid.setVerificationDept(verificationDept);
		fiPaid.setVerificationTime(new Date());
		fiPaid.setFiCashiercollectionId(fct.getId());
		if(fiPaid.getSettlementAmount().equals(fiPaid.getVerificationAmount())){
			fiPaid.setVerificationStatus(1L);
			fiPaid.setFiFundstransferStatus(1L);//上交状态
		}else{
			fiPaid.setVerificationStatus(0L);
		}
		this.fiPaidService.save(fiPaid);
		
		//POS收款账号资金流水减少
		if("POS".equals(fiPaid.getPenyJenis())){
			FiCapitaaccount fc=this.fiCapitaaccountDao.get(fiPaid.getFiCapitaaccountId());
			
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fc.getFiCapitaaccountsetId());
			if(fiCapitaaccountset==null){
				throw new ServerException("POS账号不存在!");
				
			}
			openingBalance = DoubleUtil.sub(fiCapitaaccountset.getOpeningBalance() , thisVerificationAmount);// 账号余额+本次收款金额
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setBorrow(thisVerificationAmount);// 贷s方金额
			fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			fiCapitaaccount.setSourceData("出纳收款单");
			fiCapitaaccount.setRemark("POS核销");
			fiCapitaaccount.setSourceNo(fct.getId());
			fiCapitaaccount.setBalance(openingBalance);
			this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
			
			fiCapitaaccountset.setOpeningBalance(openingBalance);// 计算后余额
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
		}
	}
	//资金上交单核销
	private void fundstransferVerification(Map map,User user) throws Exception{
		Long ids=Long.valueOf(String.valueOf(map.get("ids")));//资金交接单ID
		Long fiCashiercollectionId=Long.valueOf(String.valueOf(map.get("fiCashiercollectionId")));//出纳收款单ID
		FiCashiercollection fct=this.fiCashiercollectionDao.get(fiCashiercollectionId);//出纳收款单
		FiFundstransfer fiFundstransfer=this.fiFundstransferService.get(ids);//资金交接单
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		
		if(fct==null){
			throw new ServiceException("核销失败，出纳收款单ID不存在！");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("出纳收款单已审核");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("出纳收款单已作废");
		}
		
		//保存出纳收款单核销
		fct.setEntrustAmount(fct.getCollectionAmount());
		fct.setEntrustTime(new Date());
		fct.setEntrustUser(String.valueOf(user.get("name")));
		fct.setEntrustRemark("收款核销自动确认");
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		fct.setVerificationAmount(fct.getCollectionAmount());
		fct.setVerificationStatus(1L);
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		this.fiCashiercollectionDao.save(fct);
		
		//核销资金交接单
		if(fiFundstransfer.getPaymentaccountId().equals(fct.getFiCapitaaccountsetId())) throw new ServiceException("核销失败,收纳收款账号与现金交接付款不能为同一账号！");
		
		if(fiFundstransfer.getPaymentStatus()!=1L){
			throw new ServiceException("当前收款单还未付款确认！");
		}
		
		if(fiFundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("当前收款状态为已确收,不允许再核销");
		}
		
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("资金交接单已作废!");
		}
		
		if(!fiFundstransfer.getAmount().equals(fct.getCollectionAmount())){
			throw new ServiceException("核销失败,出纳收款金额:"+fct.getCollectionAmount()+"与资金上交金额:"+fiFundstransfer.getAmount()+"不一致。");
		}
		fiFundstransfer.setStatus(2L);
		fiFundstransfer.setReceivablesStatus(1L);
		fiFundstransfer.setReceivablesTime(new Date());
		this.fiFundstransferService.save(fiFundstransfer);//保存资金上交单核销状态
		
		//核销其它模块数据
		if("实收实付单".equals(fiFundstransfer.getSourceData())){
			this.fiPaidService.batchExecute("update FiPaid f set f.verificationAmount=f.settlementAmount,f.verificationStatus=1,f.verificationUser=?,f.verificationTime=? where f.fiFundstransferId=?", user.get("name"),new Date(),fiFundstransfer.getId());
		}
	}

	public void saveExcelData(Long batchNo) throws Exception {
		double openingBalance=0.0;
		double collectionAmount=0.0;
		List<FiCashiercollectionExcel> list=this.fiCashiercollectionExcelDao.findBy("batchNo", batchNo);
		if(list.size()==0) throw new ServiceException("未找到导入数据");
		for(FiCashiercollectionExcel fiCaExcel:list){
			collectionAmount=fiCaExcel.getAmount();
			
			//保存出纳收款单
			FiCashiercollection fiCashiercollection=new FiCashiercollection();
			fiCashiercollection.setFiCapitaaccountsetId(fiCaExcel.getFiCapitaaccountsetId());
			fiCashiercollection.setCollectionTime(fiCaExcel.getDoMoneyData());
			fiCashiercollection.setCollectionAmount(collectionAmount);
			fiCashiercollection.setCreateRemark(fiCaExcel.getCompanyName()+","+fiCaExcel.getRemark()==null?"":fiCaExcel.getRemark());
			//fiCashiercollection.setCreateRemark("交易笔数:"+fiExcelPos.getTransactionNumber()+"_交易金额:"+fiExcelPos.getAmount()+"_手续费:"+fiExcelPos.getFeeAmount()+"_结算金额:"+fiExcelPos.getSettlemenAmount());
			this.fiCashiercollectionDao.save(fiCashiercollection);//收纳收款单
			
			//账号设置(余额计算)
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCaExcel.getFiCapitaaccountsetId());
			openingBalance = fiCapitaaccountset.getOpeningBalance();// 账号余额	
			openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// 账号余额+本次收款金额
			fiCapitaaccountset.setOpeningBalance(openingBalance);// 计算后余额
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
			
			//账号流水
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setLoan(collectionAmount);// 借方金额
			fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			fiCapitaaccount.setSourceData("导入");
			fiCapitaaccount.setRemark("批量导入数据");
			fiCapitaaccount.setSourceNo(fiCaExcel.getId());
			fiCapitaaccount.setBalance(openingBalance);// 账号余额+本次收款金额
			this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
		}
	}
	
	public void saveExcelPosData(Long batchNo) throws Exception {
		double openingBalance=0.0;
		double collectionAmount=0.0;
		List<FiExcelPos> list=this.fiExcelPosService.findBy("batchNo", batchNo);
		if(list.size()==0) throw new ServiceException("未找到POS导入数据");
		for(FiExcelPos fiExcelPos:list){
			collectionAmount=fiExcelPos.getAmount();//交易金额
			
			//保存出纳收款单
			FiCashiercollection fiCashiercollection=new FiCashiercollection();
			fiCashiercollection.setFiCapitaaccountsetId(fiExcelPos.getFiCapitaaccountsetId());
			fiCashiercollection.setCollectionTime(fiExcelPos.getCollectionTime());
			fiCashiercollection.setCollectionAmount(collectionAmount);
			fiCashiercollection.setCreateRemark(fiExcelPos.getRemark());
			this.fiCashiercollectionDao.save(fiCashiercollection);//收纳收款单
			
			//账号设置(余额计算)
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiExcelPos.getFiCapitaaccountsetId());
			if(fiCapitaaccountset.getOpeningBalance()!=null){
				openingBalance = fiCapitaaccountset.getOpeningBalance();// 账号余额
			}
			openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// 账号余额+本次收款金额
			fiCapitaaccountset.setOpeningBalance(openingBalance);// 计算后余额
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
			
			//账号流水
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setLoan(collectionAmount);// 借方金额
			fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			fiCapitaaccount.setSourceData("导入POS");
			fiCapitaaccount.setRemark(fiExcelPos.getRemark());
			fiCapitaaccount.setSourceNo(fiExcelPos.getId());
			fiCapitaaccount.setBalance(openingBalance);// 账号余额+本次收款金额
			this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
		}
	}
	
	public void invalidCashiercollection(FiCashiercollection fiCashiercollection) throws Exception{
		double openingBalance=0.0;
		double collectionAmount=0.0;
		if(fiCashiercollection==null) throw new Exception("出纳收款单不存在!");
		if(fiCashiercollection.getVerificationAmount()!=0.0||fiCashiercollection.getVerificationStatus()!=0L){
		 throw new Exception("出纳收款单已核销或部分核销，不允许删除!");
		}
		fiCashiercollection.setStatus(0L);
		this.fiCashiercollectionDao.save(fiCashiercollection);
		
		collectionAmount=fiCashiercollection.getCollectionAmount();
		
		//账号设置(余额计算)
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCashiercollection.getFiCapitaaccountsetId());
		openingBalance = fiCapitaaccountset.getOpeningBalance();// 账号余额	
		openingBalance = DoubleUtil.sub(openingBalance , collectionAmount);// 账号余额+本次收款金额
		fiCapitaaccountset.setOpeningBalance(openingBalance);// 计算后余额
		this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// 更新账号余额
		
		//账号流水
		FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
		fiCapitaaccount.setLoan(DoubleUtil.mul(collectionAmount,-1));// 借方金额
		fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
		fiCapitaaccount.setSourceData("出纳收款单");
		fiCapitaaccount.setRemark("作废出纳收款单");
		fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
		fiCapitaaccount.setBalance(openingBalance);// 账号余额+本次收款金额
		this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
	}
	
	
	//手工核销
	public void manualVerification(Map map,User user) throws Exception{
		Long id=Long.valueOf(String.valueOf(map.get("id")));//出纳收款单ID
		FiCashiercollection fct=this.fiCashiercollectionDao.get(id);//出纳收款单
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		
		if(fct==null){
			throw new ServiceException("核销失败，出纳收款单ID不存在！");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("出纳收款单已审核");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("出纳收款单已作废");
		}
		
		//保存出纳收款单核销
		fct.setEntrustAmount(fct.getCollectionAmount());
		fct.setEntrustTime(new Date());
		fct.setEntrustUser(String.valueOf(user.get("name")));
		fct.setEntrustRemark("手工核销");
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		fct.setVerificationAmount(fct.getCollectionAmount());
		fct.setVerificationStatus(1L);
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		this.fiCashiercollectionDao.save(fct);
	}
}
