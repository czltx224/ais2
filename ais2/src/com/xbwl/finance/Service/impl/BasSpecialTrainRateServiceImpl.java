package com.xbwl.finance.Service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.finance.Service.IBasSpecialTrainRateService;
import com.xbwl.finance.dao.IBasSpecialTrainRateDao;
import com.xbwl.finance.dao.IBasStSpecialTrainRateDao;

/**
 * @author CaoZhili time Aug 2, 2011 4:07:22 PM
 * 
 * 专车协议价服务实现类
 */
@Service("basSpecialTrainRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class BasSpecialTrainRateServiceImpl extends
		BaseServiceImpl<BasSpecialTrainRate, Long> implements
		IBasSpecialTrainRateService{

	@Resource(name="basSpecialTrainRateHibernateDaoImpl")
	private IBasSpecialTrainRateDao basSpecialTrainRateDao;
	
	@Resource(name="basStSpecialTrainRateHibernateDaoImpl")
	private IBasStSpecialTrainRateDao basStSpecialTrainRateDao;
	
	@Override
	public IBaseDAO<BasSpecialTrainRate, Long> getBaseDao() {

		return this.basSpecialTrainRateDao;
	}
	
	@Override
	public void save(BasSpecialTrainRate entity) {
		//basSpecialTrainRateDao.save(entity);
		Double discount=null;
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
		
		if(null==entity.getDepartId()){
			entity.setDepartId(bussDepartId);
		}
		try{
			if(allowSaveService(entity)){
				entity.setDiscount(0d);
//				discount=getDiscountService(entity);
//				entity.setDiscount(discount);
//				if(null!=discount && discount>0){
//					if(null!=entity.getId() && entity.getId()>0){
//	        			entity.setStatus(1l);//修改和把状态改为未审核
//	        		}
//					this.basSpecialTrainRateDao.save(entity);
//				}else{
//					throw new ServiceException("折扣设计失败！");
//				}
				super.save(entity);
			}else{
				throw new ServiceException("该专车协议价已经存在！");
			}
		}catch(Exception e){
			throw new ServiceException(e.getLocalizedMessage());
		}
		
		
	}



	public void updateStatusService(String[] ids, Long status)
			throws Exception {
		
		BasSpecialTrainRate basSpecialTrainRate=null;
		
		for (int i = 0; i < ids.length; i++) {
			basSpecialTrainRate = this.basSpecialTrainRateDao.getAndInitEntity(Long.valueOf(ids[i]));
			basSpecialTrainRate.setStatus(status);
			this.basSpecialTrainRateDao.save(basSpecialTrainRate);
		}
		
	}
	//FIXED-ANN 增加注释
	/* (non-Javadoc)获取折扣
	 * @see com.xbwl.finance.Service.IBasSpecialTrainRateService#getDiscountService(com.xbwl.entity.BasSpecialTrainRate)
	 */
	public Double getDiscountService(BasSpecialTrainRate basSpecialTrainRate) throws Exception{

		Double discount=Double.valueOf(0);
		Query query = this.basStSpecialTrainRateDao.createQuery("from BasStSpecialTrainRate where specialTrainLineId=? and roadType=? and departId=? and status!=?",
				basSpecialTrainRate.getSpecialTrainLineId(),basSpecialTrainRate.getRoadType(),basSpecialTrainRate.getDepartId(),0l);
		
		List<BasStSpecialTrainRate> list=query.list();
		BasStSpecialTrainRate entity=null;
		
		for(int i=0;i<list.size();i++){
			entity = list.get(i);
			if(entity.getStatus()!=2){
				throw new ServiceException("该专车标准协议价未审核！");
			}
			discount=this.getDiscount(basSpecialTrainRate,entity);
		}
		return discount;
	}

	private Double getDiscount(BasSpecialTrainRate basSpecialTrainRate,
			BasStSpecialTrainRate entity)  {
		 Double van = Double.valueOf(0);
		 Double goldCupCar=Double.valueOf(0);
		 Double twoTonCar=Double.valueOf(0);
		 Double threeTonCar=Double.valueOf(0);
		 Double fiveTonCar=Double.valueOf(0);
		 Double chillCar=Double.valueOf(0);
		 
		 if(null!=basSpecialTrainRate.getVan()){
			 van=basSpecialTrainRate.getVan()/entity.getVan();
		 }
		 if(null!=basSpecialTrainRate.getGoldCupCar()){
			 goldCupCar=basSpecialTrainRate.getGoldCupCar()/entity.getGoldCupCar();
		 }
		 if(null!=basSpecialTrainRate.getTwoTonCar()){
			 twoTonCar=basSpecialTrainRate.getTwoTonCar()/entity.getTwoTonCar();
		 }
		 if(null!=basSpecialTrainRate.getThreeTonCar()){
			 threeTonCar=basSpecialTrainRate.getThreeTonCar()/entity.getThreeTonCar();
		 }
		 if(null!=basSpecialTrainRate.getFiveTonCar()){
			 fiveTonCar=basSpecialTrainRate.getFiveTonCar()/entity.getFiveTonCar();
		 }
		 if(null!=basSpecialTrainRate.getChillCar()){
			 chillCar=basSpecialTrainRate.getChillCar()/entity.getChillCar();
		 }
		 
		double[] discounts=new double[]{van,goldCupCar,twoTonCar,threeTonCar,fiveTonCar,chillCar};
		
		Arrays.sort(discounts);
		for (int i = 0; i < discounts.length; i++) {
			//FIXED 将上面的运算合并到下面的比较中，减少运算
			if(discounts[i]>Double.valueOf(0.0)){
				return Double.valueOf(Math.round(discounts[i]  * 100 ))/100;
			}
		}
		return null;
	}

	public boolean allowSaveService(BasSpecialTrainRate basSpecialTrainRate) {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");

		Query query =null;
		if(null!=basSpecialTrainRate.getId() && basSpecialTrainRate.getId()>0){
			query =this.basStSpecialTrainRateDao.createQuery("from BasSpecialTrainRate where specialTrainLineId=? and roadType=? and cusId=? and departId=? and id!=? AND status!=?",
					basSpecialTrainRate.getSpecialTrainLineId(),basSpecialTrainRate.getRoadType(),basSpecialTrainRate.getCusId(),bussDepartId,basSpecialTrainRate.getId(),0l);
		}else{
			query =this.basStSpecialTrainRateDao.createQuery("from BasSpecialTrainRate where specialTrainLineId=? and roadType=? and cusId=? and departId=? AND status!=? ",
					basSpecialTrainRate.getSpecialTrainLineId(),basSpecialTrainRate.getRoadType(),basSpecialTrainRate.getCusId(),bussDepartId,0l);
		}
		List<BasSpecialTrainRate> list = query.list();
		
		if(null!=list && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
