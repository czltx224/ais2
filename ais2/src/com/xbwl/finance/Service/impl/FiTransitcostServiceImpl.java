package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiOutcost;
import com.xbwl.entity.FiTransitcost;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.Service.IFiTransitcostService;
import com.xbwl.finance.dao.IFiCostDao;
import com.xbwl.finance.dao.IFiTransicostDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author shuw
 * time Oct 7, 2011 11:47:14 AM
 */
@Service("fiTransitcostServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiTransitcostServiceImpl extends BaseServiceImpl<FiTransitcost, Long> implements
	IFiTransitcostService {

	@Resource(name = "fiTransitcostHibernateDaoImpl")
	private IFiTransicostDao fiTransicostDao;

	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;

	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;	
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface fiInterfaceImpl;
	@Override
	public IBaseDAO<FiTransitcost, Long> getBaseDao() {
		return fiTransicostDao;
	}

	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;

	@Value("${fiAuditCost.log_qxAuditCost}")
	private Long log_qxAuditCost ;

	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	//����״̬��
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	/* 
	 *�����ɱ����
	 */
	@ModuleName(value="��ת�����ɱ�����",logType=LogType.fi)
	public String saveFiTransitcostAndFicost(User user,String ts,Long id) throws Exception {
		//Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
		Long batchNo= getBatchNo();
		FiTransitcost fiTransitcost= fiTransicostDao.get(id);
		OprFaxIn oprFaxIn =oprFaxInDao.get(fiTransitcost.getDno());
		
		fiTransitcost.setBatchNo(batchNo);
		fiTransitcost.setStatus(1l);
		fiTransitcost.setReviewUser(user.get("name").toString());
		fiTransitcost.setReviewDate(new Date());
		fiTransitcost.setReviewRemark("֧����ת�����ɱ�"+fiTransitcost.getAmount()+"Ԫ");
		oprHistoryService.saveLog(oprFaxIn.getDno(), "������ת�ɱ�(�����Ǽ�)�������"+fiTransitcost.getAmount() , log_auditCost);     //������־
		
		//���浽�ɱ�����
		FiCost fiCost = new FiCost();
		fiCost.setCostType("��ת�ɱ�");
		fiCost.setDataSource("��ת�ɱ�");
		fiCost.setCostAmount(fiTransitcost.getAmount());
		fiCost.setCostTypeDetail("�����Ǽ�");
		fiCost.setDataSource("��ת�ɱ�");
		fiCost.setCostTypeDetail("����¼��");
		fiCost.setCostAmount(fiTransitcost.getAmount());
		fiCost.setStatus(1l);
		fiCost.setDno(fiTransitcost.getDno());
		fiCostDao.save(fiCost);
		
		//customerDao.get(oprFaxIn.getGoWhereId());

		//���浽Ӧ��Ӧ������
		FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();
		fiInterfaceProDto.setCustomerId(oprFaxIn.getGoWhereId());
		fiInterfaceProDto.setCustomerName(oprFaxIn.getGowhere());
		fiInterfaceProDto.setDistributionMode("����");
		fiInterfaceProDto.setDno(fiTransitcost.getDno());
		fiInterfaceProDto.setSettlementType(2l);
		fiInterfaceProDto.setDocumentsType("�ɱ�");
		fiInterfaceProDto.setAmount(fiTransitcost.getAmount());
		fiInterfaceProDto.setDocumentsSmalltype("���͵�");
		fiInterfaceProDto.setDocumentsNo(fiTransitcost.getDno());
		fiInterfaceProDto.setCollectionUser(fiTransitcost.getReviewUser());
		fiInterfaceProDto.setCostType("��ת��");
		fiInterfaceProDto.setDepartId(Long.parseLong(user.get("departId").toString()));
		fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
		fiInterfaceProDto.setSourceData("��ת�ɱ�");
		fiInterfaceProDto.setSourceNo(fiTransitcost.getId());
		listPro.add(fiInterfaceProDto);

		fiInterfaceImpl.addFinanceInfo(listPro);
		 return id+"";
	}

	/* 
	 * ����¼�� ����ת�ɱ����
	 * @see com.xbwl.finance.Service.IFiTransitcostService#saveFiTransitcostAndFicost(org.ralasafe.user.User, java.util.List)
	 */
	@ModuleName(value="��ת�ɱ�����",logType=LogType.fi)
	public String saveFiTransitcostAndFicost(User user, List<FiTransitcost> aa) throws Exception {
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();
		StringBuffer ids=new StringBuffer(500);
		Long batchNo= getBatchNo();
		for(FiTransitcost fiTransitcost2 : aa){
		
			 OprFaxIn oprFaxIn =oprFaxInDao.get(fiTransitcost2.getDno());
		/*	
			if(!oprFaxIn.getInDepartId().equals(Long.parseLong(user.get("bussDepart")+""))){
				throw new ServiceException("������˷Ǳ�ҵ���ŵ����ݣ�лл��");
			}*/
			
			FiTransitcost fiTransitcost = new FiTransitcost();
			fiTransitcost.setAmount(oprFaxIn.getTraFee());
			fiTransitcost.setDno(oprFaxIn.getDno());
			fiTransitcost.setCustomerId(oprFaxIn.getGoWhereId());
			fiTransitcost.setCustomerName(oprFaxIn.getGowhere());
			fiTransitcost.setPayStatus(0l);
			fiTransitcost.setSourceData("����¼��");
			fiTransitcost.setBatchNo(batchNo);
			fiTransitcost.setStatus(1l);
			fiTransitcost.setReviewUser(user.get("name").toString());
			fiTransitcost.setReviewDate(new Date());
			fiTransitcost.setReviewRemark("֧����ת�ɱ�"+fiTransitcost.getAmount()+"Ԫ");
			save(fiTransitcost);
			
			ids.append(fiTransitcost.getId()).append(",");
			oprHistoryService.saveLog(oprFaxIn.getDno(), "������ת�ɱ��������"+fiTransitcost.getAmount() , log_auditCost);     //������־
			
			List<OprStatus> listu =oprStatusDao.find(" from OprStatus o where o.dno=?  ",oprFaxIn.getDno());
			if(listu.size()==1){
				
				boolean flag=oprFaxIn.getConsigneeFee()==0.0&&oprFaxIn.getPaymentCollection()==0.0&&oprFaxIn.getCusValueAddFee()==0.0;
				//�ж�����״̬
				if(!flag){
					if(listu.get(0).getCashStatus()!=1l){
						throw new ServiceException("���͵���"+oprFaxIn.getDno()+"�Ļ���û������ȷ�ϣ����ܽ��и���");
					}
				}
				
				if(listu.get(0).getFeeAuditStatus()==1l){
					throw new ServiceException("���͵���"+oprFaxIn.getDno()+"�Ļ�����ת�ɱ��Ѹ���");
				}else{
					listu.get(0).setFeeAuditStatus(1l);
					listu.get(0).setFeeAuditTime(new Date());
					oprStatusDao.save(listu.get(0));
				}
			}else{
				throw new ServiceException("��ѯ���͵���"+oprFaxIn.getDno()+"�Ļ�����ת�ɱ�����״̬����");
			}
			
			//���浽�ɱ�����
			FiCost fiCost = new FiCost();
			fiCost.setCostType("��ת�ɱ�");
			fiCost.setDataSource("��ת�ɱ�");
			fiCost.setCostAmount(fiTransitcost.getAmount());
			fiCost.setCostTypeDetail("�����Ǽ�");
			fiCost.setDataSource("��ת�ɱ�");
			fiCost.setCostTypeDetail("����¼��");
			fiCost.setCostAmount(oprFaxIn.getTraFee());
			fiCost.setStatus(1l);
			fiCost.setDno(fiTransitcost.getDno());
			fiCostDao.save(fiCost);
			
			FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
			fiInterfaceProDto.setCustomerId(oprFaxIn.getGoWhereId());
			fiInterfaceProDto.setCustomerName(oprFaxIn.getGowhere());
			fiInterfaceProDto.setDistributionMode("����");
			fiInterfaceProDto.setDno(fiTransitcost.getDno());
			fiInterfaceProDto.setAmount(fiTransitcost.getAmount());
			fiInterfaceProDto.setSettlementType(2l);
			fiInterfaceProDto.setDocumentsType("�ɱ�");
			fiInterfaceProDto.setDocumentsSmalltype("���͵�");
			fiInterfaceProDto.setDocumentsNo(fiTransitcost.getDno());
			fiInterfaceProDto.setCollectionUser(fiTransitcost.getReviewUser());
			fiInterfaceProDto.setCostType("��ת��");
			fiInterfaceProDto.setDepartId(Long.parseLong(user.get("bussDepart").toString()));
			fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
			fiInterfaceProDto.setSourceData("��ת�ɱ�");
			fiInterfaceProDto.setSourceNo(fiTransitcost.getId());
			listPro.add(fiInterfaceProDto);
		}
		String  s =fiInterfaceImpl.addFinanceInfo(listPro);
		 return ids.toString();
	}
	
	/* 
	 * �������
	 */
	@ModuleName(value="������ת�ɱ�����",logType=LogType.fi)
	public void qxFiAudit(Long id, String ts,String sourceData) throws Exception {
		FiTransitcost fiTransitcost = getAndInitEntity(id);
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
	
	/*	if(bussDepartId-fiTransitcost.getDepartId()!=0){
			throw new ServiceException("�����������ҵ���ŵ�����");
		}*/
		
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();
		fiTransitcost.setTs(ts);
		if("�����Ǽ�".equals(sourceData)){
			fiTransitcost.setStatus(0l);
			fiTransitcost.setReviewRemark(null);
			fiTransitcost.setReviewUser(null);
			save(fiTransitcost);
			
			FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
			fiInterfaceProDto.setSourceData("��ת�ɱ�");
			fiInterfaceProDto.setCustomerId(fiTransitcost.getCustomerId());
			fiInterfaceProDto.setSourceNo(fiTransitcost.getId());
			listPro.add(fiInterfaceProDto);     //���ò���ӿڣ�����Ӧ��Ӧ������
			
			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("��ת�ɱ�");
			fiCostNew.setCostTypeDetail("�����Ǽ�");
			fiCostNew.setCostAmount(- fiTransitcost.getAmount());
			fiCostNew.setDataSource("�����Ǽ�");
			fiCostNew.setDno(fiTransitcost.getDno());
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //��ȥ�����ɱ�
			
			oprHistoryService.saveLog(fiTransitcost.getDno(), "��ת�����ɱ��������������"+fiTransitcost.getAmount() , log_auditCost);     //������־
		}else{
			String hql="from FiTransitcost fo where fo.dno=?  and fo.status=1 and fo.departId=? and  (fo.sourceData=? or fo.sourceData=? ) and fo.customerId=? ";
			List<FiTransitcost>list =find(hql,fiTransitcost.getDno(),fiTransitcost.getDepartId(),"����¼��","��������",fiTransitcost.getCustomerId());  
			if(list.size()==0){
				//REVIEW-ACCEPT ��ʾҪ׼ȷ
				//FIXED
				throw new ServiceException("���Ҳ�����ת�ɱ�����");
			}
			
			double costDouble=0.0;
			 for(FiTransitcost fiTransitcost2:list){
				 if(fiTransitcost2.getPayStatus()==1l){
					 throw new ServiceException("������ת�ɱ���֧��������");
				 }
				 costDouble=DoubleUtil.add(costDouble, fiTransitcost2.getAmount());   //������
				 
				FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
				if("��������".equals(fiTransitcost2.getSourceData())){
					fiInterfaceProDto.setSourceData(fiTransitcost2.getSourceData());
				}else{
					fiInterfaceProDto.setSourceData("��ת�ɱ�");
				}
				fiInterfaceProDto.setCustomerId(fiTransitcost.getCustomerId());
				fiInterfaceProDto.setSourceNo(fiTransitcost2.getId());
				listPro.add(fiInterfaceProDto);     //���ò���ӿڣ�����Ӧ��Ӧ������
				 
				 delete(fiTransitcost2.getId());  //ɾ����ת�ɱ�����
			 }
			 
			 List<OprStatus> listu =oprStatusDao.find(" from OprStatus o where o.dno=?  ",fiTransitcost.getDno());
			 if(listu.size()==1){
					listu.get(0).setFeeAuditStatus(0l);
					listu.get(0).setFeeAuditTime(new Date());
					oprStatusDao.save(listu.get(0));  //״̬�����ת�ɱ����״̬����
			 }else{
				 throw new ServiceException("ȡ�����ݵ�״̬��¼����");
			 }
			 
			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("��ת�ɱ�");
			fiCostNew.setCostTypeDetail("����¼��");
			fiCostNew.setCostAmount(-costDouble);
			fiCostNew.setDataSource("��ת�ɱ�");
			fiCostNew.setDno(fiTransitcost.getDno());
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //�Գ�ɱ����е�����
		
			oprHistoryService.saveLog(fiTransitcost.getDno(), "��ת�ɱ��������������"+costDouble, log_qxAuditCost);     //������־
		}
		fiInterfaceImpl.invalidToFi(listPro);  //����ӿ�
	}

	/* 
	 * ��ȡ���κ�
	 */
	public Long getBatchNo() throws Exception {
		String carString ;
		Map  times = (Map)fiTransicostDao.createSQLQuery("  select SEQ_FI_TRANSI_BATCH_NO.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		return s;
	}

	/* 
	 * ��ת�ɱ������ѯSqlƴ��
	 */
	@ModuleName(value="��ת�ɱ���ѯSQLƴ��",logType=LogType.fi)
	public String getSelectSql(Map map, Long type) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ( ");
				if(type==null){
					throw new ServiceException("��ѡ�����״̬���в�ѯ");
				}
				if(type==-1l||type==0l){  //��ѯȫ������δ��˵�����
						 sb=addSqlBuffer(sb);
						 sb.append("WHERE  (  ");
				   		     			 sb.append("t0.DISTRIBUTION_MODE  =  '��ת' ");  
							   sb.append("AND t0.D_NO = t2.D_NO  ");
							   sb.append("AND t0.D_NO = t3.D_NO(+)   ");
							   sb.append("AND t0.D_NO = t1.D_NO ");
							   sb.append("AND t0.tra_fee >0  ");
							   sb.append("AND t2.fee_audit_status = 0  ");
							   sb.append("AND t3.source_data(+) <> '�����Ǽ�'  ");  //��ѯδ��˵����ݣ��Զ��������ģ�����δ��˵ģ������������Ǽǵ�����
							   sb.append("AND t0.D_NO = t1.D_NO ");
								   sb.append(")  ");
							   
					sb.append("UNION ALL  ");
						sb=addSqlBuffer(sb);
						sb.append("WHERE  (  ");
								   		     sb.append("t0.DISTRIBUTION_MODE  =  '��ת' ");  
								   sb.append("AND t0.D_NO = t2.D_NO  ");
								   sb.append("AND t0.D_NO = t3.D_NO  ");
								   sb.append("AND t0.D_NO = t1.D_NO ");
								   sb.append("AND t3.source_data <> '����¼��'  ");   //��ѯ�����Ǽǵ�δ��˵�����
								   sb.append("AND t3.status=0  ");
						sb.append(")  ");
	
				}
				
				if(type==0l){  
					sb.append("UNION ALL  ");
				}
				if(type==1l||type==0l){  //��ѯ����˻�ȫ��������
						sb=addSqlBuffer(sb);
						sb.append("WHERE  (  ");
								   		     sb.append("t0.DISTRIBUTION_MODE  =  '��ת' ");  
								   sb.append("AND t0.D_NO = t2.D_NO  ");
								   sb.append("AND t0.D_NO = t3.D_NO  ");
								   sb.append("AND t0.D_NO = t1.D_NO ");
								   sb.append("AND t3.status=1  ");
						sb.append(")  ");
				}
		sb.append(")  where  (t0_IN_DEPART_ID =:departId  OR t3_DEPART_ID = :depart2 )  ");  //��ѯ����˵�����
		String sql=addSelectCondition(sb,map);   //���ϲ�ѯ����
		return sql;
	}
	
	//SQLƴ��
	public StringBuffer addSqlBuffer(StringBuffer sb){
		sb.append("SELECT  ");
				sb.append("t0.D_NO  AS t0_D_NO , ");
				sb.append("t0.DISTRIBUTION_MODE  AS t0_DISTRIBUTION_MODE ,   ");
			//	sb.append("t0.GOWHERE  AS t0_GOWHERE , ");
				sb.append("t0.TAKE_MODE  AS t0_TAKE_MODE , ");
				sb.append("t0.ADDR  AS  t0_ADDR ,  ");
				sb.append("t0.PIECE  AS t0_PIECE, ");
				sb.append("t0.CQ_WEIGHT  AS t0_CQ_WEIGHT, ");
				 sb.append(" case when t3.amount is null then t0.TRA_FEE  else t3.amount end as t0_TRA_FEE ,"); 
				sb.append("t0.AREA_RANK  AS t0_AREA_RANK, ");
				sb.append("t0.CREATE_TIME  AS t0_CREATE_TIME , ");
			//	sb.append("t0.GOWHERE_ID  AS t0_GOWHERE_ID ,   ");
				
				sb.append(" case when t3.source_data  is null then  t0.GOWHERE_ID else t3.customer_id end as t0_GOWHERE_ID ,"); 
				sb.append(" case when t3.source_data  is null then  t0.GOWHERE else t3.customer_name  end as t0_GOWHERE , "); 
				
				sb.append("t0.DISTRIBUTION_DEPART  AS t0_IN_DEPART ,   ");
				sb.append("t0.DISTRIBUTION_DEPART_ID  AS t0_IN_DEPART_ID , ");
				sb.append("t0.CP_NAME  AS t0_CP_NAME ,   ");
				sb.append("t0.FLIGHT_MAIN_NO  AS t0_FLIGHT_MAIN_NO , ");
				sb.append("t0.SUB_NO  AS t0_SUB_NO ,   ");
				sb.append("t0.CONSIGNEE  AS t0_CONSIGNEE , "); 
				sb.append("t0.CP_FEE  AS t0_CP_FEE , ");
				sb.append("t0.CONSIGNEE_FEE AS t0_CONSIGNEE_FEE, ");
				sb.append(" t0.CP_FEE+t0.CONSIGNEE_FEE totalFee, ");
				sb.append("t0.CP_FEE+t0.CONSIGNEE_FEE-t0.TRA_FEE deficitFee, ");
				sb.append("t1.CONFIRM_STATUS AS t1_CONFIRM_STATUS , ");
				sb.append("t2.FEE_AUDIT_STATUS  AS t2_FEE_AUDIT_STATUS , "); 
				sb.append("decode(t2.CASH_STATUS,null,0,t2.CASH_STATUS)  AS t2_CASH_STATUS, ");
				sb.append("t3.id t3_ID, ");
				sb.append("t3.STATUS  AS t3_STATUS,  ");
				sb.append("t3.REVIEW_USER AS t3_REVIEW_USER, ");
				sb.append("t3.REVIEW_DATE  AS t3_REVIEW_DATE, ");
				sb.append("t3.REVIEW_REMARK  AS t3_REVIEW_REMARK, ");
				sb.append("decode(t3.PAY_STATUS,null,0,t3.PAY_STATUS)  AS t3_PAY_STATUS, ");
				sb.append("t3.TS AS t3_TS, ");
				sb.append("t3.DEPART_ID  AS t3_DEPART_ID , "); 
				sb.append("t3.DEPART_NAME  AS t3_DEPART_NAME , ");   
				sb.append("t3.BATCH_NO batchNo, ");
				sb.append("0 t3_amount, ");
		        sb.append("t3.source_data  AS t3_source_data , ");
				sb.append("t3.CREATE_TIME t3_CREATE_TIME , ");
				sb.append("t3.source_no t3_source_no, ");
				sb.append("t3.customer_name  t3_customer_name, ");
				sb.append("t3.customer_id  t3_customer_id, ");
				sb.append("t0.cus_Depart_Code  t0_cus_Depart_Code, ");
				sb.append("t0.cus_Depart_Name  t0_cus_Depart_Name ");
		sb.append("FROM  OPR_FAX_IN t0, ");
			   sb.append("OPR_RECEIPT t1, ");
			   sb.append("OPR_STATUS t2, ");
			   sb.append("FI_TRANSITCOST t3 ");    
	   return sb;
	}
	
	//���ϲ�ѯ�����ķ���
	public  String  addSelectCondition(StringBuffer sb,Map map ) throws Exception{
		String itemsValue = (String)map.get("itemsValue");
		Date startDate = (Date)map.get("startDate");
		Date endDate = (Date)map.get("endDate");
		Long confirmStatus =(Long) map.get("confirmStatus"); 
 		String checkItems = (String) map.get("checkItems");
 		String serviceDepartCode=(String )map.get("serviceDepartCode");
 		String dateType=(String)map.get("dateType");
		
 		if("��������".equals(dateType)){
			if(startDate!=null){
				sb.append("AND( t0_CREATE_TIME >= to_date( :startDate ,'yyyy-mm-dd hh24:mi:ss') OR   t3_CREATE_TIME >= to_date( :startDate2 ,'yyyy-mm-dd hh24:mi:ss') ) ");
			}
			if(endDate!=null){
				sb.append("AND( t0_CREATE_TIME <= to_date( :endDate ,'yyyy-mm-dd hh24:mi:ss')  OR   t3_CREATE_TIME <= to_date( :endDate2 ,'yyyy-mm-dd hh24:mi:ss')  ) ");
			}
 		}else{
 			if(startDate!=null){
				sb.append("AND( t3_REVIEW_DATE >= to_date( :startDate ,'yyyy-mm-dd hh24:mi:ss') OR   t3_REVIEW_DATE >= to_date( :startDate2 ,'yyyy-mm-dd hh24:mi:ss') ) ");
			}
			if(endDate!=null){
				sb.append("AND( t3_REVIEW_DATE <= to_date( :endDate ,'yyyy-mm-dd hh24:mi:ss')  OR   t3_REVIEW_DATE <= to_date( :endDate2 ,'yyyy-mm-dd hh24:mi:ss')  ) ");
			}
 			
 		}
		if(confirmStatus!=null){
			sb.append("AND t1_CONFIRM_STATUS = :confirmStatus ");
		}
		
		if(serviceDepartCode!=null){
			sb.append("AND t0_cus_Depart_Code  like :serviceDepartCode ");
		}
		if(itemsValue!=null&&checkItems!=null&&!"".equals(itemsValue)){
			if("cashStatus".equals(checkItems)){
				sb.append("AND t2_CASH_STATUS = :itemsValue ");
			}else if("dno".equals(checkItems)){
				sb.append("AND t0_D_NO = :itemsValue ");
			}else if("batchNo".equals(checkItems)){
				sb.append("AND batchNo = :itemsValue ");
			}else if("goWhere".equals(checkItems)){
				sb.append("AND t0_GOWHERE_ID = :itemsValue ");
			}else if("payStatus".equals(checkItems)){
				sb.append("AND t3_PAY_STATUS = :itemsValue ");
			}
			
		}
		return sb.toString();
	}
	
	//���������Ϣ��ʾ
	@ModuleName(value="��ת�ɱ��������ʱ��Ϣ��ʾ",logType=LogType.fi)
	public String qxAmountCheck(Long id) throws Exception {
		FiTransitcost fiTransitcost =get(id);
		String hql="from FiTransitcost fo where fo.dno=?  and fo.status=1 and fo.departId=? and ( fo.sourceData=? or fo.sourceData=? ) and fo.customerId=? ";
		List<FiTransitcost>list =find(hql,fiTransitcost.getDno(),fiTransitcost.getDepartId(),"����¼��","��������",fiTransitcost.getCustomerId());  
		double totaAmount=0.0;
		for (FiTransitcost fiTransitcost2 : list) {
			totaAmount=DoubleUtil.add(fiTransitcost2.getAmount(), totaAmount);
		}
		StringBuffer sb=new StringBuffer();
		sb.append("������ת�ɱ��ܼ�<span  style='color:red'> ").append(list.size()).append(" </span>��,")
			.append("���<span  style='color:red'> ").append(totaAmount).append(" </span>Ԫ");
		return sb.toString();
	}
	
	public void payConfirmationBybatchNo(Long batchNo) throws Exception{
		//FiTransitcost fiTransicost=this.fiTransicostDao.get(id);
		List<FiTransitcost> list=this.fiTransicostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("��ת�ɱ��и������κ�:"+batchNo+"δ�ҵ����ݣ�");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiTransitcost fiTransicost:list){
			if(fiTransicost==null) throw new ServiceException("������ת�ɱ�ʧ�ܣ�");
			fiTransicost.setPayStatus(1L);
			fiTransicost.setPayTime(new Date());
			fiTransicost.setPayUser(user.get("name").toString());
			this.fiTransicostDao.save(fiTransicost);
			
			//�ɱ�֧��״̬
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiTransicost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(1L);
				oprStatus.setPayTraTime(new Date());
				this.oprStatusService.save(oprStatus);
			}
		}
	}
	
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception{
		List<FiTransitcost> list=this.fiTransicostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("��ת�ɱ��и������κ�:"+batchNo+"δ�ҵ����ݣ�");
		for(FiTransitcost fiTransicost:list){
			if(fiTransicost==null) throw new ServiceException("������ת�ɱ�ʧ�ܣ�");
			fiTransicost.setPayStatus(0L);
			fiTransicost.setPayTime(null);
			fiTransicost.setPayUser(null);
			this.fiTransicostDao.save(fiTransicost);
			
			//�����ɱ�֧��״̬
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiTransicost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(0L);
				oprStatus.setPayTraTime(null);
				this.oprStatusService.save(oprStatus);
			}
		}
	}
}
