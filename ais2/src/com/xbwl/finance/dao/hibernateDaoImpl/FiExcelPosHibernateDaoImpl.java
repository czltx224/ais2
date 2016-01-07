package com.xbwl.finance.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiExcelPos;
import com.xbwl.finance.dao.IFiExcelPosDao;

@Repository("fiExcelPosHibernateDaoImpl")
public class FiExcelPosHibernateDaoImpl extends BaseDAOHibernateImpl<FiExcelPos,Long> implements
		IFiExcelPosDao {


}
