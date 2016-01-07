package com.xbwl.finance.dao.hibernateDaoImpl;
import org.springframework.stereotype.Repository;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.BasSpecialTrainLine;
import com.xbwl.finance.dao.ISpecialTrainLineDao;

/**
 *@author LiuHao
 *@time 2011-7-22 обнГ02:55:35
 */
@Repository("specialTrainLineHibernateDaoImpl")
public class SpecialTrainLineHibernateDaoImpl extends
		BaseDAOHibernateImpl<BasSpecialTrainLine, Long> implements ISpecialTrainLineDao {
}
