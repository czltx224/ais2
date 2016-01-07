package com.xbwl.finance.Service.impl;

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
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.finance.Service.IBasStSpecialTrainRateService;
import com.xbwl.finance.dao.IBasStSpecialTrainRateDao;

/**
 * @author CaoZhili time Aug 2, 2011 4:03:59 PM
 * 
 * 专车标准协议价服务实现类
 */
@Service("basStSpecialTrainRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class BasStSpecialTrainRateServiceImpl extends
		BaseServiceImpl<BasStSpecialTrainRate, Long> implements
		IBasStSpecialTrainRateService {

	@Resource(name="basStSpecialTrainRateHibernateDaoImpl")
	private IBasStSpecialTrainRateDao basStSpecialTrainRateDao;
	
	@Override
	public IBaseDAO<BasStSpecialTrainRate, Long> getBaseDao() {

		return this.basStSpecialTrainRateDao;
	}
	
	@Override
	public void save(BasStSpecialTrainRate entity) {
		try {
        	if(allowSaveService(entity)){
        		if(null!=entity.getId() && entity.getId()>0){
        			entity.setStatus(1l);//修改和把状态改为未审核
        		}
		        this.basStSpecialTrainRateDao.save(entity);
        	}else{
        		throw new ServiceException("该专车标准协议价已经存在！");
        	}
        } catch (Exception e) {
        	throw new ServiceException("专车标准协议价保存失败！");
        }
	}



	public void updateStatusService(String[] ids, Long status)
			throws Exception {
		BasStSpecialTrainRate basStSpecialTrainRate=null;
		
		for (int i = 0; i < ids.length; i++) {
			
			basStSpecialTrainRate=this.basStSpecialTrainRateDao.getAndInitEntity(Long.valueOf(ids[i]));
			basStSpecialTrainRate.setStatus(status);
			this.basStSpecialTrainRateDao.save(basStSpecialTrainRate);
		}
		
	}

	public boolean allowSaveService(BasStSpecialTrainRate basStSpecialTrainRate) {
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");

		Query query = null;
		if(null!=basStSpecialTrainRate.getId() && basStSpecialTrainRate.getId()>0){
			query = this.basStSpecialTrainRateDao.createQuery("from BasStSpecialTrainRate where specialTrainLineId=? and roadType=? and departId=? and id!=?  AND STATUS!=?",
				 basStSpecialTrainRate.getSpecialTrainLineId(),basStSpecialTrainRate.getRoadType(),bussDepartId,basStSpecialTrainRate.getId(),0l);
		}else{
			query = this.basStSpecialTrainRateDao.createQuery("from BasStSpecialTrainRate where specialTrainLineId=? and roadType=? and departId=?  AND STATUS!=?",
					 basStSpecialTrainRate.getSpecialTrainLineId(),basStSpecialTrainRate.getRoadType(),bussDepartId,0l);
		}
		List<BasStSpecialTrainRate> list = query.list();
		
		if(null!=list && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
