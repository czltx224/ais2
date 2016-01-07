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
 * DAO层的抽象类，所有的DAO的实现都需要继承
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
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public BaseDAOHibernateImpl() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User, Long>(sessionFactory, User.class);
	 */
	public BaseDAOHibernateImpl(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	//-- 分页查询函数 --//
	/**
	 * 分页获取全部对象.
	 */
	public Page<T> getAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数.不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);
		// REVIEW-ACCEPT q应当进行非空判断,
		// 2012-01-03 createSQLQuery使用异常的方式处理，将直接向前台发送不可意料的异常提示
		//FIXED 在createSQLQuery 对返回值进行了判断
		
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
	 * User: 赵文强
	 * Date: Mar 9, 2011 
	 * Time: 3:24:32 PM
	 * @see com.cndatacom.common.orm.IBaseDAO#findPage(com.cndatacom.common.orm.Page, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPageBySql(final Page<T> page, final String sql, final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createSQLQuery(sql, values);
		// REVIEW-ACCEPT q应当进行非空判断
		// 2012-01-03 createSQLQuery使用异常的方式处理，将直接向前台发送不可意料的异常提示
		//FIXED 在createSQLQuery 对返回值进行了判断
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
	 * User: 赵文强
	 * Date: Mar 9, 2011 
	 * Time: 3:24:32 PM
	 * @see com.cndatacom.common.orm.IBaseDAO#findPage(com.cndatacom.common.orm.Page, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPageBySqlMap(final Page<T> page, final String sql, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		Query q = createSQLQuery(sql, values);
		// REVIEW-ACCEPT q应当进行非空判断
		// 2012-01-03 createSQLQuery使用异常的方式处理，将直接向前台发送不可意料的异常提示
		//FIXED 在createSQLQuery 对返回值进行了判断
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
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);
		// REVIEW-ACCEPT q应当进行非空判断
		// 2012-01-03 createSQLQuery使用异常的方式处理，将直接向前台发送不可意料的异常提示
		//FIXED 在createSQLQuery 对返回值进行了判断
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
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

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
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		//hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getStart());
		q.setMaxResults(page.getLimit());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
		// REVIEW-ACCEPT page应当进行非空判断
		//hibernate的firstResult的序号从0开始
		//FIXED
		Assert.notNull(page, "page 不能为空！");
		c.setFirstResult(page.getStart());
		c.setMaxResults(page.getLimit());

		if (page.isOrderBySetted()) {
			
			Assert.notNull(page.getOrderBy(), "排序字段不能为空！");
			// REVIEW-ACCEPT orderByArray应当进行非空判断
			//FIXED
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');
			
			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

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
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String fromHql = hql;
		//select子句与order by子句会影响count查询,进行简单的排除.
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
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countSqlResult(final String sql, final Object... values) {
		String fromSql = sql;
		//select子句与order by子句会影响count查询,进行简单的排除.
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
		//select子句与order by子句会影响count查询,进行简单的排除.
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
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String fromHql = hql;
		//select子句与order by子句会影响count查询,进行简单的排除.
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
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
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
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	//-- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public List<T> findBy(final String propertyName, final Object value, final MatchType matchType) {
		Criterion criterion = buildPropertyFilterCriterion(propertyName, value, matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return find(criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return findPage(page, criterions);
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) {
	    list=new ArrayList<String>();
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if(!filter.isLeftJion()){
			}else{
				list.add(filter.getPropertyName());
			}
				if ( !filter.isMultiProperty()) { //只有一个属性需要比较的情况.
					Criterion criterion = buildPropertyFilterCriterion(generateAlias(null,filter.getPropertyName(),RandomGenerator.randomString(8)), filter.getPropertyValue(),
							filter.getMatchType());
					criterionList.add(criterion);
				} else {//包含多个属性需要比较的情况,进行or处理.
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

	// REVIEW-ACCEPT 增加方法注释
	  /**对传入的name 做 hibernate Criteria 处理
	 * @param criteria
	 * @param name
	 * @param aliasKey
	 * @return
	 */
	//FIXED
	private String generateAlias(Criteria criteria, String name, String aliasKey) {
		  // REVIEW-ACCEPT 传入参数需要进行非空判断
		 //FIXED
			Assert.notNull(name, "需要处理的name 不能为空！");
			
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
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildPropertyFilterCriterion(final String propertyName, final Object propertyValue,
			final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		try {
			//根据MatchType构造criterion
			
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
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}
