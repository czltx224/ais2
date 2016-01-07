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
	
	//��־
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//��־
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
		
		//д����־��
		StringBuffer logsql=new StringBuffer();
		logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
		logsql.append("select Seq_Opr_History.Nextval,'�������',37,'������||verification_amount||',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
		logsql.append("where reconciliation_no=?");
		this.oprHistoryDao.batchSQLExecute(logsql.toString(),reconciliationNo);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean saveFiReceivablestatement(Map map,Page page,ValidateInfo validateInfo) throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sqlbatch = new StringBuffer();
		StringBuffer sqlselect=new StringBuffer();
		double accountsAmount=0.0; // Ӧ�ս��
		double copeAmount=0.0;//Ӧ�����
		double accountsBalance=0.0; // Ӧ�����
		
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
		//��Ӷ���������ϸ���κţ���ֹ������������µ����ݽ��뵼��ͳ�ƽ�����д��¼��һ��
		this.fiReceivabledetailDao.batchSQLExecute(sqlbatch.toString(),map);
		
		//���ݶԶ��˵���ϸ���κţ������ݻ���д����˵�������д����������ϸ�Ķ��˵��š�
		sqlselect.append("select c.customer_Id,c.Accountsamount,c.DEPART_ID,c.DEPART_NAME,c.Copeamount,c.reconciliation_user,c.CUS_NAME,c.stateDate,c.endDate,nvl(c.OPENING_BALANCE,0) as OPENING_BALANCE from (select fd.customer_Id,fd.DEPART_ID,fd.DEPART_NAME,sum(case when fd.payment_type=1 then fd.amount else 0 end) Accountsamount,sum(case when fd.payment_type=2 then fd.amount else 0 end) Copeamount, min(fd.create_time) as stateDate,max(fd.create_time) as endDate,fa.reconciliation_user,c.CUS_NAME,max(fa.OPENING_BALANCE) OPENING_BALANCE from Fi_Receivabledetail fd, Customer c, Fi_Arrearset fa where fd.customer_Id = c.id and fd.customer_Id = fa.customer_Id(+) and fd.depart_id=fa.depart_id(+)");
		sqlselect.append(" and fd.batch=:SEQ");
		sqlselect.append(" group by fd.customer_Id,fd.DEPART_ID,fd.DEPART_NAME,fa.reconciliation_user,c.CUS_NAME) c");
		Page pagesearch=this.fiReceivabledetailDao.findPageBySqlMap(page, sqlselect.toString(), map);
		if(pagesearch.getTotalCount()<=0){
			throw new ServiceException("δ�ҵ�δ���˵�������ϸ����!");
		}
		System.out.print( pagesearch.getTotalPages());
		for (int i = 1; i <= pagesearch.getTotalPages(); i++) {
			Iterator it=pagesearch.getResultMap().iterator();
			while(it.hasNext()){
				Map mapline=(Map) it.next();
				FiReceivablestatement fiReceivablestatement = new FiReceivablestatement();
				fiReceivablestatement.setAccountsAmount(Double.valueOf(mapline.get("ACCOUNTSAMOUNT")+""));//Ӧ��
				fiReceivablestatement.setCopeAmount(Double.valueOf(mapline.get("COPEAMOUNT")+"")); //Ӧ��
				fiReceivablestatement.setCustomerId(Long.valueOf(mapline.get("CUSTOMER_ID")+ ""));
				fiReceivablestatement.setDepartId(Long.valueOf(mapline.get("DEPART_ID")+ ""));
				fiReceivablestatement.setDepartName(String.valueOf(mapline.get("DEPART_NAME")));
				fiReceivablestatement.setReconciliationUser(mapline.get("RECONCILIATION_USER")+"");
				fiReceivablestatement.setCustomerName(mapline.get("CUS_NAME")+"");
				fiReceivablestatement.setStateDate(sdf.parse(mapline.get("STATEDATE")+""));
				fiReceivablestatement.setEndDate(sdf.parse(mapline.get("ENDDATE")+""));
				
				//�ڳ�����
				if(Double.valueOf(mapline.get("OPENING_BALANCE")+"")>0){
					fiReceivablestatement.setOpeningBalance(Double.valueOf(mapline.get("OPENING_BALANCE")+""));//�ڳ�������˵�
					String hql="update FiArrearset set openingBalance=0 where customerId=? and departId=?";
					fiArrearsetService.batchExecute(hql, Long.valueOf(mapline.get("CUSTOMER_ID")+ ""),Long.valueOf(mapline.get("DEPART_ID")+ ""));
				}
				
				//Ӧ���ܽ��=�ڳ����+(Ӧ��-Ӧ��)
				accountsAmount=DoubleUtil.add(Double.valueOf(mapline.get("OPENING_BALANCE")+""),Double.valueOf(mapline.get("ACCOUNTSAMOUNT")+""));//Ӧ�ս��
				copeAmount=Double.valueOf(mapline.get("COPEAMOUNT")+"");//Ӧ�����
				accountsBalance=DoubleUtil.sub(accountsAmount, copeAmount);//Ӧ��-Ӧ��

				fiReceivablestatement.setAccountsBalance(accountsBalance);
				fiReceivablestatement.setReconciliationStatus(Long.valueOf(2));
				fiReceivablestatementService.save(fiReceivablestatement);
				Long frid=fiReceivablestatement.getId();
				String sqlupdate="update FiReceivabledetail set reconciliationNo=?,reconciliationStatus=2 where batch=? and customerId=?";
				this.fiReceivablestatementService.batchExecute(sqlupdate, Long.valueOf(frid),Long.valueOf(seq),Long.valueOf(mapline.get("CUSTOMER_ID")+""));
				
				//д����־��
				StringBuffer logsql=new StringBuffer();
				logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
				logsql.append("select Seq_Opr_History.Nextval,'���ɶ��˵�',46,'���˵���:"+Long.valueOf(frid)+"',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
				logsql.append("where batch=? and customer_id=?");
				this.oprHistoryDao.batchSQLExecute(logsql.toString(),Long.valueOf(seq),Long.valueOf(mapline.get("CUSTOMER_ID")+""));
			}
			
			if(page.getPageNo()*page.getLimit()+1<=page.getTotalCount()){
				page.setStart(page.getPageNo()*page.getLimit());
				saveFiReceivablestatement(map,page,validateInfo);
			}
		}
		

		validateInfo.setMsg("���˵����ɳɹ���");
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
			if(fiReceivablestatement==null) throw new ServiceException("���˵�������");
			
			if(fiReceivablestatement.getReconciliationStatus()==0L){
				throw  new ServiceException("���˵������ϣ����������!");
			}
			if(fiReceivablestatement.getReconciliationStatus()==3L){
				throw  new ServiceException("���˵�����ˣ����������!");
			}
			
			if(fiReceivabledetail.getPaymentType()==1L){//�տ
				fiReceivablestatement.setAccountsAmount(DoubleUtil.sub(fiReceivablestatement.getAccountsAmount(), fiReceivabledetail.getAmount()));
			}else if(fiReceivabledetail.getPaymentType()==2L){
				fiReceivablestatement.setCopeAmount(DoubleUtil.sub(fiReceivablestatement.getCopeAmount(), fiReceivabledetail.getAmount()));
			}
			fiReceivablestatement.setAccountsBalance(DoubleUtil.sub(fiReceivablestatement.getAccountsAmount(), fiReceivablestatement.getCopeAmount()));
			this.fiReceivablestatementService.save(fiReceivablestatement);
			
			fiReceivabledetail.setReconciliationStatus(1L);
			fiReceivabledetail.setReconciliationNo(null);
			this.fiReceivabledetailDao.save(fiReceivabledetail);
			
		    //������־
			oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "���˵��ţ�"+fiReceivablestatement.getId()+",����������ϸ���ţ�"+fiReceivabledetail.getId()+",��"+fiReceivabledetail.getAmount() ,52L); 
		}
	}
	
	public void receivabledetailAdd(Map map) throws Exception{
		Long fiReceivablestatementId=Long.valueOf(map.get("fiReceivablestatementId")+"");
		String ids=(String)map.get("ids");
		ids=this.tosqlids(ids);
		
		if(fiReceivablestatementId==null){
			throw  new ServiceException("���˵��Ų�����!");
		}
		FiReceivablestatement fiReceivablestatement=this.fiReceivablestatementService.get(fiReceivablestatementId);
		if(fiReceivablestatement==null){
			throw  new ServiceException("���˵�������!");
		}
		
		if(fiReceivablestatement.getReconciliationStatus()==0L){
			throw  new ServiceException("���˵������ϣ����������!");
		}
		if(fiReceivablestatement.getReconciliationStatus()==3L){
			throw  new ServiceException("���˵�����ˣ����������!");
		}
		
		StringBuffer hql=new StringBuffer();
		hql.append("from FiReceivabledetail f where f.id in(");
		hql.append(ids);
		hql.append(")");
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql.toString());
		
		for(FiReceivabledetail fiReceivabledetail:list){

			if(fiReceivabledetail.getPaymentType()==1L){//�տ
				fiReceivablestatement.setAccountsAmount(DoubleUtil.add(fiReceivablestatement.getAccountsAmount(), fiReceivabledetail.getAmount()));
			}else if(fiReceivabledetail.getPaymentType()==2L){
				fiReceivablestatement.setCopeAmount(DoubleUtil.add(fiReceivablestatement.getCopeAmount(), fiReceivabledetail.getAmount()));
			}
			fiReceivablestatement.setAccountsBalance(DoubleUtil.sub(fiReceivablestatement.getAccountsAmount(), fiReceivablestatement.getCopeAmount()));
			this.fiReceivablestatementService.save(fiReceivablestatement);
			
			fiReceivabledetail.setReconciliationStatus(2L);
			fiReceivabledetail.setReconciliationNo(fiReceivablestatement.getId());
			this.fiReceivabledetailDao.save(fiReceivabledetail);
			
		    //������־
			oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "���˵��ţ�"+fiReceivablestatement.getId()+",����������ϸ���ţ�"+fiReceivabledetail.getId()+",��"+fiReceivabledetail.getAmount()+"��ӵ����˵���" ,52L); 
		}
	}
	
	public void saveProble(FiReceivabledetail fiReceivabledetail) throws Exception{
		//���¹�Ӧ��������ϸ�����˿������Ϣ
		fiReceivabledetail.setProblemStatus(Long.valueOf(1));
		getBaseDao().save(fiReceivabledetail);
		
		//���¶��˵��������˿�����Ӧ�ս�
		FiReceivablestatement frt=fiReceivablestatementService.get(fiReceivabledetail.getReconciliationNo());
		if(frt==null){
			throw new ServiceException("���˵�������");
		}
		if(frt.getVerificationStatus()==2L||frt.getVerificationStatus()==3L){
			 throw new ServiceException("���˵��Ѻ����������ٵǼ������˿�");
		}
		frt.setProblemAmount(fiReceivabledetail.getProblemAmount());
		this.fiReceivablestatementService.save(frt);
		
		
		//�����������˿��¼��
		FiProblemreceivable fiProblemreceivable=new FiProblemreceivable();
		fiProblemreceivable.setSourceNo(fiReceivabledetail.getId());
		fiProblemreceivable.setSourceData("Ƿ����˵�");
		fiProblemreceivable.setCustomerId(fiReceivabledetail.getCustomerId());
		fiProblemreceivable.setCustomerName(fiReceivabledetail.getCustomerName());
		fiProblemreceivable.setDno(fiReceivabledetail.getDno());
		fiProblemreceivable.setProblemType(fiReceivabledetail.getProblemType());
		fiProblemreceivable.setProblemAmount(fiReceivabledetail.getProblemAmount());
		fiProblemreceivable.setProblemRemark(fiReceivabledetail.getProblemRemark());
		fiProblemreceivable.setStatus(Long.valueOf(1));
		fiProblemreceivableDao.save(fiProblemreceivable);
		
		//������־
		oprHistoryService.saveFiLog(fiReceivabledetail.getDno(), "���˵��ţ�"+frt.getId()+",����������ϸ���ţ�"+fiReceivabledetail.getId()+",�Ǽ������˿��"+fiProblemreceivable.getProblemAmount() ,53L);
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
		if (fiReceivabledetail.getReconciliationStatus()==4){//3:�����,4:������
			flag=true;
		}
		return flag;
	}
	
	public List<FiReceivabledetail> findCollectionByreconciliationNo(Long reconciliationNo) throws Exception{
		String hql="from FiReceivabledetail f where f.reconciliationNo=? and f.costType='���ջ���'";
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql,reconciliationNo);
		return list;
	}
	
	// ȥ��ѡ�ж�����¼������һ�����ţ��磺(1,2,3,)��ɣ�(1,2,3)
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
		hql.append(") and f.costType='���ջ���' and f.paymentType=2");
		List<FiReceivabledetail> list=this.fiReceivabledetailDao.find(hql.toString());
		return list;
	}
	
	public void invalid(Long reconciliationNo){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		
		//д����־��
		StringBuffer logsql=new StringBuffer();
		logsql.append("insert into opr_history (id,opr_name,opr_node,opr_comment,opr_time,opr_man,opr_depart,d_no,opr_type) ");
		logsql.append("select Seq_Opr_History.Nextval,'���˵�����',47,'�����ϵĶ��˵���:"+reconciliationNo+"',sysdate,'"+user.get("name").toString()+"','"+user.get("rightDepart").toString()+"',d_no,6 from fi_receivabledetail ");
		logsql.append("where reconciliation_no=?");
		this.oprHistoryDao.batchSQLExecute(logsql.toString(),reconciliationNo);
		
		String sqlupdate="update Fi_Receivabledetail set reconciliation_Status=1,reconciliation_no='',verification_time='',verification_status=1,verification_amount='' where reconciliation_No=? ";
		this.fiReceivabledetailDao.batchSQLExecute(sqlupdate,reconciliationNo);
		
	}
	
	@XbwlInt(isCheck=false)
	public void verificationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		if("��������".equals(fiReceivabledetail.getSourceData())){
			sb.append("and sourceData='��������'");
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
	@ModuleName(value="��������Ӧ�����ջ�������״̬",logType=LogType.fi)
	public void verificationReceistatment(FiPayment fiPayment) throws Exception{
		
		double fpAmount=0.0;//Ӧ��Ӧ�����ջ����ܽ��
		double fiReceAmount=0.0;//������ϸ���ջ����ܽ��
		
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
		
		/*if("ʵ�䵥".equals(fiPayment.getSourceData())||"���ᵥ".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='����¼��'");
			list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType());
		}
		if("��������".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='��������' and f.sourceNo=?");
			 list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType(),fiPayment.getSourceNo());
		}
		if(list1.size()<1){
			throw new ServiceException("����ʧ�ܣ�Ӧ�����ջ������");
		}
		
		if(list1.size()>1){
			throw new ServiceException("����ʧ�ܣ�Ӧ�����ջ�����ڶ�����¼");
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
	@ModuleName(value="����Ӧ�մ��ջ��������޸�Ӧ�����ջ���״̬",logType=LogType.fi)
	public void revocationFiPaid(FiPayment fiPayment) throws Exception{
		Double settlementAmount=0.0;
		StringBuffer sb=new StringBuffer();
		List<FiReceivabledetail> list1=null;
		sb.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		if("ʵ�䵥".equals(fiPayment.getSourceData())||"���ᵥ".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='����¼��'");
			list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType());
		}
		if("��������".equals(fiPayment.getSourceData())){
			sb.append("and f.sourceData='��������' and f.sourceNo=?");
			 list1=this.fiReceivabledetailDao.find(sb.toString(),fiPayment.getDocumentsNo(),fiPayment.getCostType(),fiPayment.getSourceNo());
		}
		if(list1.size()<1){
			throw new ServiceException("����ʧ�ܣ�Ӧ�����ջ������");
		}
		
		if(list1.size()>1){
			throw new ServiceException("����ʧ�ܣ�Ӧ�����ջ�����ڶ�����¼");
		}
		FiReceivabledetail frd=list1.get(0);
		
		frd.setCollectionStatus(1L);
		this.fiReceivabledetailDao.save(frd);
	}
	
	@XbwlInt(isCheck=false)
	@ModuleName(value="�������˵�ʱ,����Ӧ�����ջ�������״̬",logType=LogType.fi)
	public void revocationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("from FiReceivabledetail f where f.dno=? and f.costType=? and f.paymentType=2");
		if("��������".equals(fiReceivabledetail.getSourceData())){
			sb.append("and sourceData='��������'");
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
				 .append("  case when fil.cost_type='���ջ���' then fil.amount")
					 .append("  else 0 end PaymentAmount,")
				 .append("  case when fil.cost_type='Ԥ����ֵ��' then fil.amount")
					 .append("  else 0 end CpValueAddFee,")
				 .append(" case when fil.cost_type='���ջ���' then 0 ")
						 .append("  when fil.cost_type='Ԥ����ֵ��' then 0 ")
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
			throw new ServiceException("���������ID");
		}
		if("".equals(fiReceivabledetail.getCostType())||"null".equals(fiReceivabledetail.getCostType())){
			throw new ServiceException("�������������");
		}
		if(fiReceivabledetail.getAmount()<=0.0){
			throw new ServiceException("�����������");
		}
		
		if(fiReceivabledetail.getPaymentType()==1L){
			fiReceivabledetail.setReviewStatus(1L);
		}else{
			fiReceivabledetail.setReviewStatus(0L);
		}
		fiReceivabledetail.setSourceData("�ֹ�����");
		fiReceivabledetail.setSourceNo(0L);
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
	public void audit(Map map,User user) throws Exception{
		Long id=Long.valueOf(map.get("id")+"");
		String reviewRemark=map.get("reviewRemark")+"";
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(id);
		if(fiReceivabledetail==null){
			throw new ServiceException("���ݲ�����!");
		}
		if(fiReceivabledetail.getReconciliationStatus()==0L){
			throw new ServiceException("����������!");
		}
		if(fiReceivabledetail.getReconciliationStatus()>=2L){
			throw new ServiceException("�����Ѷ���!");
		}

		if(fiReceivabledetail.getReviewStatus()==1L){
			throw new ServiceException("����ˣ��������ظ����!");
		}
		fiReceivabledetail.setReviewStatus(1L);//�����
		fiReceivabledetail.setReviewDate(new Date());
		fiReceivabledetail.setReviewUser(user.get("name")+"");
		fiReceivabledetail.setReviewRemark(reviewRemark);
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
	
	public void revocationAudit(Map map,User user) throws Exception{
		Long id=Long.valueOf(map.get("id")+"");
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(id);
		if(fiReceivabledetail==null){
			throw new ServiceException("���ݲ�����!");
		}
		if(fiReceivabledetail.getReconciliationStatus()==0L){
			throw new ServiceException("����������!");
		}
		if(fiReceivabledetail.getReconciliationStatus()>=2L){
			throw new ServiceException("�����Ѷ���,��������!");
		}
		if(fiReceivabledetail.getReviewStatus()==0L){
			throw new ServiceException("���ݻ�δ��ˣ��޷��������!");
		}
		fiReceivabledetail.setReviewStatus(0L);//δ���
		fiReceivabledetail.setReviewDate(new Date());
		fiReceivabledetail.setReviewUser(user.get("name")+"");
		fiReceivabledetail.setReviewRemark("�������");
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
	public void invalid(Map map,User user)throws Exception{
		Long id=Long.valueOf(map.get("id")+"");
		FiReceivabledetail fiReceivabledetail=this.fiReceivabledetailDao.get(id);
		if(fiReceivabledetail==null){
			throw new ServiceException("���ݲ�����!");
		}
		if(fiReceivabledetail.getReconciliationStatus()==0L){
			throw new ServiceException("����������!");
		}
		if(fiReceivabledetail.getReconciliationStatus()>=2L){
			throw new ServiceException("�����Ѷ���,����������!");
		}
		if(fiReceivabledetail.getReviewStatus()==null||fiReceivabledetail.getReviewStatus()==1L){
			throw new ServiceException("��������ˣ�����������!");
		}
		if(!"�ֹ�����".equals(fiReceivabledetail.getSourceData())){
			throw new ServerException("ֻ���ֹ��������ݲ���������!");
		}
		fiReceivabledetail.setReconciliationStatus(0L);
		this.fiReceivabledetailDao.save(fiReceivabledetail);
	}
	
}
