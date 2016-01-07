package com.xbwl.oper.reports.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprUnloadingStandard;
import com.xbwl.oper.reports.dao.IOprUnloadingStandardDao;
import com.xbwl.oper.reports.service.IOprUnloadingStandardService;

/**卸货时效标准服务层实现类
 * @author Administrator
 *
 */
@Service("oprUnloadingStandardServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprUnloadingStandardServiceImpl extends BaseServiceImpl<OprUnloadingStandard, Long>  implements IOprUnloadingStandardService{

	@Resource(name="oprUnloadingStandardHibernateDaoImpl")
	private IOprUnloadingStandardDao oprUnloadingStandardDao;
	
	@Override
	public IBaseDAO<OprUnloadingStandard, Long> getBaseDao() {
		return this.oprUnloadingStandardDao;
	}

	@Override
	public void save(OprUnloadingStandard entity) {
		
		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.oprUnloadingStandardDao.createQuery("from OprUnloadingStandard where departId=? and carType=? and id!=?",
				 entity.getDepartId(),entity.getCarType(),entity.getId());
		}else{
			query = this.oprUnloadingStandardDao.createQuery("from OprUnloadingStandard where departId=? and carType=?",
				 entity.getDepartId(),entity.getCarType());
		}
		List<OprUnloadingStandard> list = query.list();
		
		if(null!=list && list.size()>0){
			throw new ServiceException("该部门次类型车已经存在标准！");
		}else{
			super.save(entity);
		}
	}
	

}
