package com.xbwl.common.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 * @createTime 10:49:19 AM
 * @updateName Administrator
 * @updateTime 10:49:19 AM
 * 
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})  
public @interface XbwlInt {
	boolean autoDepart() default true;
	
	//�ж�service �����Ƿ���Ҫʱ�����֤
	boolean isCheck() default true;
}
