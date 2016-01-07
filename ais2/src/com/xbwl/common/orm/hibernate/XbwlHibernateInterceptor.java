package com.xbwl.common.orm.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.sql.DataSource;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.Assert;

import com.xbwl.common.utils.AnnotationUtils;
import com.xbwl.common.utils.Rate;
import com.xbwl.common.utils.ReflectionUtils;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.finance.Service.IBasTreatyChangeListService;

//REVIEW-ACCEPT 增加类注释
//FIXED
/**HIBERNATE 实体监控
 * @author Administrator
 *
 */
public class XbwlHibernateInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Resource(name="springContextHolder")
	private com.xbwl.common.utils.SpringContextHolder  springContextHolder ;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
			// REVIEW-ACCEPT 将不必要进行try的语句移出try块。
			//FIXED
		
			int departNum=getNumForArray("departName",propertyNames);
			int departIdNum=getNumForArray("departId",propertyNames);
				
			try {
				if(departNum!=0){
					User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
					Field field= ReflectionUtils.getDeclaredField(entity, propertyNames[departNum].toString());
					if(getAutoDepat(field)){
						ReflectionUtils.invokeSetterMethod(entity, "departName", user.get("rightDepart"));
						state[departNum]=user.get("rightDepart")+"";
					}
					
				}
				if(departIdNum!=0){
					Field field= ReflectionUtils.getDeclaredField(entity, propertyNames[departIdNum].toString());
					if(getAutoDepat(field)){
						User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
						ReflectionUtils.invokeSetterMethod(entity, "departId", Long.valueOf(user.get("bussDepart").toString()));
						state[departIdNum]=Long.valueOf(user.get("bussDepart")+"");
					}
				}
					
				} catch (SecurityException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} 
		
		return super.onSave(entity, id, state, propertyNames, types);
	}
	
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		try {
			Rate mobj = entity.getClass().getAnnotation(Rate.class);
			if(mobj!=null){
				String mapping=AnnotationUtils.getValue(
						mobj, "mapping").toString();
			
				IBasTreatyChangeListService basTreatyChangeListService= springContextHolder.getBean("basTreatyChangeListServiceImpl");
				basTreatyChangeListService.saveRecord(entity, mapping, true);
			}
		
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		super.onDelete(entity, id, state, propertyNames, types);
	}
	
	// REVIEW-ANN 增加方法注释
	public int getNumForArray(Object ob,Object[] obs){
		int i=0;
		for(Object obj:obs){
			if(obj.equals(ob)){
				return i;
			}else{
				i++;
			}
		}
		return 0;
	}

	


	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}
	
	
	/**
	 * @param ob
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static String getIDName(Object ob) throws SecurityException, NoSuchFieldException{
		Method[] methods= ob.getClass().getMethods();
		for(Method method:methods){
			Id id=method.getAnnotation(Id.class);
			if(id!=null){
				Column column=method.getAnnotation(Column.class);
				return column.name();
			}
		}
    return null;
    
	}
	

	
	protected boolean getAutoDepat(Field field){
		XbwlInt xbwlInt=field.getAnnotation(XbwlInt.class);	
		if(xbwlInt!=null){
			 return Boolean.valueOf(AnnotationUtils.getValue(xbwlInt, "autoDepart")+"");
		}
		
		return true;
	}
	
	
	
	
	

}
