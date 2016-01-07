package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.oper.stock.dao.IOprRequestDoDao;

/**
 * author CaoZhili time Jul 12, 2011 10:21:45 AM
 */
@Repository("oprRequestDoHibernateDaoImpl")
public class OprRequestDoHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprRequestDo, Long> implements IOprRequestDoDao {

	public OprRequestDo getOprRequestDoByDnoAndStage(String s, Long dno) {
		List <OprRequestDo>list= find( " from OprRequestDo o where o.dno=?  and  o.requestStage=?  ", dno,s);
		if(list.size()==0){
			return new OprRequestDo();
		}else {
			return list.get(0);
		}
	}

}
