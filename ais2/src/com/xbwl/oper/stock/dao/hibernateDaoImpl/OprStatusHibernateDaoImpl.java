package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.stock.dao.IOprStatusDao;

/**
 * author CaoZhili time Jul 6, 2011 2:53:14 PM
 */
@Repository("oprStatusHibernateDaoImpl")
public class OprStatusHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprStatus, Long> implements IOprStatusDao {

	public OprStatus findStatusByDno(Long dno) throws Exception {
		List<OprStatus> list=this.find("from OprStatus sta where sta.dno=?", dno);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
