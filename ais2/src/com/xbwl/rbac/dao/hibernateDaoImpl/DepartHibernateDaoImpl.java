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
	 * �����ϼ�����ID��ѯ������Ϣ
	 */
	public List<SysDepart> getDepartByParentId(Long parentId) throws Exception {
		return this.find("from SysDepart sd where sd.parent.id=?", parentId);
		//REVIEW ��Ȼ�᷵�ؿգ��β�ֱ�ӷ��أ�����Ҫ�����жϸ�ʲô
		//FIXED LIUH
	}

}
