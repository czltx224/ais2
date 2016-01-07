package com.xbwl.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *@author LiuHao
 *@time Aug 29, 2011 9:57:50 AM
 */
public class ReflectUntil {
	private static ReflectUntil until=new ReflectUntil();
	private ReflectUntil(){
		
	}
	public static ReflectUntil getInstance(){
		return until;
	}
	/**  
	    * java反射bean的get方法  
	    *   
	    * @param objectClass  
	    * @param fieldName  
	    * @return  
	    */  
	   public Method getGetMethod(Class objectClass, String fieldName) {   
	       StringBuffer sb = new StringBuffer();   
	       sb.append("get");   
	       sb.append(fieldName.substring(0, 1).toUpperCase());   
	       sb.append(fieldName.substring(1));   
	       try {   
	           return objectClass.getMethod(sb.toString());   
	       } catch (Exception e) {   
	       }   
	       return null;   
	   }  
	   /**  
	        * java反射bean的set方法  
	        *   
	        * @param objectClass  
	        * @param fieldName  
	        * @return  
	        */  
	       public Method getSetMethod(Class objectClass, String fieldName) {   
	           try {   
	               Class[] parameterTypes = new Class[1];   
	               Field field = objectClass.getDeclaredField(fieldName);   
	               parameterTypes[0] = field.getType();   
	               StringBuffer sb = new StringBuffer();   
	               sb.append("set");   
	               sb.append(fieldName.substring(0, 1).toUpperCase());   
	              sb.append(fieldName.substring(1));   
	               Method method = objectClass.getMethod(sb.toString(), parameterTypes);   
	               return method;   
	           } catch (Exception e) {   
	               e.printStackTrace();   
	           }   
	           return null;   
	       }  

}
