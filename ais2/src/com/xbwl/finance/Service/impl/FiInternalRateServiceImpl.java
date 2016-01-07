package com.xbwl.finance.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalRate;
import com.xbwl.finance.Service.IFiInternalRateService;
import com.xbwl.finance.dao.IFiInternalRateDao;

/**
 * author CaoZhili time Oct 19, 2011 5:22:10 PM �ڲ���������ʵ����
 */
@Service("fiInternalRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiInternalRateServiceImpl extends
		BaseServiceImpl<FiInternalRate, Long> implements IFiInternalRateService {

	@Resource(name = "fiInternalRateHibernateDaoImpl")
	private IFiInternalRateDao fiInternalRateDao;

	@Override
	public IBaseDAO<FiInternalRate, Long> getBaseDao() {
		return this.fiInternalRateDao;
	}

	@Override
	public void save(FiInternalRate entity) {
		if(entity.getStartDepartId().compareTo(entity.getEndDepartId())==0){
			throw new ServiceException("������ʼ�����ź͵��ﲿ����ȵ��ڲ�����Э��ۣ�");
		}
		List<FiInternalRate> list = this.fiInternalRateDao
				.createQuery(
						"from FiInternalRate where startDepartId=? and endDepartId=? and id!=?",
						entity.getStartDepartId(), entity.getEndDepartId(),
						entity.getId()==null?0l:entity.getId()).list();
		if(null!=list && list.size()>0){
			throw new ServiceException("���ڲ�����Э����Ѿ����ڣ�");
		}
		super.save(entity);
	}

	public void invalidService(String[] strids, Long status) throws Exception {
		FiInternalRate entity=null;
		for (int i = 0; i < strids.length; i++) {
			entity=this.fiInternalRateDao.get(Long.valueOf(strids[i]));
			entity.setStatus(status);//�޸�Ϊ����״̬
			this.fiInternalRateDao.save(entity);//����
		}
	}
	
	public FiInternalDetail calculateCost(Long startDepartId,Long endDepartId,Long dno,Double flightWeight,String outStockMode)throws Exception{
		double amount=0.0;//���
		double amountLowest=0.0;//���һƱ���
		List<FiInternalRate> list = this.fiInternalRateDao.createQuery("from FiInternalRate where startDepartId=? and endDepartId=?",startDepartId, endDepartId).list();
		if (list.size()==0){
			//throw new ServiceException("���ڲ�����Э��۲����ڣ�");
			return null;
		}else if(list.size() > 1) {
			throw new ServiceException("���ڲ�����Э��۴��ڶ�����¼��");
		}
		FiInternalRate fiInternalRate=list.get(0);
		if(outStockMode.equals("�°�����")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getXbFrommention());
			amountLowest=fiInternalRate.getXbFrommentionLowest();
		}else if(outStockMode.equals("�°������ͻ�")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getXbCity());
			amountLowest=fiInternalRate.getXbCityLowest();
		}else if(outStockMode.equals("�°���ͻ�")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getXbSuburbs());
			amountLowest=fiInternalRate.getXbSuburbsLowest();
		}else if(outStockMode.equals("��ת����")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getTransitFrommention());
			amountLowest=fiInternalRate.getTransitFrommentionLowest();
		}else if(outStockMode.equals("��ת�����ͻ�")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getTransitCity());
			amountLowest=fiInternalRate.getTransitCityLowest();
		}else if(outStockMode.equals("��ת�����ͻ�")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getTransitSuburbs());
			amountLowest=fiInternalRate.getTransitSuburbsLowest();
		}else if(outStockMode.equals("�ⷢ����")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getOutFrommention());
			amountLowest=fiInternalRate.getOutFrommentionLowest();
		}else if(outStockMode.equals("�ⷢ�ͻ�")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getOutDelivery());
			amountLowest=fiInternalRate.getOutDeliveryLowest();
		}else{
			throw new ServiceException("���ͷ�ʽ�����ڣ�");
		}
		FiInternalDetail fiInternalDetail=new FiInternalDetail();
		fiInternalDetail.setAmount(amount>amountLowest?amount:amountLowest);
		fiInternalDetail.setAgreementId(fiInternalRate.getId());
		return fiInternalDetail;
	}
}
