package com.xbwl.finance.Service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasSpecialTrainRate;
import com.xbwl.finance.Service.ISpecialTrainRateService;
import com.xbwl.finance.Service.IStSpecialTrainRateService;
import com.xbwl.finance.dao.ISpecialTrainRateDao;

/**
 *@author LiuHao
 *@time 2011-7-22 ÉÏÎç10:26:06
 */
@Service("specialTrainRateServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class SpecialTrainRateServiceImpl extends BaseServiceImpl<BasSpecialTrainRate, Long>
		implements ISpecialTrainRateService {
	@Resource(name="specialTrainRateHiberndateDaoImpl")
	private ISpecialTrainRateDao specialTrainRateDao;
	@Resource(name="stSpecialTrainRateServiceImpl")
	private IStSpecialTrainRateService stSpecialTrainRateService;
	
	@Override
	public IBaseDAO<BasSpecialTrainRate, Long> getBaseDao() {
		return specialTrainRateDao;
	}

	public Page findSpecialTrainRate(Page page, Long cusId, String roadType,
			String town, String street,Long departId,String city) throws Exception {
		Page<BasSpecialTrainRate> page1=null;
		String sql="select bsstr.ID,bsstr.GOLD_CUP_CAR,bsstr.TWO_TON_CAR,bsstr.THREE_TON_CAR,bsstr.CHILL_CAR,bsstr.VAN,bsstr.FIVE_TON_CAR from bas_special_trai_rate bsstr,bas_special_train_line_detail bstld where bsstr.special_train_line_id=bstld.special_train_line_id and bsstr.cus_id=? and bsstr.road_type=? and bstld.area_name=? and bsstr.depart_id=?";
		page1=this.getPageBySql(page, sql, cusId+"",roadType,street,departId+"");
		if(page1.getResultMap().size()<1){
			page1=this.getPageBySql(page, sql, cusId+"",roadType,town,departId+"");
			if(page1.getResultMap().size()<1){
				page1=this.getPageBySql(page, sql, cusId+"",roadType,city,departId+"");
				if(page1.getResultMap().size()<1){
					page1=stSpecialTrainRateService.findStSpecialTrainRate(page,roadType,town,street,departId,city);
				}
			}
		}
		return page1;
	}
}
