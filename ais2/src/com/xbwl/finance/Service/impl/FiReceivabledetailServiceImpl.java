package com.xbwl.finance.Service.impl;

import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiProblemreceivable;
import com.xbwl.entity.FiReceivabledetail;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.finance.Service.IFiArrearsetService;
import com.xbwl.finance.Service.IFiPaymentService;
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.Service.IFiReceivablestatementService;
import com.xbwl.finance.dao.IFiProblemreceivableDao;
import com.xbwl.finance.dao.IFiReceivabledetailDao;
import com.xbwl.oper.stock.dao.IOprHistoryDao;
import com.xbwl.oper.stock.service.IOprHistoryService;

@Service("fiReceivabledetailServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiReceivabledetailServiceImpl extends
		BaseServiceImpl<FiReceivabledetail, Long> implements
		IFiReceivabledetailService {

	@Resource(name = "fiReceivabledetailHibernateDaoImpl")
	private IFiReceivabledetailDao fiReceivabledetailDao;
	
	@Resource(name="fiProblemreceivableHibernateDaoImpl")
	private IFiProblemreceivableDao fiProblemreceivableDao;

	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	@Resource(name="fiArrearsetServiceImpl")
	private IFiArrearsetService fiArrearsetService;

	@Resource(name = "fiPaymentServiceImpl")
	private IFiPaymentService fiPaymentService;
	
	//日志
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//日志
	@Resource(name="oprHistoryHibernateDaoImpl")
	private IOprHistoryDao oprHistoryDao;

	@Override
	public IBaseDAO getBaseDao() {
		return fiReceivabledetailDao;
	}

	public void updateVerificationStatus(Long reconciliationNo){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String sqlupdate="update Fi_Receivabledetail set verification_status=3,VERIFICATION_AMOUNT=AMOUNT,VERIFICATION_TIME=sysdate where reconciliation_No=? ";
		this.fiReceivabledetailDao.batchSQLExecute(sqlupdate, reconciliationNo);
		
		//写入日志表
		StringBuffer logsql=new StringBuffer();
		logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
		logsql.append("select Seq_Opr_History.Nextval,'还款核销',37,'核销金额：||verification_amount||',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
		logsql.append("where reconciliation_no=?");
		this.oprHistoryDao.batchSQLExecute(logsql.toString(),reconciliationNo);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean saveFiReceivablestatement(Map map,Page page,ValidateInfo validateInfo) throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sqlbatch = new StringBuffer();
		StringBuffer sqlselect=new StringBuffer();
		double accountsAmount=0.0; // 应收金额
		double copeAmount=0.0;//应付金额
		double accountsBalance=0.0; // 应收余额
		
		Long seq; 
		List<Map> searchlist = null;
		page.setLimit(200);
		page.setPageSize(page.getLimit());
		String ids=(String) map.get("ids");
		
		String sqlseq="select SEQ_BATCH.nextval SEQ from dual";
		Map seqid=(Map) this.fiReceivabledetailDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		map.put("SEQ",seq);
		
		if("".equals(ids)||ids==""){
			sqlbatch.append("update Fi_Receivabledetail fd set fd.batch=:SEQ where exists(select 1 from fi_arrearset fa,customer ct where fd.customer_id=fa.customer_id");
			sqlbatch.append(" and fd.CREATE_TIME>=:stateDate and fd.CREATE_TIME<=:endDate and fd.Reconciliation_status=1");
			if (map.get("departId")==null) {
				map.put("departId", user.get("bussDepart"));
			}
			sqlbatch.append(" and fd.depart_Id=:departId");
			
			if (map.get("customerId") != null) {
				sqlbatch.append(" and fd.customer_id=:customerId");
			}

			if (map.get("reconciliationUser")!=null) {
				sqlbatch.append(" and fa.reconciliationUser=:reconciliationUser");
			}
			if(!"".equals(map.get("custprop"))){
				sqlbatch.append(" and ct.CUSTPROP=:custprop");
			}
			if(map.get("billingCycle")!=null){
				sqlbatch.append(" and fa.billing_Cycle=:billingCycle");
			}
			sqlbatch.append(")");
		}else{
			sqlbatch.append("update Fi_Receivabledetail fd set fd.batch=:SEQ where fd.id in (");
			sqlbatch.append(tosqlids(ids));
			sqlbatch.append(") and fd.Reconciliation_status=1");
		}
		//添加对账往来明细批次号，防止处理过程中有新的数据进入导致统计金额与回写记录不一致
		this.fiReceivabledetailDao.batchSQLExecute(sqlbatch.toString(),map);
		
		//根据对对账单明细批次号，将数据汇总写入对账单表，并回写对账往来明细的对账单号。
		sqlselect.append("select c.customer_Id,c.Accountsamount,c.DEPART_ID,c.DEPART_NAME,c.Copeamount,c.reconciliation_user,c.CUS_NAME,c.stateDate,c.endDate,nvl(c.OPENING_BALANCE,0) as OPENING_BALANCE from (select fd.customer_Id,fd.DEPART_ID,fd.DEPART_NAME,sum(case when fd.payment_type=1 then fd.amount else 0 end) Accountsamount,sum(case when fd.payment_type=2 then fd.amount else 0 end) Copeamount, min(fd.create_time) as stateDate,max(fd.create_time) as endDate,fa.reconciliation_user,c.CUS_NAME,max(fa.OPENING_BALANCE) OPENING_BALANCE from Fi_Receivabledetail fd, Customer c, Fi_Arrearset fa where fd.customer_Id = c.id and fd.customer_Id = fa.customer_Id(+) and fd.depart_id=fa.depart_id(+)");
		sqlselect.append(" and fd.batch=:SEQ");
		sqlselect.append(" group by fd.customer_Id,fd.DEPART_ID,fd.DEPART_NAME,fa.reconciliation_user,c.CUS_NAME) c");
		Page pagesearch=this.fiReceivabledetailDao.findPageBySqlMap(page, sqlselect.toString(), map);
		if(pagesearch.getTotalCount()<=0){
			throw new ServiceException("未找到未对账的往来明细数据!");
		}
		System.out.print( pagesearch.getTotalPages());
		for (int i = 1; i <= pagesearch.getTotalPages(); i++) {
			Iterator it=pagesearch.getResultMap().iterator();
			while(it.hasNext()){
				Map mapline=(Map) it.next();
				FiReceivablestatement fiReceivablestatement = new FiReceivablestatement();
				fiReceivablestatement.setAccountsAmount(Double.valueOf(mapline.get("ACCOUNTSAMOUNT")+""));//应收
				fiReceivablestatement.setCopeAmount(Double.valueOf(mapline.get("COPEAMOUNT")+"")); //应付
				fiReceivablestatement.setCustomerId(Long.valueOf(mapline.get("CUSTOMER_ID")+ ""));
				fiReceivablestatement.setDepartId(Long.valueOf(mapline.get("DEPART_ID")+ ""));
				fiReceivablestatement.setDepartName(String.valueOf(mapline.get("DEPART_NAME")));
				fiReceivablestatement.setReconciliationUser(mapline.get("RECONCILIATION_USER")+"");
				fiReceivablestatement.setCustomerName(mapline.get("CUS_NAME")+"");
				fiReceivablestatement.setStateDate(sdf.parse(mapline.get("STATEDATE")+""));
				fiReceivablestatement.setEndDate(sdf.parse(mapline.get("ENDDATE")+""));
				
				//期初余额处理
				if(Double.valueOf(mapline.get("OPENING_BALANCE")+"")>0){
					fiReceivablestatement.setOpeningBalance(Double.valueOf(mapline.get("OPENING_BALANCE")+""));//期初加入对账单
					String hql="update FiArrearset set openingBalance=0 where customerId=? and departId=?";
					fiArrearsetService.batchExecute(hql, Long.valueOf(mapline.get("CUSTOMER_ID")+ ""),Long.valueOf(mapline.get("DEPART_ID")+ ""));
				}
				
				//应收总金额=期初余额+(应收-应付)
				accountsAmount=DoubleUtil.add(Double.valueOf(mapline.get("OPENING_BALANCE")+""),Double.valueOf(mapline.get("ACCOUNTSAMOUNT")+""));//应收金额
				copeAmount=Double.valueOf(mapline.get("COPEAMOUNT")+"");//应付金额
				accountsBalance=DoubleUtil.sub(accountsAmount, copeAmount);//应收-应付

				fiReceivablestatement.setAccountsBalance(accountsBalance);
				fiReceivablestatement.setReconciliationStatus(Long.valueOf(2));
				fiReceivablestatementService.save(fiReceivablestatement);
				Long frid=fiReceivablestatement.getId();
				String sqlupdate="update FiReceivabledetail set reconciliationNo=?,reconciliationStatus=2 where batch=? and customerId=?";
				this.fiReceivablestatementService.batchExecute(sqlupdate, Long.valueOf(frid),Long.valueOf(seq),Long.valueOf(mapline.get("CUSTOMER_ID")+""));
				
				//写入日志表
				StringBuffer logsql=new StringBuffer();
				logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
				logsql.append("select Seq_Opr_History.Nextval,'生成对账单',46,'对账单号:"+Long.valueOf(frid)+"',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
				logsql.append("where batch=? and customer_id=?");
				this.oprHistoryDao.batchSQLExecute(logsql.toString(),Long.valueOf(seq),Long.valueOf(mapline.get("CUSTOMER_ID")+""));
			}
			
			if(page.getPageNo()*page.getLimit()+1<=page.getTotalCount()){
				page.setStart(page.getPageNo()*page.getLimit());
				saveFiReceivablestatement(map,page,validateInfo);
			}
		}
		

		validateInfo.setMsg("对账单生成成功！");
		return true;
	}
	
	@XbwlInt(isCheck=false)
	public void eliminate(Map map)  throws Exception{
		String ids=(String)map.get("ids");
		ids=this.tosqlids(ids);
		StringBuffer hql=new StringBuffer();
		hql.append("from FiReceivabledetail f where f.id in(");
		hql.append(ids);
		hql.append(")");
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql.toString());
		for(FiReceivabledetail fiReceivabledetail:list){
			FiReceivablestatement fiReceivablestatement=this.fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
			if(fiReceivablestatement==null) throw new ServiceException("对账单不存在");
			
			if(fiReceivablestatement.getReconciliationStatus()==0L){
				throw  new ServiceException("对账单已作废，不允许操作!");
			}
			if(fiReceivablestatement.getReconciliationStatus()==3L){
				throw  new ServiceException("对账单已审核，不允许操作!");
			}
			
			if(fiReceivabledetail.getPaymentType()==1L){//收款单
				fiReceivablestatement.setAccountsAmount(DoubleUtil.sub(fiReceivablestatement.getAccountsAmount(), fiReceivabledetail.getAmount()));
			}else if(fiReceivabledetail.getPaymentType()==2L){
				fiReceivablestatement.setCopeAmount(DoubleUtil.sub(fiReceivablestatement.getCopeAmount(), fiReceivabledetail.getAmount()));
			}
			fiReceivablestatement.setAccountsBalance(DoubleUtil.sub(fiReceivablestatement.getAccountsAmount(), fiReceivablestatement.getCopeAmount()));
			this.fiReceivablestatementService.save(fiReceivablestatement);
			
			fiReceivabledetail.setReconciliationStatus(1L);
			fiReceivabledetail.setReconciliationNo(null);
			this.fiReceivabledetailDao.save(fiReceivabledetail);
			
		    //操作日志
			oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "对账单号："+fiReceivablestatement.getId()+",客商往来明细单号："+fiReceivabledetail.getId()+",金额："+fiReceivabledetail.getAmount() ,52L); 
		}
	}
	
	public void receivabledetailAdd(Map map) throws Exception{
		Long fiReceivablestatementId=Long.valueOf(map.get("fiReceivablestatementId")+"");
		String ids=(String)map.get("ids");
		ids=this.tosqlids(ids);
		
		if(fiReceivablestatementId==null){
			throw  new ServiceException("对账单号不存在!");
		}
		FiReceivablestatement fiReceivablestatement=this.fiReceivablestatementService.get(fiReceivablestatementId);
		if(fiReceivablestatement==null){
			throw  new ServiceException("对账单不存在!");
		}
		
		if(fiReceivablestatement.getReconciliationStatus()==0L){
			throw  new ServiceException("对账单已作废，不允许操作!");
		}
		if(fiReceivablestatement.getReconciliationStatus()==3L){
			throw  new ServiceException("对账单已审核，不允许操作!");
		}
		
		StringBuffer hql=new StringBuffer();
		hql.append("from FiReceivabledetail f where f.id in(");
		hql.append(ids);
		hql.append(")");
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql.toString());
		
		for(FiReceivabledetail fiReceivabledetail:list){

			if(fiReceivabledetail.getPaymentType()==1L){//收款单
				fiReceivablestatement.setAccountsAmount(DoubleUtil.add(fiReceivablestatement.getAccountsAmount(), fiReceivabledetail.getAmount()));
			}else if(fiReceivabledetail.getPaymentType()==2L){
				fiReceivablestatement.setCopeAmount(DoubleUtil.add(fiReceivablestatement.getCopeAmount(), fiReceivabledetail.getAmount()));
			}
			fiReceivablestatement.setAccountsBalance(DoubleUtil.sub(fiReceivablestatement.getAccountsAmount(), fiReceivablestatement.getCopeAmount()));
			this.fiReceivablestatementService.save(fiReceivablestatement);
			
			fiReceivabledetail.setReconciliationStatus(2L);
			fiReceivabledetail.setReconciliationNo(fiReceivablestatement.getId());
			this.fiReceivabledetailDao.save(fiReceivabledetail);
			
		    //操作日志
			oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "对账单号："+fiReceivablestatement.getId()+",客商往来明细单号："+fiReceivabledetail.getId()+",金额："+fiReceivabledetail.getAmount()+"添加到对账单中" ,52L); 
		}
	}
	
	public void saveProble(FiReceivabledetail fiReceivabledetail) throws Exception{
		//更新供应商往来明细问题账款相关信息
		fiReceivabledetail.setProblemStatus(Long.valueOf(1));
		getBaseDao().save(fiReceivabledetail);
		
		//更新对账单的问题账款金额与应收金额。
		FiReceivablestatement frt=fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		if(frt==null){
			throw new ServiceException("对账单不存在");
		}
		if(frt.getVerificationStatus()==2L||frt.getVerificationStatus()==3L){
			 throw new ServiceException("对账单已核销，不能再登记问题账款");
		}
		frt.setProblemAmount(fiReceivabledetail.getProblemAmount());
		this.fiReceivablestatementService.save(frt);
		
		
		//新增至问题账款记录表
		FiProblemreceivable fiProblemreceivable=new FiProblemreceivable();
		fiProblemreceivable.setSourceNo(fiReceivabledetail.getId());
		fiProblemreceivable.setSourceData("欠款对账单");
		fiProblemreceivable.setCustomerId(fiReceivabledetail.getCustomerId());
		fiProblemreceivable.setCustomerName(fiReceivabledetail.getCustomerName());
		fiProblemreceivable.setDno(fiReceivabledetail.getDno());
		fiProblemreceivable.setProblemType(fiReceivabledetail.getProblemType());
		fiProblemreceivable.setProblemAmount(fiReceivabledetail.getProblemAmount());
		fiProblemreceivable.setProblemRemark(fiReceivabledetail.getProblemRemark());
		fiProblemreceivable.setStatus(Long.valueOf(1));
		fiProblemreceivableDao.save(fiProblemreceivable);
		
		//操作日志
		oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "对账单号："+frt.getId()+",客商往来明细单号："+fiReceivabledetail.getId()+",登记问题账款金额："+fiProblemreceivable.getProblemAmount() ,53L);
	}
	
	public void updateStatusByreconciliationNo(Long reconciliationNo,Long reconciliationStatus){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String sqlupdate="update Fi_Receivabledetail set reconciliation_Status=? where reconciliation_No=? ";
		this.fiReceivabledetailDao.batchSQLExecute(sqlupdate, reconciliationStatus,reconciliationNo);
	}
	
	public boolean isProbleBytailNo(Long id){
		boolean flag=true;
		long problemStatus;
		FiReceivabledetail frd=this.fiReceivabledetailDao.get(id);
		problemStatus=(frd.getProblemStatus()==null)?0:frd.getProblemStatus();
		if(problemStatus!=0){
			flag=false;
		}
		return flag;
	}
	
	public boolean isStatusAudited(Long receivabledetailNo){
		boolean flag=false;
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(receivabledetailNo);
		if (fiReceivabledetail.getReconciliationStatus()==4){//3:已审核,4:已收银
			flag=true;
		}
		return flag;
	}
	
	public List<FiReceivabledetail> findCollectionByreconciliationNo(Long reconciliationNo) throws Exception{
		String hql="from FiReceivabledetail f where f.reconciliationNo=? and f.costType='代收货款'";
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql,reconciliationNo);
		return list;
	}
	
	// 去掉选中多条记录后的最后一个逗号，如：(1,2,3,)变成：(1,2,3)
	private String tosqlids(String ids) throws Exception {
		if (!ids.equals("")) {
			String last;
			last = ids.substring(ids.length() - 1, ids.length());
			if (last.equals(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
		}
		return ids;

	}
	
	public List<FiReceivabledetail> findCollectionByDnos(String dnos) throws Exception{
		dnos=this.tosqlids(dnos);
		StringBuffer hql=new StringBuffer();
		hql.append("from FiReceivabledetail f where f.dno in(");
		hql.append(dnos);
		hql.append(") and f.costType='代收货款' and f.paymentType=2");
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql.toString());
		return list;
	}
	
	public void invalid(Long reconciliationNo){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		
		//写入日志表
		StringBuffer logsql=new StringBuffer();
		logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
		logsql.append("select Seq_Opr_History.Nextval,'对账单作废',47,'被作废的对账单号:"+reconciliationNo+"',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
		logsql.append("where reconciliation_no=?");
		this.oprHistoryDao.batchSQLExecute(logsql.toString(),reconciliationNo);
		
		String sqlupdate="update Fi_Receivabledetail set reconciliation_Status=1,reconciliation_no='',verification_time='',verification_status=1,verification_amount='' where reconciliation_No=? ";
		this.fiReceivabledetailDao.batchSQLExecute(sqlupdate,reconciliationNo);
		
	}
	
	@XbwlInt(isCheck=false)
	public void verificationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		if("更改申请".equals(fiReceivabledetail.getSourceData())){
			sb.append("and sourceData='更改申请'");
		}
		List<FiReceivabledetail> list1=this.fiReceivabledetailDao.find(sb.toString(),fiReceivabledetail.getDno(),fiReceivabledetail.getCostType());
		for(FiReceivabledetail frd:list1){
			if(frd.getAmount().equals(fiReceivabledetail.getAmount())){
				frd.setCollectionStatus(2L);
				this.fiReceivabledetailDao.save(frd);
			}
		}
	}
	
	@XbwlInt(isCheck=false)
	@ModuleName(value="收银核销应付代收货款收银状态",logType=LogType.fi)
	public void verificationReceistatment(FiPayment fiPayment) throws Exception{
		
		double fpAmount=0.0;//应收应付代收货款总金额
		double fiReceAmount=0.0;//往来明细代收货款总金额
		
		StringBuffer sb=new StringBuffer();
		StringBuffer sb1=new StringBuffer();
		sb.append("from FiPayment f where f.paymentType=1 and f.documentsNo=? and f.costType=? and f.documentsSmalltype=?");
		List<FiPayment> list=this.fiPaymentService.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType(),fiPayment.getDocumentsSmalltype());
		for(FiPayment fp:list){
			fpAmount=DoubleUtil.add(fpAmount, fp.getAmount());
		}
		sb1.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		List<FiReceivabledetail> list1=this.fiReceivabledetailDao.find(sb1.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType());
		for(FiReceivabledetail frd:list1){
			fiReceAmount=DoubleUtil.add(fiReceAmount, frd.getAmount());
		}
		
		/*if("实配单".equals(fiPayment.getSourceData())||"自提单".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='传真录入'");
			list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType());
		}
		if("更改申请".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='更改申请' and f.sourceNo=?");
			 list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType(),fiPayment.getSourceNo());
		}
		if(list1.size()<1){
			throw new ServiceException("核销失败，应付代收货款不存在");
		}
		
		if(list1.size()>1){
			throw new ServiceException("核销失败，应付代收货款存在多条记录");
		}
		FiReceivabledetail frd=list1.get(0);
		*/
		if(fpAmount>=fiReceAmount){
			this.fiReceivabledetailDao.batchExecute("update FiReceivabledetail f set f.collectionStatus=2L where f.dno=? and f.costType=? and f.paymentType=2", fiPayment.getDocumentsNo(),fiPayment.getCostType());
			//frd.setCollectionStatus(2L);
			//this.fiReceivabledetailDao.save(frd);
		}
	}
	
	@XbwlInt(isCheck=false)
	@ModuleName(value="撤销应收代收货款收银修改应付代收货款状态",logType=LogType.fi)
	public void revocationFiPaid(FiPayment fiPayment) throws Exception{
		Double settlementAmount=0.0;
		StringBuffer sb=new StringBuffer();
		List<FiReceivabledetail> list1=null;
		sb.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		if("实配单".equals(fiPayment.getSourceData())||"自提单".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='传真录入'");
			list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType());
		}
		if("更改申请".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='更改申请' and f.sourceNo=?");
			 list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType(),fiPayment.getSourceNo());
		}
		if(list1.size()<1){
			throw new ServiceException("核销失败，应付代收货款不存在");
		}
		
		if(list1.size()>1){
			throw new ServiceException("核销失败，应付代收货款存在多条记录");
		}
		FiReceivabledetail frd=list1.get(0);
		
		frd.setCollectionStatus(1L);
		this.fiReceivabledetailDao.save(frd);
	}
	
	@XbwlInt(isCheck=false)
	@ModuleName(value="撤销对账单时,撤销应付代收货款收银状态",logType=LogType.fi)
	public void revocationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		if("更改申请".equals(fiReceivabledetail.getSourceData())){
			sb.append("and sourceData='更改申请'");
		}
		List<FiReceivabledetail> list1=this.fiReceivabledetailDao.find(sb.toString(),fiReceivabledetail.getDno(),fiReceivabledetail.getCostType());
		for(FiReceivabledetail frd:list1){
			if(frd.getAmount().equals(fiReceivabledetail.getAmount())){
				frd.setCollectionStatus(2L);
				this.fiReceivabledetailDao.save(frd);
			}
		}
	}

	public StringBuffer getAllReceivabledetailSql() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("  select  min(orn.cp_name) as cpname,")
				    .append(" min(orn.flight_main_no) as flightmainno,")
				    .append(" min(orn.sub_no) as subno,")
				    .append(" firec.d_no as dno,")
				    .append(" min(orn.piece) as piece,")
				    .append(" min(orn.cus_weight) as cusweight,")
					.append(" min(orn.consignee) as consignee,")
					.append(" min(orn.create_time) as createtime,")
					.append(" sum(PaymentAmount) PaymentAmount,")
					.append(" sum(CpFee) CpFee,")
					.append(" min(orn.addr) addr,")
					.append(" sum(CpValueAddFee) CpValueAddFee,")
					.append(" sum(orn.bulk) bulk")
	    .append("  from")
		    .append("  (select fil.d_no d_no,")
				 .append("  case when fil.cost_type='代收货款' then fil.amount")
					 .append("  else 0 end PaymentAmount,")
				 .append("  case when fil.cost_type='预付增值费' then fil.amount")
					 .append("  else 0 end CpValueAddFee,")
				 .append(" case when fil.cost_type='代收货款' then 0 ")
						 .append("  when fil.cost_type='预付增值费' then 0 ")
				 .append("  else fil.amount end CpFee ")
				  .append(" from fi_receivabledetail fil ")
				 .append(" where fil.reconciliation_no=:fid    ) firec,")
					 .append(" opr_fax_in orn ")
		.append(" where  firec.d_no=orn.d_no ")
		.append(" group by  firec.d_no ")
		.append("  order by  min(orn.create_time) ");
		return sb;
	}
	
	public void saveReceivabledetail(FiReceivabledetail fiReceivabledetail) throws Exception{
		if(fiReceivabledetail.getCustomerId()==null){
			throw new ServiceException("请输入客商ID");
		}
		if("".equals(fiReceivabledetail.getCostType())||"null".equals(fiReceivabledetail.getCostType())){
			throw new ServiceException("请输入费用类型");
		}
		if(fiReceivabledetail.getAmount()<=0.0){
			throw new ServiceException("金额必须大于零");
		}
		
		if(fiReceivabledetail.getPaymentType()==1L){
			fiReceivabledetail.setReviewStatus(1L);
		}else{
			fiReceivabledetail.setReviewStatus(0L);
		}
		fiReceivabledetail.setSourceData("手工新增");
		fiReceivabledetail.setSourceNo(0L);
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
	public void audit(Map map,User user) throws Exception{
		Long id=Long.valueOf(map.get("id")+"");
		String reviewRemark=map.get("reviewRemark")+"";
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(id);
		if(fiReceivabledetail==null){
			throw new ServiceException("单据不存在!");
		}
		if(fiReceivabledetail.getReconciliationStatus()==0L){
			throw new ServiceException("单据已作废!");
		}
		if(fiReceivabledetail.getReconciliationStatus()>=2L){
			throw new ServiceException("单据已对账!");
		}

		if(fiReceivabledetail.getReviewStatus()==1L){
			throw new ServiceException("已审核，不允许重复审核!");
		}
		fiReceivabledetail.setReviewStatus(1L);//已审核
		fiReceivabledetail.setReviewDate(new Date());
		fiReceivabledetail.setReviewUser(user.get("name")+"");
		fiReceivabledetail.setReviewRemark(reviewRemark);
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
	
	public void revocationAudit(Map map,User user) throws Exception{
		Long id=Long.valueOf(map.get("id")+"");
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(id);
		if(fiReceivabledetail==null){
			throw new ServiceException("单据不存在!");
		}
		if(fiReceivabledetail.getReconciliationStatus()==0L){
			throw new ServiceException("单据已作废!");
		}
		if(fiReceivabledetail.getReconciliationStatus()>=2L){
			throw new ServiceException("单据已对账,无允许撤销!");
		}
		if(fiReceivabledetail.getReviewStatus()==0L){
			throw new ServiceException("单据还未审核，无法撤销审核!");
		}
		fiReceivabledetail.setReviewStatus(0L);//未审核
		fiReceivabledetail.setReviewDate(new Date());
		fiReceivabledetail.setReviewUser(user.get("name")+"");
		fiReceivabledetail.setReviewRemark("撤销审核");
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
	public void invalid(Map map,User user)throws Exception{
		Long id=Long.valueOf(map.get("id")+"");
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(id);
		if(fiReceivabledetail==null){
			throw new ServiceException("单据不存在!");
		}
		if(fiReceivabledetail.getReconciliationStatus()==0L){
			throw new ServiceException("单据已作废!");
		}
		if(fiReceivabledetail.getReconciliationStatus()>=2L){
			throw new ServiceException("单据已对账,无允许作废!");
		}
		if(fiReceivabledetail.getReviewStatus()==null||fiReceivabledetail.getReviewStatus()==1L){
			throw new ServiceException("单据已审核，不允许作废!");
		}
		if(!"手工新增".equals(fiReceivabledetail.getSourceData())){
			throw new ServerException("只有手工新增单据才允许作废!");
		}
		fiReceivabledetail.setReconciliationStatus(0L);
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
}
