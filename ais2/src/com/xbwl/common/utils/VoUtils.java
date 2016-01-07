package com.xbwl.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

/**
 * VO的工具类
 * @author Administrator
 *
 */
public class VoUtils {
	
	private static DozerBeanMapper dozer=SpringContextHolder.getBean("dozer");
	
	@SuppressWarnings("unchecked")
	public  static<T>  List<Object> forPojoToVo(List<T> result,Class cla){ 
		List<Object> resultList=new ArrayList<Object>();
		for(T t:result){
			try {
				Object destObject = dozer.map(t,cla);
				resultList.add(destObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		return resultList;
		
	}

}
