package com.xbwl.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.persistence.Id;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;

import com.xbwl.common.exception.ServiceException;

/**
 * ��ȡhibernateӳ���ļ�
 * @author Administrator
 *
 */
public class HibernateParter {

 private static Configuration hibernateConf;

 private static Configuration getHibernateConf() {
  if (hibernateConf == null) {
   // return new Configuration().configure();
   return new Configuration();
  }
  return hibernateConf;
 }

 private static PersistentClass getPersistentClass(Class clazz) {

  synchronized (HibernateParter.class) {
   PersistentClass pc = getHibernateConf().getClassMapping(
     clazz.getName());

   if (pc == null) {
    hibernateConf = getHibernateConf().addClass(clazz);
    pc = getHibernateConf().getClassMapping(clazz.getName());
   }
   return pc;
  }
 }

 /**
  * ��ȡʵ���Ӧ�ı���
  * @param clazz ʵ����
  * @return ����
  */
 public static String getTableName(Class clazz) {
  return getPersistentClass(clazz).getTable().getName();
 }

 /**
  * ��ȡʵ���Ӧ��������ֶ�����
  * @param clazz ʵ����
  * @return �����ֶ�����
  */
 public static String getPkColumnName(Class clazz) {
  Table table = getPersistentClass(clazz).getTable();
  return table.getPrimaryKey().getColumn(0).getName();
 }
 
 /**��ȡID��
 * @param clazz
 * @return
 */
public static String getPkPropertyName(Class clazz) {
	  Table table = getPersistentClass(clazz).getTable();
	  return table.getPrimaryKey().getColumn(0).getName();
	 }

 /**
  * ͨ��ʵ��������ԣ���ȡʵ�������Զ�Ӧ�ı��ֶ�����
  * @param clazz ʵ����
  * @param propertyName ��������
  * @return �ֶ�����
  */
 public static String getColumnName(Class clazz, String propertyName) {
  PersistentClass persistentClass = getPersistentClass(clazz);
  Property property = persistentClass.getProperty(propertyName);
  Iterator it = property.getColumnIterator();
  if (it.hasNext()) {   
   Column column= (Column)it.next();   
   return column.getName();
  }
  return null;
 }
 
	/**
	 * @param ob
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getIDValue(Object ob) throws SecurityException,
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
				javax.persistence.Column column=method.getAnnotation(javax.persistence.Column.class);
				return column.name();
			}
		}
    return null;
    
	}
 
}

