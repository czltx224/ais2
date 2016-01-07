package com.xbwl.common.orm.hibernate;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.PropertyFilter.MatchType;
import com.xbwl.common.utils.DateUtil;
import com.xbwl.common.utils.RandomGenerator;
import com.xbwl.common.utils.ReflectionUtils;



/**
 * DAO��ĳ����࣬���е�DAO��ʵ�ֶ���Ҫ�̳�
 * @author Administrator
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseDAOHibernateImpl<T, PK extends Serializable> extends
		SimpleHibernateDao<T, PK> implements IBaseDAO<T, PK> {
	
	Map aliasesMap;
	
	List<String>  list;
	
	/**
	 * ����Dao������ʹ�õĹ��캯��.
	 * ͨ������ķ��Ͷ���ȡ�ö�������Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public BaseDAOHibernateImpl() {
		super();
	}

	/**
	 * ����ʡ��Dao��, Service��ֱ��ʹ��ͨ��HibernateDao�Ĺ��캯��.
	 * �ڹ��캯���ж����������Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public BaseDAOHibernateImpl(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- ��ҳ��ѯ���� --//
	/**
	 * ��ҳ��ȡȫ������.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.��֧�����е�orderBy����.
	 * @param hql hql���.
	 * @param values �����ɱ�Ĳ�ѯ����,��˳���.
	 * 
	 * @return ��ҳ��ѯ���, ��������б����в�ѯʱ�Ĳ���.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page����Ϊ��");

		Query q = createQuery(hql, values);
		// REVIEW-ACCEPT qӦ�����зǿ��ж�,
		// 2012-01-03 createSQLQueryʹ���쳣�ķ�ʽ������ֱ����ǰ̨���Ͳ������ϵ��쳣��ʾ
		//FIXED ��createSQLQuery �Է���ֵ�������ж�
		
		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}
	
	/* declare: no javadoc
	 * User: ����ǿ
	 * Date: Mar 9, 2011 
	 * Time: 3:24:32 PM
	 * @see com.cndatacom.common.orm.IBaseDAO#findPage(com.cndatacom.common.orm.Page, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPageBySql(final Page<T> page, final String sql, final Object... values) {
		Assert.notNull(page, "page����Ϊ��");

		Query q = createSQLQuery(sql, values);
		// REVIEW-ACCEPT qӦ�����зǿ��ж�
		// 2012-01-03 createSQLQueryʹ���쳣�ķ�ʽ������ֱ����ǰ̨���Ͳ������ϵ��쳣��ʾ
		//FIXED ��createSQLQuery �Է���ֵ�������ж�
		if (page.isAutoCount()) {
			long totalCount = countSqlResult(sql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResultMap(result);
		return page;
	}
	
	/* declare: no javadoc
	 * User: ����ǿ
	 * Date: Mar 9, 2011 
	 * Time: 3:24:32 PM
	 * @see com.cndatacom.common.orm.IBaseDAO#findPage(com.cndatacom.common.orm.Page, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPageBySqlMap(final Page<T> page, final String sql, final Map<String, ?> values) {
		Assert.notNull(page, "page����Ϊ��");

		Query q = createSQLQuery(sql, values);
		// REVIEW-ACCEPT qӦ�����зǿ��ж�
		// 2012-01-03 createSQLQueryʹ���쳣�ķ�ʽ������ֱ����ǰ̨���Ͳ������ϵ��쳣��ʾ
		//FIXED ��createSQLQuery �Է���ֵ�������ж�
		if (page.isAutoCount()) {
			long totalCount = countSqlResultMap(sql, values);
			page.setTotalCount(totalCount);
		}
		setPageParameter(q, page);
		List result = q.list();
		page.setResultMap(result);
		return page;
	}

	/**
	 * ��HQL��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.
	 * @param hql hql���.
	 * @param values ��������,�����ư�.
	 * 
	 * @return ��ҳ��ѯ���, ��������б����в�ѯʱ�Ĳ���.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page����Ϊ��");

		Query q = createQuery(hql, values);
		// REVIEW-ACCEPT qӦ�����зǿ��ж�
		// 2012-01-03 createSQLQueryʹ���쳣�ķ�ʽ������ֱ����ǰ̨���Ͳ������ϵ��쳣��ʾ
		//FIXED ��createSQLQuery �Է���ֵ�������ж�
		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ��Criteria��ҳ��ѯ.
	 * 
	 * @param page ��ҳ����.
	 * @param criterions �����ɱ��Criterion.
	 * 
	 * @return ��ҳ��ѯ���.��������б����в�ѯʱ�Ĳ���.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page����Ϊ��");

		Criteria c = createCriteria(criterions);
		
		if(list != null) {
			for(String foo:list){
				generateAlias(c,foo,RandomGenerator.randomString(8));
			}
		}

		if (page.isAutoCount()) {
			int totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameter(c, page);
		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * ���÷�ҳ������Query����,��������.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		//hibernate��firstResult����Ŵ�0��ʼ
		q.setFirstResult(page.getStart());
		q.setMaxResults(page.getLimit());
		return q;
	}

	/**
	 * ���÷�ҳ������Criteria����,��������.
	 */
	protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
		// REVIEW-ACCEPT pageӦ�����зǿ��ж�
		//hibernate��firstResult����Ŵ�0��ʼ
		//FIXED
		Assert.notNull(page, "page ����Ϊ�գ�");
		c.setFirstResult(page.getStart());
		c.setMaxResults(page.getLimit());

		if (page.isOrderBySetted()) {
			
			Assert.notNull(page.getOrderBy(), "�����ֶβ���Ϊ�գ�");
			// REVIEW-ACCEPT orderByArrayӦ�����зǿ��ж�
			//FIXED
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');
			
			Assert.isTrue(orderByArray.length == orderArray.length, "��ҳ�������������,�����ֶ���������ĸ��������");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String fromHql = hql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	
	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countSqlResult(final String sql, final Object... values) {
		String fromSql = sql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		//fromSql = "select 1 from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");

		String countHql = "select count(*) num from( " + fromSql+")";

		try {
			Long count = Long.valueOf(((Map)findSQLUnique(countHql, values)).get("NUM").toString());
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, sql is:" + countHql, e);
		}
	}
	
	protected long countSqlResultMap(final String sql, final Map<String, ?> values) {
		String fromHql = sql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		//fromHql = "select 1 from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) num from( "+ fromHql+")";

		try {
			Long count = Long.valueOf(((Map)findSQLUnique(countHql, values)).get("NUM").toString());
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}
	
	

	/**
	 * ִ��count��ѯ��ñ���Hql��ѯ���ܻ�õĶ�������.
	 * 
	 * ������ֻ���Զ�����򵥵�hql���,���ӵ�hql��ѯ�����б�дcount����ѯ.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String fromHql = hql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * ִ��count��ѯ��ñ���Criteria��ѯ���ܻ�õĶ�������.
	 */
	@SuppressWarnings("unchecked")
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// �Ȱ�Projection��ResultTransformer��OrderByȡ����,������ߺ���ִ��Count����
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}

		// ִ��Count��ѯ
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();

		// ��֮ǰ��Projection,ResultTransformer��OrderBy�����������ȥ
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}

		return totalCount;
	}

	//-- ���Թ�������(PropertyFilter)��ѯ���� --//

	/**
	 * �����Բ��Ҷ����б�,֧�ֶ���ƥ�䷽ʽ.
	 * 
	 * @param matchType ƥ�䷽ʽ,Ŀǰ֧�ֵ�ȡֵ��PropertyFilter��MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildPropertyFilterCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * �����Թ��������б���Ҷ����б�.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return find(criterions);
	}

	/**
	 * �����Թ��������б��ҳ���Ҷ���.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return findPage(page, criterions);
	}

	/**
	 * �����������б���Criterion����,��������.
	 */
	protected Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) {
	    list=new ArrayList<String>();
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if(!filter.isLeftJion()){
			}else{
				list.add(filter.getPropertyName());
			}
				if ( !filter.isMultiProperty()) { //ֻ��һ��������Ҫ�Ƚϵ����.
					Criterion criterion = buildPropertyFilterCriterion(generateAlias(null,filter.getPropertyName(),RandomGenerator.randomString(8)), filter.getPropertyValue(),
							filter.getMatchType());
					criterionList.add(criterion);
				} else {//�������������Ҫ�Ƚϵ����,����or����.
					Disjunction disjunction = Restrictions.disjunction();
					for (String param : filter.getPropertyNames()) {
						Criterion criterion = buildPropertyFilterCriterion(generateAlias(null,param,RandomGenerator.randomString(8)), filter.getPropertyValue(), filter
								.getMatchType());
						disjunction.add(criterion);
					}
					criterionList.add(disjunction);
				}
			
			
		}
		
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	// REVIEW-ACCEPT ���ӷ���ע��
	  /**�Դ����name �� hibernate Criteria ����
	 * @param criteria
	 * @param name
	 * @param aliasKey
	 * @return
	 */
	//FIXED
	private String generateAlias(Criteria criteria, String name, String aliasKey) {
		  // REVIEW-ACCEPT ���������Ҫ���зǿ��ж�
		 //FIXED
			Assert.notNull(name, "��Ҫ�����name ����Ϊ�գ�");
			
	        int aliasPos = name.indexOf(':');
	        if (aliasPos != -1) {
	            String trueName = name.substring(aliasPos + 1);
	            if (trueName.indexOf('.') == -1) {
	                logger.error("ERROR:" + name + " -- the parameter name with alias should contain a . char!");
	                return null;
	            }
	            String alias = trueName.substring(0, trueName.indexOf('.'));
	            if (aliasesMap == null) {
	                aliasesMap = new HashMap();
	            }
	            String aliases = (String) aliasesMap.get(aliasKey);
	            if (aliases == null)
	                aliases = "";
	            if (aliases.indexOf(alias + '|') == -1) {
	            	if(criteria!=null){
	            		criteria = criteria.createAlias(name.substring(0, aliasPos), alias);
	            	}
	                aliases = aliases + alias + '|';
	                aliasesMap.put(aliasKey, aliases);
	            }
	            name = trueName;
	            if (name.lastIndexOf(':') != -1) {
	                name = generateAlias(criteria, name, aliasKey);
	            }
	        }
	        return name;
	    }
	  
	  
	
	/**
	 * ������������������Criterion,��������.
	 */
	protected Criterion buildPropertyFilterCriterion(final String propertyName, final Object propertyValue,
			final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName����Ϊ��");
		Criterion criterion = null;
		try {
			//����MatchType����criterion
			
			if(null==propertyValue){
				criterion=Restrictions.isNull(propertyName);
			}else if (MatchType.EQ.equals(matchType)) {
				criterion = Restrictions.eq(propertyName, propertyValue);
			} else if (MatchType.LIKE.equals(matchType)) {
				if(StringUtils.startsWith(propertyValue.toString(), "%")){
					criterion = Restrictions.like(propertyName, StringUtils.substringAfter(propertyValue+"", "%") , MatchMode.END);
				}else if(StringUtils.endsWith(propertyValue.toString(), "%")){
					criterion = Restrictions.like(propertyName, StringUtils.substringBeforeLast(propertyValue+"", "%"), MatchMode.START);
				}
				else{
					criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
				}
			} else if (MatchType.LE.equals(matchType)) {
				if(propertyValue instanceof java.util.Date){
					Date date=(Date)propertyValue;
					try {
						Date temp = DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD));
						if(date.equals(temp)){
							criterion = Restrictions.le(propertyName, DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD)+" 23:59:59",DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
						}else{
							criterion = Restrictions.le(propertyName, propertyValue);
						}
					} catch (ParseException e) {
						logger.error(e.getLocalizedMessage());
						e.printStackTrace();
					}
				}else{
					criterion = Restrictions.le(propertyName, propertyValue);
				}
				
			} else if (MatchType.LT.equals(matchType)) {
				criterion = Restrictions.lt(propertyName, propertyValue);
			} else if (MatchType.GE.equals(matchType)) {
				criterion = Restrictions.ge(propertyName, propertyValue);
		
			} else if (MatchType.GT.equals(matchType)) {
				
				if(propertyValue instanceof java.util.Date){
					Date date=(Date)propertyValue;
					try {
						Date temp = DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD));
						if(date.equals(temp)){
							criterion = Restrictions.ge(propertyName, DateUtil.parse(DateUtil.format(date,DateUtil.DATE_FORMAT_YYYY_MM_DD)+" 23:59:59",DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
						}else{
							criterion = Restrictions.ge(propertyName, propertyValue);
						}
					} catch (ParseException e) {
						logger.error(e.getLocalizedMessage());
						e.printStackTrace();
					}
				}else{
					criterion = Restrictions.ge(propertyName, propertyValue);
				}
			}else if (MatchType.SQ.equals(matchType)) {
				criterion = Restrictions.sqlRestriction(propertyName, propertyValue,Hibernate.STRING);
			}else if (MatchType.NE.equals(matchType)) {
				criterion = Restrictions.ne(propertyName, propertyValue);
			}else if(MatchType.EMPTY.equals(matchType)){
				criterion=Restrictions.isEmpty(propertyName);
			}else if(MatchType.NULL.equals(matchType)){
				criterion=Restrictions.isNull(propertyName);
			}else if(MatchType.NOTNULL.equals(matchType)){
				criterion=Restrictions.isNotNull(propertyName);
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
		return criterion;
	}
	

	/**
	 * �ж϶��������ֵ�����ݿ����Ƿ�Ψһ.
	 * 
	 * ���޸Ķ�����龰��,����������޸ĵ�ֵ(value)��������ԭ����ֵ(orgValue)�����Ƚ�.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}
