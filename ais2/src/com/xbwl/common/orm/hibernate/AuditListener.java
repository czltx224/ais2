package com.xbwl.common.orm.hibernate;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.hibernate.proxy.HibernateProxy;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.utils.AnnotationUtils;
import com.xbwl.common.utils.Rate;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.finance.Service.IBasTreatyChangeListService;

/**
 * ���Զ�Ϊentity��������Ϣ��Hibernate EventListener.
 * 
 * ��hibernateִ��saveOrUpdate()ʱ,�Զ�ΪAuditableEntity��������������Ϣ.
 * 
 * @author calvin
 * @deprecated
 */
@SuppressWarnings("serial")
public class AuditListener implements SaveOrUpdateEventListener {

	private static Logger logger = LoggerFactory.getLogger(AuditListener.class);

	private SimpleJdbcTemplate jdbcTemplate;
	
	@Resource(name="springContextHolder")
	private com.xbwl.common.utils.SpringContextHolder  springContextHolder ;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		final SessionImplementor source = event.getSession();
		final Object object = event.getObject();
		final Serializable requestedId = event.getRequestedId();
		Object entityPro =null;

		if (requestedId != null) {
			if (object instanceof HibernateProxy) {
				((HibernateProxy) object).getHibernateLazyInitializer().setIdentifier(requestedId);
			}
		}

		if (reassociateIfUninitializedProxy(object, source)) {
			logger.trace("reassociated uninitialized proxy");
		} else {
			entityPro = source.getPersistenceContext().unproxyAndReassociate(object);
		}

		try {
			
			Object idObject = getIDValue(entityPro);
			
			Rate mobj = entityPro.getClass().getAnnotation(Rate.class);
			if(mobj!=null){
				String mapping=AnnotationUtils.getValue(
						mobj, "mapping").toString();
			
				IBasTreatyChangeListService basTreatyChangeListService= springContextHolder.getBean("basTreatyChangeListServiceImpl");
				basTreatyChangeListService.saveRecord(entityPro, mapping, false);
			}
		

			if (entityPro instanceof AuditableEntity) {
				HttpServletRequest req = null;
				try{
					req = Struts2Utils.getRequest();
				}catch (Exception e) {
					//���������������ⲿ�ӿڵ���
				}
				if(null!=req){
					User user = WebRalasafe.getCurrentUser(req);
					
					AuditableEntity entity = (AuditableEntity) entityPro;
					// �����¶���
					
					if (idObject == null) {
						entity.setCreateName(user.get("name") + "");
						entity.setCreateTime(new Date());
						entity.setTs(new Date().getTime() + "");
	
						entity.setUpdateTime(new Date());
						entity.setUpdateName(user.get("name") + "");
						entity.setTs(new Date().getTime() + "");
	
						logger.debug("{}����  {} ����,ts:{}", new Object[] {
								object.getClass().getName(), new Date(),
								entity.getTs() });
						
						
					} else {
						// �޸ľɶ���
	
						entity.setUpdateTime(new Date());
						entity.setUpdateName(user.get("name") + "");
						entity.setTs(new Date().getTime() + "");
	
						logger.debug("{}����  {} �޸�", new Object[] {
								object.getClass().getName(), new Date() });
					}
				}
			}
		} catch (Exception e) {
			logger.error("Hibernate�����������Ƚ����ص����⣬����ģ��ܶ࣡{}", e
					.getLocalizedMessage());
			e.printStackTrace();
			throw new HibernateException(e.getLocalizedMessage());
		}
	}

	private String getIDName(Object ob) throws SecurityException,
			NoSuchFieldException {
		Method[] methods = ob.getClass().getMethods();
		for (Method method : methods) {
			Id id = method.getAnnotation(Id.class);
			if (id != null) {
				Column column = method.getAnnotation(Column.class);
				return column.name();
			}
		}
		return null;

	}

	private Object getIDValue(Object ob) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		try {
			Method[] methods = ob.getClass().getMethods();
			for (Method method : methods) {
				Id id = method.getAnnotation(Id.class);
				if (id != null) {
					Object idValue = method.invoke(ob);
					return idValue;
				}
			}
		} catch (Exception e) {
			throw new ServiceException(
					"�ڻ�ȡIDֵ��ʱ���������δ����ID��ע�⣬����δ����ע��ķ�ʽ�����룡 �������{}", e
							.getLocalizedMessage());
		}

		return null;

	}

	protected String getLoggableName(String entityName, Object entity) {
		return entityName == null ? entity.getClass().getName() : entityName;
	}

	protected boolean reassociateIfUninitializedProxy(Object object,
			SessionImplementor source) {
		return source.getPersistenceContext().reassociateIfUninitializedProxy(
				object);
	}
	

}
