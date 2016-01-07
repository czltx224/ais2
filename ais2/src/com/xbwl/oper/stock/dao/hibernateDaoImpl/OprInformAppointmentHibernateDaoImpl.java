package com.xbwl.oper.stock.dao.hibernateDaoImpl;

import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.OprInformAppointment;
import com.xbwl.oper.stock.dao.IOprInformAppointmentDao;

/**
 * author CaoZhili time Jul 18, 2011 2:13:43 PM
 */
@Repository
public class OprInformAppointmentHibernateDaoImpl extends
		BaseDAOHibernateImpl<OprInformAppointment, Long> implements
		IOprInformAppointmentDao {

}
