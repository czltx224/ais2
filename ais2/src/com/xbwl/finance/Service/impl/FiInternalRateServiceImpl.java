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
 * author CaoZhili time Oct 19, 2011 5:22:10 PM 内部结算服务层实现类
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
			throw new ServiceException("不允许始发部门和到达部门相等的内部结算协议价！");
		}
		List<FiInternalRate> list = this.fiInternalRateDao
				.createQuery(
						"from FiInternalRate where startDepartId=? and endDepartId=? and id!=?",
						entity.getStartDepartId(), entity.getEndDepartId(),
						entity.getId()==null?0l:entity.getId()).list();
		if(null!=list && list.size()>0){
			throw new ServiceException("该内部结算协议价已经存在！");
		}
		super.save(entity);
	}

	public void invalidService(String[] strids, Long status) throws Exception {
		FiInternalRate entity=null;
		for (int i = 0; i < strids.length; i++) {
			entity=this.fiInternalRateDao.get(Long.valueOf(strids[i]));
			entity.setStatus(status);//修改为作废状态
			this.fiInternalRateDao.save(entity);//保存
		}
	}
	
	public FiInternalDetail calculateCost(Long startDepartId,Long endDepartId,Long dno,Double flightWeight,String outStockMode)throws Exception{
		double amount=0.0;//金额
		double amountLowest=0.0;//最低一票金额
		List<FiInternalRate> list = this.fiInternalRateDao.createQuery("from FiInternalRate where startDepartId=? and endDepartId=?",startDepartId, endDepartId).list();
		if (list.size()==0){
			//throw new ServiceException("该内部结算协议价不存在！");
			return null;
		}else if(list.size() > 1) {
			throw new ServiceException("该内部结算协议价存在多条记录！");
		}
		FiInternalRate fiInternalRate=list.get(0);
		if(outStockMode.equals("新邦自提")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getXbFrommention());
			amountLowest=fiInternalRate.getXbFrommentionLowest();
		}else if(outStockMode.equals("新邦市内送货")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getXbCity());
			amountLowest=fiInternalRate.getXbCityLowest();
		}else if(outStockMode.equals("新邦郊区送货")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getXbSuburbs());
			amountLowest=fiInternalRate.getXbSuburbsLowest();
		}else if(outStockMode.equals("中转自提")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getTransitFrommention());
			amountLowest=fiInternalRate.getTransitFrommentionLowest();
		}else if(outStockMode.equals("中转市内送货")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getTransitCity());
			amountLowest=fiInternalRate.getTransitCityLowest();
		}else if(outStockMode.equals("中转郊区送货")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getTransitSuburbs());
			amountLowest=fiInternalRate.getTransitSuburbsLowest();
		}else if(outStockMode.equals("外发自提")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getOutFrommention());
			amountLowest=fiInternalRate.getOutFrommentionLowest();
		}else if(outStockMode.equals("外发送货")){
			amount=DoubleUtil.mul(flightWeight, fiInternalRate.getOutDelivery());
			amountLowest=fiInternalRate.getOutDeliveryLowest();
		}else{
			throw new ServiceException("配送方式不存在！");
		}
		FiInternalDetail fiInternalDetail=new FiInternalDetail();
		fiInternalDetail.setAmount(amount>amountLowest?amount:amountLowest);
		fiInternalDetail.setAgreementId(fiInternalRate.getId());
		return fiInternalDetail;
	}
}
