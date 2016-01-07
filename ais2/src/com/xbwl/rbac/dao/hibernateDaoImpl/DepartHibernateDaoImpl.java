package com.xbwl.rbac.dao.hibernateDaoImpl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.SysDepart;
import com.xbwl.rbac.dao.IDepartDao;

/**
 *author LiuHao
 *time Jun 9, 2011 2:10:22 PM
 */
@Repository("departHibernateDaoImpl")
public class DepartHibernateDaoImpl extends BaseDAOHibernateImpl<SysDepart,Long> implements
		IDepartDao {
	/**
	 * 根据上级部门ID查询部门信息
	 */
	public List<SysDepart> getDepartByParentId(Long parentId) throws Exception {
		return this.find("from SysDepart sd where sd.parent.id=?", parentId);
		//REVIEW 既然会返回空，何不直接返回，还需要进行判断干什么
		//FIXED LIUH
	}

}
