package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
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
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.Service.IFiCostService;
import com.xbwl.finance.Service.IFiOutCostService;
import com.xbwl.finance.dao.IFiOutCostDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 *@author LiuHao
 *@time Aug 29, 2011 5:50:26 PM
 */
@Service("fiOutCostServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiOutCostServiceImpl extends BaseServiceImpl<FiOutcost,Long> implements
		IFiOutCostService {
	
	@Resource(name="fiOutCostHibernateDaoImpl")
	private IFiOutCostDao fiOutCostDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Value("${fiAuditCost.log_qxAuditCost}")
	private Long log_qxAuditCost ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name = "fiCostServiceImpl")
	private IFiCostService fiCostService;
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface fiInterfaceImpl;
	
	//����״̬��
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiOutCostDao;
	}
	
	//�ⷢ�ɱ����
	@ModuleName(value="�ⷢ�ɱ����",logType=LogType.fi)
	public String outCostAduit(List<FiOutcost>aa ,Long batchNo) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		
		String hql="from FiOutcost fo where fo.batchNo=? and fo.isdelete=1 and fo.departId=? and fo.customerId=?  ";
		List<FiOutcost>listFoct =find(hql,batchNo,bussDepartId,aa.get(0).getCustomerId());
		Map<Long,Double>map = new HashMap<Long, Double>();
		
		for(FiOutcost fiOct:listFoct){
			if(fiOct.getStatus()==1l){
				throw new ServiceException("��������˵����ݣ������ύ���");         //״̬�ж�
			}
			if(fiOct.getIsdelete()==0l){
				throw new  ServiceException("�������ϵ����ݲ������");  // ����ɾ���ж�
			}
			
		/*	if(!bussDepartId.equals(fiOct.getDepartId())){   //����������Ǳ����ŵ�����
				throw new ServiceException("��������Ǳ�ҵ���ŵ�����");
			}*/
			
			if(map.get(fiOct.getDno())==null){  //���д��Map���ٻ�д������״̬��
				map.put(fiOct.getDno(), fiOct.getAmount());
			}else{
				Double sDouble =map.get(fiOct.getDno());
				map.put(fiOct.getDno(), DoubleUtil.add(sDouble,fiOct.getAmount()));
			}
			
			//���״̬��д��ʱ����ж�
			fiOct.setStatus(1l);
			fiOct.setReviewUser(user.get("name").toString());
			fiOct.setReviewDate(new Date());
			fiOutCostDao.save(fiOct);
			
			oprHistoryService.saveLog(fiOct.getDno(), "֧���ⷢ�ɱ���֧����"+fiOct.getAmount() , log_auditCost);     //������־
			FiCost ficost=new FiCost();
			ficost.setDno(fiOct.getDno());
			ficost.setCostType("�ⷢ�ɱ�");
			ficost.setSourceSignNo(fiOct.getId()+"");
			ficost.setDataSource("�ⷢ�ɱ�");
			ficost.setCostAmount(fiOct.getAmount());
			ficost.setCostTypeDetail(fiOct.getSourceData());
			fiCostService.save(ficost);
			

		}
		
	

		String s =changeOprStatus(map,listFoct.get(0),bussDepartId);   //����״̬
		return s;
	}

	/* 
	 * �����ɱ����
	 */
	@ModuleName(value="�ⷢ�����ɱ����",logType=LogType.fi)
	public String qxFiAduit(Long id, String ts) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");

		FiOutcost fiOutCost =fiOutCostDao.getAndInitEntity(id);
	/*	if(!bussDepartId.equals(fiOutCost.getDepartId())){
			throw new ServiceException("��������Ǳ�ҵ���ŵ�����");
		}*/
		
		//ʱ����ж�
		fiOutCost.setTs(ts);
		
		List<OprStatus> listo =oprStatusDao.findBy("dno", fiOutCost.getDno()); //ȡ����״̬  �����ﷵ��һ�Σ����û�д�ɱ����״̬
		OprStatus oprStatus=null;    
		if(listo.size()==1){
			oprStatus=listo.get(0);
		}else{
			throw new ServiceException("ȡ����״̬ʱ����");
		}
		
		String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.status=1 and fo.departId=? and fo.customerId=? ";
		List<FiOutcost>list =find(hql,fiOutCost.getDno(),fiOutCost.getDepartId(),fiOutCost.getCustomerId());  
		double totaAmount=0.0;    //�ܼ���Ҫ�������ⷢ�ɱ�
		for (FiOutcost fiOutcost2 : list) {
			if(fiOutcost2.getPayStatus()==1l){
				throw new ServiceException("������֧��,���ܳ���������");
			}
			
			fiOutcost2.setStatus(0l);
			if(fiOutCost.getId()-fiOutcost2.getId()==0){
				fiOutcost2.setTs(ts);
			}
			fiOutcost2.setReviewDate(null);
			fiOutcost2.setReviewUser(null);
			fiOutCostDao.save(fiOutcost2);
			totaAmount=DoubleUtil.add(fiOutcost2.getAmount(), totaAmount);
			
			if("����¼��".equals(fiOutcost2.getSourceData())){
				oprStatus.setFeeAuditStatus(0l);
				oprStatus.setFeeAuditTime(new Date());
				oprStatusDao.save(oprStatus);
			}
		}
		
		FiCost fiCostNew = new FiCost();  // �Գ�ɱ����
		fiCostNew.setCostType("�ⷢ�ɱ�");
		fiCostNew.setCostTypeDetail("�������");
		fiCostNew.setCostAmount(- totaAmount);
		fiCostNew.setDataSource("�ⷢ�ɱ�");
		fiCostNew.setDno(list.get(0).getDno());
		fiCostNew.setStatus(1l);
		fiCostService.save(fiCostNew);  //����
		
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();   // ���ò���ӿ� ȡ��Ӧ��Ӧ��
		FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
		fiInterfaceProDto.setCostType("�ⷢ��");
		fiInterfaceProDto.setSourceData("�ⷢ�ɱ�");
		fiInterfaceProDto.setDno(fiOutCost.getDno());
		fiInterfaceProDto.setDocumentsNo(fiOutCost.getDno());
		fiInterfaceProDto.setSourceNo(fiOutCost.getId());
		fiInterfaceProDto.setCustomerId(fiOutCost.getCustomerId()	);
		listPro.add(fiInterfaceProDto);
		fiInterfaceImpl.invalidToFi(listPro);
		
		oprHistoryService.saveLog(fiOutCost.getDno(), "�ⷢ�ɱ���˳�����������"+totaAmount , log_qxAuditCost);     //������־
		return null;
	}
	
	/*
	 * �����ⷢ�ɱ�
	 */
	@ModuleName(value="�����ⷢ�ɱ�",logType=LogType.fi)
	public void payConfirmationBybatchNo(Long batchNo) throws Exception{
		//FiOutcost fiOutcost=this.fiOutCostDao.get(id);
		List<FiOutcost> list=this.fiOutCostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("�ⷢ�ɱ��и������κ�:"+batchNo+"δ�ҵ����ݣ�");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiOutcost fiOutcost:list){
			fiOutcost.setPayStatus(1L);
			fiOutcost.setPayTime(new Date());
			fiOutcost.setPayUser(user.get("name").toString());
			this.fiOutCostDao.save(fiOutcost);
			
			//�ɱ�֧��״̬
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiOutcost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(1L);
				oprStatus.setPayTraTime(new Date());
				this.oprStatusService.save(oprStatus);
			}
		}
		
	}
	
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception{
		List<FiOutcost> list=this.fiOutCostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("�ⷢ�ɱ��и������κ�:"+batchNo+"δ�ҵ����ݣ�");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiOutcost fiOutcost:list){
			fiOutcost.setPayStatus(0L);
			fiOutcost.setPayTime(null);
			fiOutcost.setPayUser(null);
			this.fiOutCostDao.save(fiOutcost);
			
			//�����ɱ�֧��״̬
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiOutcost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(0L);
				oprStatus.setPayTraTime(null);
				this.oprStatusService.save(oprStatus);
			}
		}
	}

	//�������ʱ���ⷢ���״̬����
	@ModuleName(value="����ʱ�������ɱ����״̬����",logType=LogType.fi)
	public String returnGoodsPrompt(Long dno, Long departId) throws Exception {
		StringBuffer sb= new StringBuffer();
		sb.append("���͵���Ϊ").append(dno).append("�Ļ���ⷢ�ɱ�");
		OprFaxIn oprFaxIn  = oprFaxInDao.get(dno);
		List<FiOutcost>list =find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.customerId=? and fo.amount>0 ",dno,"����¼��",departId,oprFaxIn.getGoWhereId());
		if(list.size()>0){
			sb.append("��¼��");
			if(1l==list.get(0).getStatus()){
				sb.append("�����");
				if(list.get(0).getPayStatus()==1l){
					sb.append("��֧��");
				}else{
					sb.append("δ֧��");
				}
			}else{
				sb.append("δ���");
			}
			sb.append("�ⷢ�ɱ�Ϊ").append(list.get(0).getAmount()).append("Ԫ");
		}else{
			sb.append("δ¼��");
		}
		sb.append("����ȷ�ϡ�");
		return sb.toString();
	}

	//�ⷢ�ɱ�����
	@ModuleName(value="�ⷢ�ɱ�����",logType=LogType.fi)
	public String delOutcostData(Long id, String ts) throws Exception {
		FiOutcost fiOutcost=get(id);
		if(!"����¼��".equals(fiOutcost.getSourceData())){
			throw new ServiceException("������Դ�Ǵ���¼����ⷢ�ɱ���������ɾ��");
		}
		fiOutcost.setTs(ts);
		fiOutcost.setIsdelete(0l);
		save(fiOutcost);
		return null;
	}

	//��дsave������������������
	public void save(FiOutcost entity) {
		if(entity.getId()==null){
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
			
			OprFaxIn oprFaxIn=oprFaxInDao.get(entity.getDno());  //ȡ����Ĵ����¼
			if(oprFaxIn==null){
				throw new ServiceException("�����͵��ŵĻ��ﲻ���ڣ�");
			}
			
			List<OprStatus> listo =oprStatusDao.findBy("dno", entity.getDno()); //ȡ����״̬
			OprStatus oprStatus=null;    
			if(listo.size()==1){
				oprStatus=listo.get(0);
			}else{
				throw new ServiceException("ȡ����״̬ʱ����");
			}
			
			if(entity.getAmount()>0){
				if(oprStatus.getOutStatus()-1l!=0&&oprStatus.getOutStatus()-2l!=0){
					throw new ServiceException("����δ���⣬������¼���ⷢ�ɱ�");
				}
	
				String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.customerId=? and fo.amount>0 ";
				if(oprStatus.getReturnStatus()==0l){   // �������ѷ���ʱ������Լ��;δ����ֻ��¼һ��
					List<FiOutcost>listFo =find(hql,entity.getDno(),"����¼��",bussDepartId,entity.getCustomerId());  //�ж��Ƿ�¼���ⷢ�ɱ�  ���û��¼�� ������¼�븺��
					if(listFo.size()>0){
						throw new ServiceException("���ܶ��¼���ⷢ�ɱ�");
					}
				}
			}else if(entity.getAmount()<0){
				String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.amount>0 ";
				List<FiOutcost>list =find(hql,entity.getDno(),"����¼��",bussDepartId);  //�ж��Ƿ�¼���ⷢ�ɱ�  ���û��¼�� ������¼�븺��
				if(list.size()>0){
					if(oprFaxIn.getGoWhereId()!=null){
						if(oprFaxIn.getGoWhereId()-entity.getCustomerId()!=0&&entity.getCustomerId()-list.get(0).getCustomerId()!=0){  
							throw new ServiceException("����¼�������ⷢ���̵ĳɱ�");
						}
					}
	//				if(oprFaxIn.getGoWhereId()-entity.getCustomerId()==0){    //�ж��Ƿ�ڶ��γ���
	///*					boolean flag=true;
	//					for(FiOutcost fiOutcost:list) {
	//						if(fiOutcost.getCustomerId()-entity.getCustomerId()==0){
	//							flag=true;
	//							break;
	//						}
	//					}
	//					if(flag){
	//						throw new ServiceException("����û��¼���ⷢ�ɱ���������¼�븺�ɱ�");
	//					}*/
	//				}
				}else{
					throw new ServiceException("����û��¼���ⷢ�ɱ���������¼�븺�ɱ�");
				}
			}else{
				throw new ServiceException("¼����ⷢ�ɱ�Ϊ�㣬û������");
			}
		}
		fiOutCostDao.save(entity);
	}

	//���������Ϣ��ʾ
	@ModuleName(value="�ⷢ�ɱ����������Ϣ��ʾ",logType=LogType.fi)
	public String qxAmountCheck(Long id) throws Exception {
		FiOutcost fiOutcost =fiOutCostDao.getAndInitEntity(id);
		String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.status=1 and fo.departId=? and fo.customerId=? ";
		List<FiOutcost>list =find(hql,fiOutcost.getDno(),fiOutcost.getDepartId(),fiOutcost.getCustomerId());  
		double totaAmount=0.0;
		for (FiOutcost fiOutcost2 : list) {
			totaAmount=DoubleUtil.add(fiOutcost2.getAmount(), totaAmount);
		}
		StringBuffer sb=new StringBuffer();
		sb.append("�����ⷢ�ɱ��ܼ�<span  style='color:red'> ").append(list.size()).append(" </span>��,")
			.append("���<span  style='color:red'> ").append(totaAmount).append(" </span>Ԫ");
		return sb.toString();
	}

	/* 
	 * ���ʱ��Ϣ��ʾ
	 * @see com.xbwl.finance.Service.IFiOutCostService#aduitAmountCheck(java.util.List)
	 */
	 @ModuleName(value="�ⷢ�ɱ������Ϣ��ʾ",logType=LogType.fi)
	public Map<String,String> aduitAmountCheck(List<FiOutcost> aa) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		StringBuffer sb=new StringBuffer(); 
		Long batchNo=getOutcostBatchNo();
		Map<String, String>map=new HashMap<String, String>();
		sb.append("����ⷢ�ɱ��ܼ�:");
		for(FiOutcost fiOct:aa){
			int num=0;
			double totaAmount=0.0;
			String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.status=0 and fo.departId=? ";
			List<FiOutcost>list =find(hql,fiOct.getDno(),bussDepartId);  
			for(FiOutcost fiOutcost:list){
				if(fiOutcost.getBatchNo()!=null&&fiOutcost.getBatchNo()-batchNo==0l){  //������κ���ȣ�˵��ǰһ�������Ѽ�������˵Ľ���������ѭ��
					continue;
				}else{
					fiOutcost.setBatchNo(batchNo); //д�����κ�
					save(fiOutcost);
					num=num+1;  //������
					totaAmount=DoubleUtil.add(totaAmount, fiOutcost.getAmount());  // �ܽ����
				}
			}
			if(num!=0){
				sb.append("���͵��� ").append(fiOct.getDno()).append(" �ϼ�<span  style='color:red'> ").append(list.size()).append(" </span>��,")
					 .append("���<span  style='color:red'> ").append(totaAmount).append(" </span>Ԫ��</b>");
			}
		}
		map.put("auditInfo", sb.toString());
		map.put("batchNo", batchNo+"");
		return map;
	}

	// ���ʱ��д״̬��  ���ò���ӿ�
	public String changeOprStatus(Map<Long, Double>map,FiOutcost fiOutcost,Long bussDepartId) throws Exception{
		List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>();
		String msg=null;
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			Long dnoLong = (Long) entry.getKey();
			Double value = (Double)entry.getValue();
			//Ӧ��Ӧ������ӿ�
			FiInterfaceProDto fipd=new FiInterfaceProDto();
			fipd.setAmount(value==null?0.0:value);
			fipd.setCustomerId(fiOutcost.getCustomerId());
			fipd.setCustomerName(fiOutcost.getCustomerName());
			fipd.setDno(dnoLong);
			fipd.setDocumentsType("�ɱ�");
			fipd.setDocumentsSmalltype("���͵�");
			fipd.setDistributionMode("����");
			fipd.setSettlementType(2l);
			fipd.setDocumentsNo(dnoLong);
			fipd.setCostType("�ⷢ��");
			fipd.setDepartId(bussDepartId);
			fipd.setSourceData("�ⷢ�ɱ�");
			fipd.setSourceNo(fiOutcost.getBatchNo());
			fipd.setCreateRemark("֧���ⷢ��˾"+fiOutcost.getCustomerName()+"�ⷢ��"+value+"Ԫ");
			list.add(fipd);
			
			//״̬��д
			List<OprStatus> listo =oprStatusDao.findBy("dno", dnoLong);
			if(listo.size()==0){
				throw new ServiceException("��ǰ���ݵ����͵�״̬�����ڣ��������");
			} 

			StringBuffer sb = new StringBuffer();  //  ��ȡ�ܵ���˷��ã���д�����
			sb.append("select sum(t.amount) amount from fi_outcost t where t.status=1 and t.depart_id=? and t.isdelete=1 and t.d_no=? " );
			Query query = createSQLQuery(sb.toString(), bussDepartId,dnoLong);
			if(query!=null){
				OprFaxIn ofi=oprFaxInDao.get(dnoLong);
				Map mapCost=(Map) query.list().get(0);
				double amount=Double.valueOf(mapCost.get("AMOUNT")+"");
				ofi.setTraFee(amount);   //��д��ת��/�ⷢ��
			//	oprFaxInDao.save(ofi);
			}
			
			 listo.get(0).setFeeAuditStatus(1l);   // ��д״̬�ĳɱ����״̬
			 listo.get(0).setFeeAuditTime(new Date());
			 oprStatusDao.save( listo.get(0));
		}
		msg =fiInterface.addFinanceInfo(list);    //���ò���ӿ�
		return msg;
	}
	
	
	
	/* 
	 * ��ȡ���κ�
	 * @see com.xbwl.finance.Service.IFiOutCostService#getOutcostBatchNo()
	 */
	public Long getOutcostBatchNo() throws Exception {
		Map  batch = (Map)createSQLQuery("  select SEQ_FI_OUTCOST_BATCHNO.nextval batchNo from  dual  ").list().get(0);
		Long batchNo =Long.valueOf(batch.get("BATCHNO")+"");
		return batchNo;
	}
	
	
}
