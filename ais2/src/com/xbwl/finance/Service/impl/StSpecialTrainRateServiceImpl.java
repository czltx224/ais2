package com.xbwl.finance.Service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.entity.CusRecord;
import com.xbwl.finance.Service.IBasSpecialTrainRateService;
import com.xbwl.finance.Service.IStSpecialTrainRateService;
import com.xbwl.finance.dao.ISpecialTrainRateDao;
import com.xbwl.finance.dao.IStSpecialTrainRateDao;

/**
 *@author LiuHao
 *@time 2011-7-22 上午10:36:54
 */
@Service("stSpecialTrainRateServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class StSpecialTrainRateServiceImpl extends BaseServiceImpl<BasStSpecialTrainRate, Long>
		implements IStSpecialTrainRateService {
	@Resource(name="stSpecialTrainRateHibernateDaoImpl")
	private IStSpecialTrainRateDao stSpecialTraiRateDao;
	@Resource(name="specialTrainRateHiberndateDaoImpl")
	private ISpecialTrainRateDao specialTraiRateDao;
	
	@Resource(name="cusRecordHibernateDaoImpl")
	private ICusRecordDao cusRecordDao;
	
	@Resource(name="basSpecialTrainRateServiceImpl")
	private IBasSpecialTrainRateService basSpecialTrainRateService;
	@Override
	public IBaseDAO<BasStSpecialTrainRate, Long> getBaseDao() {
		return stSpecialTraiRateDao;
	}
	public Page findStSpecialTrainRate(Page page, String roadType, String town,
			String street,Long departId,String city) throws Exception {
		Page<BasStSpecialTrainRate> page1=null;
		String sql="select bsstr.ID,bsstr.GOLD_CUP_CAR,bsstr.TWO_TON_CAR,bsstr.THREE_TON_CAR,bsstr.CHILL_CAR,bsstr.VAN,bsstr.FIVE_TON_CAR from bas_st_special_trai_rate bsstr,bas_special_train_line_detail bstld where bsstr.special_train_line_id=bstld.special_train_line_id and bsstr.road_type=? and bstld.area_name=? and bsstr.depart_id=?";
		page1=this.getPageBySql(page, sql, roadType,street,departId+"");
		if(page1.getResultMap().size()<1){
			page1=this.getPageBySql(page, sql, roadType,town,departId+"");
			if(page1.getResultMap().size()<1){
				page1=this.getPageBySql(page, sql, roadType,city,departId+"");
			}
		}
		return page1;
	}
	public void discountSpeRate(List stId, Long cusId,String cpName, Double rebate)
			throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		List<CusRecord> list=cusRecordDao.findBy("cusId", cusId);
		if(list.size()>0){
			CusRecord cr=list.get(0);
			cr.setImportanceLevel("目标客户");
			cusRecordDao.save(cr);
		}else{
			throw new ServiceException("该客户与客商没有对应，请联系系统管理员！");
		}
		for (int i = 0; i < stId.size(); i++) {
			BasStSpecialTrainRate bsstr=stSpecialTraiRateDao.get(Long.valueOf(stId.get(i)+""));
			BasSpecialTrainRate bstr=new BasSpecialTrainRate();
			bstr.setCusId(cusId);
			bstr.setCusName(cpName);
			bstr.setChillCar(bsstr.getChillCar()*rebate);
			bstr.setCreateName(user.get("name").toString());
			bstr.setCreateTime(new Date());
			bstr.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			bstr.setDepartId(1L);
			bstr.setDiscount(rebate);
			bstr.setFiveTonCar(bsstr.getFiveTonCar()*rebate);
			bstr.setGoldCupCar(bsstr.getGoldCupCar()*rebate);
			bstr.setRoadType(bsstr.getRoadType());
			bstr.setSpecialTrainLineId(bsstr.getSpecialTrainLineId());
			bstr.setStatus(1L);
			bstr.setThreeTonCar(bsstr.getThreeTonCar()*rebate);
			bstr.setTwoTonCar(bsstr.getTwoTonCar()*rebate);
			bstr.setVan(bsstr.getVan()*rebate);
			//REVIEW-ACCEPT 注释具体意义
			//FIXED LIUH 
			//bstr.setTs("3213135");
			if(basSpecialTrainRateService.allowSaveService(bstr)){
				specialTraiRateDao.save(bstr);
			}else{
				continue;
			}
		}
	}
}
