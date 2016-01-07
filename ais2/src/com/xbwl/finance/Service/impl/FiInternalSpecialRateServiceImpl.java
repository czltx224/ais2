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
import com.xbwl.entity.FiInternalSpecialRate;
import com.xbwl.finance.Service.IFiInternalSpecialRateService;
import com.xbwl.finance.dao.IFiInternalSpecialRateDao;

/**
 * author CaoZhili time Oct 20, 2011 10:40:36 AM
 * 内部特殊客户协议价设置服务层实现类
 */
@Service("fiInternalSpecialRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiInternalSpecialRateServiceImpl extends
		BaseServiceImpl<FiInternalSpecialRate, Long> implements
		IFiInternalSpecialRateService {

	@Resource(name="fiInternalSpecialRateHibernateDaoImpl")
	private IFiInternalSpecialRateDao fiInternalSpecialRateDao;
	
	@Override
	public IBaseDAO<FiInternalSpecialRate, Long> getBaseDao() {
		return this.fiInternalSpecialRateDao;
	}

	public void invalidService(String[] strids, Long status) throws Exception {
		FiInternalSpecialRate entity=null;
		for (int i = 0; i < strids.length; i++) {
			entity=this.fiInternalSpecialRateDao.get(Long.valueOf(strids[i]));
			entity.setStatus(status);//修改为作废状态
			this.fiInternalSpecialRateDao.save(entity);//保存
		}
	}

	@Override
	public void save(FiInternalSpecialRate entity) {
		List<FiInternalRate> list = this.fiInternalSpecialRateDao
		.createQuery(
				"from FiInternalSpecialRate where customerId=? and id!=?",
				entity.getCustomerId(),
				entity.getId()==null?0l:entity.getId()).list();
	if(null!=list && list.size()>0){
		throw new ServiceException("该客商内部特殊协议价已经存在！");
	}
	super.save(entity);
	}
	
	public FiInternalDetail calculateCost(Long customerId,Double flightWeight,Long flightPiece,Double bulk)throws Exception{
		String valuationType;//计价方式(公斤/体积/件数)
		double amount=0.0;//金额
		double amountLowest=0.0;//最低一票金额
		FiInternalDetail fiInternalDetail=new FiInternalDetail();
		List<FiInternalSpecialRate> list=this.fiInternalSpecialRateDao.find("from FiInternalSpecialRate where customerId=? and status=?", customerId,1L);
		
		if(list==null||list.size()==0){
			return null;
		}else if(list.size()>1){
			throw new ServiceException("该客商内部特殊协议价存在多条记录！");
		}else{
			FiInternalSpecialRate fiInternalSpecialRate=list.get(0);
			valuationType=fiInternalSpecialRate.getValuationType();
			fiInternalDetail.setAgreementId(fiInternalSpecialRate.getId());
			if("公斤".equals(valuationType)){
				if(flightWeight==null||flightWeight<=0.0){
					throw new ServiceException("请输入重量!");
				}
				amount=DoubleUtil.mul(flightWeight, fiInternalSpecialRate.getRate());
				amountLowest=fiInternalSpecialRate.getLowest();
			}else if("件数".equals(valuationType)){
				if(flightPiece==null||"".equals(flightPiece)){
					throw new ServiceException("请输入件数!");
				}
				amount=DoubleUtil.mul(flightPiece, fiInternalSpecialRate.getRate());
				amountLowest=fiInternalSpecialRate.getLowest();
			}else if("体积".equals(valuationType)){
				if(bulk==null||bulk<=0.0){
					throw new ServiceException("请输入体积!");
				}
				amount=DoubleUtil.mul(bulk, fiInternalSpecialRate.getRate());
				amountLowest=fiInternalSpecialRate.getLowest();
			}else{
				return null;
			}
			fiInternalDetail.setAmount(amount>amountLowest?amount:amountLowest);
		}
		
		
		return fiInternalDetail;
	}
	
}
