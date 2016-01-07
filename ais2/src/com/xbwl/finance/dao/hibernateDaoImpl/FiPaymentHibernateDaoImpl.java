package com.xbwl.finance.dao.hibernateDaoImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.FiPayment;
import com.xbwl.finance.dao.IFiPaymentDao;



/**
 * π¶ƒ‹√Ë ˆ£∫
 * @author  ÊŒ»
*@2011-7-16
 *
 */
@Repository("fiPaymentHibernateDaoImpl")
public class FiPaymentHibernateDaoImpl extends 
		BaseDAOHibernateImpl<FiPayment, Long> implements IFiPaymentDao {

}
