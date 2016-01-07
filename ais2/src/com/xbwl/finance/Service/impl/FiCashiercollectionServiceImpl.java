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
	
	// �ʽ��˺�Dao
	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// �ʽ��˺���ˮDao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;

	//ʵ��ʵ��Service
	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	
	//�ʽ𽻽ӵ�Service
	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	//POS���뱣��
	@Resource(name = "fiExcelPosServiceImpl")
	private IFiExcelPosService fiExcelPosService;
	
	@Resource
	private IBasDictionaryDetailDao idictionaryDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiCashiercollectionDao;
	}

	@ModuleName(value="�����տ����",logType=LogType.fi)
	public void saveCashiercollection(FiCashiercollection fiCashiercollection)
			throws Exception {
		
		Double collectionAmount = null;// ǰ̨ת�Ľ��
		Double amount = null;// ���ݿ��ѱ�����
		Long capitaaccountsetId = null;// ���ݿ��ѱ��������˺�id
		Double openingBalance = null;// �˺����
		Double openingBalance1 =null;

		if (fiCashiercollection == null) {
			throw new ServiceException("����ʧ�ܣ������տʵ��Ϊ�գ�");
		}
		collectionAmount = fiCashiercollection.getCollectionAmount();// ���ν�����
		if (fiCashiercollection.getId()!=null) {// �޸��տ����ȡԭ������
			FiCashiercollection fc = this.fiCashiercollectionDao.get(fiCashiercollection.getId());
			amount = fc.getCollectionAmount();//100
			capitaaccountsetId = fc.getFiCapitaaccountsetId();//���ݿⱣ����˺�ID
			FiCapitaaccountset fiCapitaaccountset1 = this.fiCapitaaccountsetDao.get(fc.getFiCapitaaccountsetId());//ԭ
			
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCashiercollection.getFiCapitaaccountsetId());//��
			openingBalance = fiCapitaaccountset.getOpeningBalance();// ǰ̨¼��ʱ��Ӧ�˺����200	
			
			if (capitaaccountsetId.equals(fiCashiercollection.getFiCapitaaccountsetId())) {// δ�޸������˺�
				// �����˺���ˮ���
				FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
				if (collectionAmount > amount) {
					collectionAmount = collectionAmount - amount;// ���ν�����-�ѱ�����
					fiCapitaaccount.setLoan(collectionAmount);// �跽���
					openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// �˺����+�����տ���
				} else if (collectionAmount < amount) {
					collectionAmount = DoubleUtil.sub(amount , collectionAmount);// ���ݿ��ѱ�����-���ν�����DoubleUtil.add(v1,
																	// v2)
					fiCapitaaccount.setLoan(collectionAmount);// �������
					openingBalance = DoubleUtil.sub(openingBalance , collectionAmount);
					
				}
				fiCapitaaccount.setSourceData("�����տ");
				fiCapitaaccount.setFiCapitaaccountsetId(fiCashiercollection.getFiCapitaaccountsetId());
				fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
				fiCapitaaccount.setBalance(openingBalance);
				fiCapitaaccount.setRemark("�޸ĳ����տ���");
				if(openingBalance<0.0){
					throw new ServiceException("�޸�ʧ�ܣ��˺����㣡");
				}
				this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��
				fiCapitaaccountset.setOpeningBalance(openingBalance);// ��������
				this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
			} else {
				// ԭ�˺Ŵ���
				openingBalance1=fiCapitaaccountset1.getOpeningBalance();
				fiCapitaaccountset1.setOpeningBalance(DoubleUtil.sub(openingBalance1 ,fc.getCollectionAmount()));
				this.fiCapitaaccountsetDao.save(fiCapitaaccountset1);

				FiCapitaaccount fct = new FiCapitaaccount();
				fct.setBorrow(fc.getCollectionAmount());
				fct.setSourceData("�����տ");
				fct.setFiCapitaaccountsetId(fc.getFiCapitaaccountsetId());
				fct.setSourceNo(fiCashiercollection.getId());
				fct.setBalance(DoubleUtil.sub(openingBalance1 , fc.getCollectionAmount()));// �˺����-�����տ���
				fct.setRemark("���������տ��˺�");
				if(fct.getBalance()<0.0){
					throw new ServiceException("�޸�ʧ�ܣ��˺����㣡");
				}
				this.fiCapitaaccountDao.save(fct);

				// ���˺Ŵ���
				FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
				fiCapitaaccount.setLoan(collectionAmount);// �跽���
				fiCapitaaccount.setFiCapitaaccountsetId(fiCashiercollection.getFiCapitaaccountsetId());
				fiCapitaaccount.setBalance(DoubleUtil.add(openingBalance, fiCashiercollection.getCollectionAmount()));// �˺����+�����տ���
				fiCapitaaccount.setSourceData("�����տ");
				fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
				fiCapitaaccount.setRemark("���������տ��˺�");
				
				this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��

				fiCapitaaccountset.setOpeningBalance(DoubleUtil.add(openingBalance, fiCashiercollection.getCollectionAmount()));// ��������
				this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
			}
			Session session=this.fiCashiercollectionDao.getSession().getSessionFactory().getCurrentSession();
			session.merge(fiCashiercollection);// ��������տ
		} else {// �����տ
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCashiercollection.getFiCapitaaccountsetId());
			openingBalance = fiCapitaaccountset.getOpeningBalance();// �˺����	
			openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// �˺����+�����տ���
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setLoan(collectionAmount);// �跽���
			fiCapitaaccount.setFiCapitaaccountsetId(fiCashiercollection.getFiCapitaaccountsetId());
			fiCapitaaccount.setSourceData("�����տ");
			fiCapitaaccount.setRemark("���������տ");
			fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
			fiCapitaaccount.setBalance(openingBalance);// �˺����+�����տ���
			this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��
			
			fiCapitaaccountset.setOpeningBalance(openingBalance);// ��������
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
			this.fiCashiercollectionDao.save(fiCashiercollection);
		}

	}

	@ModuleName(value="�����տ����",logType=LogType.fi)
	public void saveVerification(Map map,User user) throws Exception{
		String verificationType=String.valueOf(map.get("verificationType"));//��������
		if("ί���տ".equals(verificationType)){
			cashiercollectionVerification(map,user);
		}else if("�ʽ��Ͻ���".equals(verificationType)){
			fundstransferVerification(map,user);
		}else if("֧Ʊ".equals(verificationType)){
			this.checkVerification(map, user);
		}else if("POS".equals(verificationType)){
			this.cashiercollectionVerification(map, user);
		}else{
			throw new ServiceException("�������Ͳ�����");
		}

		
	}
	
	//֧Ʊ����
	private void checkVerification(Map map,User user) throws Exception{
		Long fiCashiercollectionId=Long.valueOf(String.valueOf(map.get("fiCashiercollectionId")));//�����տID
		Long ids=Long.valueOf(String.valueOf(map.get("ids")));//�ʽ𽻽ӵ�ID
		FiCashiercollection fct=this.fiCashiercollectionDao.get(fiCashiercollectionId);//�����տ
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		Double thisVerificationAmount=0.0;//���κ������
		Double cashVerificationAmount=0.0;//�����տδ�������
		Double paidVerificationAmount=0.0;//ʵ��ʵ����δ�������
		
		if(fct==null){
			throw new ServiceException("����ʧ�ܣ������տID�����ڣ�");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("�����տ�����");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("�����տ������");
		}
		cashVerificationAmount=DoubleUtil.sub(fct.getCollectionAmount(),fct.getVerificationAmount());//�տ���-�Ѻ������
		if(cashVerificationAmount<=0){
			throw new ServiceException("�����տδ�˽������������");
		}

		//this.fiPaidService.verificationById(ids,user);//����ʵ��ʵ������
		FiPaid fiPaid=this.fiPaidService.get(ids);
		if(fiPaid==null){
			throw new ServiceException("����ʧ�ܣ�ʵ��ʵ���������ڣ�");
		}
		if(fiPaid.getVerificationStatus()==1L)throw new ServiceException("����ʧ�ܣ�ʵ�յ��Ѻ���");
		paidVerificationAmount=DoubleUtil.sub(fiPaid.getSettlementAmount(), fiPaid.getVerificationAmount());//ʵ�ո����-�Ѻ������
		if(paidVerificationAmount<=0){
			throw new ServiceException("ʵ��ʵ����δ�˽������������");
		}
		
		if(cashVerificationAmount>=paidVerificationAmount){
			thisVerificationAmount=cashVerificationAmount;
		}else{
			thisVerificationAmount=paidVerificationAmount;
		}
		
		//����ʵ��ʵ������
		fiPaid.setVerificationAmount(DoubleUtil.add(fiPaid.getSettlementAmount(),thisVerificationAmount));
		fiPaid.setVerificationUser(String.valueOf(user.get("name")));
		fiPaid.setVerificationDept(String.valueOf(user.get("departName")));
		fiPaid.setVerificationTime(new Date());
		fiPaid.setVerificationStatus(1L);
		
		//��������տ����
		fct.setVerificationAmount(DoubleUtil.add(fct.getVerificationAmount(), thisVerificationAmount));
		fct.setEntrustRemark("�����Զ�ί��");
		fct.setEntrustTime(new Date());
		fct.setEntrustUser(verificationUser);
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		fct.setVerificationAmount(fct.getCollectionAmount());
		fct.setVerificationStatus(1L);
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		this.fiCashiercollectionDao.save(fct);//��������տ����
		
	}
	
	
	//ί���տ����
	private void cashiercollectionVerification(Map map,User user) throws Exception{
		Long fiCashiercollectionId=Long.valueOf(String.valueOf(map.get("fiCashiercollectionId")));//�����տID
		Long ids=Long.valueOf(String.valueOf(map.get("ids")));//�ʽ𽻽ӵ�ID
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		Double thisVerificationAmount=0.0;//���κ������
		Double cashVerificationAmount=0.0;//�����տδ�������
		Double paidVerificationAmount=0.0;//ʵ��ʵ����δ�������
		//Double verificationAmount=0.0; //�Ѻ������+������Ҫ�������
		Double openingBalance=0.0;
		
		FiCashiercollection fct=this.fiCashiercollectionDao.get(fiCashiercollectionId);//�����տ
		
		if(fct==null){
			throw new ServiceException("����ʧ�ܣ������տID�����ڣ�");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("�����տ�Ѻ���");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("�����տ������");
		}
		cashVerificationAmount=DoubleUtil.sub(fct.getCollectionAmount(),fct.getVerificationAmount());//�տ���-�Ѻ������
		if(cashVerificationAmount<=0){
			throw new ServiceException("�����տδ�˽������������");
		}
		
		//ʵ��ʵ����
		FiPaid fiPaid=this.fiPaidService.get(ids);
		if(fiPaid==null) throw new ServiceException("ʵ�յ��Ų�����");
		if(fiPaid.getVerificationStatus()==1L)throw new ServiceException("����ʧ�ܣ�ʵ�յ��Ѻ���");
		paidVerificationAmount=DoubleUtil.sub(fiPaid.getSettlementAmount(), fiPaid.getVerificationAmount());//ʵ�ո����-�Ѻ������
		if(paidVerificationAmount<=0){
			throw new ServiceException("ʵ��ʵ����δ�˽������������");
		}
		
		if(cashVerificationAmount>=paidVerificationAmount){
			thisVerificationAmount=paidVerificationAmount;
		}else{
			thisVerificationAmount=cashVerificationAmount;
		}
		
		//��������տ����
		fct.setVerificationAmount(DoubleUtil.add(fct.getVerificationAmount(), thisVerificationAmount));
		if(fct.getCollectionAmount().equals(fct.getVerificationAmount())){
			fct.setVerificationStatus(1L);
		}
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		this.fiCashiercollectionDao.save(fct);
		
		//����ʵ��ʵ������
		fiPaid.setVerificationAmount(DoubleUtil.add(fiPaid.getVerificationAmount(),thisVerificationAmount));
		fiPaid.setVerificationUser(verificationUser);
		fiPaid.setVerificationDept(verificationDept);
		fiPaid.setVerificationTime(new Date());
		fiPaid.setFiCashiercollectionId(fct.getId());
		if(fiPaid.getSettlementAmount().equals(fiPaid.getVerificationAmount())){
			fiPaid.setVerificationStatus(1L);
			fiPaid.setFiFundstransferStatus(1L);//�Ͻ�״̬
		}else{
			fiPaid.setVerificationStatus(0L);
		}
		this.fiPaidService.save(fiPaid);
		
		//POS�տ��˺��ʽ���ˮ����
		if("POS".equals(fiPaid.getPenyJenis())){
			FiCapitaaccount fc=this.fiCapitaaccountDao.get(fiPaid.getFiCapitaaccountId());
			
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fc.getFiCapitaaccountsetId());
			if(fiCapitaaccountset==null){
				throw new ServerException("POS�˺Ų�����!");
				
			}
			openingBalance = DoubleUtil.sub(fiCapitaaccountset.getOpeningBalance() , thisVerificationAmount);// �˺����+�����տ���
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setBorrow(thisVerificationAmount);// ��s�����
			fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			fiCapitaaccount.setSourceData("�����տ");
			fiCapitaaccount.setRemark("POS����");
			fiCapitaaccount.setSourceNo(fct.getId());
			fiCapitaaccount.setBalance(openingBalance);
			this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��
			
			fiCapitaaccountset.setOpeningBalance(openingBalance);// ��������
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
		}
	}
	//�ʽ��Ͻ�������
	private void fundstransferVerification(Map map,User user) throws Exception{
		Long ids=Long.valueOf(String.valueOf(map.get("ids")));//�ʽ𽻽ӵ�ID
		Long fiCashiercollectionId=Long.valueOf(String.valueOf(map.get("fiCashiercollectionId")));//�����տID
		FiCashiercollection fct=this.fiCashiercollectionDao.get(fiCashiercollectionId);//�����տ
		FiFundstransfer fiFundstransfer=this.fiFundstransferService.get(ids);//�ʽ𽻽ӵ�
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		
		if(fct==null){
			throw new ServiceException("����ʧ�ܣ������տID�����ڣ�");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("�����տ�����");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("�����տ������");
		}
		
		//��������տ����
		fct.setEntrustAmount(fct.getCollectionAmount());
		fct.setEntrustTime(new Date());
		fct.setEntrustUser(String.valueOf(user.get("name")));
		fct.setEntrustRemark("�տ�����Զ�ȷ��");
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		fct.setVerificationAmount(fct.getCollectionAmount());
		fct.setVerificationStatus(1L);
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		this.fiCashiercollectionDao.save(fct);
		
		//�����ʽ𽻽ӵ�
		if(fiFundstransfer.getPaymentaccountId().equals(fct.getFiCapitaaccountsetId())) throw new ServiceException("����ʧ��,�����տ��˺����ֽ𽻽Ӹ����Ϊͬһ�˺ţ�");
		
		if(fiFundstransfer.getPaymentStatus()!=1L){
			throw new ServiceException("��ǰ�տ��δ����ȷ�ϣ�");
		}
		
		if(fiFundstransfer.getReceivablesStatus()==1L){
			throw new ServiceException("��ǰ�տ�״̬Ϊ��ȷ��,�������ٺ���");
		}
		
		if(fiFundstransfer.getStatus()==0L){
			throw new ServiceException("�ʽ𽻽ӵ�������!");
		}
		
		if(!fiFundstransfer.getAmount().equals(fct.getCollectionAmount())){
			throw new ServiceException("����ʧ��,�����տ���:"+fct.getCollectionAmount()+"���ʽ��Ͻ����:"+fiFundstransfer.getAmount()+"��һ�¡�");
		}
		fiFundstransfer.setStatus(2L);
		fiFundstransfer.setReceivablesStatus(1L);
		fiFundstransfer.setReceivablesTime(new Date());
		this.fiFundstransferService.save(fiFundstransfer);//�����ʽ��Ͻ�������״̬
		
		//��������ģ������
		if("ʵ��ʵ����".equals(fiFundstransfer.getSourceData())){
			this.fiPaidService.batchExecute("update FiPaid f set f.verificationAmount=f.settlementAmount,f.verificationStatus=1,f.verificationUser=?,f.verificationTime=? where f.fiFundstransferId=?", user.get("name"),new Date(),fiFundstransfer.getId());
		}
	}

	public void saveExcelData(Long batchNo) throws Exception {
		double openingBalance=0.0;
		double collectionAmount=0.0;
		List<FiCashiercollectionExcel> list=this.fiCashiercollectionExcelDao.findBy("batchNo", batchNo);
		if(list.size()==0) throw new ServiceException("δ�ҵ���������");
		for(FiCashiercollectionExcel fiCaExcel:list){
			collectionAmount=fiCaExcel.getAmount();
			
			//��������տ
			FiCashiercollection fiCashiercollection=new FiCashiercollection();
			fiCashiercollection.setFiCapitaaccountsetId(fiCaExcel.getFiCapitaaccountsetId());
			fiCashiercollection.setCollectionTime(fiCaExcel.getDoMoneyData());
			fiCashiercollection.setCollectionAmount(collectionAmount);
			fiCashiercollection.setCreateRemark(fiCaExcel.getCompanyName()+","+fiCaExcel.getRemark()==null?"":fiCaExcel.getRemark());
			//fiCashiercollection.setCreateRemark("���ױ���:"+fiExcelPos.getTransactionNumber()+"_���׽��:"+fiExcelPos.getAmount()+"_������:"+fiExcelPos.getFeeAmount()+"_������:"+fiExcelPos.getSettlemenAmount());
			this.fiCashiercollectionDao.save(fiCashiercollection);//�����տ
			
			//�˺�����(������)
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCaExcel.getFiCapitaaccountsetId());
			openingBalance = fiCapitaaccountset.getOpeningBalance();// �˺����	
			openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// �˺����+�����տ���
			fiCapitaaccountset.setOpeningBalance(openingBalance);// ��������
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
			
			//�˺���ˮ
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setLoan(collectionAmount);// �跽���
			fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			fiCapitaaccount.setSourceData("����");
			fiCapitaaccount.setRemark("������������");
			fiCapitaaccount.setSourceNo(fiCaExcel.getId());
			fiCapitaaccount.setBalance(openingBalance);// �˺����+�����տ���
			this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��
		}
	}
	
	public void saveExcelPosData(Long batchNo) throws Exception {
		double openingBalance=0.0;
		double collectionAmount=0.0;
		List<FiExcelPos> list=this.fiExcelPosService.findBy("batchNo", batchNo);
		if(list.size()==0) throw new ServiceException("δ�ҵ�POS��������");
		for(FiExcelPos fiExcelPos:list){
			collectionAmount=fiExcelPos.getAmount();//���׽��
			
			//��������տ
			FiCashiercollection fiCashiercollection=new FiCashiercollection();
			fiCashiercollection.setFiCapitaaccountsetId(fiExcelPos.getFiCapitaaccountsetId());
			fiCashiercollection.setCollectionTime(fiExcelPos.getCollectionTime());
			fiCashiercollection.setCollectionAmount(collectionAmount);
			fiCashiercollection.setCreateRemark(fiExcelPos.getRemark());
			this.fiCashiercollectionDao.save(fiCashiercollection);//�����տ
			
			//�˺�����(������)
			FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiExcelPos.getFiCapitaaccountsetId());
			if(fiCapitaaccountset.getOpeningBalance()!=null){
				openingBalance = fiCapitaaccountset.getOpeningBalance();// �˺����
			}
			openingBalance = DoubleUtil.add(openingBalance , collectionAmount);// �˺����+�����տ���
			fiCapitaaccountset.setOpeningBalance(openingBalance);// ��������
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
			
			//�˺���ˮ
			FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
			fiCapitaaccount.setLoan(collectionAmount);// �跽���
			fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
			fiCapitaaccount.setSourceData("����POS");
			fiCapitaaccount.setRemark(fiExcelPos.getRemark());
			fiCapitaaccount.setSourceNo(fiExcelPos.getId());
			fiCapitaaccount.setBalance(openingBalance);// �˺����+�����տ���
			this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��
		}
	}
	
	public void invalidCashiercollection(FiCashiercollection fiCashiercollection) throws Exception{
		double openingBalance=0.0;
		double collectionAmount=0.0;
		if(fiCashiercollection==null) throw new Exception("�����տ������!");
		if(fiCashiercollection.getVerificationAmount()!=0.0||fiCashiercollection.getVerificationStatus()!=0L){
		 throw new Exception("�����տ�Ѻ����򲿷ֺ�����������ɾ��!");
		}
		fiCashiercollection.setStatus(0L);
		this.fiCashiercollectionDao.save(fiCashiercollection);
		
		collectionAmount=fiCashiercollection.getCollectionAmount();
		
		//�˺�����(������)
		FiCapitaaccountset fiCapitaaccountset = this.fiCapitaaccountsetDao.get(fiCashiercollection.getFiCapitaaccountsetId());
		openingBalance = fiCapitaaccountset.getOpeningBalance();// �˺����	
		openingBalance = DoubleUtil.sub(openingBalance , collectionAmount);// �˺����+�����տ���
		fiCapitaaccountset.setOpeningBalance(openingBalance);// ��������
		this.fiCapitaaccountsetDao.save(fiCapitaaccountset);// �����˺����
		
		//�˺���ˮ
		FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
		fiCapitaaccount.setLoan(DoubleUtil.mul(collectionAmount,-1));// �跽���
		fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
		fiCapitaaccount.setSourceData("�����տ");
		fiCapitaaccount.setRemark("���ϳ����տ");
		fiCapitaaccount.setSourceNo(fiCashiercollection.getId());
		fiCapitaaccount.setBalance(openingBalance);// �˺����+�����տ���
		this.fiCapitaaccountDao.save(fiCapitaaccount);// ������ˮ��
	}
	
	
	//�ֹ�����
	public void manualVerification(Map map,User user) throws Exception{
		Long id=Long.valueOf(String.valueOf(map.get("id")));//�����տID
		FiCashiercollection fct=this.fiCashiercollectionDao.get(id);//�����տ
		String verificationUser=String.valueOf(user.get("name"));
		String verificationDept=String.valueOf(user.get("departName"));
		
		if(fct==null){
			throw new ServiceException("����ʧ�ܣ������տID�����ڣ�");
		}
		if(fct.getVerificationStatus()==1L){
			throw new ServiceException("�����տ�����");
		}
		if(fct.getStatus()==0L){
			throw new ServiceException("�����տ������");
		}
		
		//��������տ����
		fct.setEntrustAmount(fct.getCollectionAmount());
		fct.setEntrustTime(new Date());
		fct.setEntrustUser(String.valueOf(user.get("name")));
		fct.setEntrustRemark("�ֹ�����");
		fct.setVerificationRemark(map.get("verificationRemark")+"");
		fct.setVerificationAmount(fct.getCollectionAmount());
		fct.setVerificationStatus(1L);
		fct.setVerificationDept(verificationDept);
		fct.setVerificationUser(verificationUser);
		fct.setVerificationTime(new Date());
		this.fiCashiercollectionDao.save(fct);
	}
}
