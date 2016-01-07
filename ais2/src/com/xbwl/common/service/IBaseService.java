package com.xbwl.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.PropertyFilter.MatchType;

/**
 * ͨ��Service��ӿ�
 * @author yab
 *
 * @param <T> ���Ͳ���
 * @param <PK> id��ʶ
 */
public interface IBaseService<T, PK extends Serializable> {
	/**
	 * ��ҳ��ȡȫ������.
	 */
	public Page<T> getAll(final Page<T> page);

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page
	 *            ��ҳ����.��֧�����е�orderBy����.
	 * @param hql
	 *            hql���.
	 * @param values
	 *            �����ɱ�Ĳ�ѯ����,��˳���.
	 * 
	 * @return ��ҳ��ѯ���, ��������б������в�ѯʱ�Ĳ���.
	 */
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values);

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page
	 *            ��ҳ����.
	 * @param hql
	 *            hql���.
	 * @param values
	 *            ��������,�����ư�.
	 * 
	 * @return ��ҳ��ѯ���, ��������б������в�ѯʱ�Ĳ���.
	 */
	public Page<T> findPage(final Page<T> page, final String hql,
			final Map<String, ?> values);

	/**
	 * ��Criteria��ҳ��ѯ.
	 * 
	 * @param page
	 *            ��ҳ����.
	 * @param criterions
	 *            �����ɱ��Criterion.
	 * 
	 * @return ��ҳ��ѯ���.��������б������в�ѯʱ�Ĳ���.
	 */
	public Page<T> findPage(final Page<T> page, final Criterion... criterions);

	/**
	 * �����Բ��Ҷ����б�,֧�ֶ���ƥ�䷽ʽ.
	 * 
	 * @param matchType
	 *            ƥ�䷽ʽ,Ŀǰ֧�ֵ�ȡֵ��PropertyFilter��MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value,
			final MatchType matchType);

	/**
	 * �����Թ��������б����Ҷ����б�.
	 */
	public List<T> find(List<PropertyFilter> filters);

	/**
	 * �����Թ��������б���ҳ���Ҷ���.
	 */
	public Page<T> findPage(final Page<T> page,
			final List<PropertyFilter> filters);

	/**
	 * �ж϶��������ֵ�����ݿ����Ƿ�Ψһ.
	 * 
	 * ���޸Ķ�����龰��,����������޸ĵ�ֵ(value)��������ԭ����ֵ(orgValue)�����Ƚ�.
	 */
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object oldValue);

	/**
	 * �����������޸ĵĶ���.
	 */
	public void save(final T entity);

	/**
	 * ɾ������.
	 * 
	 * @param entity
	 *            ���������session�еĶ����id���Ե�transient����.
	 */
	public void delete(final T entity);

	/**
	 * ��idɾ������.
	 */
	public void delete(final PK id);

	/**
	 * ��id��ȡ����.
	 */
	public T get(final PK id);

	/**
	 * ��ȡȫ������.
	 */
	public List<T> getAll();

	/**
	 * ��ȡȫ������,֧������.
	 */
	public List<T> getAll(String orderBy, boolean isAsc);

	/**
	 * �����Բ��Ҷ����б�,ƥ�䷽ʽΪ���.
	 */
	public List<T> findBy(final String propertyName, final Object value);

	/**
	 * �����Բ���Ψһ����,ƥ�䷽ʽΪ���.
	 */
	public T findUniqueBy(final String propertyName, final Object value);

	/**
	 * ��id�б���ȡ����.
	 */
	public List<T> findByIds(List<PK> ids);

	/**
	 * ��HQL��ѯ�����б�.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	public <X> List<X> find(final String hql, final Object... values);

	/**
	 * ��HQL��ѯ�����б�.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values);

	/**
	 * ��HQL��ѯΨһ����.
	 * 
	 * @param values
	 *            �����ɱ�Ĳ���,��˳���.
	 */
	public <X> X findUnique(final String hql, final Object... values);

	/**
	 * ��HQL��ѯΨһ����.
	 * 
	 * @param values
	 *            ��������,�����ư�.
	 */
	public <X> X findUnique(final String hql, final Map<String, ?> values);

	/**
	 * ִ��HQL���������޸�/ɾ������.
	 */
	public int batchExecute(final String hql, final Object... values);

	/**
	 * ִ��HQL���������޸�/ɾ������.
	 * 
	 * @return ���¼�¼��.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values);

	/**
	 * ��Criteria��ѯ�����б�.
	 * 
	 * @param criterions
	 *            �����ɱ��Criterion.
	 */
	public List<T> find(final Criterion... criterions);

	/**
	 * ��Criteria��ѯΨһ����.
	 * 
	 * @param criterions
	 *            �����ɱ��Criterion.
	 */
	public T findUnique(final Criterion... criterions);

	/**
	 * ����Criterion��������Criteria.
	 * 
	 * �����װ��find()����ȫ��Ĭ�Ϸ��ض�������ΪT,����ΪTʱʹ�ñ�����.
	 * 
	 * @param criterions
	 *            �����ɱ��Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions);


	/**
	 * ȡ�ö����������.
	 */
	public String getIdName();
	
	public T getAndInitEntity(final PK id);
	
	/**
	 * ����ids��Ӧ��List��������ɾ��ʵ��.
	 * @param ids ������ʾid����
	 */
	public void deleteByIds(List<PK> ids);
	
	
	/**ralasafe��װ
	 * @return
	 */
	public Page getPageByRela(List<PropertyFilter> list,HttpServletRequest req,int privilegeId,Map context,Page page);
	
	
	/**����SQL��ѯ��ҳ
	 * @param page
	 * @param sql
	 * @param map
	 * @return
	 */
	public Page getPageBySqlMap(Page page,String sql,Map<String, ?> map );
	

	/**����SQL��ѯ��ҳ
	 * @param page
	 * @param sql
	 * @param map
	 * @return
	 */
	public Page getPageBySql(Page page,String sql,String ... object );
	
	/**�Զ�ƴװSQL
	 * @param page
	 * @param sql
	 * @param fiList
	 * @return
	 */
	public Page getPageBySql(Page page,String sql,List<PropertyFilter> fiList );
	
	/**
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Query createSQLQuery(final String queryString, final Object... values);
	
	
	/**
	 * ���ݲ�ѯHQL������б�����Query����.
	 * 
	 * @param values ��������,�����ư�.
	 */
	public Query createSQLMapQuery(final String queryString, final Map<String, ?> values) ;	
	
}