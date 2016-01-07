package com.xbwl.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasRightDepart;
import com.xbwl.entity.BasStSpecialTrainRate;
import com.xbwl.sys.dao.IBasRightDepartDao;
import com.xbwl.sys.service.IBasRightDepartService;

@Service("basRightDepartServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasRightDepartServiceImpl extends BaseServiceImpl<BasRightDepart, Long>
	implements IBasRightDepartService{

	@Resource(name="basRightDepartDaoImpl")
	private IBasRightDepartDao basRightDepartDao;
	
	@Override
	public IBaseDAO<BasRightDepart, Long> getBaseDao() {
		return basRightDepartDao;
	}

	
	@Override
	public void save(BasRightDepart entity) {

		Query query = null;
		if(null!=entity.getId() && entity.getId()>0){
			query = this.basRightDepartDao.createQuery("from BasRightDepart where rightDepartid=? and userId=? and id!=?",
					entity.getRightDepartid(),entity.getUserId(),entity.getId());
		}else{
			query = this.basRightDepartDao.createQuery("from BasRightDepart where rightDepartid=? and userId=?",
					entity.getRightDepartid(),entity.getUserId());
		}
		List<BasStSpecialTrainRate> list = query.list();
		
		if(null!=list && list.size()>0){
			throw new ServiceException("该部门已经是权限部门，请勿重复添加！");
		}else{
			super.save(entity);
		}
	}


	public String findDepartName(Map<String, String> map)
			throws ServiceException {
		String userId = map.get("EQL_userId");
		String departName = map.get("LIKES_departName");
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.right_departid rightDepartId,d.depart_name departName")
		  .append(" from bas_right_depart t,sys_depart d where t.right_departid=d.depart_id");
		
		if(null!=userId && !"".equals(userId)){
			sb.append(" and t.user_id=:EQL_userId");
		}
		if(null!=departName && !"".equals(departName)){
			sb.append(" and d.depart_name like '%'||:LIKES_departName||'%'");
		}
		return sb.toString();
	}
}
