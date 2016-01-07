package com.xbwl.common.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.ralasafe.WebRalasafe;
import org.ralasafe.entitle.CustomizedWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.PropertyFilter.MatchType;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.utils.DateUtil;

/**
 * BaseServiceʵ����
 * @author yab
 * 2010.11.17 �������쳣�Ĳ�׽
 */
@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
public abstract class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public abstract IBaseDAO<T, PK>  getBaseDao();
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page<T> getAll(final Page<T> page){
		try{
			return getBaseDao().getAll(page);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��ҳ��ѯ����ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values){
		try{
		return getBaseDao().findPage(page, hql, values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��������ҳ��ѯ����ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page<T> findPage(final Page<T> page, final String hql,
			final Map<String, ?> values){
		try{
			return getBaseDao().findPage(page, hql, values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��������ҳ��ѯ����ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page<T> findPage(final Page<T> page, final Criterion... criterions){
		try{
			return getBaseDao().findPage(page, criterions);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��������ҳ��ѯ����ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> findBy(final String propertyName, final Object value,
			final MatchType matchType){
		try{
			return getBaseDao().findBy(propertyName, value, matchType);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("�����Բ�ѯ����ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> find(List<PropertyFilter> filters){
		try{
			return getBaseDao().find(filters);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			throw new ServiceException("��Filter��ѯ��ѯ����ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page<T> findPage(final Page<T> page,
			final List<PropertyFilter> filters){
		try{
			return getBaseDao().findPage(page, filters);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			throw new ServiceException("��Filter��ѯ��ҳ����ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object oldValue){
		try{
			return getBaseDao().isPropertyUnique(propertyName, newValue, oldValue);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("���������ƣ�ֵ��ѯ����ʧ�ܣ�");
		}
	}
	
	public void save(final T entity){
		try{
			getBaseDao().save(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			throw new ServiceException("��������ʧ�ܣ�");
		}
	}

	public void delete(final T entity){
		try{
			getBaseDao().delete(entity);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��ʵ��ɾ������ʧ�ܣ�");
		}
	}

	public void delete(final PK id){
		try{
			getBaseDao().delete(id);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("������ɾ������ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public T get(final PK id){
		try{
			return getBaseDao().get(id);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��������ѯ����ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> getAll(){
		try{
			return getBaseDao().getAll();
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��ѯȫ������ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> getAll(String orderBy, boolean isAsc){
		try{
			return getBaseDao().getAll(orderBy, isAsc);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ�������ֶβ�ѯȫ������ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> findBy(final String propertyName, final Object value){
		try{
			return getBaseDao().findBy(propertyName, value);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ��ָ���ֶβ�ѯȫ������ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public T findUniqueBy(final String propertyName, final Object value){
		try{
			return getBaseDao().findUniqueBy(propertyName, value);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ��ָ���ֶβ�ѯΨһ��¼ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> findByIds(List<PK> ids){
		try{
			return getBaseDao().findByIds(ids);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ����ѯlist���ϼ�¼ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public <X> List<X> find(final String hql, final Object... values){
		try{
			return getBaseDao().find(hql, values);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			throw new ServiceException("ͨ����ѯHQL����ѯ��¼ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public <X> List<X> find(final String hql, final Map<String, ?> values){
		try{
			return getBaseDao().find(hql, values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ����ѯHQL����ѯ��¼ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public <X> X findUnique(final String hql, final Object... values){
		try{
			return getBaseDao().findUnique(hql,values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ����ѯHQL����ѯΨһ��¼ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public <X> X findUnique(final String hql, final Map<String, ?> values){
		try{
			return getBaseDao().findUnique(hql, values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ����ѯHQL����ѯΨһ��¼ʧ�ܣ�");
		}
	}
	
	public int batchExecute(final String hql, final Object... values){
		try{
			return getBaseDao().batchExecute(hql, values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ִ��HQL������ʧ�ܣ�");
		}
	}

	public int batchExecute(final String hql, final Map<String, ?> values){
		try{
			return getBaseDao().batchExecute(hql, values);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ִ��HQL������ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<T> find(final Criterion... criterions){
		try{
			return getBaseDao().find(criterions);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ��������ѯ���ݲ���ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public T findUnique(final Criterion... criterions){
		try{
			return getBaseDao().findUnique(criterions);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("ͨ��������ѯΨһ��¼����ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Criteria createCriteria(final Criterion... criterions){
		try{
			return getBaseDao().createCriteria(criterions);
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("����Criteria����ʧ�ܣ�");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public String getIdName(){
		try{
			return getBaseDao().getIdName();
		}catch(Exception e){
			logger.error(e.toString());
			throw new ServiceException("��ѯ�������Ʋ���ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public T getAndInitEntity(final PK id){
		try{
			return getBaseDao().getAndInitEntity(id);
		}catch(Exception e){
			logger.error(e.toString());
			e.printStackTrace();
			throw new ServiceException("��id��ѯʵ�����ʧ�ܣ�");
		}
	}
	
	public void deleteByIds(List<PK> ids){
		try{
			Assert.notNull(ids,"ɾ���ļ��ϲ���Ϊ�գ�");
			for(PK id : ids){
				getBaseDao().delete(id);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			throw new ServiceException("����ɾ��ʵ�����ʧ�ܣ�");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page getPageByRela(List<PropertyFilter> list,HttpServletRequest req,int privilegeId,Map context,Page page){
		
		page.setResult((List)WebRalasafe.query(req, privilegeId, context, seyCustomizedWhere(list), page.getFirst(), page.getLimit()));
		page.setTotalCount(WebRalasafe.queryCount(req, privilegeId, context, seyCustomizedWhere(list)));
		
		return page;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	protected CustomizedWhere seyCustomizedWhere(List<PropertyFilter> list){
		CustomizedWhere where =new CustomizedWhere();
		
		for(PropertyFilter propertyFilter:list){
			
			Object propertyValue=propertyFilter.getPropertyValue();
			MatchType matchType=propertyFilter.getMatchType();
			String property=propertyFilter.getPropertyName();
			
			 if (MatchType.EQ.equals(matchType)) {
				 where.addEqual(property, propertyValue);
			} else if (MatchType.LIKE.equals(matchType)) {
				if(propertyValue.toString().indexOf("%")!=-1){
					where.addLike(property, propertyValue);
				}else{
					where.addLike(property, "%"+propertyValue+"%");
				}
			} else if (MatchType.LE.equals(matchType)) {
				if(propertyValue instanceof java.util.Date){
					Date date=(Date)propertyValue;
					try {
						Date temp = DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD));
						if(date.equals(temp)){
							propertyValue=DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD)+" 23:59:59",DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
						}
					} catch (ParseException e) {
						logger.error(e.getLocalizedMessage());
						e.printStackTrace();
					}
				}
				where.addLessEqual(property, propertyValue);
			} else if (MatchType.LT.equals(matchType)) {
				where.addLessThan(property, propertyValue);
			} else if (MatchType.GE.equals(matchType)) {
				where.addGreaterEqual(property, propertyValue);
			} else if (MatchType.GT.equals(matchType)) {
				if(propertyValue instanceof java.util.Date){
					Date date=(Date)propertyValue;
					try {
						Date temp = DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD));
						if(date.equals(temp)){
							propertyValue=DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD)+" 23:59:59",DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
						}
					} catch (ParseException e) {
						logger.error(e.getLocalizedMessage());
						e.printStackTrace();
					}
				}
				where.addGreaterThan(property, propertyValue);
			}else if (MatchType.NE.equals(matchType)) {
				where.addNotEqual(property, propertyValue);
			}
			 
		}
		
		return where;
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page getPageBySqlMap(Page page,String sql,Map<String, ?> map ){
		return 	this.getBaseDao().findPageBySqlMap(page, sql, map);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page getPageBySql(Page page,String sql,String ... object ){
		return  this.getBaseDao().findPageBySql(page, sql,object);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Page getPageBySql(Page page,String sql,List<PropertyFilter> fiList ){
		StringBuffer where =new StringBuffer(sql);
				for(PropertyFilter propertyFilter:fiList){
					Object propertyValue=propertyFilter.getPropertyValue();
					MatchType matchType=propertyFilter.getMatchType();
					String property=propertyFilter.getPropertyName();
				}
		
		return page;
	}
	
	
	public Query createSQLQuery(final String queryString, final Object... values){
		return this.getBaseDao().createSQLQuery( queryString, values);
	};
	// REVIEW ȥ����Ч��䣻���������ģ���
	//FIXED ������
	public Query createSQLMapQuery(final String queryString, final Map<String, ?> values){
		return this.getBaseDao().createSQLQuery( queryString, values);
	} ;	
	// REVIEW ȥ����Ч��䣻���������ģ���
	//FIXED ������
}
